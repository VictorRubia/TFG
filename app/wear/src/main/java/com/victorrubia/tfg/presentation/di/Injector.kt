package com.victorrubia.tfg.presentation.di

import com.victorrubia.tfg.presentation.di.home.HomeSubComponent

interface Injector {
    fun createHomeSubComponent() : HomeSubComponent
}