package com.apptt.axdecor.activities

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.PixelCopy
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.apptt.axdecor.Dialogs.RoomsSelectDialog
import com.apptt.axdecor.R
import com.apptt.axdecor.Utilities.ARCoreUtils
import com.apptt.axdecor.fragments.ModoDecoracionFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_arcrear.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ARCrearActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: AppBarLayout
    lateinit var botonMenu: ImageView
    lateinit var toogle: ActionBarDrawerToggle
    lateinit var bottomNavigate: BottomNavigationView
    lateinit var botonFoto: FloatingActionButton
    private var arFragment: ArFragment? = null
    private var Modelo: ModelRenderable? = null
    private var arsesion: Session? = null
    private var conf: Config? = null
    private val Lampara_asset =
        "https://firebasestorage.googleapis.com/v0/b/axdecortt.appspot.com/o/lamp%20(1).glb?alt=media&token=c7c5a764-4912-4f5e-ac12-4546d09db5ce"
    private val espejo_asset =
        "https://firebasestorage.googleapis.com/v0/b/axdecortt.appspot.com/o/1%2F6%2Fespejo_1.glb?alt=media&token=0abdad72-d201-4fad-9862-bf2a605d2595"
    private var mUserRequestedInstall = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        if (!ARCoreUtils.checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment?
        botonFoto = findViewById(R.id.btnPhoto)
        botonFoto.setOnClickListener { takePhoto() }
        inicializaNavigationDrawer()
        navegacionDeCatalogos()
    }

    private fun navegacionDeCatalogos() {
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
    }

    private fun inicializaNavigationDrawer() {
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
        val headervIew = navigationView.getHeaderView(0)
        val txtUser = headervIew.findViewById<TextView>(R.id.tvNombre)
        val txtHabit = headervIew.findViewById<TextView>(R.id.tvHabitacion)
        txtUser.text = nombre
        txtHabit.text = "Decorando: " + habitacion
        botonMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
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


    override fun onResume() {
        super.onResume()
        //TODO Comprobar permisos de la camara
        Log.i("TESTFILE", generateFilename())
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

    private fun defineMOdelo(modelURL: String) {
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
        RoomsSelectDialog().show(supportFragmentManager, "Selecciona Habitacion")
    }

    private fun removeObject(nodo: AnchorNode) {
        arFragment?.arSceneView?.scene?.removeChild(nodo)
        nodo.anchor?.detach()
        nodo.setParent(null)
        Toast.makeText(this, "Objeto Removido", Toast.LENGTH_SHORT).show()
        btnRemove.visibility = View.INVISIBLE
    }

    private fun generateFilename(): String {
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "Sceneform/" + date + "_screenshot.png"
    }

    @Throws(IOException::class)
    private fun saveBitmapToDisk(bitmap: Bitmap, filename: String) {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                this,
                "No nos diste permiso para guardar la imagen :(",
                Toast.LENGTH_LONG
            ).show()
        }
        val out = File(filename)
        out.createNewFile()
        try {
            val outputStream = FileOutputStream(out)
            val outputData = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
            outputData.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()
            Log.i("ERRORPHOTO", "HECHO")
        } catch (ex: IOException) {
            Log.i("ERRORPHOTO", ex.toString())
            throw IOException("Fallo al guardar ", ex)
        }
    }

    private fun takePhoto() {
        val filename = generateFilename()
        Log.i("TESTIMAGE", filename)
        val view = arFragment?.arSceneView
        val bitmap = Bitmap.createBitmap(view!!.width, view.height, Bitmap.Config.ARGB_8888)
        val handlerThread = HandlerThread("PixelCopier")
        handlerThread.start()
        PixelCopy.request(view, bitmap, {
            if (it == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename)
                } catch (e: IOException) {
                    val toast = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                    Log.i("ERRORPHOTO", e.printStackTrace().toString())
                    toast.show()
                    return@request
                }
            }
            handlerThread.quitSafely()
        }, Handler(handlerThread.looper))

    }

}
