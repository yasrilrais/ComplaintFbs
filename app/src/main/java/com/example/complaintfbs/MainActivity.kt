package com.example.complaintfbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.complaintfbs.Const.PATH_COLLECTION
import com.example.complaintfbs.Const.PATH_TGL
import com.example.complaintfbs.model.Keluhan
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: FirestoreRecyclerAdapter<Keluhan, KeluhanViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mKeluhanCollection = mFirestore.collection(PATH_COLLECTION)
    private val mQuery = mKeluhanCollection.orderBy(PATH_TGL, Query.Direction.ASCENDING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setupAdapter()
    }

    private fun initView() {
        supportActionBar?.title = "Riwayat Keluhan"
        rv_firedb.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        fab_firedb.setOnClickListener {
            //berpindah ke activity CrudActivity untuk tambah data
            startActivity(Intent(this, CrudActivity::class.java).apply {
                putExtra(CrudActivity.REQ_EDIT, false)
            })
        }
    }

    private fun setupAdapter() {
        //set adapter yang akan menampilkan data pada recyclerview
        val options = FirestoreRecyclerOptions.Builder<Keluhan>()
            .setQuery(mQuery, Keluhan::class.java)
            .build()

        mAdapter = object : FirestoreRecyclerAdapter<Keluhan, KeluhanViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeluhanViewHolder {
                return KeluhanViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_keluhan, parent, false))
            }

            override fun onBindViewHolder(viewHolder: KeluhanViewHolder, position: Int, model: Keluhan) {
                viewHolder.bindItem(model)
                viewHolder.itemView.setOnClickListener {
                    showDialogMenu(model)
                }
            }
        }
        mAdapter.notifyDataSetChanged()
        rv_firedb.adapter = mAdapter
    }

    private fun showDialogMenu(users: Keluhan) {
        //dialog popup edit hapus
        val builder = AlertDialog.Builder(this@MainActivity, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val option = arrayOf("Edit", "Hapus")
        builder.setItems(option) { dialog, which ->
            when (which) {
                //0 -> untuk berpindah ke activity CrudActivity untuk edit dengan membawa data
                0 -> startActivity(Intent(this, CrudActivity::class.java).apply {
                    putExtra(CrudActivity.REQ_EDIT, true)
                    putExtra(CrudActivity.EXTRA_DATA, users)
                })
                1 -> showDialogDel(users.strId)
            }
        }
        builder.create().show()
    }

    private fun showDialogDel(strId: String) {
        //dialog pop delete
        val builder = AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle("Hapus Data")
            .setMessage("Yakin mau hapus?")
            .setPositiveButton(android.R.string.yes){dialog, which ->
                deleteById(strId)
            }
            .setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    private fun deleteById(id: String) {
        //menghapus data berdasarkan id
        mKeluhanCollection.document(id)
            .delete()
            .addOnCompleteListener { Toast.makeText(this, "Succes Hapus data", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(this, "Gagal Hapus data", Toast.LENGTH_SHORT).show() }
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }
}