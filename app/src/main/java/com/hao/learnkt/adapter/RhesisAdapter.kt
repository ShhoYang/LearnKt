package com.hao.learnkt.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hao.learnkt.R
import com.yhao.model.bean.Rhesis
import org.jetbrains.anko.find

/**
 * @author Yang Shihao
 */
class RhesisAdapter(val datas: MutableList<Rhesis>?) : RecyclerView.Adapter<RhesisAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_rhesis, parent, false))
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var rhesis = datas?.get(position)

        holder.textView.text = rhesis?.english + "\n\n" + rhesis?.chinese
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.find(R.id.tvContent)
    }
}