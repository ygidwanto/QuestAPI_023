package com.example.pertemuan12.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan12.MahasiswaApplications
import com.example.pertemuan12.dependenciesinjection.MahasiswaContainer
import com.example.pertemuan12.repository.MahasiswaRepository


object PenyediaViewModel {
    // Factory class to provide ViewModel instances
    val Factory= viewModelFactory {
        initializer { HomeViewModel(MahasiswaContainer().container.kontakRepository) }
        initializer { InsertViewModel(MahasiswaContainer().container.kontakRepository) }
    }
}
fun CreationExtras.MahasiswaContainer(): MahasiswaApplications =
    ((this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as MahasiswaApplications))