package com.liyak28.siswabaru.ui.student

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
class StudentViewModel@Inject constructor(
    private val studentRepo: StudentsRepo
): ViewModel() {

    val studentCreateState = MutableLiveData<ViewState<Boolean>>()

    fun insertStudent(data: StudentsEntity) {
        viewModelScope.launch {
            try {

                if (data.name.isEmpty() || data.nisn.isEmpty() || data.akreditasi.isEmpty() || data.nomorKk.isEmpty() || data.asalSekolah.isEmpty()) {
                    studentCreateState.postValue(ViewState.Error("Harap Lengkapi Data"))
                } else {
                    studentRepo.insertStudents(data)
                    studentCreateState.postValue(ViewState.Success(true))
                }

            } catch (error: Exception) {
                studentCreateState.postValue(ViewState.Error("Gagal"))
            }
        }
    }

    fun updateStudent(data: StudentsEntity) {
        viewModelScope.launch {
            try {

                if (data.name.isEmpty() || data.nisn.isEmpty() || data.akreditasi.isEmpty() || data.nomorKk.isEmpty() || data.asalSekolah.isEmpty()) {
                    studentCreateState.postValue(ViewState.Error("Harap Lengkapi Data"))
                } else {
                    studentRepo.updateStudent(data)
                    studentCreateState.postValue(ViewState.Success(true))
                }

            } catch (error: Exception) {
                studentCreateState.postValue(ViewState.Error("Gagal"))
            }
        }
    }

}