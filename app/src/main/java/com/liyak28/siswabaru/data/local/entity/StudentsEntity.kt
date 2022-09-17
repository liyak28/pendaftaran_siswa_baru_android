package com.liyak28.siswabaru.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentsEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = 0,
    @ColumnInfo(name = "nisn")
    val nisn: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "nomor_kk")
    val nomorKk: String,
    @ColumnInfo(name = "asal_sekolah")
    val asalSekolah: String,
    @ColumnInfo(name = "akreditasi")
    val akreditasi: String,
    @ColumnInfo(name = "latitude")
    val lat: Double? = 0.0,
    @ColumnInfo(name = "longitude")
    val lng: Double? = 0.0,
)