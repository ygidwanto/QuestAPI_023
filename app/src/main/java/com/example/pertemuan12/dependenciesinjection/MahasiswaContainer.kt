package com.example.pertemuan12.dependenciesinjection

import com.example.pertemuan12.repository.MahasiswaRepository

interface AppContainer{
    val kontakRepository: MahasiswaRepository
}