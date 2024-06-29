package com.cme_mahmoud.domain.usecases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


/**
SingleUseCase use two generic types
@param backgroundScheduler , foregroundScheduler it expects two Scheduler in the constructor for Scheduling.
@param T is the input type
@return the value returned by the method
@throws what kind of exception does this method throw
 */
abstract class CompletableUseCase<in Input> constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun generateSingle(input: Input? = null)

    suspend fun buildUseCase(Input: Input? = null){
        return withContext(defaultDispatcher) {
            generateSingle(Input)
        }
    }

}