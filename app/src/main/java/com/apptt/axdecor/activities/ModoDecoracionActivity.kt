package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.apptt.axdecor.R
import com.apptt.axdecor.dialogs.RoomsSelectDialog
import com.apptt.axdecor.fragments.ConceptosFragment
import com.apptt.axdecor.fragments.ContactoFragment
import com.apptt.axdecor.fragments.PreguntasFrecuentesFragment
import com.apptt.axdecor.fragments.ProveedoresFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textview.MaterialTextView

class ModoDecoracionActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        val stub = findViewById<ViewStub>(R.id.stub)
        stub.layoutResource = R.layout.activity_modo_decoracion
        stub.inflate()
        inicializaNavigationDrawer()
        val acciontv = findViewById<MaterialTextView>(R.id.tvTituloAccion)
        acciontv.setText("AXDecor")
    }

    private fun inicializaNavigationDrawer() {
        val sharePref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        val nombre = sharePref.getString(getString(R.string.user_Name), "")
        val habitacion = sharePref.getString(getString(R.string.room_key), "")
        drawerLayout = findViewById(R.id.drawerLayout)
        val toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.abre, R.string.cierra)
        val botonMenu = findViewById<ImageView>(R.id.imgMenu)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val headervIew = navigationView.getHeaderView(0)
        val txtUser = headervIew.findViewById<TextView>(R.id.tvNombre)
        val txtHabit = headervIew.findViewById<TextView>(R.id.tvHabitacion)
        txtUser.text = nombre
        txtHabit.text = "Decorando: " + habitacion
        navigationView.setNavigationItemSelectedListener(this)
        drawerLayout.setDrawerListener(toogle)
        toogle.syncState()
        botonMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemModo -> {
                val mInt = Intent(this, ModoDecoracionActivity::class.java)
                startActivity(mInt)
                finish()
            }
            R.id.itemPreguntas -> {
                navigateToFragment(PreguntasFrecuentesFragment())
            }
            R.id.itemhabitacion -> {
                openDialogRooms()
            }
            R.id.itemCatalogo -> {
                val mInt = Intent(this, CatalogoActivity::class.java)
                startActivity(mInt)
            }
            R.id.itemGaleria -> {
                val mInt = Intent(this, GaleriaActivity::class.java)
                startActivity(mInt)
            }
            R.id.itemTutorial -> {
                navigateToFragment(ConceptosFragment())
            }
            R.id.itemProveedores -> {
                navigateToFragment(ProveedoresFragment())
            }
            R.id.itemContacto -> {
                navigateToFragment(ContactoFragment())
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openDialogRooms() {
        RoomsSelectDialog(ARCrearActivity().javaClass).show(
            supportFragmentManager,
            "Selecciona Habitacion"
        )
    }

    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contenedorFR, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
