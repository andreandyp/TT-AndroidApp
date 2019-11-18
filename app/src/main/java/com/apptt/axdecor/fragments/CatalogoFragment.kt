package com.apptt.axdecor.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.apptt.axdecor.R
import com.apptt.axdecor.adapters.CatalogoAdapter
import com.apptt.axdecor.databinding.CatalogoFragmentBinding
import com.apptt.axdecor.domain.ModelWithCategory
import com.apptt.axdecor.domain.Paint
import com.apptt.axdecor.viewmodels.ARViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CatalogoFragment : Fragment() {
    lateinit var binding: CatalogoFragmentBinding
    lateinit var bottomNavigate: BottomNavigationView

    private val viewModel: ARViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(activity, ARViewModel.Factory(activity.application))
            .get(ARViewModel::class.java)
    }

    private val catalogoAdapter = CatalogoAdapter {
        if(it is ModelWithCategory) {
            viewModel.verDetallesModelo(it)
        }
        else {
            viewModel.verDetallesPintura(it as Paint)
        }
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
        binding.arViewModel = viewModel
        binding.catalogo.adapter = catalogoAdapter

        bottomNavigate = binding.bottomNav

        bottomNavigate.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemLamparas -> {
                    viewModel.cambiarModoDecoracion(0)
                    viewModel.verModelosConCategoria(4)
                    true
                }
                R.id.itemMuebles -> {
                    viewModel.cambiarModoDecoracion(0)
                    viewModel.verModelosConCategoria(2)
                    true
                }
                R.id.itemPisos -> {
                    viewModel.cambiarModoDecoracion(0)
                    viewModel.verModelosConCategoria(1)
                    true

                }
                R.id.itemAdornos -> {
                    viewModel.cambiarModoDecoracion(2)
                    viewModel.verModelosConCategoria(0)
                    true
                }
                R.id.itemColores -> {
                    viewModel.cambiarModoDecoracion(1)
                    viewModel.verModelosConCategoria(3)
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity?.javaClass?.simpleName == "CatalogoActivity") {
            binding.constraintLayoutCatalogo.setBackgroundColor(Color.TRANSPARENT)
        }

        viewModel.listaModelos.observe(viewLifecycleOwner, Observer {
            it?.apply {
                catalogoAdapter.modelos = it
            }
        })

        viewModel.verModelo.observe(viewLifecycleOwner, Observer { modeloSeleccionado ->
            if (modeloSeleccionado != null) {
                val directions =
                    CatalogoFragmentDirections.actionCatalogoFragmentToVerModeloFragment()
                this.findNavController().navigate(directions)
                viewModel.verDetallesModeloComplete()
            }
        })

        viewModel.verPintura.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                val directions =
                    CatalogoFragmentDirections.actionCatalogoFragmentToVerPinturaFragment()
                this.findNavController().navigate(directions)
                viewModel.verDetallesPinturaComplete()
            }
        })
    }
}
