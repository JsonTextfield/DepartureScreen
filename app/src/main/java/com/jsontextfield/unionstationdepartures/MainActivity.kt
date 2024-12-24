package com.jsontextfield.unionstationdepartures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jsontextfield.unionstationdepartures.ui.MainViewModel
import com.jsontextfield.unionstationdepartures.ui.Timer
import com.jsontextfield.unionstationdepartures.ui.TrainListItem
import com.jsontextfield.unionstationdepartures.ui.theme.UnionStationDeparturesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val trains by mainViewModel.trains.collectAsStateWithLifecycle()
            var remainingMs by remember { mutableIntStateOf(30000) }
            LaunchedEffect(Unit) {
                mainViewModel.downloadData()
                while (true) {
                    delay(20)
                    remainingMs -= 20
                    if (remainingMs <= 0) {
                        mainViewModel.downloadData()
                        remainingMs = 30000
                    }
                }
            }
            UnionStationDeparturesTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        Text(
                            stringResource(R.string.app_name)
                        )
                    }, actions = {
                        Timer(
                            totalMs = 30_000,
                            remainingMs = remainingMs,
                        )
                    })
                }) { innerPadding ->
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        itemsIndexed(trains) { index, train ->
                            TrainListItem(train, index % 2 == 0)
                        }
                    }
                }
            }
        }
    }
}