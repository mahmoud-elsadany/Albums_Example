package com.cme_mahmoud.presentation.base

import androidx.lifecycle.ViewModel
import com.cme_mahmoud.common.ErrorModel

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

open class BaseViewModel @Inject constructor(
) : ViewModel() {
    val exceptionState = MutableSharedFlow<String>()
    val apiErrorState = MutableSharedFlow<ErrorModel>()
    val loadingState = MutableSharedFlow<Boolean>(1)
}