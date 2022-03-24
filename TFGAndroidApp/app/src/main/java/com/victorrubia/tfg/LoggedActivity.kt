package com.victorrubia.tfg

import android.content.*
import android.os.Bundle
import android.os.Looper
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.google.android.material.color.DynamicColors
import com.victorrubia.tfg.databinding.MainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class LoggedActivity : AppCompatActivity(), CoroutineScope, CapabilityClient.OnCapabilityChangedListener, DataClient.OnDataChangedListener {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var viewModel : LoadingViewModel
    private val API_KEY_CAPABILITY_NAME = "api_key"
    lateinit var dataClient: MessageClient
    private var transcriptionNodeId: String? = null
    lateinit var apiKey : String

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // Get extra data included in the Intent
            val message = intent.getStringExtra("Status")
            Log.d("receiver", "Got message: " + message)

            Thread {
                // a potentially time consuming task
                setupApiKey()
                requestTranscription(apiKey!!.toByteArray())
            }.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = this.getSharedPreferences("MY_APP",Context.MODE_PRIVATE)
        apiKey = sharedPref.getString("tfg_estres_api_key", "ERROR")!!
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            LoadingViewModelFactory(apiKey!!)
        )
            .get(LoadingViewModel::class.java)
        DynamicColors.applyIfAvailable(this)
        val binding: MainBinding = DataBindingUtil.setContentView(this, R.layout.activity_logged)

        setContentView(binding.root)

        dataClient = Wearable.WearableOptions.Builder().setLooper(Looper.myLooper()).build().let { options ->
            Wearable.getMessageClient(this.applicationContext, options)
        }

        Thread {
            // a potentially time consuming task
            setupApiKey()
            requestTranscription(apiKey!!.toByteArray())
        }.start()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter("API_KEY_sender")
        )

        binding.also {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        job.cancel()
    }

    private fun setupApiKey() {
        val capabilityInfo: CapabilityInfo = Tasks.await(
            Wearable.getCapabilityClient(this.applicationContext)
                .getCapability(
                    API_KEY_CAPABILITY_NAME,
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
        if(nodes.firstOrNull { it.isNearby }?.isNearby ?: nodes.firstOrNull()?.isNearby != null){
            val textView = findViewById<View>(R.id.textFour) as TextView

            val imageSpan = ImageSpan(this, R.drawable.ic_baseline_check_circle_outline_24)
            val spannableString = SpannableString(textView.text)

            spannableString.setSpan(imageSpan, textView.text.length-1, textView.text.length, 0)

            textView.text = spannableString
        }
        else{
            val textView = findViewById<View>(R.id.textFour) as TextView

            val imageSpan = ImageSpan(this, R.drawable.ic_baseline_error_outline_24)
            val spannableString = SpannableString(textView.text)

            spannableString.setSpan(imageSpan, textView.text.length-1, textView.text.length, 0)

            textView.text = spannableString
        }
        return nodes.firstOrNull { it.isNearby }?.id ?: nodes.firstOrNull()?.id
    }

    private fun requestTranscription(api_key: ByteArray) {
        transcriptionNodeId?.also { nodeId ->
            val sendTask: Task<*> = Wearable.getMessageClient(this.applicationContext).sendMessage(
                nodeId,
                API_KEY_CAPABILITY_NAME,
                api_key
            ).apply {
                addOnSuccessListener { Log.d("COMMUNICATION", "SUCCESS") }
                addOnFailureListener { Log.d("COMMUNICATION", "FAILURE") }
            }
        }
    }

    fun ejemplo(item : MenuItem){
        val sharedPref = this.getSharedPreferences("MY_APP",Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("tfg_estres_api_key")
        editor.apply()

        this.startActivity(Intent(this, MainActivity::class.java).putExtra("loggin", "out"));
        finish()
    }

    override fun onCapabilityChanged(p0: CapabilityInfo) {
        Thread {
            // a potentially time consuming task
            setupApiKey()
            requestTranscription(apiKey!!.toByteArray())
        }.start()
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_DELETED) {
                Log.d("DATACHANGED", "DataItem deleted: " + event.dataItem.uri)
            } else if (event.type == DataEvent.TYPE_CHANGED) {
                Log.d("DATACHANGED", "DataItem changed: " + event.dataItem.uri)
            }
        }
    }

}