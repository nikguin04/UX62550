package com.niklas.ux62550.domain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.WatchListDataObject
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.ui.feature.search.MovieItemsUIState
import com.niklas.ux62550.ui.feature.search.NonMovieBoxRow
import com.niklas.ux62550.ui.theme.SearchColorForText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.internal.connection.reorderForHappyEyeballs

class WatchListRepository {
    private val firebaseDataSource = RemoteFirebase()

    private val mutableWatchListFlow = MutableSharedFlow<List<Int>?>()
    val watchListFlow = mutableWatchListFlow.asSharedFlow()
    suspend fun getWatchList()  = firebaseDataSource.getWatchList(mutableWatchListFlow)


    private val MovieDataSource = RemoteMediaDataSource()

    private val mutableWatchListRowFlow = MutableSharedFlow<WatchListDataObject>()
    val watchListRowFlow = mutableWatchListRowFlow.asSharedFlow()
    suspend fun getMovieForRow(MovieId: Int)  = mutableWatchListRowFlow.emit(
        MovieDataSource.getMovieForRow(MovieId)
    )

}

