package com.cdc.githuborganization.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cdc.githuborganization.model.OrganizationUI

@Composable
fun ListItemComposable(
  modifier: Modifier = Modifier,
  organizationUI: OrganizationUI,
  onUrlClicked: (String) -> Unit,
  selected: Boolean,
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
      .alpha(if (selected) 1f else 0.5f),
    elevation = 8.dp,
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
          modifier = Modifier
            .width(64.dp)
            .height(64.dp),
          model = organizationUI.image,
          contentDescription = null
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
          Text(
            text = organizationUI.name
          )

          Text(
            modifier = Modifier
              .clickable {
                onUrlClicked(organizationUI.githubUrl)
              }
              .padding(top = 4.dp),
            text = organizationUI.githubUrl,
            textDecoration = TextDecoration.Underline,
            color = Color.Blue,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
          )
        }

      }

      if (!organizationUI.description.isNullOrBlank()) {
        Text(
          modifier = Modifier.padding(top = 8.dp),
          text = organizationUI.description,
          maxLines = 3,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ListItemComposablePreview() {
  ListItemComposable(
    Modifier,
    OrganizationUI("abc", "Name", "This is a github repo", "url"),
    {},
    false
  )
}