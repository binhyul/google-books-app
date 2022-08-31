package com.example.google_books.ui.book

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.google_books.component.SearchBar
import com.example.google_books.domain.LoadBooksUseCase
import com.example.google_books.domain.UpdateBooksUseCase
import com.example.google_books.ui.book.model.BookModel
import com.example.google_books.ui.book.model.ListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadBooksUseCase: LoadBooksUseCase,
    private val updateBooksUseCase: UpdateBooksUseCase
) : ViewModel() {

    private val searchTextFlow: StateFlow<String> =
        savedStateHandle.getStateFlow(SEARCH_INPUT_TEXT, "")

    private val listUpdateChannel = Channel<Unit>().apply {
        viewModelScope.launch {
            send(Unit)
        }
    }
    private val listUpdateEvent = listUpdateChannel.receiveAsFlow()

    val searchText: String
        get() = searchTextFlow.value

    private val _searchBarState: MutableLiveData<SearchBar.State> = MutableLiveData()
    val searchBarState: LiveData<SearchBar.State>
        get() = _searchBarState

    val listType: LiveData<ListType> = savedStateHandle.getLiveData(LIST_TYPE)

    val loading: LiveData<Boolean> by lazy {
        savedStateHandle.getLiveData(LOADING_STATE)
    }

    val books: Flow<PagingData<BookModel>> = searchTextFlow.flatMapLatest { text ->
        loadBooksUseCase(text)
    }.cachedIn(viewModelScope).combine(listUpdateEvent) { pagingData, event ->
        updateBooksUseCase(pagingData).getOrDefault(pagingData)
    }.cachedIn(viewModelScope)

    init {
        savedStateHandle[LIST_TYPE] = ListType.VERTICAL
    }

    private fun updateSearchText(text: String?) {
        if (searchTextFlow.value == text) return
        savedStateHandle[SEARCH_INPUT_TEXT] = text.orEmpty()
    }

    fun setSearchState(onKeyAction: () -> Unit) {
        _searchBarState.value = SearchBar.State(
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

    fun changeListType() {
        savedStateHandle[LIST_TYPE] = when (listType.value) {
            ListType.VERTICAL -> ListType.GRID
            else -> ListType.VERTICAL
        }
    }

    fun updateList() {
        viewModelScope.launch {
            listUpdateChannel.send(Unit)
        }
    }

    companion object {
        const val SEARCH_INPUT_TEXT = "search_tab_input_text"
        const val LIST_TYPE = "list_type"
        const val LOADING_STATE = "loading_state"
    }

}