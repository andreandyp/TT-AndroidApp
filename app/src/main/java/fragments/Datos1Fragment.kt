package fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.apptt.axdecor.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_datos1.*

class Datos1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombre = etxtNombre as EditText
        val edad = etxtEdad as EditText
        btnSiguiente.setOnClickListener {
            if (nombre.text.toString().equals("") || edad.text.toString().equals("") ) {
                Snackbar.make(btnSiguiente, "Parece que olvidas algo.", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                it.findNavController().navigate(Datos1FragmentDirections.actionDatos1FragmentToDatos2Fragment(nombre.text.toString(),edad.text.toString().toInt()))
            }

        }
    }
}
