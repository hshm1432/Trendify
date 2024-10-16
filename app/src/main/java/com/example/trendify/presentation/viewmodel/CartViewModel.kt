package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.AddOrDeleteCartRequest
import com.example.trendify.data.model.AddOrDeleteCartResponse
import com.example.trendify.data.model.GetCartsResponse
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val apiServices: ApiServices,val authInterceptor: AuthInterceptor)  : ViewModel() {

    private val _getcartResponse = MutableStateFlow<GetCartsResponse?>(null)
    val cartResponse: StateFlow<GetCartsResponse?> get() = _getcartResponse


    private val _AddOrDeleteCartResponse = MutableStateFlow<AddOrDeleteCartResponse?>(null)
    val AddOrDeleteCartResponse: StateFlow<AddOrDeleteCartResponse?> get() = _AddOrDeleteCartResponse


    fun getCarts() {
        viewModelScope.launch {
            val response = apiServices.getCarts()
            if (response.isSuccessful) {
                _getcartResponse.value = response.body()
            }
        }
    }
    fun addOrDeleteCart(prdId: Int) {
        viewModelScope.launch {
            val response = apiServices.addOrDeleteCart(AddOrDeleteCartRequest(productId=prdId))
            if (response.isSuccessful) {
                _AddOrDeleteCartResponse.value = response.body()

            }
        }
    }
}