package com.example.githubrepousers.app.models

import com.beust.klaxon.*
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

data class RepoResponse (
    @SerializedName("total_count")
    val totalCount: Long? = null,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    val items: List<Repo>? = null
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<RepoResponse>(json)
    }
}

data class Repo (
    val id: Long? = null,

    @SerializedName("node_id")
    val nodeID: String? = null,

    val name: String? = null,

    @SerializedName("full_name")
    val fullName: String? = null,

    val owner: Owner? = null,
    val private: Boolean? = null,

    @SerializedName("html_url")
    val htmlURL: String? = null,

    val description: String? = null,
    val fork: Boolean? = null,
    val url: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("pushed_at")
    val pushedAt: String? = null,

    val homepage: String? = null,
    val size: Long? = null,

    @SerializedName("stargazers_count")
    val stargazersCount: Long? = null,

    @SerializedName("watchers_count")
    val watchersCount: Long? = null,

    val language: String? = null,

    @SerializedName("forks_count")
    val forksCount: Long? = null,

    @SerializedName("open_issues_count")
    val openIssuesCount: Long? = null,

    @SerializedName("master_branch")
    val masterBranch: String? = null,

    @SerializedName("default_branch")
    val defaultBranch: String? = null,

    val score: Long? = null,

    @SerializedName("archive_url")
    val archiveURL: String? = null,

    @SerializedName("assignees_url")
    val assigneesURL: String? = null,

    @SerializedName("blobs_url")
    val blobsURL: String? = null,

    @SerializedName("branches_url")
    val branchesURL: String? = null,

    @SerializedName("collaborators_url")
    val collaboratorsURL: String? = null,

    @SerializedName("comments_url")
    val commentsURL: String? = null,

    @SerializedName("commits_url")
    val commitsURL: String? = null,

    @SerializedName("compare_url")
    val compareURL: String? = null,

    @SerializedName("contents_url")
    val contentsURL: String? = null,

    @SerializedName("contributors_url")
    val contributorsURL: String? = null,

    @SerializedName("deployments_url")
    val deploymentsURL: String? = null,

    @SerializedName("downloads_url")
    val downloadsURL: String? = null,

    @SerializedName("events_url")
    val eventsURL: String? = null,

    @SerializedName("forks_url")
    val forksURL: String? = null,

    @SerializedName("git_commits_url")
    val gitCommitsURL: String? = null,

    @SerializedName("git_refs_url")
    val gitRefsURL: String? = null,

    @SerializedName("git_tags_url")
    val gitTagsURL: String? = null,

    @SerializedName("git_url")
    val gitURL: String? = null,

    @SerializedName("issue_comment_url")
    val issueCommentURL: String? = null,

    @SerializedName("issue_events_url")
    val issueEventsURL: String? = null,

    @SerializedName("issues_url")
    val issuesURL: String? = null,

    @SerializedName("keys_url")
    val keysURL: String? = null,

    @SerializedName("labels_url")
    val labelsURL: String? = null,

    @SerializedName("languages_url")
    val languagesURL: String? = null,

    @SerializedName("merges_url")
    val mergesURL: String? = null,

    @SerializedName("milestones_url")
    val milestonesURL: String? = null,

    @SerializedName("notifications_url")
    val notificationsURL: String? = null,

    @SerializedName("pulls_url")
    val pullsURL: String? = null,

    @SerializedName("releases_url")
    val releasesURL: String? = null,

    @SerializedName("ssh_url")
    val sshURL: String? = null,

    @SerializedName("stargazers_url")
    val stargazersURL: String? = null,

    @SerializedName("statuses_url")
    val statusesURL: String? = null,

    @SerializedName("subscribers_url")
    val subscribersURL: String? = null,

    @SerializedName("subscription_url")
    val subscriptionURL: String? = null,

    @SerializedName("tags_url")
    val tagsURL: String? = null,

    @SerializedName("teams_url")
    val teamsURL: String? = null,

    @SerializedName("trees_url")
    val treesURL: String? = null,

    @SerializedName("clone_url")
    val cloneURL: String? = null,

    @SerializedName("mirror_url")
    val mirrorURL: String? = null,

    @SerializedName("hooks_url")
    val hooksURL: String? = null,

    @SerializedName("svn_url")
    val svnURL: String? = null,

    val forks: Long? = null,

    @SerializedName("open_issues")
    val openIssues: Long? = null,

    val watchers: Long? = null,

    @SerializedName("has_issues")
    val hasIssues: Boolean? = null,

    @SerializedName("has_projects")
    val hasProjects: Boolean? = null,

    @SerializedName("has_pages")
    val hasPages: Boolean? = null,

    @SerializedName("has_wiki")
    val hasWiki: Boolean? = null,

    @SerializedName("has_downloads")
    val hasDownloads: Boolean? = null,

    val archived: Boolean? = null,
    val disabled: Boolean? = null,
    val visibility: String? = null,
    val license: License? = null
)

data class License (
    val key: String? = null,
    val name: String? = null,
    val url: String? = null,

    @SerializedName("spdx_id")
    val spdxID: String? = null,

    @SerializedName("node_id")
    val nodeID: String? = null,

    @SerializedName("html_url")
    val htmlURL: String? = null
)

data class Owner (
    val login: String? = null,
    val id: Long? = null,

    @SerializedName("node_id")
    val nodeID: String? = null,

    @SerializedName("avatar_url")
    val avatarURL: String? = null,

    @SerializedName("gravatar_id")
    val gravatarID: String? = null,

    val url: String? = null,

    @SerializedName("received_events_url")
    val receivedEventsURL: String? = null,

    val type: String? = null,

    @SerializedName("html_url")
    val htmlURL: String? = null,

    @SerializedName("followers_url")
    val followersURL: String? = null,

    @SerializedName("following_url")
    val followingURL: String? = null,

    @SerializedName("gists_url")
    val gistsURL: String? = null,

    @SerializedName("starred_url")
    val starredURL: String? = null,

    @SerializedName("subscriptions_url")
    val subscriptionsURL: String? = null,

    @SerializedName("organizations_url")
    val organizationsURL: String? = null,

    @SerializedName("repos_url")
    val reposURL: String? = null,

    @SerializedName("events_url")
    val eventsURL: String? = null,

    @SerializedName("site_admin")
    val siteAdmin: Boolean? = null
)
