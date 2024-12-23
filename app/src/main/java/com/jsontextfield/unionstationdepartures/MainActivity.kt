package com.jsontextfield.unionstationdepartures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jsontextfield.unionstationdepartures.ui.MainViewModel
import com.jsontextfield.unionstationdepartures.ui.TrainListItem
import com.jsontextfield.unionstationdepartures.ui.theme.UnionStationDeparturesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val trains by mainViewModel.trains.collectAsStateWithLifecycle()
            UnionStationDeparturesTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        Text(
                            stringResource(R.string.app_name)
                        )
                    })
                }) { innerPadding ->
                    LaunchedEffect(Unit) {
                        mainViewModel.downloadData(getString(R.string.go_key))
                    }
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(trains) {
                            TrainListItem(it)
                        }
                    }
                }
            }
        }
    }
}