package com.apptt.axdecor.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.apptt.axdecor.R
import kotlinx.android.synthetic.main.fragment_preguntas_frecuentes.*

/**
 * A simple [Fragment] subclass.
 */
class PreguntasFrecuentesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preguntas_frecuentes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnR1.setOnClickListener {
            if(!tvResp1.isVisible)
                tvResp1.visibility = View.VISIBLE
            else
                tvResp1.visibility = View.GONE
        }
        btnR2.setOnClickListener {
            if(!tvResp2.isVisible)
                tvResp2.visibility = View.VISIBLE
            else
                tvResp2.visibility = View.GONE
        }
        btnR3.setOnClickListener {
            if(!tvResp3.isVisible)
                tvResp3.visibility = View.VISIBLE
            else
                tvResp3.visibility = View.GONE
        }
        btnR4.setOnClickListener {
            if(!tvResp4.isVisible)
                tvResp4.visibility = View.VISIBLE
            else
                tvResp4.visibility = View.GONE
        }

    }

}
