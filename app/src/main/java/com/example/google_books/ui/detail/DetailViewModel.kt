package com.example.google_books.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.google_books.domain.ClickLikeBookUseCase
import com.example.google_books.ui.book.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val clickLikeBookUseCase: ClickLikeBookUseCase
) : ViewModel() {

    val bookModel = savedStateHandle.getLiveData<BookModel>(DETAIL_MODEL)


    fun onClickLike() {
        viewModelScope.launch {
            clickLikeBookUseCase(bookModel.value ?: return@launch)
                .onSuccess {
                    savedStateHandle[DETAIL_MODEL] = it
                }
                .onFailure { it.printStackTrace() }
        }
    }

    companion object {
        const val DETAIL_MODEL = "model"
    }

}