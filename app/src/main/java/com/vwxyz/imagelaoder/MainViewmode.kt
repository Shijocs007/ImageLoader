package com.vwxyz.imagelaoder

import androidx.lifecycle.ViewModel
import com.example.kotlincoroutin.utils.Coroutines

class MainViewmode(private val myApi: MyApi) : ViewModel() {

    var iPhotosListner : IPhotosListner? = null

    fun loadPhotosList() {
        Coroutines.main{
            try {
                val response = myApi.getPhotos(1)
                response.body()?.let { iPhotosListner?.onSuccess(it) }
            } catch (e : ApiException) {
                iPhotosListner?.onFailure(e.message)
            } catch (e : NoInternetException) {
                iPhotosListner?.onFailure(e.message)
            }
        }
    }

}