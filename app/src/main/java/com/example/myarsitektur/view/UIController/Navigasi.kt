package com.example.navigasiku

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.MyArsitektur.view.FormSiswa
import com.example.MyArsitektur.view.TampilSiswa
import com.example.myarsitektur.model.DataJK.JenisK
import com.example.myarsitektur.viewmodel.SiswaViewModel

enum class Navigasi {
    Formulirku,
    Detail
}

@Composable
fun DataApp(
    navControlller: NavHostController = rememberNavController(),
    modifier: Modifier,
    viewModel: SiswaViewModel = viewModel ()
) {
    Scaffold { isiRuang ->
        val uiState = viewModel.statusUI.collectAsState()
        NavHost(
            navController = navControlller,
            startDestination = Navigasi.Formulirku.name,

            modifier = Modifier.padding(isiRuang)) {
            composable(route = Navigasi.Formulirku.name) {
                val konteks = LocalContext.current
                FormSiswa(
                    pilihanJK = JenisK.map{ id -> konteks.resources.getString(id)},
                    onSubmitButtonClicked = {
                        viewModel.setSiswa(it)
                        navControlller.navigate(Navigasi.Detail.name)
                    }
                )
            }
            // Halaman kedua → TampilData
            composable(route = Navigasi.Detail.name) {
                TampilSiswa (
                    statusUISiswa = uiState.value,
                    onBackButtonClicked = {
                        cancelAndBackToFormulirku(navControlller)
                    }
                )
            }
        }
    }
}

private fun cancelAndBackToFormulirku(
    navController: NavHostController
) {

    navController.popBackStack(
        Navigasi.Formulirku.name,
        inclusive = false
    )
}


