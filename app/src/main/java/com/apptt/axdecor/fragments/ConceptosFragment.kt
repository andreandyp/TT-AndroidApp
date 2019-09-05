package com.apptt.axdecor.fragments


import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apptt.axdecor.R
import com.apptt.axdecor.databinding.FragmentConceptosBinding
import pl.bclogic.pulsator4droid.library.PulsatorLayout


/**
 * A simple [Fragment] subclass.
 */
class ConceptosFragment : Fragment() {
    private lateinit var binding: FragmentConceptosBinding
    private lateinit var iconos: List<PulsatorLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_conceptos, container, false
        )

        iconos = listOf(
            binding.infoImg,
            binding.quieroImg,
            binding.decoracionImg,
            binding.cotizacionImg,
            binding.compartirImg
        )

        for (icono in iconos) {
            icono.start()
            icono.setOnClickListener { v: View ->
                (v as PulsatorLayout).stop()

                val metrics = DisplayMetrics()
                activity!!.windowManager.defaultDisplay.getMetrics(metrics)
                ObjectAnimator.ofFloat(v, "X", (metrics.widthPixels * 0.50 - v.width * 0.50).toFloat())
                    .apply {
                        duration = 1000
                        start()
                    }
                ObjectAnimator.ofFloat(v, "Y", (metrics.heightPixels * 0.25 - v.height * 0.50).toFloat())
                    .apply {
                        duration = 1000
                        start()
                    }
            }
        }



        return binding.root
    }


}
