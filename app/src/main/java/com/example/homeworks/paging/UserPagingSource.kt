package com.example.homeworks.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homeworks.api.ApiService
import com.example.homeworks.api.User

class UserPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getUsers(currentPage)

            LoadResult.Page(
                data = response.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage >= response.totalPages) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}