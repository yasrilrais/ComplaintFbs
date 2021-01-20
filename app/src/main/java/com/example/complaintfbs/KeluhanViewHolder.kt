package com.example.complaintfbs

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.complaintfbs.model.Keluhan
import kotlinx.android.synthetic.main.item_list_keluhan.view.*

class KeluhanViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(users: Keluhan) {
        view.apply {
            //get data keluhan
            val tanggal = "Tanggal : ${users.strTgl}"
            val keluhan = "Keluhan : \n${users.strKeluhan}"
            //set view
            tv_tgl.text = tanggal
            tv_ket.text = keluhan

        }
    }
}