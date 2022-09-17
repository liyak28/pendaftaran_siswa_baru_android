package com.liyak28.siswabaru.data.model

import com.liyak28.siswabaru.data.local.entity.StudentsEntity

data class LocationDistanceModel(
    val distance: Double,
    val student: StudentsEntity
)