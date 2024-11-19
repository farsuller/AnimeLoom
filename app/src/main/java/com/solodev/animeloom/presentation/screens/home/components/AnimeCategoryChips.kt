package com.solodev.animeloom.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.domain.model.CategoryData
import com.solodev.animeloom.presentation.screens.home.states.CategoryState

@Composable
fun AnimeCategoryChips(
    modifier: Modifier = Modifier,
    categoryState: CategoryState,
    onClickCategory: (CategoryData?) -> Unit = {},
) {
    categoryState.categories?.let {
        LazyRow(
            modifier = modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(it) { category ->
                DefaultChipButton(
                    text = category.attributes.title ?: "Anime",
                    onClickedCategory = {
                        onClickCategory(category)
                    },
                )
            }
        }
    }
}
