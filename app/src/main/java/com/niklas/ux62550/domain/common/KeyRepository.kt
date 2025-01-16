package com.niklas.ux62550.domain.common

import android.util.Log
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class KeyRepository <T> (
    private val remoteDataSource: RemoteMediaDataSource
) {

    private val mutableItemsFlow: MutableMap<Int, MutableSharedFlow<T>> = mutableMapOf()
    val itemsFlow: MutableMap<Int, SharedFlow<T>> = mutableMapOf()

    suspend fun getWithKey(itemId: Int, getUnit: suspend() -> T, scope: CoroutineScope): SharedFlow<T> {
        if (itemsFlow.containsKey(itemId)) {
            return itemsFlow[itemId]!!
        }
        mutableItemsFlow[itemId] = MutableSharedFlow()
        itemsFlow[itemId] = mutableItemsFlow[itemId]!!.asSharedFlow()

        // Launch scope here
//        mutableItemsFlow[itemId]!!.emit(
//            getUnit()
//        )
        scope.launch {
            mutableItemsFlow[itemId]!!.emit(
                getUnit()
            )
        }


        return itemsFlow[itemId]!!
    }

}