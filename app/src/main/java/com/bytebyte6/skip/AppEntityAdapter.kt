package com.bytebyte6.skip

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.bytebyte6.skip.databinding.ItemAppEntityBinding

class AppEntityAdapter : RecyclerView.Adapter<AppEntityAdapter.ViewHolder>() {

    private val list = mutableListOf<AppEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<AppEntity>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_entity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = list[position]
        holder.binding.tvPackageName.text = entity.packageName
        holder.binding.tvCount.text = entity.count.toString()
        holder.binding.tvIsClickable.text = entity.isClickable.toString()
        holder.binding.tvParentClickable.text = entity.parentIsClickable.toString()
        holder.binding.tvText.text = entity.text
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAppEntityBinding.bind(itemView)
    }
}