package com.niklas.ux62550.domain.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class KeyRepository<K, T> {
    private val mutableItemsFlow: MutableMap<K, MutableSharedFlow<Result<T>>> = mutableMapOf()
    val itemsFlow: MutableMap<K, SharedFlow<Result<T>>> = mutableMapOf()

    fun getWithKey(itemKey: K, getUnit: suspend () -> T, scope: CoroutineScope): SharedFlow<Result<T>> {
        if (itemsFlow.containsKey(itemKey)) {
            return itemsFlow[itemKey]!!
        }
        mutableItemsFlow[itemKey] = MutableSharedFlow(replay = 1)
        itemsFlow[itemKey] = mutableItemsFlow[itemKey]!!.asSharedFlow()

        scope.launch {
            mutableItemsFlow[itemKey]!!.emit(
                try { Result.success(getUnit()) }
                catch (e: Exception) { Result.failure(e) }
            )
        }

        return itemsFlow[itemKey]!!
    }
}
