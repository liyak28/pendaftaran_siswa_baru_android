package com.liyak28.siswabaru.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liyak28.siswabaru.data.const.LatLngSchool
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.data.model.LocationDistanceModel
import com.liyak28.siswabaru.data.state.ViewState
import com.liyak28.siswabaru.repo.StudentsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val studentRepo: StudentsRepo
) : ViewModel() {

    val studentAllState = MutableLiveData<ViewState<List<StudentsEntity>>>()

    fun getStudentAll() {

        studentAllState.postValue(ViewState.Loading())
        viewModelScope.launch {
            try {
                val response = studentRepo.getStudentAll()
                val sortedLocation = sortedDistanceLocation(response)
                studentAllState.postValue(ViewState.Success(sortedLocation))
            } catch (error: Exception) {
                studentAllState.postValue(ViewState.Error(error.message))
            }
        }
    }

    private fun sortedDistanceLocation(location: List<StudentsEntity>): List<StudentsEntity> {

        val distanceLocationList: MutableList<LocationDistanceModel> = ArrayList()

        location.forEach {
            val distance = getDistance(it.lat ?: 0.0, it.lng ?: 0.0)
            setLog("name : ${it.name}")
            setLog("distance : ${distance}")
            distanceLocationList.add(
                LocationDistanceModel(
                    distance,
                    it
                )
            )
        }

        var sortedDistance = distanceLocationList.sortedByDescending { it.distance }

        val sortedDistanceList: MutableList<StudentsEntity> = ArrayList()

        sortedDistance.forEach {
            sortedDistanceList.add(it.student)
        }

        return sortedDistanceList
    }

    private fun getDistance(lat: Double, lng: Double): Double {
        val theta = lng - LatLngSchool.LNG
        var dist = (Math.sin(deg2rad(lat))
                * Math.sin(deg2rad(LatLngSchool.LAT))
                + (Math.cos(deg2rad(lat))
                * Math.cos(deg2rad(LatLngSchool.LNG))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

//    private fun getSampleData(): List<StudentsEntity> {
//        return mutableListOf<StudentsEntity>(
//            StudentsEntity(
//                1, "", "Outlet Biru", "", "", "",
//                lat = -7.768795994165753,
//                lng = 110.40187898433689
//            ),
//            StudentsEntity(
//                1, "", "Malioboro", "", "", "",
//                lat = -7.790515709521777,
//                lng = 110.36595237432917
//            ),
//            StudentsEntity(
//                1, "", "Els Computer", "", "", "",
//                lat = -7.778032105693385,
//                lng = 110.37372950056336
//            ),
//        )
//    }

    private fun setLog(msg: String) {
        Log.e("main", msg)
    }


}