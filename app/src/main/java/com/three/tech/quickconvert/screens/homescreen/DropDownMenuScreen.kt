package com.three.tech.quickconvert.screens.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.R

@Composable
fun SearchableDropdown(
    modifier: Modifier,
    items: List<String>,
    label: String = "Select Item",
    onItemSelected: (String) -> Unit
) {
    var selectedText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isPressed) {
        if (isPressed) {
            showDialog = true
        } else {
            focusManager.clearFocus()

        }
    }

    val filteredItems = remember(searchQuery, items) {
        if (searchQuery.isBlank()) items
        else items.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .then(modifier)
    ) {
        Column(
            modifier = modifier
        ) {
            TextField(
                value = selectedText,
                onValueChange = {
                },
                shape = RoundedCornerShape(12.dp), // 👈 This makes all corners rounded
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                label = { Text(label) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = if (showDialog) R.drawable.back_arrow else R.drawable.convert_bottom_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                readOnly = true,
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth().clickable {
                        focusManager.clearFocus()
                    }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialog = true
                }
        ) {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                                       },
                    confirmButton = {},
                    title = { Text("Search & Select") },
                    text = {
                        Column {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                label = { Text("Search") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .heightIn(max = 300.dp)
                            ) {
                                items(filteredItems.size) { index ->
                                    val item = filteredItems[index]
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedText = item
                                                onItemSelected(item)
                                                showDialog = false
                                                searchQuery = ""
                                            }
                                            .padding(12.dp)
                                    )
                                }
                                noResultText(filteredItems)
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun LazyListScope.noResultText(filteredItems: List<String>) {
    if (filteredItems.isEmpty()) {
        item {
            Text(
                "No results found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
