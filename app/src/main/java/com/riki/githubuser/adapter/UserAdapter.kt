package com.riki.githubuser.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riki.githubuser.data.local.UserEntity
import com.riki.githubuser.databinding.ItemUserBinding
import com.riki.githubuser.model.UserModel
import com.riki.githubuser.ui.DetailUserActivity
import android.view.View

import android.R
import android.net.Uri

import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent.ACTION_VIEW

import android.content.Intent.CATEGORY_BROWSABLE
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity






















class UserAdapter(private val listUser: List<UserModel>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(user: UserModel) {
            with(binding) {
                tvUsername.text = "@${user.login}"
                Glide.with(itemView.context).load(user.avatarUrl).circleCrop().into(avatarUser)

                buttonId.setOnClickListener {
                    val url = user.htmlUrl
                    val browserIntent = Intent(ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }

            }

            itemView.setOnClickListener {
                val userEntity = UserEntity(
                    id = user.id,
                    login = user.login,
                    avatar_url = user.avatarUrl,
                    html_url = user.htmlUrl
                )

                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, userEntity)
                itemView.context.startActivity(intent)
            }

        }
    }
}