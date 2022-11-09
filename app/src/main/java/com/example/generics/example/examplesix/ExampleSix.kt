package com.example.generics.example.examplesix

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


fun demo() {
    CoroutineScope(Dispatchers.IO).launch {
        getResultFromApi().onEach {
            when (it) {
                is Result.Success -> {
                    it.response
                }
                is Result.Error -> {
                    it.message
                }
                is Result.Loading -> {}
            }
        }
    }
}



