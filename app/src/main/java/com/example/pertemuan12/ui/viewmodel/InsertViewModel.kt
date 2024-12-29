package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.model.Mahasiswa
import com.example.pertemuan12.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val mhs: MahasiswaRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set
    fun updateInsertMhsState(insertUiEvent.InsertUiEvent){
        uiState=InsertUiState(insertUievent = InsertUiEvent)
    }

    suspend fun insertMhs(){
        viewModelScope.launch{
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val nim: String="",
    val nama: String="",
    val alamat: String= "",
    val jenisKelamin: String= "",
    val kelas: String= "",
    val angkatan: String = ""
)

fun Mahasiswa.toUiStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Mahasiswa.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)