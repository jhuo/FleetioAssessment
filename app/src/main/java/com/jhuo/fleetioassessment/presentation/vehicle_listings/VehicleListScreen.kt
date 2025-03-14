package com.jhuo.fleetioassessment.presentation.vehicle_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.jhuo.fleetioassessment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    navController: NavHostController
) {
    val vehicleListViewModel = hiltViewModel<VehicleListViewModel>()
    val nameFilter = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.vehicle_list)) },
                actions = {
                    FilterByNameBar(nameFilter, vehicleListViewModel, focusManager)
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val pagingData = vehicleListViewModel.vehiclePagingFlow.collectAsLazyPagingItems()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    count = pagingData.itemCount,
                    key = { index -> pagingData[index]?.id ?: index }
                ) { index ->
                    val vehicle = pagingData[index]
                    if (vehicle != null) {
                        VehicleItem(vehicle = vehicle, navController)
                    } else {
                        PlaceholderItem()
                    }
                }

                if (pagingData.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FilterByNameBar(
    nameFilter: MutableState<String>,
    viewModel: VehicleListViewModel,
    focusManager: FocusManager
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = nameFilter.value,
            onValueChange = { nameFilter.value = it },
            label = { Text(stringResource(R.string.filter_by_name)) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true,
            trailingIcon = {
                if (nameFilter.value.isNotEmpty()) {
                    IconButton(onClick = {
                        nameFilter.value = ""
                        viewModel.setNameFilter("")
                    }) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.clear))
                    }
                }
            }
        )

        TextButton(
            onClick = {
                viewModel.setNameFilter(nameFilter.value.takeIf { it.isNotEmpty() })
                focusManager.clearFocus()
            }
        ) {
            Text(
                text = stringResource(R.string.filter),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PlaceholderItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.LightGray)
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}