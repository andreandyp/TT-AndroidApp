package com.apptt.axdecor.fragments


import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.FragmentConceptosBinding
import com.apptt.axdecor.viewmodels.ConceptosViewModel
import pl.bclogic.pulsator4droid.library.PulsatorLayout

/**
 * A simple [Fragment] subclass.
 */
class ConceptosFragment : Fragment() {
    private lateinit var binding: FragmentConceptosBinding
    private lateinit var iconos: List<PulsatorLayout>
    private lateinit var conceptosViewModel: ConceptosViewModel
    private lateinit var desaparecerIconos: Animation
    private lateinit var aparecerIconos: Animation
    private var conceptoActual: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_conceptos, container, false
        )

        binding.siguienteFragmento.setOnClickListener {
            it.findNavController().navigate(ConceptosFragmentDirections.actionConceptosFragmentToProveedoresFragment())
        }

        conceptosViewModel = ViewModelProviders.of(this).get(ConceptosViewModel::class.java)
        binding.conceptosViewModel = conceptosViewModel
        binding.lifecycleOwner = this

        iconos = listOf(
            binding.infoImg,
            binding.quieroImg,
            binding.decoracionImg,
            binding.cotizacionImg,
            binding.compartirImg
        )

        desaparecerIconos = AnimationUtils.loadAnimation(activity, R.anim.desaparecer_iconos)
        aparecerIconos = AnimationUtils.loadAnimation(activity, R.anim.aparecer_iconos)

        for (icono in iconos) {
            icono.start()
            icono.setOnClickListener(this::moverAlCentro)
        }

        binding.atrasConceptos.setOnClickListener(this::regresarAConceptos)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (conceptosViewModel.eventConceptoSeleccionado.value!!) {
                    regresarAConceptos(null)
                } else {
                    this.remove()
                    NavHostFragment.findNavController(this@ConceptosFragment).navigateUp()
                }

            }
        })

        return binding.root
    }

    private fun moverAlCentro(icono: View) {
        (icono as PulsatorLayout).stop()
        conceptosViewModel.onConceptoSelected(icono.id, icono.x, icono.y)

        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val nuevoX = (metrics.widthPixels * 0.50 - icono.width * 0.50).toFloat()
        val nuevoY = (metrics.heightPixels * 0.25 - icono.height * 0.50).toFloat()
        ObjectAnimator.ofFloat(icono, "X", nuevoX).apply {
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(icono, "Y", nuevoY).apply {
            duration = 500
            start()
        }

        val otrosIconos = iconos.toMutableList()
        otrosIconos.remove(icono)

        for (otros in otrosIconos) {
            otros.startAnimation(desaparecerIconos)
            otros.isClickable = false
        }

        binding.flecha1.startAnimation(desaparecerIconos)
        binding.flecha2.startAnimation(desaparecerIconos)
        binding.flecha3.startAnimation(desaparecerIconos)
        binding.flecha4.startAnimation(desaparecerIconos)

        binding.tituloConcepto.visibility = View.VISIBLE
        binding.explicacionConcepto.visibility = View.VISIBLE
        binding.atrasConceptos.visibility = View.VISIBLE
        binding.siguienteFragmento.visibility = View.INVISIBLE

        conceptoActual = icono
    }

    private fun regresarAConceptos(view: View?) {
        binding.flecha1.startAnimation(aparecerIconos)
        binding.flecha2.startAnimation(aparecerIconos)
        binding.flecha3.startAnimation(aparecerIconos)
        binding.flecha4.startAnimation(aparecerIconos)

        binding.tituloConcepto.visibility = View.INVISIBLE
        binding.explicacionConcepto.visibility = View.INVISIBLE
        binding.atrasConceptos.visibility = View.INVISIBLE
        binding.siguienteFragmento.visibility = View.VISIBLE

        ObjectAnimator.ofFloat(conceptoActual!!, "X", conceptosViewModel.imagenX)
            .apply {
                duration = 500
                start()
            }
        ObjectAnimator.ofFloat(conceptoActual!!, "Y", conceptosViewModel.imagenY)
            .apply {
                duration = 500
                start()
            }

        val otrosIconos = iconos.toMutableList()
        otrosIconos.remove(conceptoActual)

        for (otros in otrosIconos) {
            otros.startAnimation(aparecerIconos)
            otros.isClickable = true
        }

        conceptosViewModel.eventConceptoSeleccionado.value = false
    }
}
