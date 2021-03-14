package com.example.watchworld

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.example.watchworld.Adapter.AdapterViewPager
import com.example.watchworld.api.ApiConfig
import com.example.watchworld.data.Picture
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Details : AppCompatActivity() {

    var listPicture: ArrayList<Picture>? = ArrayList()
    lateinit var adapterViewPager: AdapterViewPager
    var postion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        postion = intent.getIntExtra("pos", 0)
        listPicture = intent.getParcelableArrayListExtra("listPicture")
        btn_finish_details.setOnClickListener { finish() }
        adapterViewPager = AdapterViewPager(this, listPicture!!)
        viewPager.adapter = adapterViewPager
        viewPager.currentItem = postion
    }
}