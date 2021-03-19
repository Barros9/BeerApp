package com.barros.beerapp.ui.mainfragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barros.beerapp.R
import com.barros.beerapp.adapter.BeerAdapter
import com.barros.beerapp.adapter.LoadingStateAdapter
import com.barros.beerapp.databinding.FragmentMainBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var beerAdapter: BeerAdapter

    private var searchJob: Job? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        beerAdapter = BeerAdapter(BeerAdapter.OnClickListener {
            mainViewModel.displayPropertyDetails(it)
        })

        binding.itemList.apply {
            this.adapter = beerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == beerAdapter.itemCount - 1) {
                        search()
                    }
                }
            })
        }

        binding.searchText.apply {
            setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)

                    val checkedId = binding.chipGroup.checkedChipId
                    binding.chipGroup.children.forEach { chip ->
                        if ((chip as Chip).id == checkedId) {
                            chip.isChecked = false
                        }
                    }

                    mainViewModel.search.value = textView.text.toString()
                    search()

                    return@OnEditorActionListener true
                }
                false
            })

            doOnTextChanged { text, _, _, _ ->
                mainViewModel.search.value = text.toString()

                val checkedId = binding.chipGroup.checkedChipId
                if (text!!.isEmpty()) {
                    if (checkedId != View.NO_ID) {
                        binding.chipGroup.children.forEach { chip ->
                            if ((chip as Chip).id == checkedId) {
                                chip.isChecked = false
                            }
                        }
                    } else {
                        search()
                    }
                }
            }
        }

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            inputMethodManager.hideSoftInputFromWindow(binding.chipGroup.windowToken, 0)

            mainViewModel.search.value = when (checkedId) {
                R.id.chipBlonde -> binding.chipBlonde.text.toString()
                R.id.chipLager -> binding.chipLager.text.toString()
                R.id.chipMalts -> binding.chipMalts.text.toString()
                R.id.chipStouts -> binding.chipStouts.text.toString()
                R.id.chipPale -> binding.chipPale.text.toString()
                R.id.chipAle -> binding.chipAle.text.toString()
                else -> ""
            }

            binding.searchText.setText(mainViewModel.search.value)
            if (checkedId != View.NO_ID) {
                search()
            }
        }

        binding.itemList.adapter = beerAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter(),
            footer = LoadingStateAdapter()
        )

        beerAdapter.addLoadStateListener { loadState ->
            binding.itemList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.loadingProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.Error) {
                showToast(getString(R.string.error_text))
            }
        }

        mainViewModel.navigateToDetail.observe(viewLifecycleOwner) {
            if (it != null && this@MainFragment.findNavController().currentDestination?.id == R.id.mainFragment) {
                this@MainFragment.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToItemListDialogFragment(it)
                )
                mainViewModel.displayPropertyDetailsComplete()
            }
        }
    }

    @ExperimentalPagingApi
    private fun search() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.getBeers().collectLatest {
                beerAdapter.submitData(it)
            }
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }
}
