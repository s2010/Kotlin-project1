package com.bash.shoestore.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bash.shoestore.screens.DetailFragmentDirections
import com.bash.shoestore.databinding.FragmentDetailBinding
import com.bash.shoestore.model.SaveState
import com.bash.shoestore.model.Shoe
import com.bash.shoestore.model.ShoeViewModel
import timber.log.Timber

class DetailFragment : Fragment() {

    private val viewModel : ShoeViewModel by activityViewModels()
    private val shoeItem = Shoe("", 0.0, "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )

        binding.shoeViewModel = viewModel
        binding.lifecycleOwner = this
        binding.shoeItem = shoeItem

        binding.cancelButton.setOnClickListener {
            navigateToShoeList()
        }

        viewModel.saveState.observe(this as LifecycleOwner, Observer { saveState ->
            when(saveState) {
                SaveState.SAVE -> {
                    navigateToShoeList()
                    viewModel.onEventSaveComplete()
                    Timber.i("inside SaveState $saveState")
                }
            }
            Timber.i("outside SaveState")
        })
        return binding.root
    }

    private fun navigateToShoeList() {
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToListingFragment())
    }
}