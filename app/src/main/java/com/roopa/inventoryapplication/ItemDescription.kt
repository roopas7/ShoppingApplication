package com.roopa.inventoryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ItemDescription : AppCompatActivity() {
    private var mFirebaseDBInstance: FirebaseDatabase? = null
    private var mFirebaseDB: DatabaseReference? = null

    lateinit var img_view: ImageView
    lateinit var txt_view : TextView
    lateinit var name_view : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_description)

        mFirebaseDBInstance = FirebaseDatabase.getInstance()
        mFirebaseDB = mFirebaseDBInstance?.getReference("Product")


        val id = intent.getStringExtra("Id")

        img_view = findViewById(R.id.img_desc_Id)
        txt_view = findViewById(R.id.item_desc_Id)
        name_view = findViewById(R.id.item_name_id)

        mFirebaseDB?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val person = dataSnapshot.child(id.toString()).getValue<ShopAppData>()

                Glide.with(this@ItemDescription).load(person?.pImage).into(img_view)

                name_view.text = "Name - "+person?.pName
                txt_view.text = "Description - "+person?.pDesc

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }


        })

    }
}