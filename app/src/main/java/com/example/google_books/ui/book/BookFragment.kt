package com.example.google_books.ui.book

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.google_books.component.SearchBar
import com.example.google_books.databinding.FragmentBookBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null

    private val viewModel: BookSearchViewModel by viewModels()

    private val binding get() = _binding!!

    private val bookController = object : BookController {
        override fun onClickBook(link: String) {
            if (link.isBlank()) return
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
    }

    private val bookAdapter = BookAdapter(bookController)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookList.adapter = bookAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    bookAdapter.submitData(it)
                }
        }

        bookAdapter.addLoadStateListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                    if (it.refresh is LoadState.Loading && !viewModel.searchText.value.isNullOrBlank()) {
                        showLoading()
                    }

                    if (it.source.refresh is LoadState.NotLoading && bookAdapter.itemCount > 0) {
                        hideLoading()
                    }

                    val currentState = it.refresh
                    if (currentState is LoadState.Error) {
                        hideLoading()
                    }
                }
            }
        }



        binding.searchBar.setState(
            SearchBar.State(
                text = viewModel.searchText.value,
                updateTextAction = {
                    viewModel.updateSearchText(it)
                },
                onKeyAction = {
                    closeKeyboard()
                }
            )
        )
    }

    private fun closeKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(
            binding.searchBar.searchBarWindowToken,
            0
        )
    }

    private fun showLoading() {
        binding.loadingView.onLoading()
    }

    private fun hideLoading() {
        binding.loadingView.onEnd()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}