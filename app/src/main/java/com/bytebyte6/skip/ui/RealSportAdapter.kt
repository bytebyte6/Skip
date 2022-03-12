package com.bytebyte6.skip.ui

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bytebyte6.skip.R
import com.bytebyte6.skip.data.RealSport
import com.bytebyte6.skip.data.TrainingWay
import com.bytebyte6.skip.databinding.ItemRealSportBinding
import com.bytebyte6.skip.getTimeString

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
        val ctx = holder.binding.root.context
        val checkBox = holder.binding.checkBox
        if (entity.trainingWay == TrainingWay.BY_GROUP) {
            val string = ctx.getString(
                R.string.name_s_group_d_count_d,
                entity.name,
                entity.group,
                entity.count,
                ctx.getTimeString(entity.groupRestDuration)
            )
            checkBox.text = Html.fromHtml(string)
        } else {
            val string = ctx.getString(
                R.string.name_s_duration_s,
                entity.name,
                ctx.getTimeString(entity.duration),
                ctx.getTimeString(entity.restDuration)
            )
            checkBox.text = Html.fromHtml(string)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRealSportBinding.bind(itemView)
    }
}