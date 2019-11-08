package com.apptt.axdecor.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apptt.axdecor.databinding.VerModeloFragmentBinding
import com.apptt.axdecor.viewmodels.VerModeloViewModel


class VerModeloFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = VerModeloFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val modelo = VerModeloFragmentArgs.fromBundle(arguments!!).modelo

        val viewModel = ViewModelProviders.of(activity!!).get(VerModeloViewModel::class.java)
        viewModel._modelo.value = modelo

        binding.viewModel = viewModel

        if (activity?.javaClass?.simpleName == "CatalogoActivity") {
            binding.constraintLayoutVerModelo.setBackgroundColor(Color.TRANSPARENT)
            binding.btnColocar.visibility = View.GONE
        }

        binding.btnColocar.setOnClickListener {
            val viewModel = binding.viewModel
            viewModel!!.modeloAR.value = viewModel.modelo.value
        }

        return binding.root
    }

}
