package com.liyak28.siswabaru.ui.main

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.databinding.ItemStudentBinding

class StudentAdapter(
    private val onClick: (StudentsEntity) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    var listData: MutableList<StudentsEntity> = ArrayList()

    fun insertAll(data: List<StudentsEntity>) {
        data.forEach {
            listData.add(it)
            notifyItemInserted(listData.size - 1)
        }
    }

    fun clear() {
        if (listData.isNotEmpty()) {
            listData.clear()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStudentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(listData[position])
    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(it: StudentsEntity) {

            with(binding) {
                tvName.text = it.name
                tvPhone.text = it.phone
                tvAddress.text = it.address
                tvGender.text = if (it.gender == "L") "Laki-laki" else "Perempuan"
                tvLocation.text = "latitude : ${it.lat}, longitude : ${it.lng}"

                val imageBitmap = BitmapFactory.decodeFile(it.image)
                binding.ivProfile.setImageBitmap(imageBitmap)

            }

        }

    }

}