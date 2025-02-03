package com.example.storeapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.storeapp.R
import com.example.storeapp.data.TabType

@Composable
fun StoreHomeScreen(
    storeUiState: StoreUiState,
    viewModel: StoreViewModel,
    modifier: Modifier = Modifier
){
    val navigationItemContentList = listOf(
        NavigationItemContent(
            iconType = TabType.SCAN,
            icon = Icons.Filled.Search,
            text = stringResource(R.string.scan_tab)
        ),
        NavigationItemContent(
            iconType = TabType.HOME,
            icon = Icons.Filled.Home,
            text = stringResource(R.string.home_tab)
        ),
        NavigationItemContent(
            iconType = TabType.INOUT,
            icon = Icons.Filled.Done,
            text = stringResource(R.string.inout_tab)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        StoreAppBottomNavigationBar(
            selectedTab = storeUiState.currentTab,
            navigationItemContentList = navigationItemContentList,
            onTabSelected = { tabType: TabType ->
            viewModel.updateCurrentMailbox(tabType)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StoreAppBottomNavigationBar(
    selectedTab: TabType,
    navigationItemContentList: List<NavigationItemContent>,
    onTabSelected: (TabType) -> Unit,
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier,
        tonalElevation = 8.dp
    ) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = selectedTab == navItem.iconType,
                onClick = { onTabSelected(navItem.iconType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                label = { Text(navItem.text) }
            )
        }
    }
}

data class NavigationItemContent(
    val iconType: TabType,
    val icon: ImageVector,
    val text: String
)


