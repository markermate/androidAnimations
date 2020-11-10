package com.telstra.animationdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.telstra.animationdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var animationTime: Long = 0
    private var animating = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        animationTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    }
}