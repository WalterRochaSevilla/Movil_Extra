package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarParallelUseCase

class DollarViewModel(
    val fetchDollarUseCase: FetchDollarUseCase,
    val fetchDollarParallelUseCase: FetchDollarParallelUseCase
): ViewModel() {

    sealed class DollarUIState {
        object Loading : DollarUIState()
        class Error(val message: String) : DollarUIState()
        class Success(val data: DollarModel) : DollarUIState()
    }

    init {
        getDollar()
        getDollarParallel()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()

    private val _uiStateParallel = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiStateParallel: StateFlow<DollarUIState> = _uiStateParallel.asStateFlow()


    fun getDollar() {
        viewModelScope.launch(Dispatchers.IO) {
                    fetchDollarUseCase.invoke().collect {
                        data -> _uiState.value = DollarUIState.Success(data) }
        }
    }

    fun getDollarParallel() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDollarParallelUseCase.invoke().collect {
                    data -> _uiStateParallel.value = DollarUIState.Success(data) }
        }
    }
}