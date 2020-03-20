package com.demo.popcornapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

/**
 * Container of the Application, holds the navigation fragment.
 * Also, it's the entry point of the application.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.activity_main)
    }
}