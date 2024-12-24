package com.jsontextfield.unionstationdepartures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsontextfield.unionstationdepartures.Train
import com.jsontextfield.unionstationdepartures.data.IGoTrainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val goTrainRepository: IGoTrainRepository
) : ViewModel() {

    private var _trains : MutableStateFlow<List<Train>> = MutableStateFlow(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun downloadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _trains.value = goTrainRepository.getTrains()
            }
        }
    }
}