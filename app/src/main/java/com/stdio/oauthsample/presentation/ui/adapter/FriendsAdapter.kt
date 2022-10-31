package com.stdio.oauthsample.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.stdio.oauthsample.R
import com.stdio.oauthsample.databinding.ItemFriendBinding
import com.stdio.oauthsample.domain.models.Friend

class FriendsAdapter() :
    RecyclerView.Adapter<FriendsAdapter.CourseViewHolder>() {

    private var dataList = emptyList<Friend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemFriendBinding
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
            binding.tvName.text = holder.itemView.context.getString(
                R.string.full_name,
                friend.firstName,
                friend.lastName
            )
            Glide.with(holder.itemView.context)
                .load(friend.photo)
                .transform(CircleCrop())
                .into(binding.avatar)
        }
    }

    inner class CourseViewHolder(val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root)
}