package com.cme_mahmoud.domain.usecases.base

import com.cme_mahmoud.common.Outcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
ObservableUseCase use two generic types
@param backgroundScheduler , foregroundScheduler it expects two Scheduler in the constructor for Scheduling.
@param T is the input type
@param Input is the parameter type
@return the value returned by the method
@throws what kind of exception does this method throw
 */
abstract class FlowableUseCase<T, in Input>constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun generateFlowable(input: Input? = null,networStatus :Boolean= true): Flow<Outcome<T>>

    suspend fun buildUseCase(input: Input? = null,networStatus :Boolean= true): Flow<Outcome<T>> {
        return generateFlowable(input, networStatus )
            .flowOn(defaultDispatcher)

    }

}