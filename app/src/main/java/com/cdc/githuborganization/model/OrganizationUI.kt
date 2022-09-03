package com.cdc.githuborganization.model

data class OrganizationUI(
  val image: String? = null,
  val name: String,
  val description: String? = null,
  val githubUrl: String = ""
)
