package com.victorrubia.tfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.color.DynamicColors
import com.google.android.material.textfield.TextInputEditText
import com.victorrubia.tfg.databinding.ActivityPasswordRecoveryBinding
import helpers.RetrofitHelper
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class PasswordRecoveryActivity : AppCompatActivity(), CoroutineScope {

    lateinit var binding : ActivityPasswordRecoveryBinding
    lateinit var email : TextInputEditText

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyIfAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)
        setSupportActionBar(findViewById(R.id.topAppBar))
        binding = ActivityPasswordRecoveryBinding.inflate(layoutInflater)

        val btn_click_me = findViewById(R.id.send_email_button) as Button
        var animacion = findViewById(R.id.animacion_check_mail) as LottieAnimationView
        email = findViewById(R.id.email_recover_password) as TextInputEditText

        btn_click_me.setOnClickListener {
            animacion.visibility = View.VISIBLE
            animacion.playAnimation()
            launch {
                sendInfo()
            }
        }


//        binding.sendEmailButton.setOnClickListener{

//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun sendInfo() {
        // Do an asynchronous operation to fetch users.
        Log.d("ENVIANDO CORREO", email.text.toString())
        val quotesApi = RetrofitHelper.getInstance().create(UsuarioAPI::class.java)
        val result = quotesApi.recoverPassword(email.text.toString())
    }

}