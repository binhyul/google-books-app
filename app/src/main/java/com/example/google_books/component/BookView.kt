package com.example.google_books.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.google_books.R
import com.example.google_books.databinding.BookBinding
import com.example.google_books.loadUrlImage

class BookView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    data class State(
        val id: String,
        val title: String,
        val thumbnail: String,
        val author: String,
        val date: String
    )

    private val binding: BookBinding =
        BookBinding.inflate(LayoutInflater.from(context), this)

    init {
        foreground = AppCompatResources.getDrawable(context, R.drawable.bg_selectable_item)
    }

    fun setState(state: State) {
        binding.ivImg.loadUrlImage(state.thumbnail)
        binding.tvTitle.text = state.title
        binding.tvAuthor.text = state.author
        binding.tvDate.text = state.date
    }
}