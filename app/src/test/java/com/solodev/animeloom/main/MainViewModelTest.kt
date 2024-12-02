package com.solodev.animeloom.main
import com.solodev.animeloom.data.manager.RouteManager
import com.solodev.animeloom.domain.usecase.AppEntryUseCases
import com.solodev.animeloom.presentation.MainViewModel
import com.solodev.animeloom.presentation.navgraph.Route
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest{

    private val appEntryUseCases: AppEntryUseCases = mockk()
    private lateinit var viewModel: MainViewModel
    private val routeManager: RouteManager = mockk(relaxUnitFun = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { appEntryUseCases.readAppEntry() } returns flowOf(true)
        viewModel = MainViewModel(appEntryUseCases, routeManager)
    }

    @Test
    fun `test checkAppEntry with startHomeScreen true`() = runTest {
        coEvery { appEntryUseCases.readAppEntry() } returns flowOf(true)

        viewModel.checkAppEntry()

        advanceUntilIdle()
        assertEquals(Route.AnimesNavigation.route, viewModel.startDestination)
        assertEquals(false, viewModel.splashCondition)
    }

    @Test
    fun `test checkAppEntry with startHomeScreen false`() = runTest {
        coEvery { appEntryUseCases.readAppEntry() } returns flowOf(false)

        viewModel.checkAppEntry()

        advanceUntilIdle()
        assertEquals(Route.AppStartNavigation.route, viewModel.startDestination)
        assertEquals(false, viewModel.splashCondition)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}