package com.example.google_books.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.google_books.remote.AppService
import com.example.google_books.remote.model.BookItem

class ListContentRemotePagingSource(
    private val appService: AppService,
    private val searchText: String
) : PagingSource<Int, BookItem>() {
    override fun getRefreshKey(state: PagingState<Int, BookItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(PAGE_SIZE) ?: anchorPage?.nextKey?.minus(PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookItem> {
        return loadData(searchText, params.key ?: 0)
    }

    private suspend fun loadData(searchText: String, startIndex: Int): LoadResult<Int, BookItem> {
        return try {
            if (searchText.isNotBlank()) {
                val response =
                    appService.getListContents(
                        searchText,
                        startIndex.toString(),
                        PAGE_SIZE.toString()
                    )

                val prevPage = if (startIndex == 0) null else startIndex - PAGE_SIZE
                val list = response.items.orEmpty()
                val nextPage = if (list.isEmpty()) null else startIndex + PAGE_SIZE
                val skeletonSize = if (list.isEmpty()) 0 else PAGE_SIZE

                LoadResult.Page(list, prevPage, nextPage, itemsAfter = skeletonSize)
            } else {
                LoadResult.Page(emptyList(), -1, 0)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}