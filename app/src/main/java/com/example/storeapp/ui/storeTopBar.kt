package com.example.storeapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.storeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun storeTopBar(
    viewModel: StoreViewModel,
    storeUiState: StoreUiState
) {
    TopAppBar(
        modifier = Modifier.height(75.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Last sync: ${storeUiState.lastSyncTime}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if (storeUiState.serverConnection) "Connected" else "Offline",
                    color = if (storeUiState.serverConnection) Color(0xFF4CAF50) else Color(0xFFE57373),
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                IconButton(
                    onClick = {
                        viewModel.fetchDataFromServerAndInsertItIntoDatabase()
                    }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Download",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = { /* Handle menu action */ }
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowUp,
                        contentDescription = "Upload",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    )
}
