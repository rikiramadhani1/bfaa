package com.riki.githubuser.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = 0,
    @ColumnInfo(name = "login")
    var login: String? = null,
    @ColumnInfo(name = "repos_url")
    var repos_url: String? = null,
    @ColumnInfo(name = "avatar_url")
    var html_url: String? = null,
    @ColumnInfo(name = "html_url")
    var avatar_url: String? = null,
    @ColumnInfo(name = "company")
    var company: String? = null,
    @ColumnInfo(name = "location")
    var location: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "isFav")
    var isFav:Boolean? = null
) : Parcelable