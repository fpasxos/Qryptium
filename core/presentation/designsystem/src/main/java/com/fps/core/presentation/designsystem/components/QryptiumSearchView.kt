@file:OptIn(ExperimentalMaterial3Api::class)

package com.fps.core.presentation.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fps.core.presentation.designsystem.R

@Composable
fun QryptiumSearchView(
    searchQuery: String,
    onQueryChanged: (query: String) -> Unit
) {
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }

    var text by rememberSaveable {
        mutableStateOf("")
    }

    SearchBar(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
        query = searchQuery,
        onQueryChange = {
            onQueryChanged(it)
            text = it
        },
        onSearch = {
            isActive = false
        },
        active = false,
        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (isActive) {
                IconButton(
                    onClick = {
                        onQueryChanged("")
                        text = ""
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear icon"
                    )
                }
            }
        },
        onActiveChange = {
            isActive = it
        }) {
    }
}