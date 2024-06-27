package com.yogadimas.videogamesdatabaseyogadimaspambudi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.ResultsItem
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.networking.ApiService


class GamePagingSource(private val apiService: ApiService) :
    PagingSource<Int, ResultsItem>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->

            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }

    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {

            val position = params.key ?: INITIAL_PAGE_INDEX

            val responsDataList =
                apiService.getAllGamesPaging(page = position, size = params.loadSize)

            val responseData = processResults(responsDataList.results)

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }

    }

    private fun processResults(resultsList: List<ResultsItem?>?): List<ResultsItem> {
        // Ensure the list is not null
        val nonNullList: List<ResultsItem?> = resultsList ?: emptyList()

        // Filter out null values
        val nonNullValues: List<ResultsItem> = nonNullList.filterNotNull()

        // Convert to the expected type
        return nonNullValues
    }


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


}
