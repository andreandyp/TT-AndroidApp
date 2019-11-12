package com.apptt.axdecor.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.CatalogoAdapter
import com.apptt.axdecor.databinding.CatalogoFragmentBinding
import com.apptt.axdecor.viewmodels.CatalogoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CatalogoFragment : Fragment() {
    lateinit var binding: CatalogoFragmentBinding
    lateinit var bottomNavigate: BottomNavigationView

    private val viewModel: CatalogoViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, CatalogoViewModel.Factory(activity.application))
            .get(CatalogoViewModel::class.java)
    }

    private val catalogoAdapter = CatalogoAdapter {
        viewModel.verDetallesModelo(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.catalogo_fragment,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.catalogoViewModel = viewModel
        binding.catalogo.adapter = catalogoAdapter

        bottomNavigate = binding.bottomNav

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity?.javaClass?.simpleName == "CatalogoActivity") {
            binding.constraintLayoutCatalogo.setBackgroundColor(Color.TRANSPARENT)
        }

        viewModel.modelos.observe(viewLifecycleOwner, Observer {
            it?.apply {
                catalogoAdapter.modelos = it
            }
        })

        viewModel.verModelo.observe(viewLifecycleOwner, Observer { modeloSeleccionado ->
            if (modeloSeleccionado != null) {
                val directions =
                    CatalogoFragmentDirections.actionCatalogoFragmentToVerModeloFragment(
                        modeloSeleccionado
                    )
                this.findNavController().navigate(directions)
                viewModel.verDetallesModeloComplete()
            }
        })
    }

    public fun cambiarModelos(id: Int) {
        viewModel.verModelosConCategoria(id)
    }

}
