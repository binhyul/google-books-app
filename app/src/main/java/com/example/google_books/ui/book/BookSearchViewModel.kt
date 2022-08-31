package com.example.google_books.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.google_books.component.SearchBar
import com.example.google_books.domain.LoadBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadBooksUseCase: LoadBooksUseCase
) : ViewModel() {

    private val searchTextFlow: StateFlow<String> =
        savedStateHandle.getStateFlow(SEARCH_INPUT_TEXT, "")

    val searchText: String
        get() = searchTextFlow.value

    val searchBarState: LiveData<SearchBar.State> by lazy {
        savedStateHandle.getLiveData(SEARCH_BAR_STATE)
    }

    val loading: LiveData<Boolean> by lazy {
        savedStateHandle.getLiveData(LOADING_STATE)
    }

    val books: Flow<PagingData<BookModel>> = searchTextFlow.flatMapLatest { text ->
        loadBooksUseCase.invoke(text)
    }.cachedIn(viewModelScope)

    private fun updateSearchText(text: String?) {
        if (searchTextFlow.value == text) return
        savedStateHandle[SEARCH_INPUT_TEXT] = text.orEmpty()
    }

    fun setSearchState(onKeyAction: () -> Unit) {
        savedStateHandle[SEARCH_BAR_STATE] = SearchBar.State(
            text = searchText,
            updateTextAction = {
                updateSearchText(it)
            },
            onKeyAction = {
                onKeyAction()
            }
        )
    }

    fun updateLoadingState(loading: Boolean) {
        savedStateHandle[LOADING_STATE] = loading
    }

    companion object {
        const val SEARCH_INPUT_TEXT = "search_tab_input_text"
        const val SEARCH_BAR_STATE = "search_bar_state"
        const val LOADING_STATE = "loading_state"
    }

}

data class BookModel(
    val id: String,
    val title: String,
    val thumbnail: String,
    val author: String,
    val date: String,
    val infoLink: String
)