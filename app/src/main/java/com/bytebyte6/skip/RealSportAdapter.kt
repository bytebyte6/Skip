package com.bytebyte6.skip

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bytebyte6.skip.data.Log
import com.bytebyte6.skip.data.RealSport
import com.bytebyte6.skip.databinding.ItemLogBinding
import com.bytebyte6.skip.databinding.ItemRealSportBinding

class RealSportAdapter : RecyclerView.Adapter<RealSportAdapter.ViewHolder>() {

    private val list = mutableListOf<RealSport>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<RealSport>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_real_sport, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = list[position]
        holder.binding.text.text = entity.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRealSportBinding.bind(itemView)
    }
}