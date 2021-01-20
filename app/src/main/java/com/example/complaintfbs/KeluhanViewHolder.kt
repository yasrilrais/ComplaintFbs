package com.example.complaintfbs

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.complaintfbs.model.Keluhan
import kotlinx.android.synthetic.main.item_list_keluhan.view.*

class KeluhanViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(users: Keluhan) {
        view.apply {
            //get data keluhan
            val keluhan = "Keluhan   : ${users.strKeluhan}"
            val tanggal = "Tanggal    : ${users.strTgl}"
            //set view
            tv_ket.text = keluhan
            tv_tgl.text = tanggal
        }
    }
}