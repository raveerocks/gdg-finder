package io.raveerocks.gdgfinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.raveerocks.gdgfinder.R
import io.raveerocks.gdgfinder.databinding.HomeFragmentBinding
import io.raveerocks.gdgfinder.view.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val binding = HomeFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        viewModel.navigateToSearch.observe(
            viewLifecycleOwner
        ) { shouldNavigate ->
            if (shouldNavigate == true) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_homeFragment_to_gdgListFragment)
                viewModel.onNavigatedToSearch()
            }
        }
        return binding.root
    }
}
