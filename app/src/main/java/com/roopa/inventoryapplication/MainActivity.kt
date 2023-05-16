package com.roopa.inventoryapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    lateinit var text_view: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var drawerLayout: DrawerLayout

    private var mFirebaseDBInstance: FirebaseDatabase? = null
    private var mFirebaseDB: DatabaseReference? = null
    lateinit var hashList: HashMap<String, ShopAppData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        mFirebaseDBInstance = FirebaseDatabase.getInstance()
        mFirebaseDB = mFirebaseDBInstance?.getReference("Product")

        recyclerView = findViewById(R.id.recyclerview_id)
        text_view = findViewById(R.id.text_id)

        hashList = hashMapOf()

        drawerLayout = findViewById(R.id.drawer_layout_Id)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_open,
            R.string.navigation_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var navigation_view: NavigationView = findViewById(R.id.nav_view_id)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_product -> {
                    val intent = Intent(this, AddProductForm::class.java)
                    startActivity(intent)
                    true

                }
                R.id.show_product -> {
                    val intent = Intent(this, MainActivity::class.java)
                    //intent?.putExtra("Product", item_details_list)
                    startActivity(intent)
                    true

                }
                else -> {
                    false
                }
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showProducts(){

        val adapter = ShopRecyclerAdapter(this, hashList)

        if(!hashList.isNullOrEmpty()){
            println("I was inside")
            text_view.visibility= TextView.GONE
        }

        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView?.adapter = adapter


        mFirebaseDB?.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val person = snapshot.getValue<ShopAppData>()!!
                hashList?.put(person.id.toString(), person)
                if(!hashList.isNullOrEmpty()){
                    text_view.visibility= TextView.GONE
                }
                adapter.setData(hashList)

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                val person = snapshot.getValue<ShopAppData>()!!
                val personKey = snapshot.key
                hashList?.put(personKey!!,person)
                adapter.notifyDataSetChanged()

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                hashList?.remove(snapshot.key)
                adapter.notifyDataSetChanged()

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    override fun onResume() {
        super.onResume()
        if(hashList.isNullOrEmpty()){
            println("I was inside")
            text_view.text = "No products to show"
        }
        showProducts()

    }
}