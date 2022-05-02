package com.victorrubia.tfg.presentation.di.recover_password

import com.victorrubia.tfg.presentation.recover_password.RecoverPasswordActivity
import dagger.Subcomponent

@RecoverPasswordScope
@Subcomponent(modules = [RecoverPasswordModule::class])
interface RecoverPasswordSubComponent {
    fun inject(recoverPasswordActivity: RecoverPasswordActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : RecoverPasswordSubComponent
    }
}