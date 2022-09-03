package com.cdc.githuborganization.listpage

import com.cdc.githuborganization.model.OrganizationUI

sealed interface ListPageUIEvent {
  data class OrganizationClicked(val organization: OrganizationUI, val enabled: Boolean) :
    ListPageUIEvent

  data class UrlClicked(val url: String) : ListPageUIEvent

  object BottomReached : ListPageUIEvent
}