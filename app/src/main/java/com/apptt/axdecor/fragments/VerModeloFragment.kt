package com.apptt.axdecor.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.VerModeloFragmentBinding
import com.apptt.axdecor.viewmodels.VerModeloViewModel


class VerModeloFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = VerModeloFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application
        val modelo = VerModeloFragmentArgs.fromBundle(arguments!!).modelo

        val viewModel: VerModeloViewModel  by lazy {
            ViewModelProviders.of(this, VerModeloViewModel.Factory(modelo, application))
                .get(VerModeloViewModel::class.java)
        }

        binding.viewModel = viewModel

        return binding.root
    }

}
