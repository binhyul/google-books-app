package com.example.google_books.ui.book.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.google_books.component.BookCardView
import com.example.google_books.component.BookView
import com.example.google_books.databinding.ViewBookBinding
import com.example.google_books.databinding.ViewBookCardBinding
import com.example.google_books.onThrottleClick
import com.example.google_books.ui.book.BookController
import com.example.google_books.ui.book.model.BookModel


enum class BookViewType {
    BOOK,
    BOOK_CARD
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
        val resources = binding.root.resources
        with(model) {
            binding.root.setState(
                BookView.State(
                    title = title,
                    thumbnail = thumbnail,
                    author = getAuthor(resources),
                    price = getPrice(resources),
                    description = getDescription(resources),
                    like = like
                )
            )
            binding.root.onThrottleClick {
                actionListener.onClickBook(model)
            }
        }
    }
}


class BookCardItemViewHolder(
    parent: ViewGroup,
    private val actionListener: BookController
) : BookViewHolder(
    ViewBookCardBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ).root
) {

    private val binding = ViewBookCardBinding.bind(itemView)

    fun bind(model: BookModel) {
        with(model) {
            binding.root.setState(
                BookCardView.State(
                    thumbnail, like
                )
            )
            binding.root.onThrottleClick {
                actionListener.onClickBook(model)
            }
        }
    }
}