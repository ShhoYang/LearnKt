package com.hao.learnkt.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.hao.learnkt.R
import com.hao.learnkt.utils.DisplayUtil
import com.yhao.model.bean.Huaban
import com.yhao.model.bean.Joke
import org.jetbrains.anko.find

/**
 * @author Yang Shihao
 */
class PicAdapter(var datas: MutableList<Huaban>?) : RecyclerView.Adapter<PicAdapter.VH>() {

    private val options: RequestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
    private val mHeights: MutableMap<Int, Int> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_pic, parent, false))
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(holder.ivPic.context)
                .asBitmap()
                .transition(BitmapTransitionOptions().crossFade(800))
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        val w: Int = DisplayUtil.getScreenWidth(holder.ivPic.context) / 2
                        val h: Int = (w.toFloat() * resource.height / resource.width).toInt()
                        holder.ivPic.layoutParams.height = h
                        mHeights.put(position, h)
                        return false
                    }

                })
        if (mHeights.containsKey(position)) {
            holder.ivPic.layoutParams.height = mHeights[position]!!
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPic: ImageView = itemView.find(R.id.ivPic)
    }
}