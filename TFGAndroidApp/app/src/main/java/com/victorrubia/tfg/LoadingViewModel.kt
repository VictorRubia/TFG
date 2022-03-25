package com.victorrubia.tfg

import android.util.Log
import androidx.lifecycle.*
import data.Usuario
import helpers.RetrofitHelper
import kotlinx.coroutines.launch

class LoadingViewModelFactory(private val apiKey: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoadingViewModel(apiKey) as T
    }
}

class LoadingViewModel(api: String) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _usuario : MutableLiveData<Usuario> = MutableLiveData(Usuario("", ""))
    var apiKey : String = api

    val loading: LiveData<Boolean>
        get() = _loading

    val usuario: LiveData<Usuario>
        get() = _usuario

    init {
        viewModelScope.launch {
            var state = true
//            while (true) {
                if (state) {
                    state = !state
                    //delay(ADDED_DELAY)
                    loadUsuario()
                    //delay(NORMAL_DELAY)
                    _loading.postValue(state)
                    //delay(ADDED_DELAY)
                } else {
                    state = !state
                    //delay(NORMAL_DELAY)
                    _loading.postValue(state)
                }
//            }
        }
    }

    private suspend fun loadUsuario() {
        // Do an asynchronous operation to fetch users.
        val quotesApi = RetrofitHelper.getInstance().create(UsuarioAPI::class.java)
        val result = quotesApi.getUsers("Bearer ${apiKey}")
        if (result != null)
        // Checking the results
            Log.d("ayush: ", result.body().toString())
        _usuario.postValue(result.body())
    }
    
    private fun loadWearables(){

    }

    private companion object {
        const val NORMAL_DELAY = 2500L
        const val ADDED_DELAY = 600L
    }

}