package com.barros.beerapp.ui.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.barros.beerapp.databinding.FragmentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailViewModel: DetailViewModel by viewModels()

        return FragmentDetailBinding.inflate(inflater).apply {
            viewModel = detailViewModel
            lifecycleOwner = this@DetailFragment
        }.root
    }
}
