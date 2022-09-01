package com.example.google_books.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.google_books.R
import com.example.google_books.databinding.FragDetailBinding
import com.example.google_books.loadUrlImage
import com.example.google_books.onThrottleClick
import com.example.google_books.setNavigationResult
import com.example.google_books.ui.book.BookFragment.Companion.KEY_REFRESH_REQUEST
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookModel.observe(viewLifecycleOwner) {
            binding.tvTitle.text = it.title
            binding.ivImg.loadUrlImage(it.thumbnail)
            binding.tvAuthor.text = resources.getString(R.string.author, it.author)
            binding.tvPublishDate.text = resources.getString(R.string.publish_date, it.publishDate)
            binding.tvPublisher.text = it.getPublisher(resources)
            binding.tvPrice.text = it.getPrice(resources)
            binding.tvDescription.text = it.getDescription(resources)

            binding.ivLike.setImageResource(
                if (it.like) {
                    R.drawable.ic_heart_filled_24
                } else {
                    R.drawable.ic_heart_empty_24
                }
            )
        }

        binding.ivLike.onThrottleClick {
            viewModel.onClickLike()
            setNavigationResult(true, KEY_REFRESH_REQUEST)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}