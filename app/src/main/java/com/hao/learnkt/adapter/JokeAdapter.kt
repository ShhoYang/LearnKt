package com.hao.learnkt.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hao.learnkt.R
import com.yhao.model.bean.Joke
import org.jetbrains.anko.find

/**
 * @author Yang Shihao
 */
class JokeAdapter(var datas: MutableList<Joke>?) : RecyclerView.Adapter<JokeAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_joke, parent, false))
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tvTitle.text = datas?.get(position)?.title
        holder.tvContent.text = datas?.get(position)?.text
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.find(R.id.tvTitle)
        val tvContent: TextView = itemView.find(R.id.tvContent)
    }
}