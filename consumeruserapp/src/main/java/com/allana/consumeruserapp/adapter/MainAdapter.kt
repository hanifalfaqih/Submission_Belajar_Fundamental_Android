package com.allana.consumeruserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allana.consumeruserapp.R
import com.allana.consumeruserapp.model.UserFavorite
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_user_favorite.view.*

class MainAdapter(private val userFavorite: List<UserFavorite>): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserFavorite) {
            with(itemView) {
                tv_username.text = user.username
                tv_name.text = user.name
                tv_location.text = user.location ?: context.getString(R.string.default_text_location)
                tv_company.text = user.company ?: context.getString(R.string.default_text_company)
                img_avatar.let {
                    Glide.with(itemView.context)
                        .load(user.avatar_url)
                        .into(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user_favorite, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(userFavorite[position])
    }

    override fun getItemCount(): Int {
        return userFavorite.size
    }
}