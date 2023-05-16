package com.roopa.inventoryapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase


class ShopRecyclerAdapter (val context: Context, var arrayList:HashMap<String, ShopAppData>) : RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder>() {

    private  var keys: MutableSet<String>? = arrayList.keys

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewlayout = LayoutInflater.from(parent.context).inflate(R.layout.grid_row_layout, parent, false)
        return ViewHolder(viewlayout)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val viewItem = arrayList.get(keys?.elementAt(position))

        holder.prodName.text = viewItem?.pName

        if(holder.prodImage != null){
            Glide.with(context)
                .load(viewItem?.pImage)
                .into(holder.prodImage)
        }
        else {
            holder.prodImage.setImageResource(R.drawable.ic_launcher_background)
        }

        holder.desc_page_btn.setOnClickListener {
                addDesc(viewItem?.id.toString())
        }

        holder.removeButton.setOnClickListener {

                arrayList?.remove(keys?.elementAt(position))
                FirebaseDatabase.getInstance().getReference("Product").child(keys?.elementAt(position)!!).removeValue()
                keys?.remove(keys?.elementAt(position))
                notifyDataSetChanged()

        }


    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val prodName: TextView = ItemView.findViewById(R.id.name_Id)
        val prodImage: ImageView = ItemView.findViewById(R.id.glide_image_id)
        val desc_page_btn : LinearLayout = ItemView.findViewById(R.id.linear_layout_id)
        val removeButton: ImageButton = ItemView.findViewById(R.id.delete_btn_id)

    }

    fun setData(data: HashMap<String, ShopAppData>){
        arrayList = data
        keys =arrayList.keys.toMutableSet()
        notifyDataSetChanged()
    }

    fun addDesc(id:String){

        val intent = Intent(context, ItemDescription::class.java)
        intent.putExtra("Id",id)
        context.startActivity(intent)
    }




}

