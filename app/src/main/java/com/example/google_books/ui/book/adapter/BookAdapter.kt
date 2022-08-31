package com.example.google_books.ui.book.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.google_books.ui.book.BookController
import com.example.google_books.ui.book.model.BookModel
import com.example.google_books.ui.book.model.ListType

class BookAdapter(
    private val actionListener: BookController
) : PagingDataAdapter<BookModel, BookViewHolder>(
    object : DiffUtil.ItemCallback<BookModel>() {
        override fun areItemsTheSame(
            oldItem: BookModel,
            newItem: BookModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookModel,
            newItem: BookModel
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.thumbnail == newItem.thumbnail
                    && oldItem.author == newItem.author && oldItem.price == newItem.price
                    && oldItem.description == newItem.description && oldItem.like == newItem.like
        }
    }
) {

    private lateinit var type: ListType

    fun updateListType(listType: ListType) {
        type = listType
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            ListType.VERTICAL -> BookViewType.BOOK.ordinal
            else -> BookViewType.BOOK_CARD.ordinal
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item: BookModel = getItem(position) ?: return
        when (holder) {
            is BookItemViewHolder -> {
                holder.bind(item)
            }
            else -> {
                holder as BookCardItemViewHolder
                holder.bind(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return when (viewType) {
            BookViewType.BOOK.ordinal -> BookItemViewHolder(
                parent, actionListener
            )
            else -> BookCardItemViewHolder(parent, actionListener)
        }
    }

}

