package com.liyak28.siswabaru.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liyak28.siswabaru.R
import com.liyak28.siswabaru.common.utils.viewBinding
import com.liyak28.siswabaru.databinding.ActivityMainBinding
import com.liyak28.siswabaru.databinding.ActivitySplashBinding
import com.liyak28.siswabaru.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        GlobalScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }
}