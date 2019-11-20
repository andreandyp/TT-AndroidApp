package com.apptt.axdecor.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apptt.axdecor.databinding.VerModeloFragmentBinding
import com.apptt.axdecor.viewmodels.ARViewModel


class VerModeloFragment : Fragment() {

    private val viewModel: ARViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(activity, ARViewModel.Factory(activity.application, 0))
            .get(ARViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = VerModeloFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        if (activity?.javaClass?.simpleName == "CatalogoActivity") {
            binding.constraintLayoutVerModelo.setBackgroundColor(Color.TRANSPARENT)
            binding.btnColocar.visibility = View.GONE
        }

        binding.btnColocar.setOnClickListener {
            val viewModel = binding.viewModel
            val modeloPoner = viewModel!!.detallesModelo.value
            if (!modeloPoner?.fileAR.isNullOrBlank()) {
                viewModel.modeloAR.value = modeloPoner
            } else {
                viewModel.piso.value = modeloPoner
            }
        }

        viewModel.detallesModelo.observe(viewLifecycleOwner, Observer { modelo ->
            if (modelo != null) {
                viewModel.actualizarPrecio(modelo.price)
            }
        })

        viewModel.detallesModelo.observe(viewLifecycleOwner, Observer { modelo ->
            if (modelo != null) {
                viewModel.actualizarEstilos(modelo.styles)
            }
        })

        return binding.root
    }

}
