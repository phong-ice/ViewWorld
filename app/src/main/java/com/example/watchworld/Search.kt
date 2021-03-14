package com.example.watchworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.watchworld.Adapter.AdapterPictureHome
import com.example.watchworld.api.ApiConfig
import com.example.watchworld.data.Picture
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Search : AppCompatActivity() {

    private lateinit var listPicture: ArrayList<Picture>
    private lateinit var adapterPicture: AdapterPictureHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchPatern: String = intent?.getStringExtra("searchPattern").toString()

        listPicture = ArrayList()
        adapterPicture = AdapterPictureHome(this, listPicture)

        lv_search_search.apply {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = adapterPicture
        }
        btn_backSearch.setOnClickListener {
            finish()
        }
        var api = ApiConfig.apiService

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getAllPicture()
            if (response.isSuccessful) {
                response.body()?.photos?.photo?.let {
                   for (picture in it){
                       if (picture.tags.contains(searchPatern) && picture.url_o != null){
                            listPicture.add(picture)
                       }
                   }
                }
            }
            changedNotification()
        }
    }

    private suspend fun changedNotification() {
        withContext(Main) {
            progressBar_Search.visibility = View.GONE
            if (listPicture.size == null || listPicture.size == 0){
                tv_empty_Search.visibility = View.VISIBLE
            }else{
                adapterPicture.notifyDataSetChanged()
                lv_search_search.visibility = View.VISIBLE
            }
        }
    }
}