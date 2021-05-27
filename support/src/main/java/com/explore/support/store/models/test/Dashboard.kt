package com.explore.support.store.models.test

import com.google.gson.annotations.SerializedName

data class DashboardResponseEntity(
        var status: Int?,
        var limit: Int?,
    var data: List<TitleModelEntity>,
    var languages: List<LanguageModelEntity>,
    var notRatedUser: NotRatedUserEntity?
)

data class TitleModelEntity(
        var id: Int,
        var name: String,
        var count: Int,
        var color: String,
        var value: String,
        var contents: List<JobModelEntity>
)

data class JobModelEntity(
        val cost_status: String,
        val genres: String,
        val id: Int,
        val job_code: String,
        val language: String,
        val modified_date: String,
        val title: String
)

data class LanguageModelEntity(
        val genre: String ?=null,
        val genre_id: String ?=null,
        val language: String ?=null,
        val language_id: String ?=null,
        val parent_genre: String ?=null,
        val parent_id: Int ?=null,
        val status: String ?=null
)

data class NotRatedUserEntity(
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("user_name")
        var userName: String? = null
)