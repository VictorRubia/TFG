package com.victorrubia.tfg.presentation.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.home.HomeActivity
import com.victorrubia.tfg.presentation.logged.LoggedActivity
import javax.inject.Inject

class EntryActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: EntryViewModelFactory
    private lateinit var entryViewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).createEntrySubComponent().inject(this)

        entryViewModel = ViewModelProvider(this, factory).get(EntryViewModel::class.java)

        previouslyLogged()
    }

    private fun previouslyLogged(){
        val loggedPage = Intent(this, LoggedActivity::class.java)
        val homePage = Intent(this, HomeActivity::class.java)

        val responseLiveData = entryViewModel.getUser()
        responseLiveData.observe(this, Observer {
            if(it != null){
                startActivity(loggedPage)
            }
            else{
                startActivity(homePage)
            }
            finish()
        })
    }
}