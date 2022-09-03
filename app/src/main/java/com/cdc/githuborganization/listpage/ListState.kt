package com.cdc.githuborganization.listpage

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.cdc.githuborganization.model.OrganizationUI

data class ListState(
  val organizations: SnapshotStateList<OrganizationUI> = mutableStateListOf(),
)
