package com.ehaque.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ehaque.R

/**
 * Property of Project2, Inc @ 2023 All Rights Reserved.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var mStarterIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mStarterIntent = intent
    }

    fun restartApp() {
        finish()
        startActivity(mStarterIntent)
    }
}