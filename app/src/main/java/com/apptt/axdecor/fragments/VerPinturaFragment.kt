package com.apptt.axdecor.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apptt.axdecor.databinding.FragmentVerPinturaBinding
import com.apptt.axdecor.viewmodels.ARViewModel

class VerPinturaFragment : Fragment() {

    private val viewModel: ARViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(activity, ARViewModel.Factory(activity.application))
            .get(ARViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVerPinturaBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        if (activity?.javaClass?.simpleName == "CatalogoActivity") {
            binding.constraintLayoutVerModelo.setBackgroundColor(Color.TRANSPARENT)
            binding.btnPintar.visibility = View.GONE
        }

        binding.btnPintar.setOnClickListener {
            val viewModel = binding.viewModel
            val pinturaColorear = viewModel!!.detallesPintura.value
            viewModel!!.pinturaAR.value = pinturaColorear
        }

        viewModel.detallesPintura.observe(viewLifecycleOwner, Observer { pintura ->
            if (pintura != null) {
                viewModel.actualizarPrecio(pintura.price)
            }
        })
        return binding.root
    }

}
