package com.victorrubia.tfg.presentation.measuring_menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.activity.FakeActivityRepository
import com.victorrubia.tfg.data.repository.ppg_measure.FakePPGMeasureRepository
import com.victorrubia.tfg.data.repository.user.FakeUserRepository
import com.victorrubia.tfg.domain.usecase.*
import com.victorrubia.tfg.presentation.home.HomeViewModel
import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.random.Random

//@RunWith(AndroidJUnit4::class)
class MeasuringMenuViewModelTest {
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: MeasuringMenuViewModel
//
//    @Before
//    fun setUp() {
//        val fakeActivityRepository = FakeActivityRepository()
//        val fakePPGMeasureRepository = FakePPGMeasureRepository()
//        val endActivityUseCase = EndActivityUseCase(fakeActivityRepository)
//        val getCurrentActivityUseCase = GetCurrentActivityUseCase(fakeActivityRepository)
//        val savePPGMeasureUseCase = SavePPGMeasureUseCase(fakePPGMeasureRepository)
//        val endPPGMeasureUseCase = EndPPGMeasureUseCase(fakePPGMeasureRepository)
//        viewModel = MeasuringMenuViewModel(endActivityUseCase, getCurrentActivityUseCase, savePPGMeasureUseCase, endPPGMeasureUseCase)
//    }
//
//    @Test
//    fun startMeasure_savesMeasures(){
//        var ppgMeasures = List<PPGMeasure>(15){ PPGMeasure(Random.nextInt(0,30), Date()) }
//
////        viewModel.startMeasure(context = )
//    }
}