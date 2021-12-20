package com.allana.bfaa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allana.bfaa.R
import com.allana.bfaa.model.SearchListItem
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_item_user.view.*

class  SearchUserAdapter(private val searchListItem: List<SearchListItem>) : RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder>() {

    private var onItemClickCallback: OnItemClickUserList? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickUserList) {
        this.onItemClickCallback = onItemClickCallback
    }

    class SearchUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.tv_username
        val avatar: CircleImageView = itemView.img_avatar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return SearchUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        val user = searchListItem[position]
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
        return searchListItem.size
    }

    interface OnItemClickUserList {
        fun onItemClicked(listUser: SearchListItem)
    }

}