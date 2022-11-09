package com.example.generics

import com.example.generics.example.examplesix.Result
import com.example.generics.example.examplesix.getResultFromApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.junit.Test

class ExampleSixUnitTest {

    @Test
    fun call_api_with_success_response() {
        CoroutineScope(Dispatchers.IO).launch {
            getResultFromApi().onEach {
                when (it) {
                    is Result.Success -> {
                        it.response
                        assert(true)
                    }
                    is Result.Error -> {
                        it.message
                        assert(false)
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }


    @Test
    fun call_api_with_error_response() {
        CoroutineScope(Dispatchers.IO).launch {
            getResultFromApi(true).onEach {
                when (it) {
                    is Result.Success -> {
                        it.response
                        assert(false)
                    }
                    is Result.Error -> {
                        it.message
                        assert(true)
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }


    @Test
    fun call_api_with_loading_state() {
        CoroutineScope(Dispatchers.IO).launch {
            getResultFromApi().onEach {
                when (it) {
                    is Result.Success -> {
                        it.response
                    }
                    is Result.Error -> {
                        it.message
                    }
                    is Result.Loading -> {
                        assert(true)
                    }
                }
            }
        }
    }
}