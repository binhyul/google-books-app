package com.example.google_books.ui.book

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.google_books.domain.LoadBooksUseCase
import com.example.google_books.domain.LoadSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadSearchResultUseCase: LoadSearchResultUseCase,
    private val loadBooksUseCase: LoadBooksUseCase
) : ViewModel() {

    val searchText: LiveData<String> by lazy {
        savedStateHandle.getLiveData(SEARCH_INPUT_TEXT)
    }

    private val searchChannel = Channel<String>()
    private val searchEvent = searchChannel.receiveAsFlow()

    val books: Flow<PagingData<BookModel>> = searchEvent
        .flatMapLatest { text ->
            loadBooksUseCase.invoke(text)
        }.cachedIn(viewModelScope)

    val booksCount: LiveData<Int> by lazy {
        loadSearchResultUseCase.invoke().asLiveData()
    }

    fun updateSearchText(text: String?) {
        if (searchText.value == text) return
        savedStateHandle[SEARCH_INPUT_TEXT] = text.orEmpty()
        viewModelScope.launch {
            searchChannel.send(text.orEmpty())
        }
    }

    companion object {
        const val SEARCH_INPUT_TEXT = "search_tab_input_text"
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