package com.example.githubrepousers.app.models

import com.beust.klaxon.*
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

data class UsersResponse (
    @SerializedName("total_count")
    val totalCount: Long? = null,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    val items: List<User>? = null
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<UsersResponse>(json)
    }
}

data class User (
    val login: String? = null,
    val id: Long? = null,
    val name: String? = null,
    val bio: String? = null,
    val location: String? = null,

    @SerializedName("node_id")
    val nodeID: String? = null,

    @SerializedName("avatar_url")
    val avatarURL: String? = null,

    @SerializedName("gravatar_id")
    val gravatarID: String? = null,

    val url: String? = null,

    val followers: Long? = null,

    val following: Long? = null,

    @SerializedName("html_url")
    val htmlURL: String? = null,

    @SerializedName("followers_url")
    val followersURL: String? = null,

    @SerializedName("subscriptions_url")
    val subscriptionsURL: String? = null,

    @SerializedName("organizations_url")
    val organizationsURL: String? = null,

    @SerializedName("repos_url")
    val reposURL: String? = null,

    @SerializedName("received_events_url")
    val receivedEventsURL: String? = null,

    val type: String? = null,
    val score: Long? = null,

    @SerializedName("following_url")
    val followingURL: String? = null,

    @SerializedName("gists_url")
    val gistsURL: String? = null,

    @SerializedName("starred_url")
    val starredURL: String? = null,

    @SerializedName("events_url")
    val eventsURL: String? = null,

    @SerializedName("site_admin")
    val siteAdmin: Boolean? = null
)
