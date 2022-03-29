package com.victorrubia.tfg

import android.app.Activity
import android.content.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Looper
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.victorrubia.tfg.databinding.ActivityMainBinding
import data.Actividad
import data.Usuario
import helpers.RetrofitHelper
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.*
import kotlin.coroutines.CoroutineContext

private const val API_KEY_SENDER_CAPABILITY_NAME = "api_key_sender"

class MainActivity : Activity(), SensorEventListener, DataClient.OnDataChangedListener, CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding
    private var mSensorManager: SensorManager? = null
    private var mHR: Sensor? = null
    private var mHeartRate = 0
    private var measuring: Boolean = false
    var path: File? = null
    var file1: File? = null
    var medidas: String? = ""
    lateinit var queue : RequestQueue
    val TAG = "MyTag"
    var contador = 0
    val mediciones : MutableList<PPGMeasure> = arrayListOf()
    var api_key : String = ""
    lateinit var usuario : Usuario
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private lateinit var actividadActual : Actividad
    lateinit var sharedPref: SharedPreferences
    lateinit var obtenerUsuario : Job
    lateinit var dataClient: MessageClient
    private var transcriptionNodeId: String? = null
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // Get extra data included in the Intent
            val message = intent.getStringExtra("Status")
            Log.d("receiver", "Got message: " + message)
            api_key = message!!
            binding.button.isEnabled = true
            binding.ppg2.text = message

            obtenerUsuario = launch {
                // a potentially time consuming task
                with (sharedPref!!.edit()) {
                    putString("tfg_estres_api_key", message)
                    apply()
                }
                usuario = getUserID()
                try {
                    coroutineContext.job.join()
                } finally {
                    binding.loading.visibility = View.GONE
                    binding.loadingText.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mHR = mSensorManager!!.getDefaultSensor(65572);
        queue = Volley.newRequestQueue(this)

        binding.button.setOnClickListener{

            path = this.applicationContext.getExternalFilesDir(null)
            Log.d("PATH", path.toString())
            file1 = File(path, "ch1.txt")
            //file2 = File(path, "ch2.txt")

            if(measuring == false){
                startMeasure()
                binding.button.setText("APAGAR")
            }
            else{
                stopMeasure()
                binding.button.setText("ENCENDER")
            }

        }

        dataClient = Wearable.WearableOptions.Builder().setLooper(Looper.myLooper()).build().let { options ->
            Wearable.getMessageClient(this.applicationContext, options)
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter("API_KEY")
        )

        Thread {
            // a potentially time consuming task
            askForApiKey()
            requestTranscription()
        }.start()

        sharedPref = getSharedPreferences("MY_APP",MODE_PRIVATE)

        api_key = sharedPref.getString("tfg_estres_api_key", "ERROR")!!
        binding.button.isEnabled = true
        binding.ppg2.text = api_key
        obtenerUsuario = launch{
            usuario = getUserID()
            try {
                coroutineContext.job.join()
            } finally {
                binding.loading.visibility = View.GONE
                binding.loadingText.visibility = View.GONE
            }
        }


        // USAR WIFI PARA EL DESARROLLO LOCAL
        val connectivityManager = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // The Wi-Fi network has been acquired, bind it to use this network by default
                connectivityManager.bindProcessToNetwork(network)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // The Wi-Fi network has been disconnected
            }
        }
        connectivityManager.requestNetwork(
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build(),
            callback
        )
    }

    private fun askForApiKey() {
        val capabilityInfo: CapabilityInfo = Tasks.await(
            Wearable.getCapabilityClient(this.applicationContext)
                .getCapability(
                    API_KEY_SENDER_CAPABILITY_NAME,
                    CapabilityClient.FILTER_REACHABLE
                )
        )

        updateTranscriptionCapability(capabilityInfo)
    }

    private fun updateTranscriptionCapability(capabilityInfo: CapabilityInfo) {
        transcriptionNodeId = pickBestNodeId(capabilityInfo.nodes)
    }

    private fun pickBestNodeId(nodes: Set<Node>): String? {
        // Find a nearby node or pick one arbitrarily
        Log.d("NODES", nodes.firstOrNull { it.isNearby }?.id ?: nodes.firstOrNull()?.id.toString())
        return nodes.firstOrNull { it.isNearby }?.id ?: nodes.firstOrNull()?.id
    }

    private fun requestTranscription() {
        transcriptionNodeId?.also { nodeId ->
            val sendTask: Task<*> = Wearable.getMessageClient(this.applicationContext).sendMessage(
                nodeId,
                API_KEY_SENDER_CAPABILITY_NAME,
                ByteArray(0)
            ).apply {
                addOnSuccessListener { Log.d("MESSAGE SEND", "SUCCESS") }
                addOnFailureListener { Log.d("MESSAGE SEND", "FAILURE") }
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_DELETED) {
                Log.d(TAG, "DataItem deleted: " + event.dataItem.uri)
            } else if (event.type == DataEvent.TYPE_CHANGED) {
                Log.d(TAG, "DataItem changed: " + event.dataItem.uri)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == 65572) {
            contador++
//            val msg = event.values[0].toInt().toString()

            mHeartRate = (event.values[0]).toInt()

            binding.ppg.text = event.values[0].toInt().toString()
            //binding.ppg2.text = event.values[1].toInt().toString()
            mediciones.add(PPGMeasure(mHeartRate, Date()))
            if(contador == 500) {
                launch {
                    realizarPost(Json.encodeToString(mediciones))
                }
                contador = 0
            }

            //val formattedDate: String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale.getDefault()).format(Calendar.getInstance().time)

//            medidas += msg + ",${formattedDate}"+ "\n"
//
//            medidas2 += event.values[1].toInt().toString() + "\n"
            //Log.d( "PPG", msg)
        }
    }

    private suspend fun getUserID() : Usuario {
        var user = Usuario("-1","ERROR", "ERROR")
        try{
            val quotesApi = RetrofitHelper.getInstance().create(UsersAPI::class.java)
            val result = quotesApi.getUsers("Bearer ${api_key}")
            Log.d("USER ID", result.body().toString())
            user = result.body()!!
            binding.button.visibility = View.VISIBLE
            binding.ppg.visibility = View.VISIBLE
            binding.ppg2.visibility = View.VISIBLE
        }
        catch (e : ConnectException){
            binding.ppg2.text = "NO INTERNET"
            binding.cloudDown.visibility = View.VISIBLE
            binding.noCloud.visibility = View.VISIBLE
            binding.loadingText.visibility = View.GONE
            binding.loading.visibility = View.GONE
            getUserID()
        }
        catch (e : SocketTimeoutException){
            binding.noCloud.text = "SERVIDOR INACCESIBLE"
            binding.cloudDown.visibility = View.VISIBLE
            binding.noCloud.visibility = View.VISIBLE
            binding.loadingText.visibility = View.GONE
            binding.loading.visibility = View.GONE
            getUserID()
        }
        catch (e : IOException){
            binding.noCloud.text = "SERVIDOR INACCESIBLE"
            binding.cloudDown.visibility = View.VISIBLE
            binding.noCloud.visibility = View.VISIBLE
            binding.loadingText.visibility = View.GONE
            binding.loading.visibility = View.GONE
            getUserID()
        }
        obtenerUsuario.cancel()
        binding.loading.visibility = View.GONE
        binding.loadingText.visibility = View.GONE
        return user
    }

    private suspend fun crearActividad(){
        val quotesApi = RetrofitHelper.getInstance().create(ActividadAPI::class.java)
        try{
            actividadActual = quotesApi.addActivity("Bearer ${api_key}", (1..10)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString(""), usuario.id, Calendar.getInstance().time.toString()).body()!!
            Log.d("CREAR ACTIVIDAD", "SUCCESS")
            Log.d("ACTIVIDAD ID", actividadActual.id)
        }
        catch (e : Exception){
            Log.d("CREAR ACTIVIDAD", e.toString())
            binding.button.isEnabled = false
            binding.interrupted.visibility = View.VISIBLE
        }
        catch (e : SocketException){
            binding.button.isEnabled = false
            binding.interrupted.visibility = View.VISIBLE
        }

    }

    private suspend fun terminarActividad(){
        val quotesApi = RetrofitHelper.getInstance().create(ActividadAPI::class.java)

        try{
            actividadActual = quotesApi.updateActivity("Bearer ${api_key}", Calendar.getInstance().time.toString(), actividadActual.id).body()!!
            Log.d("TERMINAR ACTIVIDAD", "SUCCESS")
            Log.d("TERMINAR ACTIVIDAD ID", actividadActual.id)
        }
        catch (e : Exception){
            Log.d("TERMINAR ACTIVIDAD", "ERROR")
        }
        catch (e : SocketTimeoutException){
            Log.d("TERMINAR ACTIVIDAD", "ERROR")
        }
    }

    private fun startMeasure() {
        val sensorRegistered = mSensorManager!!.registerListener(this, mHR, SensorManager.SENSOR_DELAY_NORMAL)
        Log.d("Sensor Status:", " Sensor registered: " + if (sensorRegistered) "yes" else "no")

        launch{
            crearActividad()
        }
        if(sensorRegistered){
            measuring = true
        }
    }

    private fun stopMeasure() {
        mSensorManager!!.unregisterListener(this, mHR)
        measuring = false
        val stream1 = FileOutputStream(file1)
        //val stream2 = FileOutputStream(file2)

        launch{
            realizarPost(Json.encodeToString(mediciones))
            terminarActividad()
        }

//        try {
//            stream1.write(medidas!!.toByteArray())
//            //stream2.write(medidas2!!.toByteArray())
//        } finally {
//            stream1.close()
//            //stream2.close()
//        }
    }

    private fun getSensorManager(): SensorManager? {
        return getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onStop() {
        super.onStop()
        queue?.cancelAll(TAG)
    }

    override fun onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        if(measuring!!) {
            stopMeasure()
        }
        super.onDestroy()
    }

    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {}

    suspend fun realizarPost(data : String) {
        var huboError = false
        try{
            RetrofitHelper.getInstance().create(PPGMeasureAPI::class.java).addMeasure("Bearer ${api_key}", data, actividadActual.id)
            Log.d("REALIZAR POST", "DONE")
            var huboError = false
            binding.button.isEnabled = true
            binding.interrupted.visibility = View.GONE
        }
        catch (e : SocketTimeoutException){
            Log.d("REALIZAR POST", "ERROR")
            huboError = true
            Log.d("ERROR", e.toString())
            binding.button.isEnabled = false
            binding.interrupted.visibility = View.VISIBLE
        }
        catch (e : SocketException){
            Log.d("REALIZAR POST", "ERROR")
            huboError = true
            Log.d("ERROR", e.toString())
            binding.button.isEnabled = false
            binding.interrupted.visibility = View.VISIBLE
        }
        catch (e : UninitializedPropertyAccessException){
            crearActividad()
            huboError = true
            Log.d("ERROR", e.toString())

            binding.button.isEnabled = false
            binding.interrupted.visibility = View.VISIBLE
        }
        catch (e : Exception){
            huboError = true
            Log.d("ERROR", e.toString())
            binding.button.isEnabled = false
            binding.interrupted.visibility = View.VISIBLE
        }

        if(!huboError){
            mediciones.clear();
        }
    }



}