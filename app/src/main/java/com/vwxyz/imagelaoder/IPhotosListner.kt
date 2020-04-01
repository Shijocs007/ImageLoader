package com.vwxyz.imagelaoder


interface IPhotosListner {
    fun onSuccess(photosList: List<Photo>)
    fun onFailure(messsage : String?)
}