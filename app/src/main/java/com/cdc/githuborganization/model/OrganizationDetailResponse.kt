package com.cdc.githuborganization.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrganizationDetailResponse(
  @Json(name = "html_url")
  val htmlUrl: String
)
