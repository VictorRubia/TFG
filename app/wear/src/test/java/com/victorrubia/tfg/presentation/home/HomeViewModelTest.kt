package com.victorrubia.tfg.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.activity.FakeActivityRepository
import com.victorrubia.tfg.data.repository.user.FakeUserRepository
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.domain.usecase.RequestUserUseCase
import com.victorrubia.tfg.domain.usecase.SaveUserUseCase
import getOrAwaitValue

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel : HomeViewModel
//
//    @Before
//    fun setUp() {
//        val fakeUserRepository = FakeUserRepository()
//        val fakeActivityRepository = FakeActivityRepository()
//        val newActivityUseCase = NewActivityUseCase(fakeActivityRepository)
//        val getUserUsecase = GetUserUseCase(fakeUserRepository)
//        val requestUserUsecase = RequestUserUseCase(fakeUserRepository)
//        val saveUserUseCase = SaveUserUseCase(fakeUserRepository)
//        viewModel = HomeViewModel(newActivityUseCase, requestUserUsecase, saveUserUseCase, getUserUsecase)
//    }
//
//    @Test
//    fun saveUser_returnTrue(){
//        val user = User("apiKey1")
//
//        val result = viewModel.saveUser(user).getOrAwaitValue()
//
//        assertThat(result).isTrue()
//    }
//
//    @Test
//    fun requestUser_returnTrue(){
//        val result = viewModel.requestUser().getOrAwaitValue()
//
//        assertThat(result).isTrue()
//    }

}