package com.apptt.axdecor.activities

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.apptt.axdecor.Dialogs.RoomsSelectDialog
import com.apptt.axdecor.R
import com.apptt.axdecor.fragments.ModoDecoracionFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
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
    lateinit var bottomNavigate: BottomNavigationView
    private var arFragment: ArFragment? = null
    private var Modelo: ModelRenderable? = null
    private var arsesion: Session? = null
    private var conf: Config? = null
    private val Lampara_asset = "https://firebasestorage.googleapis.com/v0/b/axdecortt.appspot.com/o/lamp%20(1).glb?alt=media&token=c7c5a764-4912-4f5e-ac12-4546d09db5ce"
    private val espejo_asset = "https://firebasestorage.googleapis.com/v0/b/axdecortt.appspot.com/o/1%2F6%2Fespejo_1.glb?alt=media&token=0abdad72-d201-4fad-9862-bf2a605d2595"
    private var mUserRequestedInstall = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        val sharePref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        val nombre = sharePref.getString(getString(R.string.user_Name), "")
        val habitacion = sharePref.getString(getString(R.string.room_key), "")
        toolbar = findViewById(R.id.appBarMenu)
        drawerLayout = findViewById(R.id.drawerLayout)
        botonMenu = findViewById(R.id.imgMenu)
        navigationView = findViewById(R.id.navigationView)
        bottomNavigate = findViewById(R.id.bottomNav)
        bottomNavigate.visibility = View.VISIBLE
        navigationView.setNavigationItemSelectedListener(this)
        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.abre, R.string.cierra)
        drawerLayout.setDrawerListener(toogle)
        toogle.syncState()
        var headervIew = navigationView.getHeaderView(0)
        var txtUser = headervIew.findViewById<TextView>(R.id.tvNombre)
        var txtHabit = headervIew.findViewById<TextView>(R.id.tvHabitacion)
        txtUser.text = nombre
        txtHabit.text = "Decorando: " + habitacion
        //TODO Botones de navegacion entre catalogos
        bottomNavigate.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemLamparas -> {
                    defineMOdelo(Lampara_asset)
                    Toast.makeText(this, "Lamparas", Toast.LENGTH_SHORT).show();true
                }
                R.id.itemMuebles -> {
                    Toast.makeText(this, "Muebles", Toast.LENGTH_SHORT).show();true
                }
                R.id.itemPisos -> {
                    Toast.makeText(this, "Pisos", Toast.LENGTH_SHORT).show();true
                }
                R.id.itemAdornos -> {
                    defineMOdelo(espejo_asset)
                    Toast.makeText(this, "Adornos", Toast.LENGTH_SHORT).show();true
                }
                R.id.itemColores -> {
                    Toast.makeText(this, "Colores", Toast.LENGTH_SHORT).show();true
                }
                else -> false
            }
        }
        botonMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment?
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemModo -> {
                navigateToFragment(ModoDecoracionFragment())
            }
            R.id.itemPreguntas -> {
                Toast.makeText(this, "Preguntas Frecuentes", Toast.LENGTH_SHORT).show()
            }
            R.id.itemhabitacion -> {
                openDialogRooms()
            }
            R.id.itemContacto -> {
                Toast.makeText(this, "Contacto", Toast.LENGTH_SHORT).show()
            }
        }
        bottomNavigate.visibility = View.INVISIBLE
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

    private fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
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
        //TODO Comprobar permisos de la camara

        //Asegurarse que Google Play Sevices para AR esta instalado y actualizado
        try {
            if (arsesion == null) {
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        arsesion = Session(this)
                        conf = Config(arsesion)
                        conf!!.setPlaneFindingMode(Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL)
                        conf!!.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE)
                        arsesion!!.configure(conf)
                        arFragment?.arSceneView?.setupSession(arsesion)
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        mUserRequestedInstall = false
                    }
                }
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            Toast.makeText(this, "Error:" + e, Toast.LENGTH_SHORT).show()
            return
        }

        val toast = Toast.makeText(this, "Toque el sitio para colocar elemento.", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 250)
        toast.show()
        defineMOdelo(Lampara_asset)
    }

    private fun defineMOdelo(modelURL:String){
        ModelRenderable.builder()
            .setSource(
                this, RenderableSource.builder().setSource(
                    this,
                    Uri.parse(modelURL),
                    RenderableSource.SourceType.GLB
                )
                    .setScale(0.5f)  // Scale the original model to 50%.
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build()
            )
            .setRegistryId(modelURL)
            .build()
            .thenAccept { renderable -> Modelo = renderable }
            .exceptionally { throwable ->
                val toast =
                    Toast.makeText(this, "Unable to load renderable $modelURL", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                null
            }
        arFragment?.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (Modelo == null) {
                return@setOnTapArPlaneListener
            }
            //Crear Anchor
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment?.arSceneView?.scene)
            //Crear transformable y añadir a Anchor
            val mod = TransformableNode(arFragment?.transformationSystem)
            mod.setParent(anchorNode)
            mod.localScale = Vector3(0.1f, 0.1f, 0.1f)
            mod.renderable = Modelo
            mod.select()
            mod.setOnTapListener { hitTestResult, motionEvent ->
                btnRemove.visibility = View.VISIBLE
                btnRemove.setOnClickListener {
                    removeObject(anchorNode)
                }
            }
        }
    }

    private fun openDialogRooms() {
        val ventana = RoomsSelectDialog()
        ventana.show(supportFragmentManager, "Selecciona Habitacion")
    }

    private fun removeObject(nodo:AnchorNode){
        arFragment?.arSceneView?.scene?.removeChild(nodo)
        nodo.anchor?.detach()
        nodo.setParent(null)
        Toast.makeText(this, "Test Delete - anchorNode removed", Toast.LENGTH_SHORT).show();
    }
}
