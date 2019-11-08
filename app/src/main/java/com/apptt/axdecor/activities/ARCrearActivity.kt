package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.apptt.axdecor.R
import com.apptt.axdecor.dialogs.CotizaDialog
import com.apptt.axdecor.dialogs.RoomsSelectDialog
import com.apptt.axdecor.dialogs.SugerenciaPinturaDialog
import com.apptt.axdecor.fragments.ConceptosFragment
import com.apptt.axdecor.fragments.ContactoFragment
import com.apptt.axdecor.fragments.PreguntasFrecuentesFragment
import com.apptt.axdecor.fragments.ProveedoresFragment
import com.apptt.axdecor.utilities.ARCoreUtils
import com.apptt.axdecor.viewmodels.VerModeloViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textview.MaterialTextView
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ARCrearActivity() : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var bottomNavigate: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private var arFragment: ArFragment? = null
    private var modelo: ModelRenderable? = null
    private var arsesion: Session? = null
    private var modoPlano = 0 // 0 - Horizontal, 1 - Vertical
    private var mUserRequestedInstall = true
    private lateinit var viewModel: VerModeloViewModel
    private lateinit var catalogoFragment: ConstraintLayout
    private var modelosInsertados: MutableMap<Int, Int> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, VerModeloViewModel.Factory(null, application))
            .get(VerModeloViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        val stub = findViewById<ViewStub>(R.id.stub)
        stub.layoutResource = R.layout.activity_arcrear
        stub.inflate()
        val acciontv = findViewById<MaterialTextView>(R.id.tvTituloAccion)
        acciontv.setText("Crear Estilo")
        if (!ARCoreUtils.checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment?
        val botonFoto = findViewById<FloatingActionButton>(R.id.btnPhoto)
        botonFoto.setOnClickListener { takePhoto() }
        fabCheck.setOnClickListener { muestraCotiza() }
        inicializaNavigationDrawer()
        navegacionDeCatalogos()

        //catalogoFragment = findViewById(R.id.catalogo_ra)
        viewModel.modeloAR.observe(this, androidx.lifecycle.Observer {
            //catalogoFragment.visibility = View.INVISIBLE
            defineModelo(it.fileAR, it.idModel)
            if (modelosInsertados.containsKey(it.idModel)) {
                modelosInsertados[it.idModel] = modelosInsertados[it.idModel]!!.plus(1)
            } else {
                modelosInsertados[it.idModel] = 1
            }
            //catalogoFragment.visibility = View.VISIBLE
        })
    }

    private fun muestraCotiza() {
        CotizaDialog().show(supportFragmentManager, "Cotizacion")
    }

    private fun navegacionDeCatalogos() {
        bottomNavigate.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemLamparas -> {
                    setDefaultPlane()
                    arsesion?.setupPlaneFinding(0)
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
                    arsesion?.setupPlaneFinding(2)
                    Toast.makeText(this, "Adornos", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemColores -> {
                    Toast.makeText(this, "Colores", Toast.LENGTH_SHORT).show()
                    arsesion?.setupPlaneFinding(1)
                    muestraSugerencia()
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
        drawerLayout = findViewById(R.id.drawerLayout)
        val toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.abre, R.string.cierra)
        val botonMenu = findViewById<ImageView>(R.id.imgMenu)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val headervIew = navigationView.getHeaderView(0)
        val txtUser = headervIew.findViewById<TextView>(R.id.tvNombre)
        val txtHabit = headervIew.findViewById<TextView>(R.id.tvHabitacion)
        txtUser.text = nombre
        txtHabit.text = "Decorando: " + habitacion
        bottomNavigate = findViewById(R.id.bottomNav)
        bottomNavigate.visibility = View.VISIBLE
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
        bottomNavigate.visibility = View.INVISIBLE
        btnPhoto.visibility = View.INVISIBLE
        barraProgeso.visibility = View.GONE
        btnRemove.visibility = View.INVISIBLE
        fabCheck.visibility = View.INVISIBLE
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

    private fun defineModelo(modelURL: String, id: Int) {
        barraProgeso.visibility = View.VISIBLE
        ModelRenderable.builder()
            .setSource(
                this, RenderableSource.builder().setSource(
                    this,
                    Uri.parse(modelURL),
                    RenderableSource.SourceType.GLB
                )
                    .setScale(0.5f)
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build()
            )
            .setRegistryId(modelURL)
            .build()
            .thenAccept { renderable ->
                modelo = renderable
                barraProgeso.visibility = View.GONE
            }
            .exceptionally {
                val toast =
                    Toast.makeText(this, "Unable to load renderable $modelURL", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                null
            }
        arFragment?.setOnTapArPlaneListener { hitResult, _, _ ->
            if (modelo == null) {
                return@setOnTapArPlaneListener
            }
            //Crear Anchor
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment?.arSceneView?.scene)
            anchorNode.name = id.toString()
            //Crear transformable y añadir a Anchor
            val mod = TransformableNode(arFragment?.transformationSystem)
            mod.setParent(anchorNode)
            mod.renderable = modelo
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
        RoomsSelectDialog(ARCrearActivity().javaClass).show(
            supportFragmentManager,
            "Selecciona Habitacion"
        )
    }

    private fun removeObject(nodo: AnchorNode) {
        arFragment?.arSceneView?.scene?.removeChild(nodo)
        nodo.anchor?.detach()
        nodo.setParent(null)
        val modeloEliminar = modelosInsertados[nodo.name.toInt()]

        if (modeloEliminar!! > 0) {
            modelosInsertados[nodo.name.toInt()] =
                modelosInsertados[nodo.name.toInt()]!!.toInt() - 1
        } else {
            modelosInsertados.remove(nodo.name.toInt())
        }

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
        Toast.makeText(this, "Imagen Guardada Exitosamente!", Toast.LENGTH_SHORT).show()
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

    private fun muestraSugerencia() {
        val sharePref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        val colorUser = sharePref.getString(getString(R.string.color_key), "amarillo")
        SugerenciaPinturaDialog(colorUser!!).show(supportFragmentManager, "Sugerencia")
    }

}

