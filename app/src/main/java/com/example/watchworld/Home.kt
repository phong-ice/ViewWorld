package com.example.watchworld

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.watchworld.Adapter.AdapterPictureHome
import com.example.watchworld.Adapter.AdapterSearch
import com.example.watchworld.api.ApiConfig
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : AppCompatActivity() {

    lateinit var listPicture: ArrayList<com.example.watchworld.data.Picture>
    lateinit var adapterPicture: AdapterPictureHome
    lateinit var listTag: ArrayList<String>
    lateinit var listTagBackup: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        listPicture = ArrayList()
        listTagBackup = ArrayList()
        listTag = ArrayList()
        var api = ApiConfig.apiService
        adapterPicture = AdapterPictureHome(this, listPicture)
        lv_picture.apply {
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = adapterPicture
        }
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getAllPicture()
            if (response.isSuccessful) {
                var listPhotos = response.body()!!.photos.photo
                var id = 0
                for (picture in listPhotos){
                    if (picture.url_o != null) {
                        listPicture.add(picture)
                    }
                }
                changedApdater()
            }
        }

        initSearchAuto(this)
    }

    private fun initSearchAuto(context: Context) {
        val adapterSearch = AdapterSearch(this, listTag, listTagBackup)
        lv_search.apply {
            layoutManager = LinearLayoutManager(this@Home)
            adapter = adapterSearch
        }

        searchView.setOnSearchClickListener {
            lv_search.visibility = View.VISIBLE
            lv_picture.visibility = View.GONE
        }

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null || query.length == 0){

                }else{
                    var intent = Intent(context,Search::class.java)
                    intent.putExtra("searchPattern",query)
                    startActivity(intent)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSearch.filter.filter(newText)
                return false
            }
        })
        searchView.setOnCloseListener(object :
            androidx.appcompat.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                lv_search.visibility = View.GONE
                lv_picture.visibility = View.VISIBLE
                return false
            }
        })
    }

    private suspend fun changedApdater() {
        withContext(Main) {
            adapterPicture.notifyDataSetChanged()
            for (picture in listPicture) {
                if (!listTag.contains(picture.tags)){
                    listTag.add(picture.tags)
                }
            }
            listTagBackup.addAll(listTag)
            layout_splash.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        
    }

}