package com.example.complaintfbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        buttonLogin.setOnClickListener {
            //berpindah ke activity Homepage
            startActivity(Intent(this, Homepage::class.java).apply {
            })
        }
    }
}