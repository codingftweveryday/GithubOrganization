package com.cdc.githuborganization.listpage

import com.cdc.githuborganization.model.OrganizationUI
import com.cdc.githuborganization.network.GithubOrganizationAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ListPresenter(
  private val apiService: GithubOrganizationAPIService,
  private val eventPublish: Observable<ListPageUIEvent>,
) {

  interface Interface {
    fun goToDetailPage(organization: OrganizationUI)
    fun goToBrowser(url: String)
    fun displayError(message: String)
  }

  val viewModel = ListViewModel()

  private lateinit var page: Interface

  private val compositeDisposable = CompositeDisposable()

  private var since: Int = 0

  private var loading: Boolean = false

  private var reachedEnd: Boolean = false

  fun onAttach(page: Interface) {
    this.page = page

    subscribeToEvents()
    loadOrganizations()
  }

  private fun loadOrganizations() {
    if (loading || reachedEnd) return

    apiService.getOrganizations(since)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe { loading = true }
      .doFinally { loading = false }
      .subscribe(
        {
          viewModel.updateOrganizations(it)

          it.forEachIndexed { index, organization ->
            loadOrganizationDetails(index, organization.reposUrl)
          }

          if (it.isEmpty()) {
            reachedEnd = true
          } else {
            since = it.last().id
          }
        }, {
          page.displayError("Error loading organizations")
        }
      ).let { compositeDisposable.add(it) }
  }

  private fun loadOrganizationDetails(index: Int, url: String) {
    apiService.getOrganizationDetails(url)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        viewModel.updateGithubLink(index, it.htmlUrl)
      }, {
        page.displayError("Error loading organization details $url")
      })
      .let { compositeDisposable.add(it) }
  }

  private fun subscribeToEvents() {
    eventPublish.subscribe(
      { uiEvent ->
        when (uiEvent) {
          is ListPageUIEvent.OrganizationClicked -> {
            page.goToDetailPage(uiEvent.organization)
          }
          is ListPageUIEvent.UrlClicked -> page.goToBrowser(uiEvent.url)
          ListPageUIEvent.BottomReached -> loadOrganizations()
        }
      },
      { error(it) }
    )
  }

  fun onDetach() {
    compositeDisposable.dispose()
  }
}
