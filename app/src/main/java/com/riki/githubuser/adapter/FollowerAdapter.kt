package com.riki.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riki.githubuser.R
import com.riki.githubuser.model.FollowerModel
import java.util.*

class FollowerAdapter(private val listUser: ArrayList<FollowerModel>) :
    RecyclerView.Adapter<FollowerAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_follow, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val users = listUser[position]

        Glide.with(holder.itemView.context)
            .load(users.avatarUrl)
            .apply(RequestOptions().override(55, 55))
            .into(holder.avatarUser)

        holder.tvName.text = "@${users.login}"

    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatarUser: ImageView = itemView.findViewById(R.id.avatar_user)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}