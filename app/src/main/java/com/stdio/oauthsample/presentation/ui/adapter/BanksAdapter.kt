package com.stdio.oauthsample.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stdio.oauthsample.databinding.ItemBankBinding
import com.stdio.oauthsample.domain.models.Friend

class BanksAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<BanksAdapter.CourseViewHolder>() {

    private var dataList = emptyList<Friend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemBankBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun setDataList(dataList: List<Friend>) {
        this.dataList = dataList
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val friend = dataList[position]
            binding.tvName.text = friend.firstName
            binding.btnConnect.setOnClickListener {
                listener.invoke(friend.id)
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemBankBinding) :
        RecyclerView.ViewHolder(binding.root)
}