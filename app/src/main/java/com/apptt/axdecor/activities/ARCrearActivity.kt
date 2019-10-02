package com.apptt.axdecor.activities

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.apptt.axdecor.R
import com.apptt.axdecor.fragments.ModoDecoracionFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_arcrear.*

class ARCrearActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: AppBarLayout
    lateinit var botonMenu: ImageView
    lateinit var toogle: ActionBarDrawerToggle
    lateinit var bottomNavigate:BottomNavigationView
    private var arFragment: ArFragment? = null
    private var Modelo: ModelRenderable? = null
    private var arsesion: Session? = null
    private var conf: Config? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        toolbar = findViewById(R.id.appBarMenu)
        drawerLayout = findViewById(R.id.drawerLayout)
        botonMenu = findViewById(R.id.imgMenu)
        navigationView = findViewById(R.id.navigationView)
        bottomNavigate = findViewById(R.id.bottomNav)
        navigationView.setNavigationItemSelectedListener(this)
        toogle = ActionBarDrawerToggle(this,drawerLayout,R.string.abre, R.string.cierra)
        drawerLayout.setDrawerListener(toogle)
        toogle.syncState()
        //TODO Botones de navegacion entre catalogos
        /*bottomNavigate.setOnNavigationItemSelectedListener {
            it.
        }*/
        botonMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment?
        arsesion = Session(this)
            var toast = Toast.makeText(this,"Toque el sitio para colocar elemento.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP,0,100)
            toast.show()

            arFragment?.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
                if (Modelo == null){
                    return@setOnTapArPlaneListener
                }
                //Crear Anchor
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment?.arSceneView?.scene)
                //Crear transformable y añadir a Anchor
                val mod = TransformableNode(arFragment?.transformationSystem)
                mod.setParent(anchorNode)
                mod.renderable = Modelo
                mod.select()
            }
}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemModo ->{
                navigateToFragment(ModoDecoracionFragment())
                Toast.makeText(this, "Modo Decoración", Toast.LENGTH_SHORT).show()
            }
            R.id.itemPreguntas ->{
                Toast.makeText(this, "Preguntas Frecuentes", Toast.LENGTH_SHORT).show()
            }
            R.id.itemhabitacion ->{
                Toast.makeText(this, "Selecciona habitacion", Toast.LENGTH_SHORT).show()
            }
            R.id.itemContacto ->{
                Toast.makeText(this, "Contacto", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contenedorFR, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e("LOGAXDECOR", "Sceneform requires Android N or later")
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        val openGlVersionString =
            (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < 3.0) {
            Log.e("LOGAXDECOR", "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        Log.i("RESUME", "ENTRA")
        if(arsesion == null){
            arsesion = Session(this)
        }
        conf = Config(arsesion)
        conf!!.setPlaneFindingMode(Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL)
        conf!!.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE)
        arsesion!!.configure(conf)
        arFragment?.arSceneView?.setupSession(arsesion)
    }
}
