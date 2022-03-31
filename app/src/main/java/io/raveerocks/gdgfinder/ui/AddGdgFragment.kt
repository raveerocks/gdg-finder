package io.raveerocks.gdgfinder.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import io.raveerocks.gdgfinder.R
import io.raveerocks.gdgfinder.view.AddGdgViewModel
import io.raveerocks.gdgfinder.databinding.AddGdgFragmentBinding

class AddGdgFragment : Fragment() {

    private val viewModel: AddGdgViewModel by lazy {
        ViewModelProvider(this).get(AddGdgViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = AddGdgFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.application_submitted),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackBar()
                binding.button.text = getText(R.string.done)
                binding.button.contentDescription = getText(R.string.submitted)
            }
        }

        return binding.root
    }

}
