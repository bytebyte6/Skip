package com.bytebyte6.skip

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bytebyte6.skip.data.Account
import com.bytebyte6.skip.databinding.ItemAccountBinding

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private val list = mutableListOf<Account>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(list: List<Account>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = list[position]
        holder.binding.tvAccount.text = entity.account
//        holder.binding.tvPassword.text = entity.password
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAccountBinding.bind(itemView)
    }
}