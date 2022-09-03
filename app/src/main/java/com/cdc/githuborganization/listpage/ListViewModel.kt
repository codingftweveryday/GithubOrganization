package com.cdc.githuborganization.listpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cdc.githuborganization.model.Organization
import com.cdc.githuborganization.model.OrganizationUI
import io.reactivex.rxjava3.subjects.PublishSubject

class ListViewModel {
  var state by mutableStateOf(ListState())
    private set

  fun updateOrganizations(organizations: List<Organization>) {
    val organizationsUI = organizations.map {
      OrganizationUI(
        image = it.image,
        name = it.name,
        description = it.description,
      )
    }
    state.organizations.addAll(organizationsUI)
  }

  fun updateGithubLink(index: Int, githubUrl: String) {
    state.organizations[index] = state.organizations[index].copy(githubUrl = githubUrl)
  }
}
