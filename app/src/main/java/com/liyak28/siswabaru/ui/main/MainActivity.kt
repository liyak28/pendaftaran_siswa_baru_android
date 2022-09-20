package com.liyak28.siswabaru.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.liyak28.siswabaru.R
import com.liyak28.siswabaru.common.extension.gone
import com.liyak28.siswabaru.common.extension.visible
import com.liyak28.siswabaru.common.utils.viewBinding
import com.liyak28.siswabaru.data.local.entity.StudentsEntity
import com.liyak28.siswabaru.data.state.ViewState
import com.liyak28.siswabaru.databinding.ActivityMainBinding
import com.liyak28.siswabaru.ui.student.StudentCreateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    val viewModel by viewModels<MainViewModel>()

    private val studentAdapter by lazy(LazyThreadSafetyMode.NONE) {
        StudentAdapter(
            ::onClickStudentAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        startObserve()
        viewModel.getStudentAll()

    }

    private fun initView() {

        binding.btnCreateStudent.setOnClickListener {
            startActivity(Intent(this, StudentCreateActivity::class.java))
        }

        with(binding.rvStudent) {
            adapter = studentAdapter
            apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

    }

    private fun startObserve() {

        viewModel.studentAllState.observe(this) {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    if (it.data.isEmpty()) {
                        binding.viewEmpty.emptyView.visible()
                    } else {
                        binding.viewEmpty.emptyView.gone()
                        studentAdapter.clear()
                        studentAdapter.insertAll(it.data)
                    }
                }
                is ViewState.Error -> {
                    binding.viewEmpty.emptyView.visible()
                }
            }
        }

    }

    private fun onClickStudentAdapter(data: StudentsEntity) {

    }

}