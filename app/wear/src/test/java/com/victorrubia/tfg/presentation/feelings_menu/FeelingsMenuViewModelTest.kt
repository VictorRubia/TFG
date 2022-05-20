package com.victorrubia.tfg.presentation.feelings_menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.repository.activity.FakeActivityRepository
import com.victorrubia.tfg.data.repository.tag.FakeTagRepository
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

//@RunWith(AndroidJUnit4::class)
class FeelingsMenuViewModelTest {
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: FeelingsMenuViewModel
//
//    @Before
//    fun setUp() {
//        val fakeActivityRepository = FakeActivityRepository()
//        val fakeTagRepository = FakeTagRepository()
//        val getCurrentActivityUseCase = GetCurrentActivityUseCase(fakeActivityRepository)
//        val addTagUseCase = AddTagUseCase(fakeTagRepository)
//        viewModel = FeelingsMenuViewModel(getCurrentActivityUseCase, addTagUseCase)
//    }
//
//    @Test
//    fun addTag_returnsRecord(){
//        val tag = Tag("tag1", Date(), 0)
//        viewModel.addTag(tag.tag, ArrayList<String>(), tag.tag, tag.tag)
//
//    }
}