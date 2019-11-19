package com.apptt.axdecor.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.ARCrearActivity
import com.apptt.axdecor.activities.SelectEstiloActivity
import kotlinx.android.synthetic.main.fragment_modo_decoracion.*

class ModoDecoracionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modo_decoracion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardCrear.setOnClickListener {
            val intento = Intent(activity, ARCrearActivity::class.java)
            startActivity(intento)
        }
        cardElegir.setOnClickListener {
            val intento = Intent(activity, SelectEstiloActivity::class.java)
            startActivity(intento)
        }
    }

}
