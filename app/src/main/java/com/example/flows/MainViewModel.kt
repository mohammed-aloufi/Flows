package com.example.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _sharedFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val sharedFlow: SharedFlow<String> = _sharedFlow

    private val _stateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val stateFlow: StateFlow<Int> = _stateFlow

    fun triggerStateFlow() {
        viewModelScope.launch {
            repeat(10) {
                _stateFlow.value += 1
                delay(1000L)
                if (_stateFlow.value == 10){
                    _sharedFlow.emit("Done")
                }
            }
        }
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            repeat(10){
                _sharedFlow
                delay(1000L)
            }
        }
    }
}