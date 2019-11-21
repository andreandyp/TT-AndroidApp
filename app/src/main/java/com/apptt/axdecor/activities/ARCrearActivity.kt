package com.apptt.axdecor.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.apptt.axdecor.R
import com.apptt.axdecor.dialogs.CotizaDialog
import com.apptt.axdecor.dialogs.RoomsSelectDialog
import com.apptt.axdecor.dialogs.SugerenciaPinturaDialog
import com.apptt.axdecor.domain.Paint
import com.apptt.axdecor.fragments.*
import com.apptt.axdecor.utilities.ARCoreUtils
import com.apptt.axdecor.viewmodels.ARViewModel
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
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

class ARCrearActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var barraDummy: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private var arFragment: ArFragment? = null
    private var modelo: ModelRenderable? = null
    private var arsesion: Session? = null
    private var mUserRequestedInstall = true
    private lateinit var viewModel: ARViewModel
    private var catalogoFragment: Fragment? = null
    private var modelosInsertados: HashMap<Int, Int> = hashMapOf()
    private var pinturaInsertada: Paint? = null
    private lateinit var fabCatalogo: FloatingActionButton
    private var catalogoAbierto = false


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, ARViewModel.Factory(application, 0))
            .get(ARViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        val stub = findViewById<ViewStub>(R.id.stub)
        stub.layoutResource = R.layout.activity_arcrear
        stub.inflate()
        val acciontv = findViewById<MaterialTextView>(R.id.tvTituloAccion)
        acciontv.text = "Crear Estilo"
        if (!ARCoreUtils.checkIsSupportedDeviceOrFinish(this)) {
            return
        }
        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment?
        val botonFoto = findViewById<View>(R.id.btnPhoto)
        botonFoto.setOnClickListener { takePhoto() }
        fabCheck.setOnClickListener { muestraCotiza() }
        inicializaNavigationDrawer()

        catalogoFragment = supportFragmentManager.findFragmentById(R.id.catalogo_ra)
        barraDummy = findViewById(R.id.barraDummy)

        barraDummy.visibility = View.INVISIBLE

        catalogoFragment?.view?.visibility = View.GONE
        fabCatalogo = findViewById(R.id.fabCatalogo)
        // Deshabilitado temporalmente por bug
        /*arFragment?.arSceneView?.scene?.addOnUpdateListener {
            val arframe = arFragment?.arSceneView?.arFrame
            val tracks = arframe?.getUpdatedTrackables(Plane::class.java)
            tracks?.forEach {
                if (it.trackingState == TrackingState.TRACKING && !catalogoAbierto) {
                    arFragment?.planeDiscoveryController?.hide()
                    catalogoFragment?.view?.visibility = View.VISIBLE
                    botonFoto.visibility = View.GONE
                    catalogoAbierto = true
                }
            }
        }*/

        fabCatalogo.setOnClickListener {
            catalogoAbierto = !catalogoAbierto
            it as ImageView
            if (catalogoAbierto) {
                catalogoFragment?.view?.visibility = View.VISIBLE
                botonFoto.visibility = View.GONE
                it.setImageResource(R.drawable.borraobjeto)
            } else {
                catalogoFragment?.view?.visibility = View.GONE
                botonFoto.visibility = View.VISIBLE
                it.setImageResource(android.R.drawable.ic_input_add)
            }
        }

        viewModel.modeloAR.observe(this, androidx.lifecycle.Observer {
            catalogoAbierto = false
            catalogoFragment?.view?.visibility = View.GONE
            botonFoto.visibility = View.VISIBLE
            fabCatalogo.setImageResource(android.R.drawable.ic_input_add)
            defineModelo(it.fileAR, it.idModel)
            val toast =
                Toast.makeText(this, "Toque el sitio para colocar elemento.", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 250)
            toast.show()
            catalogoFragment?.findNavController()?.navigateUp()
        })

        viewModel.piso.observe(this, androidx.lifecycle.Observer {
            catalogoAbierto = false
            catalogoFragment?.view?.visibility = View.GONE
            botonFoto.visibility = View.VISIBLE
            fabCatalogo.setImageResource(android.R.drawable.ic_input_add)
            changeFloorTexture(it.file2D!!, it.idModel)
            catalogoFragment?.findNavController()?.navigateUp()
        })

        viewModel.pinturaAR.observe(this, androidx.lifecycle.Observer { pintura ->
            catalogoAbierto = false
            catalogoFragment?.view?.visibility = View.GONE
            botonFoto.visibility = View.VISIBLE
            fabCatalogo.setImageResource(android.R.drawable.ic_input_add)
            pinturaInsertada = pintura
            pintarPared(pintura.hexCode)
            catalogoFragment?.findNavController()?.navigateUp()
        })

        // Al darle a las categorías de la barra de abajo del catálogo, se cambia el plane así como lo tenías
        viewModel.modoDecoracion.observe(this, androidx.lifecycle.Observer {
            if (it == 1) {
                muestraSugerencia() //Pinturas
            }
            if (it == 0) {
                setDefaultPlane()
            }
            arsesion?.setupPlaneFinding(it)
        })
        val sharePref = this.getSharedPreferences(
            getString(R.string.preference_file_key_datos),
            Context.MODE_PRIVATE
        ) ?: return
        if (sharePref.getInt(getString(R.string.anim2_key), 0) == 0) {
            animaciones()
            with(sharePref.edit()) {
                putInt(getString(R.string.anim2_key), 1)
                commit()
            }
        }

    }

    private fun pintarPared(hexCode: String) {
        val sampler = Texture.Sampler.builder()
            .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
            .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
            .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
            .build()

        val trigrid = Texture.builder()
            .setSource(creaBitmap(hexCode))
            .setSampler(sampler)
            .build()

        arFragment?.arSceneView?.scene?.addOnUpdateListener {
            val planeRenderer = arFragment?.arSceneView?.planeRenderer
            planeRenderer?.material?.thenAcceptBoth(trigrid) { material: Material?, texture: Texture? ->
                material?.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)
                material?.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS, 50f)
            }
        }
        arsesion?.setupPlaneFinding(1)
    }

    private fun creaBitmap(color: String): Bitmap {
        val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.parseColor(color))
        return bitmap
    }

    private fun muestraCotiza() {
        CotizaDialog(modelosInsertados, pinturaInsertada).show(supportFragmentManager, "Cotizacion")
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
        txtHabit.text = "Decorando: ${habitacion}"
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
            R.id.itemContactoProv -> {
                navigateToFragment(ListaProveedoresFragment())
            }
        }
        btnPhoto.visibility = View.INVISIBLE
        barraProgeso.visibility = View.GONE
        btnRemove.visibility = View.INVISIBLE
        fabCheck.visibility = View.INVISIBLE
        fabCatalogo.visibility = View.INVISIBLE
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
        //Asegurarse que Google Play Sevices para AR esta instalado y actualizado
        try {
            if (arsesion == null) {
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        arsesion = Session(this)
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        mUserRequestedInstall = false
                    }
                }
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
            return
        }
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
                    //.setScale(0.4f)
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
            if (modelosInsertados.containsKey(id)) {
                modelosInsertados[id] = modelosInsertados[id]!!.plus(1)
            } else {
                modelosInsertados[id] = 1
            }

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
            if (modelosInsertados[nodo.name.toInt()] == 0) {
                modelosInsertados.remove(nodo.name.toInt())
            }
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

    private fun changeFloorTexture(url: String, id: Int) {
        val sampler = Texture.Sampler.builder()
            .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
            .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
            .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
            .build()

        val trigrid = Texture.builder()
            .setSource(this, Uri.parse(url))
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

        if (modelosInsertados.containsKey(id)) {
            modelosInsertados[id] = modelosInsertados[id]!!.plus(1)
        } else {
            modelosInsertados[id] = 1
        }
    }

    private fun setDefaultPlane() {
        val sample = Texture.Sampler.builder()
            .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
            .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
            .build()
        Texture.builder()
            .setSource(this, R.drawable.circle)
            .setSampler(sample)
            .build()
            .thenAccept { texture ->
                arFragment?.arSceneView?.planeRenderer?.material?.thenAccept { material ->
                    material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)
                    material.setFloat(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS, 10f)
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

    private fun animaciones() {
        val secuencia = TapTargetSequence(this)
            .targets(
                TapTarget.forView(
                    findViewById(R.id.barraDummy),
                    "¡Elige lo que te gusta!",
                    "Selecciona una categoría y mira el catálogo de modelos diponibles. RECUERDA: Pulsa sobre un modelo y oprime el botón 'Colocar' para seleccionarlo."
                )
                    .cancelable(false)
                    .transparentTarget(true)
                    .targetCircleColor(R.color.colorAccent)
                    .drawShadow(true)
                    .targetRadius(25)
                    .outerCircleAlpha(0.96f)
                    .outerCircleColor(R.color.colorPrimary)
                    .id(1),
                TapTarget.forView(
                    findViewById<FloatingActionButton>(R.id.btnPhoto),
                    "¡Que no se te vaya este diseño!",
                    "Toma una captura y guardalo en la galería para poder compartirlo :D"
                )
                    .cancelable(false)
                    .transparentTarget(true)
                    .targetCircleColor(R.color.colorAccent)
                    .drawShadow(true)
                    .targetRadius(55)
                    .outerCircleAlpha(0.96f)
                    .outerCircleColor(R.color.colorPrimary)
                    .id(2),
                TapTarget.forView(
                    findViewById<FloatingActionButton>(R.id.fabCheck),
                    "¿Terminaste tu decoración?",
                    "Elige ver tus capturas realizadas o hacer la cotización de tu decoración."
                )
                    .cancelable(false)
                    .transparentTarget(true)
                    .targetCircleColor(R.color.colorAccent)
                    .drawShadow(true)
                    .targetRadius(55)
                    .outerCircleAlpha(0.96f)
                    .outerCircleColor(R.color.verdeCheck)
                    .id(3),
                TapTarget.forView(
                    findViewById<FloatingActionButton>(R.id.fabCatalogo),
                    "Escoge tu modelo y colócalo",
                    "Mira todos los modelos disponibles dentro de nuestro catálogo de modelos. Combina estilos y modelos hasta crear la decoración que más te agrade."
                )
                    .cancelable(false)
                    .transparentTarget(true)
                    .targetCircleColor(R.color.colorAccent)
                    .drawShadow(true)
                    .targetRadius(55)
                    .outerCircleAlpha(0.96f)
                    .outerCircleColor(R.color.colorAccent)
                    .id(4)
            )
        secuencia.considerOuterCircleCanceled(true)
        secuencia.continueOnCancel(true)
        secuencia.start()
    }
}