import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class CompletableDeleteUseCase<T, in Input> constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun generateSingle(input: Input? = null):Int

    suspend fun buildUseCase(Input: Input? = null) :Int{
        return withContext(defaultDispatcher) {
            generateSingle(Input)
        }
    }

}