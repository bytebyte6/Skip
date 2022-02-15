package com.bytebyte6.skip

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bytebyte6.skip.data.Log
import com.bytebyte6.skip.databinding.ItemLogBinding

class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    private val list = mutableListOf<Log>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<Log>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_log, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = list[position]
        holder.binding.tvLog.text = entity.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLogBinding.bind(itemView)
    }
}