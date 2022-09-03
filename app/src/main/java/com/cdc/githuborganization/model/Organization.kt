package com.cdc.githuborganization.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Organization(
  val id: Int,

  @Json(name = "avatar_url")
  val image: String,

  @Json(name = "login")
  val name: String,

  // Used to fetch organization details
  @Json(name = "url")
  val reposUrl: String,

  val description: String? = null
)
