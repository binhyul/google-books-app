package com.example.google_books.ui.book.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.google_books.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ListType(@DrawableRes val icon: Int) : Parcelable {
    GRID(R.drawable.grid_48), VERTICAL(R.drawable.list_48)
}