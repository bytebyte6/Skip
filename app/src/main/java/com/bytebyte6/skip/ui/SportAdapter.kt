package com.bytebyte6.skip.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bytebyte6.skip.R
import com.bytebyte6.skip.data.Sport
import com.bytebyte6.skip.data.TrainingWay
import com.bytebyte6.skip.databinding.ItemSportByGroupBinding
import com.bytebyte6.skip.databinding.ItemSportByTimeBinding
import com.bytebyte6.skip.getTimeString

class SportAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = mutableListOf<Sport>()

    override fun getItemViewType(position: Int): Int {
        return when (list[position].trainingWay) {
            TrainingWay.BY_TIME -> R.layout.item_sport_by_time
            else -> R.layout.item_sport_by_group
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_sport_by_time -> TimeViewHolder(view)
            else -> GroupViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sport = list[position]
        holder.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context, AddSportActivity::class.java).apply {
                putExtra(AddSportActivity.KEY_ID,sport.id)
            })
        }
        when (holder) {
            is TimeViewHolder -> {
                initByTime(holder,sport)
            }
            is GroupViewHolder -> {
                initByGroup(holder,sport)
            }
        }
    }

    private fun initByTime(holder: TimeViewHolder, sport: Sport) {
        holder.binding.run {
            tvName.text = root.context.getString(R.string.sport_name_s,sport.name)
            tvMinDuration.text = root.context.getString(R.string.duration_min_s,root.context.getTimeString(sport.minDuration))
            tvMaxDuration.text = root.context.getString(R.string.duration_max_s,root.context.getTimeString(sport.maxDuration))
            tvRestDuration.text = root.context.getString(R.string.rest_duration_s,root.context.getTimeString(sport.restDuration))
        }
    }

    private fun initByGroup(holder: GroupViewHolder, sport: Sport) {
        holder.binding.run {
            tvName.text = root.context.getString(R.string.sport_name_s,sport.name)
            tvMinGroup.text = root.context.getString(R.string.min_group_d,(sport.minGroup))
            tvMaxGroup.text = root.context.getString(R.string.max_group_d,(sport.maxGroup))
            tvMinCount.text = root.context.getString(R.string.min_count_d,(sport.minCount))
            tvMaxCount.text = root.context.getString(R.string.max_count_d,(sport.maxCount))
            tvRestDuration.text = root.context.getString(
                R.string.rest_duration_s,
                root.context.getTimeString(sport.restDuration)
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(sports: List<Sport>) {
        list.clear()
        list.addAll(sports)
        notifyDataSetChanged()
    }

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSportByTimeBinding.bind(itemView)
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSportByGroupBinding.bind(itemView)
    }
}