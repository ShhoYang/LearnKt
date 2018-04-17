package com.hao.learnkt.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.lzyzsd.circleprogress.DonutProgress
import com.hao.learnkt.R
import com.hao.learnkt.down.ProgressDownload
import com.hao.learnkt.down.ProgressListener
import com.hao.learnkt.utils.DisplayUtil
import com.yhao.model.bean.Gif
import org.jetbrains.anko.find
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * @author Yang Shihao
 */
class GifAdapter(var datas: MutableList<Gif>?, val recyclerView: RecyclerView) : RecyclerView.Adapter<GifAdapter.VH>() {

    var mHeights: MutableMap<Int, Int> = HashMap()
    val mRequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    var gifDrawable: GifDrawable? = null

    init {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                gifPause()
            }
        })
    }

    fun gifPause() {
        if (gifDrawable != null) {
            gifDrawable!!.pause()
        }
    }

    override fun getItemCount(): Int = datas?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(holder.gifImageView)
                .asBitmap()
                .transition(BitmapTransitionOptions().crossFade(800))
                .apply(mRequestOptions)
                .listener(object : RequestListener<Bitmap> {
                    override fun onResourceReady(resource: Bitmap, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        val w: Int = DisplayUtil.getScreenHeight(holder.gifImageView.context)
                        val h: Int = (resource.height.toFloat() * w / resource.width).toInt()
                        holder.gifImageView.layoutParams.height = h
                        mHeights.put(position, h)

                        holder.textView.visibility = View.VISIBLE
                        holder.textView.text = datas?.get(position)?.title
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                }).into(holder.gifImageView)
        if (mHeights.containsKey(position)) {
            holder.gifImageView.layoutParams.height = mHeights[position]!!
            holder.textView.visibility = View.VISIBLE
            holder.textView.text = datas?.get(position)?.title
        }
        holder.gifImageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                gifPause()
                ProgressDownload.downPic(datas?.get(position)?.img!!, object : ProgressListener {
                    override fun onProgress(readBytes: Long, totalBytes: Long, done: Boolean) {
                        holder.progressBar.post {
                            holder.progressBar.visibility = View.VISIBLE
                            holder.progressBar.progress = readBytes.toFloat() / totalBytes * 100
                        }
                    }

                    override fun onSave(path: String) {
                        gifDrawable = GifDrawable(path)
                        holder.gifImageView.post {
                            holder.progressBar.visibility = View.INVISIBLE
                            holder.gifImageView.setImageDrawable(gifDrawable)
                        }
                    }
                })
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false))
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifImageView: GifImageView = itemView.find(R.id.gifImageView)
        val textView: TextView = itemView.find(R.id.textView)
        val progressBar: DonutProgress = itemView.find(R.id.progressBar)
    }
}
