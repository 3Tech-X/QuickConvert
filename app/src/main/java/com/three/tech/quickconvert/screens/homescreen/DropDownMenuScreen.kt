package com.three.tech.quickconvert.screens.homescreen

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.three.tech.quickconvert.R

@Composable
fun SearchableDropdown(
    modifier: Modifier,
    items: List<String>,
    label: String,
    isError: Boolean,
    onCurrencyValidate: () -> Unit,
    onItemSelected: (String) -> Unit
) {
    var selectedText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
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
            .then(modifier)
    ) {
        Column(
            modifier = modifier
        ) {
            TextField(
                value = selectedText,
                onValueChange = { },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                isError = isError,
                label = { Text(label) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.down_arrow),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                readOnly = true,
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (!showDialog) {
                            showDialog = true
                        }
                        onCurrencyValidate()
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
                        onCurrencyValidate()
                        showDialog = false
                    },
                    confirmButton = {},
                    title = { Text(context.getString(R.string.qc_search_and_select)) },
                    text = {
                        Column {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                shape = RoundedCornerShape(12.dp),
                                label = { Text(context.getString(R.string.qc_search_text)) },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
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
                                                onCurrencyValidate()
                                            }
                                            .padding(12.dp)
                                    )
                                }
                                noResultText(filteredItems, context)
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun LazyListScope.noResultText(filteredItems: List<String>, context: Context) {
    if (filteredItems.isEmpty()) {
        item {
            Text(
                context.getString(R.string.qc_no_result_found),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
