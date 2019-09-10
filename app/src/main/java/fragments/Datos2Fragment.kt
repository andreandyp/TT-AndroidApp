package fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.apptt.axdecor.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_datos2.*

class Datos2Fragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_datos2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = Datos2FragmentArgs.fromBundle(arguments!!)
        Log.i("APPP",args.nombre)
        Log.i("APPP",args.edad.toString())
        spnPersonalidad.onItemSelectedListener = this
        ArrayAdapter.createFromResource(this.requireContext(),R.array.personalidades,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnPersonalidad.adapter = adapter
        }
        spnPersonalidad.onItemSelectedListener
        btnSiguiente.setOnClickListener {
            if(spnPersonalidad.selectedItemPosition == 0 || etxtPresupuesto.text.toString() == ""){
                Snackbar.make(btnSiguiente,"Parece que olvidas algo.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, p3: Long) {
         val valor = parent?.getItemAtPosition(pos).toString()
        if(pos == 0){

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}
