package com.victorrubia.tfg.helpers

import android.text.TextUtils
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

class FiltersHelper {

    /**
     * Returns true if email is valid
     */
    fun validateEmail(string : String) : Boolean{
        return !TextUtils.isEmpty(string) && Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }

    fun validateEmailTextInput(string : TextInputEditText): Boolean{
        val valid = validateEmail(string.text.toString())
        if (!valid) string.error = "Correo no válido"
        return valid
    }

    /**
     * Returns true if string is not empty
     * Returns false if string is empty
     */
    fun validatePassword(string : String) : Boolean{
        return string.isNotEmpty()
    }

    fun validatePasswordTextInput(string: TextInputEditText): Boolean{
        val valid = validatePassword(string.text.toString())
        if(!valid) string.error = "Contraseña no válida"
        return valid
    }

}