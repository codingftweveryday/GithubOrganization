package com.cdc.githuborganization.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cdc.githuborganization.listpage.ORGANIZATION_EXTRA
import com.cdc.githuborganization.model.OrganizationUI
import com.cdc.githuborganization.ui.theme.GithubOrganizationTheme
import com.cdc.githuborganization.widget.ListItemComposable

class DetailActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val organization = intent.getParcelableExtra(ORGANIZATION_EXTRA) as OrganizationUI?

    setContent {
      GithubOrganizationTheme {
        organization?.let {
          ListItemComposable(
            organizationUI = organization,
            onUrlClicked = {},
            selected = false
          )
        }
      }
    }
  }
}