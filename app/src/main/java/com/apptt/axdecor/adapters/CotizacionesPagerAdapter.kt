package com.apptt.axdecor.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.apptt.axdecor.db.queries.ModelProviderCategory
import com.apptt.axdecor.domain.Model
import com.apptt.axdecor.domain.Paint
import com.apptt.axdecor.domain.Store
import com.apptt.axdecor.fragments.ContactoProveedoresFragment
import com.apptt.axdecor.fragments.CotizaMueblesFragment
import com.apptt.axdecor.fragments.CotizaPinturasFragment

class CotizacionesPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val cantidades: HashMap<*, *>,
    private val modelos: MutableList<Model>,
    private val proveedores: MutableList<MutableList<ModelProviderCategory>>,
    private val pintura:Paint?,
    private val pinturaProvs:List<Store>
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CotizaMueblesFragment(modelos, cantidades)
            }
            1 -> {
                CotizaPinturasFragment(pintura)
            }
            2 -> {
                ContactoProveedoresFragment(proveedores,pinturaProvs)
            }
            else -> CotizaMueblesFragment(modelos, cantidades)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Muebles, adornos lamparas y pisos"
            1 -> "Pinturas"
            2 -> "Contacto con proveedores"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 3
    }
}