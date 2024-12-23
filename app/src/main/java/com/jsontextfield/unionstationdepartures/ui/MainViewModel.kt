package com.jsontextfield.unionstationdepartures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsontextfield.unionstationdepartures.Downloader
import com.jsontextfield.unionstationdepartures.Train
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private var _trains : MutableStateFlow<List<Train>> = MutableStateFlow(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun downloadData(apiKey: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _trains.value = Downloader.download(apiKey)
            }
        }
    }
}