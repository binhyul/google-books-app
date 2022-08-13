package com.example.google_books.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.google_books.databinding.BookSkeletonBinding

class BookSkeletonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: BookSkeletonBinding =
        BookSkeletonBinding.inflate(LayoutInflater.from(context), this)
}