package com.example.watchworld.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.watchworld.Details
import com.example.watchworld.R
import com.example.watchworld.data.Picture
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_home.view.*
import kotlinx.android.synthetic.main.item_view_pager.view.*

class AdapterPictureHome(var context: Context, var listPicture: ArrayList<Picture>) :
    RecyclerView.Adapter<AdapterPictureHome.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img = view.img_item_list_home
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterPictureHome.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_home,parent,false)
        )
    }

    override fun getItemCount(): Int {
       return listPicture.size
    }

    override fun onBindViewHolder(holder: AdapterPictureHome.ViewHolder, position: Int) {
        Picasso.get().load(listPicture[position].url_m).into(holder.img)

        holder.itemView.setOnClickListener {
            var intent =Intent(context,Details::class.java)
            intent.putExtra("pos",position)
            intent.putExtra("listPicture",listPicture)
            context.startActivity(intent)
        }
    }


}