package com.example.google_books.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.google_books.component.BookView
import com.example.google_books.databinding.ViewBookBinding
import com.example.google_books.databinding.ViewBookSkeletonBinding
import com.example.google_books.onThrottleClick

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
                    && oldItem.author == newItem.author && oldItem.date == newItem.date
                    && oldItem.infoLink == newItem.infoLink
        }
    }
) {


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BookModel -> BookViewType.BOOK.ordinal
            else -> BookViewType.BOOK_SKELETON.ordinal
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is BookItemViewHolder -> {
                item as BookModel
                holder.bind(item)
            }
            else -> {
                holder as BookSkeletonItemViewHolder
                holder.bind()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return when (viewType) {
            BookViewType.BOOK.ordinal -> BookItemViewHolder(
                parent, actionListener
            )
            else -> BookSkeletonItemViewHolder(parent)
        }
    }

}


enum class BookViewType {
    BOOK,
    BOOK_SKELETON
}


sealed class BookViewHolder(
    itemView: ViewGroup
) : RecyclerView.ViewHolder(itemView)


class BookItemViewHolder(
    parent: ViewGroup,
    private val actionListener: BookController
) : BookViewHolder(
    ViewBookBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ).root
) {

    private val binding = ViewBookBinding.bind(itemView)

    fun bind(model: BookModel) {
        with(model) {
            binding.root.setState(
                BookView.State(
                    id, title, thumbnail, author, date
                )
            )
            binding.root.onThrottleClick {
                actionListener.onClickBook(model.infoLink)
            }
        }
    }
}


class BookSkeletonItemViewHolder(
    parent: ViewGroup,
) : BookViewHolder(
    ViewBookSkeletonBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ).root
) {

    private val binding = ViewBookSkeletonBinding.bind(itemView)

    fun bind() {
    }
}