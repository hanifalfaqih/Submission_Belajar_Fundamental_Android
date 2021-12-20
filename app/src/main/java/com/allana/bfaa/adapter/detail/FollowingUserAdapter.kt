package com.allana.bfaa.adapter.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allana.bfaa.R
import com.allana.bfaa.model.detail.ResponseFollowingUser
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_item_user.view.*

class FollowingUserAdapter(private val responseFollowingUser: List<ResponseFollowingUser>) : RecyclerView.Adapter<FollowingUserAdapter.FollowingUserViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FollowingUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.tv_username
        val avatar: CircleImageView = itemView.img_avatar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return FollowingUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingUserViewHolder, position: Int) {
        val user = responseFollowingUser[position]
        holder.username.text = user.login
        holder.avatar.let {
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .into(it)
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)
        }

    }

    override fun getItemCount(): Int {
        return responseFollowingUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(listUser: ResponseFollowingUser)
    }

}