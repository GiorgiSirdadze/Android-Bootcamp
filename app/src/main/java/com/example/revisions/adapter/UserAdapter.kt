package com.example.revisions.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.revisions.R
import com.example.revisions.data.UserEntity
import com.example.revisions.databinding.UserItemBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: List<UserEntity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: List<UserEntity>) {
        userList = users
        notifyDataSetChanged()
    }

    class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.binding.apply {
            userName.text = "${user.firstName} ${user.lastName}"
            userAbout.text = user.about ?: "No details available"

            Glide.with(userAvatar.context)
                .load(user.avatar)
                .placeholder(R.drawable.badge_background)
                .error(R.drawable.badge_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userAvatar)

            val context = userStatus.context
            when {
                user.activationStatus <= 0 -> {
                    userStatus.text = "Not Activated"
                    userStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                }
                user.activationStatus.toInt() == 1 -> {
                    userStatus.text = "Online"
                    userStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
                }
                user.activationStatus.toInt() == 2 -> {
                    userStatus.text = "Active a few minutes ago"
                    userStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark))
                }
                user.activationStatus.toInt() in 3..22 -> {
                    userStatus.text = "Active a few hours ago"
                    userStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
                }
                else -> {
                    userStatus.text = "Inactive for a long time"
                    userStatus.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                }
            }
        }
    }

    override fun getItemCount(): Int = userList.size
}
