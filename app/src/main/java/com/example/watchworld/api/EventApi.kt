package com.example.watchworld.api

import com.example.watchworld.data.Photos
import com.example.watchworld.data.Picture
import com.example.watchworld.data.Respone
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface EventApi {
    @GET("services/rest/?method=flickr.favorites.getList&api_key=314d7fa47b850de6c117d6505152cffa&user_id=191169417%40N03&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+original_format%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+o_dims%2C+views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=999&page=1&format=json&nojsoncallback=1")
      suspend fun getAllPicture():Response<Respone>
}