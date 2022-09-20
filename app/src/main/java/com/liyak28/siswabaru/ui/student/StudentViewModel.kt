package com.liyak28.siswabaru.ui.student

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.data.state.ViewState
import com.liyak28.siswabaru.repo.StudentsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepo: StudentsRepo
) : ViewModel() {

    val studentCreateState = MutableLiveData<ViewState<Boolean>>()

    // data
    var latitude: Double? = 0.0
    var longitude: Double? = 0.0
    var gender: String = ""
    var image: Bitmap? = null

    fun insertStudent(data: StudentsEntity) {
        viewModelScope.launch {
            try {

                if (data.name.isEmpty() ) {
                    studentCreateState.postValue(ViewState.Error("nama harap diisi"))
                } else if (data.phone.isEmpty()) {
                    studentCreateState.postValue(ViewState.Error("nomor telephone harap diisi"))
                } else if (data.address.isEmpty()) {
                    studentCreateState.postValue(ViewState.Error("alamat harap diisi"))
                } else if (data.gender.isEmpty()) {
                    studentCreateState.postValue(ViewState.Error("jenis kelamin harap dipilih"))
                } else if (data.lat == 0.0 || data.lng == 0.0) {
                    studentCreateState.postValue(ViewState.Error("lokasi harap diisi"))
                } else {
                    studentRepo.insertStudents(data)
                    studentCreateState.postValue(ViewState.Success(true))
                }

            } catch (error: Exception) {
                setLog("error : ${error.message}")
                studentCreateState.postValue(ViewState.Error("gagal menyimpan data"))
            }
        }
    }

    private fun setLog(msg: String) {
        Log.e("form", msg)
    }


}