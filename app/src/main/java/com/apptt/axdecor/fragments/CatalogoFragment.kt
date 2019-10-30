package com.apptt.axdecor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.CatalogoFragmentBinding
import com.apptt.axdecor.viewmodels.CatalogoViewModel

class CatalogoFragment : Fragment() {

    private lateinit var viewModel: CatalogoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CatalogoFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.catalogo_fragment,
            container,
            false)

        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(CatalogoViewModel::class.java)
        binding.catalogoViewModel = viewModel


        return binding.root
    }

}
