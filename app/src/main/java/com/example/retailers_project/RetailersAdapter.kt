package com.example.retailers_project

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RetailersAdapter(private val context: Context,val dis:String) : RecyclerView.Adapter<RetailersAdapter.RetailerViewHolder>(){
    private val retailerList: MutableList<Retailer> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetailerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.retailer_list_item, parent, false)
        return RetailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return retailerList.size
    }

    override fun onBindViewHolder(holder: RetailerViewHolder, position: Int) {
        val currentRetailer=retailerList[position]
        holder.shopname.text=currentRetailer.Shopname
        holder.address.text=currentRetailer.Address
        holder.phone.text="Ph ${currentRetailer.phone}"
        holder.shopimg.setOnClickListener{
             val intent=Intent(context,shop_page::class.java)
            intent.putExtra("uid",currentRetailer.uid)
            intent.putExtra("district",dis)
            context.startActivity(intent)

        }
    }
    inner class RetailerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
val shopname:TextView=itemView.findViewById(R.id.tvshopname)
        val address:TextView=itemView.findViewById(R.id.tvadddress)
        val shopimg:ImageView=itemView.findViewById(R.id.imgview)
        val phone:TextView=itemView.findViewById(R.id.tvphone)
    }
    fun setData(retailers: List<Retailer>) {
        retailerList.clear()
        retailerList.addAll(retailers)
        notifyDataSetChanged()
    }
}