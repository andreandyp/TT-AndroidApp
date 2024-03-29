package com.apptt.axdecor.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.apptt.axdecor.R
import com.apptt.axdecor.activities.TutorialConceptosActivity
import com.apptt.axdecor.databinding.FragmentBienvenidaConceptosBinding

class BienvenidaConceptosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentBienvenidaConceptosBinding>(
            inflater,
            R.layout.fragment_bienvenida_conceptos, container, false
        )

        binding.verTutorialButton.setOnClickListener {
            it.findNavController()
                .navigate(BienvenidaConceptosFragmentDirections.actionBienvenidaConceptosFragmentToConceptosFragment())
        }
        binding.omitirButton.setOnClickListener {
            val inten = Intent(activity?.applicationContext, TutorialConceptosActivity::class.java)
            startActivity(inten)
        }

        return binding.root
    }


}
