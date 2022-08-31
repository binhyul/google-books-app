package com.example.google_books.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.google_books.R
import com.example.google_books.databinding.BookCardBinding
import com.example.google_books.loadUrlImage

class BookCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    data class State(
        val thumbnail: String,
        val like: Boolean = false
    )

    private val binding: BookCardBinding =
        BookCardBinding.inflate(LayoutInflater.from(context), this)

    init {
        foreground = AppCompatResources.getDrawable(context, R.drawable.bg_selectable_item)
    }

    fun setState(state: State) {
        binding.ivImg.loadUrlImage(state.thumbnail)
        binding.ivLike.setImageResource(
            if (state.like) {
                R.drawable.ic_heart_filled_24
            } else {
                R.drawable.ic_heart_empty_24
            }
        )
    }
}