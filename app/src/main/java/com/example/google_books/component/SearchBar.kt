package com.example.google_books.component

import android.content.Context
import android.os.IBinder
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.google_books.databinding.SearchBarBinding

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    data class State(
        val text: String?,
        val updateTextAction: (String?) -> Unit,
        val onKeyAction: () -> Unit
    )

    private val binding: SearchBarBinding =
        SearchBarBinding.inflate(LayoutInflater.from(context), this)

    val searchBarWindowToken: IBinder?
        get() = binding.tilSearch.editText?.windowToken

    fun setState(state: State) {
        binding.tilSearch.editText?.setText(state.text)
        binding.tilSearch.editText?.addTextChangedListener {
            state.updateTextAction(it?.toString())
        }
        binding.tilSearch.editText?.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER &&
                event.action == KeyEvent.ACTION_UP
            ) {
                state.onKeyAction()
            }
            return@setOnKeyListener false
        }

    }
}