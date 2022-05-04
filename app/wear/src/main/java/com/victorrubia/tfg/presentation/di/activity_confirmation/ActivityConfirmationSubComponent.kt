package com.victorrubia.tfg.presentation.di.activity_confirmation

import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationActivity
import dagger.Subcomponent

@ActivityConfirmationScope
@Subcomponent(modules = [ActivityConfirmationModule::class])
interface ActivityConfirmationSubComponent {
    fun inject(activityConfirmationActivity: ActivityConfirmationActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : ActivityConfirmationSubComponent
    }
}