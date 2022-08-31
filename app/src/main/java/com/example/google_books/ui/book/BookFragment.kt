package com.example.google_books.ui.book

import android.content.Context
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
import androidx.paging.LoadState
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
        initList()
        initSearchBar()
    }

    private fun initList() {
        binding.bookList.adapter = bookAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
                .collectLatest { data ->
                    bookAdapter.submitData(data)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            bookAdapter.loadStateFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    if (it.refresh is LoadState.Loading && viewModel.searchText.isNotBlank()) {
                        viewModel.updateLoadingState(true)
                    }

                    if (it.source.refresh is LoadState.NotLoading && bookAdapter.itemCount > 0) {
                        viewModel.updateLoadingState(false)
                    }

                    val currentState = it.refresh
                    if (currentState is LoadState.Error) {
                        viewModel.updateLoadingState(false)
                    }
                }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) showLoading() else hideLoading()
        }
    }

    private fun initSearchBar() {
        viewModel.searchBarState.observe(viewLifecycleOwner) {
            binding.searchBar.setState(it)
        }

        viewModel.setSearchState {
            closeKeyboard()
        }
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