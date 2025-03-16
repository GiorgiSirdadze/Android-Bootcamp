package com.example.homeworks.domain.usecase

import android.util.Log
import com.example.homeworks.domain.resource.Resource
import com.example.homeworks.domain.model.Category
import com.example.homeworks.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FetchCategoriesUseCase {
    suspend operator fun invoke(query: String? = null): Flow<Resource<List<Category>>>
}

class FetchCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : FetchCategoriesUseCase {
    override suspend operator fun invoke(query: String?): Flow<Resource<List<Category>>> {
        return categoryRepository.fetchCategories()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val categories = resource.data
                        val filteredCategories = if (query.isNullOrBlank()) {
                            categories
                        } else {
                            categories.mapNotNull { category ->
                                val matchingChildren = category.children.filter { child ->
                                    child.name.contains(query, ignoreCase = true)
                                }
                                when {
                                    category.name.contains(query, ignoreCase = true) -> category
                                    matchingChildren.isNotEmpty() -> category.copy(children = matchingChildren)
                                    else -> null
                                }
                            }
                        }

                        Log.d("Filtered Data", "Query: '$query', Found Categories: ${filteredCategories.map { it.name }}")
                        Resource.Success(filteredCategories)
                    }
                    is Resource.Error, is Resource.Loader -> resource
                }
            }
    }
}





