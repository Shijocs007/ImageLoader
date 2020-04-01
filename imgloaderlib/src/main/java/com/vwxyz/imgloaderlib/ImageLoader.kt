package com.vwxyz.imgloaderlib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.HashMap

class ImageLoader(val context : Context) {

    internal var memoryCache = CacheMemory()
    internal val stub_id = R.drawable.ic_cloud_download_black_24dp
    private val imageViews = HashMap<ImageView, String>()

    internal var fileCache: CacheFile

    init {
        fileCache = CacheFile(context)
    }

    fun showImage(url: String, imageView: ImageView) {
        imageViews.put(imageView, url)
        val bitmap = memoryCache[url]
        if (bitmap != null)
            imageView.setImageBitmap(bitmap)
        else {
            queuePhoto(url, imageView)
            imageView.setImageResource(stub_id)
        }
    }

    private fun queuePhoto(url: String, imageView: ImageView) {
        val bitmap: Bitmap? = getBitmap(url)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            HTTPAsyncTask(url, imageView).execute()
        }
    }

    fun getBitmap(url: String): Bitmap? {
        val f = fileCache.getFile(url)
        val b = decodeFile(f)
        return b

    }

    fun getBitmapFromWeb(url: String): Bitmap? {
        //from web
        try {
            val f = fileCache.getFile(url)
            var bitmap: Bitmap? = null
            val imageUrl = URL(url)
            val conn = imageUrl.openConnection() as HttpURLConnection
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            conn.instanceFollowRedirects = true
            val `is` = conn.inputStream
            val os = FileOutputStream(f)
            Utils.CopyStream(`is`, os)
            os.close()
            bitmap = decodeFile(f)
            return bitmap
        } catch (ex: Throwable) {
            ex.printStackTrace()
            if (ex is OutOfMemoryError)
                memoryCache.clear()
            return null
        }
    }

    private fun decodeFile(f: File): Bitmap? {
        try {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, o)

            val REQUIRED_SIZE = 70
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }

            //decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
        } catch (e: FileNotFoundException) {
        }

        return null
    }

    inner class HTTPAsyncTask(var url: String, var imageView: ImageView) :
        AsyncTask<Void, Void, Bitmap>() {

        override fun doInBackground(vararg params: Void?): Bitmap? {
            return getBitmapFromWeb(url)
        }

        override fun onPostExecute(result: Bitmap?) {

            imageView?.setImageBitmap(result)
        }
    }

}