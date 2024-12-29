package com.example.pertemuan12.repository

import com.example.pertemuan12.model.Mahasiswa
import com.example.pertemuan12.service_api.MahasiswaService
import okio.IOException

interface MahasiswaRepository {
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
    suspend fun getMahasiswa(): List<Mahasiswa>
    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)
    suspend fun deleteMahasiswa(nim: String)
    suspend fun getMahasiswabyNim(nim: String): Mahasiswa
}

class NetworkKontakRepository(
    private val kontakApiService: MahasiswaService
) : MahasiswaRepository {
    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        kontakApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        kontakApiService.updateMahasiswa(nim, mahasiswa)
    }

    override suspend fun deleteMahasiswa(nim: String) {
        try {
            val response = kontakApiService.deleteMahasiswa(nim)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kontak. HTTP Status code: ${response.code()}")
            } else {
                response.message() // Tetap dipanggil
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMahasiswa(): List<Mahasiswa> {
        return kontakApiService.getAllMahasiswa()
    }

    override suspend fun getMahasiswabyNim(nim: String): Mahasiswa {
        return kontakApiService.getMahasiswabyNim(nim)
    }
}
