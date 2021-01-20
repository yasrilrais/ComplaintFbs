package com.example.complaintfbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.complaintfbs.Const.PATH_COLLECTION
import com.example.complaintfbs.Const.setTimeStamp
import com.example.complaintfbs.model.Keluhan
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crud.*

class CrudActivity : AppCompatActivity() {
    companion object {
        //key untuk intent data
        const val EXTRA_DATA = "extra_data"
        const val REQ_EDIT = "req_edit"
    }
    private var isEdit = false
    private var keluhan: Keluhan? = null
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mKeluhanCollection = mFirestore.collection(PATH_COLLECTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)
        //mengambil data yang dibawa dari mainactivity sesuia keynya masing2
        isEdit = intent.getBooleanExtra(REQ_EDIT, false)
        keluhan = intent.getParcelableExtra(EXTRA_DATA)

        btn_save.setOnClickListener { saveData() }
        initView()
    }

    private fun initView() {
        //set view jika data di edit maka akan tampil pada form input
        if (isEdit) {
            btn_save.text = getString(R.string.update)
            ti_keluhan.text = Editable.Factory.getInstance().newEditable(keluhan?.strKeluhan)
            ti_tgl.text = Editable.Factory.getInstance().newEditable(keluhan?.strTgl)
        }
    }

    private fun saveData() {
        setData(keluhan?.strId)
    }

    private fun setData(strId: String?) {
        createUser(strId).addOnCompleteListener {
            if (it.isSuccessful) {
                if (isEdit) {
                    Toast.makeText(this, "Sukses perbarui data", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Sukses tambah data", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Gagal tambah data", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error Added data ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //fungsi untuk mengambil inputan data dan menyimpannya pada firestore
    private fun createUser(strId: String?): Task<Void> {
        val writeBatch = mFirestore.batch()
        val path = PATH_COLLECTION+setTimeStamp().toString() //exmp hasil : users-43287845
        val id = strId ?: path
        val keluhan = ti_keluhan.text.toString()
        val tanggal = ti_tgl.text.toString()

        val users = Keluhan(id, keluhan, tanggal)
        writeBatch.set(mKeluhanCollection.document(id), users) //menyimpan data dengan id yang sudah ditentukan
        return writeBatch.commit()
    }
}