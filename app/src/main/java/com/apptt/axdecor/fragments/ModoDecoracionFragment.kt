package com.apptt.axdecor.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.apptt.axdecor.R
import com.apptt.axdecor.activities.ARCrearActivity
import kotlinx.android.synthetic.main.fragment_modo_decoracion.*

class ModoDecoracionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_modo_decoracion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardCrear.setOnClickListener {
            var intento = Intent(activity?.applicationContext,ARCrearActivity::class.java)
            startActivity(intento)
        }
    }


}
