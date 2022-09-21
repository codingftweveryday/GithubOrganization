package com.cdc.githuborganization.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganizationUI(
  val image: String? = null,
  val name: String,
  val description: String? = null,
  val githubUrl: String = ""
): Parcelable
