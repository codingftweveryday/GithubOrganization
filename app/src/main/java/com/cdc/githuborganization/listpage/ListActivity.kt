package com.cdc.githuborganization.listpage

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cdc.githuborganization.detail.DetailActivity
import com.cdc.githuborganization.model.OrganizationUI
import com.cdc.githuborganization.network.GithubAPIServiceFactory
import com.cdc.githuborganization.ui.theme.GithubOrganizationTheme
import com.cdc.githuborganization.widget.ListItemComposable
import io.reactivex.rxjava3.subjects.PublishSubject

const val ORGANIZATION_EXTRA = "organization_extra"

class ListActivity : ComponentActivity(), ListPresenter.Interface {

  private val eventPublish: PublishSubject<ListPageUIEvent> = PublishSubject.create()
  private val presenter = ListPresenter(GithubAPIServiceFactory.create(), eventPublish)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GithubOrganizationTheme {
        val viewModel = remember { presenter.viewModel }
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          OrganizationList(
            organizations = viewModel.state.organizations,
            onUrlClicked = {
              eventPublish.onNext(ListPageUIEvent.UrlClicked(it))
            },
            onTileClicked = { organization, enabled ->
              eventPublish.onNext(ListPageUIEvent.OrganizationClicked(organization, enabled))
            },
            onBottomReached = {
              eventPublish.onNext(ListPageUIEvent.BottomReached)
            }
          )
        }
      }
    }

    presenter.onAttach(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    presenter.onDetach()
  }

  override fun goToBrowser(url: String) {
    try {
      val i = Intent("android.intent.action.MAIN")
      i.component = ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main")
      i.addCategory("android.intent.category.LAUNCHER")
      i.data = Uri.parse(url)
      startActivity(i)
    } catch (e: ActivityNotFoundException) {
      displayError("Browser not installed")
    }
  }

  override fun displayError(message: String) {
    Toast.makeText(this, message, LENGTH_SHORT).show()
  }

  override fun goToDetailPage(organization: OrganizationUI) {
    val intent = Intent(this, DetailActivity::class.java).apply {
      putExtra(ORGANIZATION_EXTRA, organization)
      putExtra("abc", "abccc")
    }
    startActivity(intent)
  }
}

@Composable
fun OrganizationList(
  organizations: List<OrganizationUI>,
  onUrlClicked: (String) -> Unit,
  onTileClicked: (OrganizationUI, Boolean) -> Unit,
  onBottomReached: () -> Unit
) {
  val listState = rememberLazyListState()
  var selectedIndex by remember { mutableStateOf(-1) }

  LazyColumn(
    state = listState,
    contentPadding = PaddingValues(8.dp)
  ) {
    itemsIndexed(organizations) { index, organization ->
      ListItemComposable(
        modifier = Modifier.selectable(
          selected = selectedIndex == index,
          onClick = {
            onTileClicked(organization, false)
            selectedIndex = index
          }
        ),
        organizationUI = organization,
        onUrlClicked = onUrlClicked,
        selected = selectedIndex == index
      )
    }
  }

  listState.OnBottomReached {
    onBottomReached()
  }
}

@Composable
fun LazyListState.OnBottomReached(
  loadMore: () -> Unit
) {
  val shouldLoadMore = remember {
    derivedStateOf {

      val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

      lastVisibleItem.index == layoutInfo.totalItemsCount - 5
    }
  }

  LaunchedEffect(shouldLoadMore) {
    snapshotFlow { shouldLoadMore.value }
      .collect {
        if (it) loadMore()
      }
  }
}
