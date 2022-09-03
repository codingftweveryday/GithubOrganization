package com.cdc.githuborganization.network

import com.cdc.githuborganization.model.Organization
import com.cdc.githuborganization.model.OrganizationDetailResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubOrganizationAPIService {
  @GET("/organizations")
  fun getOrganizations(
    @Query("since") since: Int
  ): Single<List<Organization>>

  @GET
  fun getOrganizationDetails(@Url url: String): Single<OrganizationDetailResponse>
}