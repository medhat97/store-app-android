package com.example.storeapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.storeapp.data.TabType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.LoadingStatus
import com.example.storeapp.network.StoreApi
import kotlinx.coroutines.launch
import java.io.IOException

class StoreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState


    fun updateCurrentTab(tabType: TabType) {
        _uiState.update {
            it.copy(
                currentTab = tabType
            )
        }
    }

    fun getStoreData(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentLoadingStatus = LoadingStatus.LOADING
                )
            }

            try {
                val listResult = StoreApi.retrofitService.getData()
                Log.d("API_RESPONSE medhat", listResult)
                _uiState.update {
                    it.copy(
                        data = listResult,
                        currentLoadingStatus = LoadingStatus.SUCCESS
                    )
                }
            } catch (e: IOException){
                Log.d("API_RESPONSE medhat faaaaaaaaaaaail" , e.toString())
                _uiState.update {
                    it.copy(
                        currentLoadingStatus = LoadingStatus.FAILED
                    )
                }
            }

        }
    }
}