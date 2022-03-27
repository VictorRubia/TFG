package com.victorrubia.tfg

import android.animation.Animator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.google.android.material.color.DynamicColors
import com.google.android.material.snackbar.Snackbar
import com.victorrubia.tfg.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var binding: ActivityMainBinding
    lateinit var sharedPref: SharedPreferences
    lateinit var dataClient: MessageClient
    private var transcriptionNodeId: String? = null
    private val API_KEY_CAPABILITY_NAME = "api_key"

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPref = getSharedPreferences("MY_APP",MODE_PRIVATE)

        if(sharedPref!!.contains("tfg_estres_api_key")){
            startActivity(Intent(this, LoggedActivity::class.java))
            finish()
        }

        super.onCreate(savedInstanceState)
        DynamicColors.applyIfAvailable(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.boton.setOnClickListener {
            if(isValidEmail(binding.email?.text) && !(binding.password.text?.isEmpty()!!)) {
                getApiKey()
            }
            if(!isValidEmail(binding.email?.text)){
                binding.email?.error = "Correo no válido"
            }
            if(binding.password.text?.isEmpty()!!){
                binding.password.error = "Contraseña no válida"
            }
        }

        dataClient = Wearable.WearableOptions.Builder().setLooper(Looper.myLooper()).build().let { options ->
            Wearable.getMessageClient(this.applicationContext, options)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
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

    private fun hideKeyboard(v: View) {
        val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    fun aboutPage(view: View) {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    fun passwordRecoveryPage(view: View) {
        val intent = Intent(this, PasswordRecoveryActivity::class.java)
        startActivity(intent)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        Log.d("EMAIL TEST", (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()).toString())
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun getApiKey(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = String.format("http://victorrubia.com:3000/api/v1/users/get_api_key/?email=%s&password_digest=%s",binding.email?.text ,binding.password.text)
        val contextView = findViewById<View>(android.R.id.content).getRootView()

        hideKeyboard(contextView)

        binding.animationView?.visibility = View.VISIBLE
        binding.animationView?.setMinAndMaxFrame(0, 59)
        binding.animationView?.playAnimation()
        binding.animationView?.loop(true)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                binding.animationView?.setMinAndMaxFrame(59, 89)
                binding.animationView?.loop(false)

                val intent = Intent(this, LoggedActivity::class.java)
                with (sharedPref!!.edit()) {
                    putString("tfg_estres_api_key", response)
                    apply()
                }
                val bytes: ByteArray = response.toByteArray()
                //requestTranscription(bytes)
                binding.animationView?.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        Log.e("Animation:", "end")
                        //Your code for remove the fragment
                        startActivity(intent)
                        finish()
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                    }
                })
            },
            { error ->
                //binding.request.text = "That didn't work! %s".format(error.toString())
                // Custom animation speed or duration.
                binding.animationView?.setMinAndMaxFrame(90, 138)
                binding.animationView?.loop(false)
                Toast.makeText(this@MainActivity, "Usuario no encontrado!.", Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}