package com.allana.bfaa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allana.bfaa.R
import com.allana.bfaa.database.UserFavorite
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_user_favorite.view.*

class UserFavoriteAdapter(private val userFavList: List<UserFavorite>) : RecyclerView.Adapter<UserFavoriteAdapter.UserFavoriteViewHolder>() {

    private var onItemClickCallback: OnItemClickUserFavorite? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickUserFavorite) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class UserFavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind (userFav: UserFavorite) {
            with(itemView) {
                tv_username.text = userFav.username
                tv_name.text = userFav.name
                tv_location.text = userFav.location ?: context.getString(R.string.default_text_location)
                tv_company.text = userFav.company ?: context.getString(R.string.default_text_company)
                img_avatar.let {
                    Glide.with(itemView.context)
                        .load(userFav.avatar_url)
                        .into(it)
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(userFav)
                }
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFavoriteAdapter.UserFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user_favorite, parent, false)
        return UserFavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFavoriteAdapter.UserFavoriteViewHolder, position: Int) {
        holder.bind(userFavList[position])
    }

    override fun getItemCount(): Int {
        return userFavList.size
    }

    interface OnItemClickUserFavorite {
        fun onItemClicked(userFavList: UserFavorite)
    }
}