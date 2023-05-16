package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class AddProductForm : AppCompatActivity() {

    lateinit var addProductBtn : Button
    lateinit var prodId: EditText
    lateinit var prodName: EditText
    lateinit var prodDesc: EditText
    lateinit var prodImageUrl : EditText

    private var mFirebaseDBInstance: FirebaseDatabase? = null
    private var mFirebaseDB: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_form)


        FirebaseApp.initializeApp(this)


        mFirebaseDBInstance = FirebaseDatabase.getInstance()
        mFirebaseDB = mFirebaseDBInstance?.getReference("Product")

        //mFirebaseDB?.setValue("Hello, World!")


        prodId = findViewById(R.id.add_product_Id)
        prodName = findViewById(R.id.add_name_Id)
        prodDesc = findViewById(R.id.add_desc_Id)
        prodImageUrl = findViewById(R.id.add_image_id)

        addProductBtn = findViewById(R.id.add_btn_id)

        addProductBtn.setOnClickListener {
            val addProduct = ShopAppData(prodId.text.toString(), prodName.text.toString(),prodDesc.text.toString(), prodImageUrl.text.toString());
            mFirebaseDB?.child(prodId.text.toString())?.setValue(addProduct)
            finish()

        }

    }
}