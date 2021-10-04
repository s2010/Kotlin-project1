package com.bash.shoestore.screens

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bash.shoestore.screens.ListingFragmentDirections
import com.bash.shoestore.R
import com.bash.shoestore.databinding.FragmentListingBinding
import com.bash.shoestore.databinding.ShoeItemBinding
import com.bash.shoestore.model.ShoeViewModel
import timber.log.Timber

class ListingFragment : Fragment() {

    private val viewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding : FragmentListingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_listing, container, false
        )

        // Get the viewModel
        //viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        // add a child view to a ViewGroup
        viewModel.shoeList.observe(this as LifecycleOwner, Observer {
            Timber.i("outside for viewModel.shoeList.value")

            for (shoe in viewModel.shoeList.value!!){
                Timber.i("inside for viewModel.shoeList.value")

                val shoeBinding = ShoeItemBinding.inflate(layoutInflater)
                shoeBinding.shoeItem = shoe
                binding.innerLinearLayout.addView(shoeBinding.root)
            }
        })

        binding.addShoeButton.setOnClickListener{
            Timber.i("addShoeButton setOnClickListener")
            Timber.i(viewModel.shoeList.value?.joinToString(separator = "\n"))
            val action = ListingFragmentDirections.actionListingFragmentToDetailFragment()
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}