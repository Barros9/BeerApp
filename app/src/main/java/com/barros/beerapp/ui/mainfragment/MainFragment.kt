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
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barros.beerapp.R
import com.barros.beerapp.adapter.BeerAdapter
import com.barros.beerapp.databinding.FragmentMainBinding
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Result.Error
import com.barros.beerapp.model.Result.Loading
import com.barros.beerapp.model.Result.Success
import com.barros.beerapp.util.setGone
import com.barros.beerapp.util.setVisible
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@InternalCoroutinesApi
class MainFragment : Fragment() {

    private lateinit var beerAdapter: BeerAdapter
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel: MainViewModel by viewModels()
        _binding = FragmentMainBinding.inflate(inflater).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainFragment

            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            beerAdapter = BeerAdapter(BeerAdapter.OnClickListener {
                mainViewModel.displayPropertyDetails(it)
            })

            itemList.apply {
                this.adapter = beerAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == beerAdapter.itemCount - 1) {
                            mainViewModel.page += 1
                            mainViewModel.updateUiState()
                        }
                    }
                })
            }

            searchText.apply {
                setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)

                        val checkedId = chipGroup.checkedChipId
                        chipGroup.children.forEach { chip ->
                            if ((chip as Chip).id == checkedId) {
                                chip.isChecked = false
                            }
                        }

                        mainViewModel.search.value = textView.text.toString()
                        mainViewModel.page = 1
                        mainViewModel.updateUiState()

                        return@OnEditorActionListener true
                    }
                    false
                })

                doOnTextChanged { text, _, _, _ ->
                    mainViewModel.search.value = text.toString()

                    val checkedId = chipGroup.checkedChipId
                    if (text!!.isEmpty()) {
                        if (checkedId != View.NO_ID) {
                            chipGroup.children.forEach { chip ->
                                if ((chip as Chip).id == checkedId) {
                                    chip.isChecked = false
                                }
                            }
                        } else {
                            mainViewModel.page = 1
                            mainViewModel.updateUiState()
                        }
                    }
                }
            }

            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                inputMethodManager.hideSoftInputFromWindow(chipGroup.windowToken, 0)

                mainViewModel.search.value = when (checkedId) {
                    R.id.chipBlonde -> chipBlonde.text.toString()
                    R.id.chipLager -> chipLager.text.toString()
                    R.id.chipMalts -> chipMalts.text.toString()
                    R.id.chipStouts -> chipStouts.text.toString()
                    R.id.chipPale -> chipPale.text.toString()
                    R.id.chipAle -> chipAle.text.toString()
                    else -> ""
                }

                searchText.setText(mainViewModel.search.value)
                if (checkedId != View.NO_ID) {
                    mainViewModel.page = 1
                    mainViewModel.updateUiState()
                }
            }

            mainViewModel.uiState.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Loading -> onLoading()
                    is Success -> onSuccess(result.data)
                    is Error -> onError()
                }
            }

            mainViewModel.navigateToDetail.observe(viewLifecycleOwner) {
                if (it != null && this@MainFragment.findNavController().currentDestination?.id == R.id.mainFragment) {
                    this@MainFragment.findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToItemListDialogFragment(it)
                    )
                }
            }
        }

        return binding.root
    }

    private fun onLoading() = with(binding) {
        loadingProgressBar.setVisible()
        retryButton.setGone()
        itemList.setGone()
    }

    private fun onSuccess(data: List<BeerItem>?) = with(binding) {
        loadingProgressBar.setGone()
        retryButton.setGone()
        itemList.setVisible()
        data?.let {
            beerAdapter.submitList(it)

            if (it.isEmpty()) {
                emptyList.setVisible()
            } else {
                emptyList.setGone()
            }
        }
    }

    private fun onError() = with(binding) {
        loadingProgressBar.setGone()
        retryButton.setVisible()
        itemList.setGone()
        showToast(getString(R.string.error_text))
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
