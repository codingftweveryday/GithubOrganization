package com.cdc.githuborganization.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockServer: Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val url = chain.request().url
    Log.d("TAG", "url=$url")
    val response = when {
      url.encodedPath.contains("organizations") -> {
        organizationsMock()
      }
      else -> {
        organizationsDetailsMock()
      }
    }
    return Response.Builder()
      .code(200)
      .message(response)
      .request(chain.request())
      .protocol(Protocol.HTTP_1_1)
      .body(ResponseBody.create("application/json".toMediaTypeOrNull(), response.toByteArray()))
      .addHeader("content-type", "application/json")
      .build()
  }

  private fun organizationsMock(): String {
    return "[\n" +
        "  {\n" +
        "    \"login\": \"errfree\",\n" +
        "    \"id\": 44,\n" +
        "    \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjQ0\",\n" +
        "    \"url\": \"https://api.github.com/orgs/errfree\",\n" +
        "    \"repos_url\": \"https://api.github.com/orgs/errfree/repos\",\n" +
        "    \"events_url\": \"https://api.github.com/orgs/errfree/events\",\n" +
        "    \"hooks_url\": \"https://api.github.com/orgs/errfree/hooks\",\n" +
        "    \"issues_url\": \"https://api.github.com/orgs/errfree/issues\",\n" +
        "    \"members_url\": \"https://api.github.com/orgs/errfree/members{/member}\",\n" +
        "    \"public_members_url\": \"https://api.github.com/orgs/errfree/public_members{/member}\",\n" +
        "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/44?v=4\",\n" +
        "    \"description\": null\n" +
        "  },\n" +
        "  {\n" +
        "    \"login\": \"engineyard\",\n" +
        "    \"id\": 81,\n" +
        "    \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjgx\",\n" +
        "    \"url\": \"https://api.github.com/orgs/engineyard\",\n" +
        "    \"repos_url\": \"https://api.github.com/orgs/engineyard/repos\",\n" +
        "    \"events_url\": \"https://api.github.com/orgs/engineyard/events\",\n" +
        "    \"hooks_url\": \"https://api.github.com/orgs/engineyard/hooks\",\n" +
        "    \"issues_url\": \"https://api.github.com/orgs/engineyard/issues\",\n" +
        "    \"members_url\": \"https://api.github.com/orgs/engineyard/members{/member}\",\n" +
        "    \"public_members_url\": \"https://api.github.com/orgs/engineyard/public_members{/member}\",\n" +
        "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/81?v=4\",\n" +
        "    \"description\": \"We're an agile team building new products for the web. And oh boy, we're kind of good at it. Want to work with us?\"\n" +
        "  }" +
        "]"
  }

  private fun organizationsDetailsMock(): String {
    return "{\n" +
        "  \"login\": \"errfree\",\n" +
        "  \"id\": 44,\n" +
        "  \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjQ0\",\n" +
        "  \"url\": \"https://api.github.com/orgs/errfree\",\n" +
        "  \"repos_url\": \"https://api.github.com/orgs/errfree/repos\",\n" +
        "  \"events_url\": \"https://api.github.com/orgs/errfree/events\",\n" +
        "  \"hooks_url\": \"https://api.github.com/orgs/errfree/hooks\",\n" +
        "  \"issues_url\": \"https://api.github.com/orgs/errfree/issues\",\n" +
        "  \"members_url\": \"https://api.github.com/orgs/errfree/members{/member}\",\n" +
        "  \"public_members_url\": \"https://api.github.com/orgs/errfree/public_members{/member}\",\n" +
        "  \"avatar_url\": \"https://avatars.githubusercontent.com/u/44?v=4\",\n" +
        "  \"description\": null,\n" +
        "  \"is_verified\": false,\n" +
        "  \"has_organization_projects\": true,\n" +
        "  \"has_repository_projects\": true,\n" +
        "  \"public_repos\": 2,\n" +
        "  \"public_gists\": 0,\n" +
        "  \"followers\": 0,\n" +
        "  \"following\": 0,\n" +
        "  \"html_url\": \"https://github.com/errfree\",\n" +
        "  \"created_at\": \"2008-01-24T02:08:37Z\",\n" +
        "  \"updated_at\": \"2020-05-13T06:35:19Z\",\n" +
        "  \"type\": \"Organization\"\n" +
        "}"
  }
}