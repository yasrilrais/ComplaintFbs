package com.example.complaintfbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        initView()
    }

    private fun initView() {
        buttonComplaint.setOnClickListener {
            //berpindah ke activity MainActivity
            startActivity(Intent(this, MainActivity::class.java).apply {
            })
        }
    }
}