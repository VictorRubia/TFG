package com.victorrubia.tfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victorrubia.tfg.R
import com.victorrubia.tfg.databinding.ActivityAboutBinding
import com.google.android.material.color.DynamicColors

class AboutActivity : AppCompatActivity() {

    lateinit var binding : ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyIfAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(findViewById(R.id.topAppBar))
        binding = ActivityAboutBinding.inflate(layoutInflater)
    }
}