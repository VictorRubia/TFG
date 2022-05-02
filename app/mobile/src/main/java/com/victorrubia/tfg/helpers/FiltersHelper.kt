package com.victorrubia.tfg.helpers

import android.text.TextUtils
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

class FiltersHelper {

    /**
     * Returns true if email is valid
     */
    fun validateEmail(string : TextInputEditText): Boolean{
        var valid = !TextUtils.isEmpty(string.text) && Patterns.EMAIL_ADDRESS.matcher(string.text).matches()
        if (!valid) string.error = "Correo no válido"
        return valid
    }

    /**
     * Returns true if string is not empty
     * Returns false if string is empty
     */
    fun validatePassword(string: TextInputEditText): Boolean{
        var valid = string.text?.isNotEmpty()
        if(!valid!!) string.error = "Contraseña no válida"
        return valid
    }

}