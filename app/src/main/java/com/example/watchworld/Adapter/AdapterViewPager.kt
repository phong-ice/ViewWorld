package com.example.watchworld.Adapter

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.watchworld.R
import com.example.watchworld.data.Picture
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_pager.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class AdapterViewPager(var context: Activity, var listPicture: ArrayList<Picture>?) :
    RecyclerView.Adapter<AdapterViewPager.ViewHolder>() {


    val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    val fromButton: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_button_anim
        )
    }
    val toButton: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_button_anim) }
    var downloadID: Long = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img = view.item_imgViewPager
        var btn_url_M = view.btn_download_urlM
        var btn_url_S = view.btn_download_urlS
        var btn_show = view.btn_show_download
        var heart = view.img_heart
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewPager.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listPicture!!.size
    }

    override fun onBindViewHolder(holder: AdapterViewPager.ViewHolder, position: Int) {

        var isClick: Boolean = false
        Log.i("position", position.toString())

        Picasso.get().load(listPicture?.get(position)?.url_o).into(holder.img)
        Log.i("position", position.toString())
        holder.btn_show.setOnClickListener {
            setVisiable(isClick, holder)
            setAnim(isClick, holder)
            isClick = !isClick
        }


        holder.btn_url_M.setOnClickListener {
            shareImage(listPicture?.get(position)?.url_o)
        }
        holder.btn_url_S.setOnClickListener {
            downLoadImg(listPicture?.get(position)?.url_o)
        }
    }


    private fun setVisiable(isClick: Boolean, holder: ViewHolder) {
        if (isClick) {
            holder.btn_url_M.visibility = View.GONE
            holder.btn_url_S.visibility = View.GONE
        } else {
            holder.btn_url_M.visibility = View.VISIBLE
            holder.btn_url_S.visibility = View.VISIBLE
        }
    }

    private fun setAnim(isClick: Boolean, holder: ViewHolder) {
        if (isClick) {
            holder.btn_url_M.startAnimation(toButton)
            holder.btn_url_S.startAnimation(toButton)
            holder.btn_show.startAnimation(rotateClose)
        } else {
            holder.btn_url_M.startAnimation(fromButton)
            holder.btn_url_S.startAnimation(fromButton)
            holder.btn_show.startAnimation(rotateOpen)
        }
    }


    private fun downLoadImg(path: String?) {

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var simpleFormat = SimpleDateFormat("yyyyMMddhhmmss")
            var date = simpleFormat.format(Date())
            var name = "VIEWWORLD${date}"
            var request = DownloadManager.Request(Uri.parse(path))
                .setTitle(name)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, ".jpg")

            var download: DownloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadID = download.enqueue(request)
            Toast.makeText(
                context,
                "Download complete \n ${Environment.DIRECTORY_PICTURES}",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            }
        }

    }

    private fun shareImage(path: String?) {
        var sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, path)
            type = "text/plain"
        }
        context.startActivity(sendIntent)
    }


}