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
import com.apptt.axdecor.Dialogs.SugerenciaPinturaDialog
import com.apptt.axdecor.R
import com.apptt.axdecor.Utilities.ARCoreUtils
import com.apptt.axdecor.fragments.ModoDecoracionFragment
import com.apptt.axdecor.fragments.PreguntasFrecuentesFragment
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
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.PlaneRenderer
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_arcrear.*
import kotlinx.android.synthetic.main.pinturas_dialog.*
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
    private var modoPlano = 0 // 0 - Horizontal, 1 - Vertical
    private val Lampara_asset =
        "https://firebasestorage.googleapis.com/v0/b/axdecortt.appspot.com/o/lamp%20(1).glb?alt=media&token=c7c5a764-4912-4f5e-ac12-4546d09db5ce"
    private val espejo_asset =
        "https://firebasestorage.googleapis.com/v0/b/axdecortt.appspot.com/o/espejo_1.glb?alt=media&token=3848d06d-2bf4-473e-bf20-d76194a3f4e2"
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
                    setDefaultPlane()
                    arsesion?.setupPlaneFinding(0)
                    defineMOdelo(Lampara_asset)
                    Toast.makeText(this, "Lamparas", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemMuebles -> {
                    setDefaultPlane()
                    arsesion?.setupPlaneFinding(0)
                    Toast.makeText(this, "Muebles", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemPisos -> {
                    Toast.makeText(this, "Pisos", Toast.LENGTH_SHORT).show()
                    arsesion?.setupPlaneFinding(0)
                    changeFloorTexture()
                    true

                }
                R.id.itemAdornos -> {
                    defineMOdelo(espejo_asset)
                    arsesion?.setupPlaneFinding(2)
                    Toast.makeText(this, "Adornos", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemColores -> {
                    Toast.makeText(this, "Colores", Toast.LENGTH_SHORT).show()
                    arsesion?.setupPlaneFinding(1)
                    muestraSugerencia("amarillo")
                    true
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
                navigateToFragment(PreguntasFrecuentesFragment())
            }
            R.id.itemhabitacion -> {
                openDialogRooms()
            }
            R.id.itemContacto -> {
                Toast.makeText(this, "Contacto", Toast.LENGTH_SHORT).show()
            }
        }
        bottomNavigate.visibility = View.INVISIBLE
        btnPhoto.visibility = View.INVISIBLE
        barraProgeso.visibility = View.INVISIBLE
        btnRemove.visibility = View.INVISIBLE
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
        //Asegurarse que Google Play Sevices para AR esta instalado y actualizado
        try {
            if (arsesion == null) {
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        arsesion = Session(this)
                        arsesion?.setupPlaneFinding(0)
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

    private fun Session.setupPlaneFinding(mode: Int) {
        val arConfig = Config(this)
        when (mode) {
            0 -> arConfig.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
            1 -> arConfig.planeFindingMode = Config.PlaneFindingMode.VERTICAL
            2 -> arConfig.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL
        }
        arConfig.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        this.configure(arConfig)
        arFragment?.arSceneView?.setupSession(this)
    }

    private fun defineMOdelo(modelURL: String) {
        barraProgeso.visibility = View.VISIBLE
        ModelRenderable.builder()
            .setSource(
                this, RenderableSource.builder().setSource(
                    this,
                    Uri.parse(modelURL),
                    RenderableSource.SourceType.GLB
                )
                    .setScale(0.4f)  // Scale the original model to 50%.
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build()
            )
            .setRegistryId(modelURL)
            .build()
            .thenAccept { renderable ->
                Modelo = renderable
                barraProgeso.visibility = View.INVISIBLE
            }
            .exceptionally {
                val toast =
                    Toast.makeText(this, "Unable to load renderable $modelURL", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                null
            }
        arFragment?.setOnTapArPlaneListener { hitResult, _, _ ->
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
            mod.setOnTapListener { _, _ ->
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
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "AXDecor/" + date + "_screenshot.png"
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
        if (!out.parentFile.exists()) {
            out.parentFile.mkdirs()
        }
        out.createNewFile()
        try {
            val outputStream = FileOutputStream(out)
            val outputData = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData)
            outputData.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (ex: IOException) {
            Log.i("ERRORPHOTO", ex.toString())
            throw IOException("Fallo al guardar ", ex)
        }
    }

    private fun takePhoto() {
        val filename = generateFilename()
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
                val toast =
                    Toast.makeText(this, "Imagen Guardada Exitosamente!", Toast.LENGTH_SHORT)
                toast.show()
            }
            handlerThread.quitSafely()
        }, Handler(handlerThread.looper))

    }

    private fun changeFloorTexture() {
        val sampler = Texture.Sampler.builder()
            .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
            .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
            .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
            .build()

        val trigrid = Texture.builder()
            .setSource(this, R.drawable.piso_1)
            .setSampler(sampler)
            .build()

        arFragment?.arSceneView?.scene?.addOnUpdateListener {
            val planeRenderer = arFragment?.arSceneView?.planeRenderer
            planeRenderer?.material?.thenAcceptBoth(trigrid) { material: Material?, texture: Texture? ->
                material?.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)
                material?.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS, 100f)
                material?.setFloat2("uvScale", 1f, 1.19245f)
            }
        }
    }

    private fun setDefaultPlane() {
        val sample = Texture.Sampler.builder()
            .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
            .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
            .build()

        Texture.builder()
            .setSource(this, R.drawable.floordiffuse)
            .setSampler(sample)
            .build()
            .thenAccept { texture ->
                arFragment?.arSceneView?.planeRenderer?.material?.thenAccept { material ->
                    material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)
                }
            }
    }

    private fun muestraSugerencia(color: String) {
        var cuadrito = SugerenciaPinturaDialog("rojo")
        cuadrito.show(supportFragmentManager, "Sugerencia")
    }

}

