package com.vwxyz.imagelaoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vwxyz.imagelaoder.databinding.ActivityMainBinding

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), IPhotosListner, KodeinAware  {

    override val kodein by kodein()

    private val factory : PhotosViewModelFactory by instance()
    lateinit var binding: ActivityMainBinding
    private var mAdapter: PhotosAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewmodel = ViewModelProviders.of(this, factory).get(MainViewmode::class.java)
        binding.viewmodel = viewmodel
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        viewmodel.iPhotosListner = this

        viewmodel.loadPhotosList()

    }


    override fun onSuccess(photosList: List<Photo>) {
        Log.d("shijo", "shijo")
        mAdapter = PhotosAdapter(this, photosList)
        binding.recyclerView?.setAdapter(mAdapter)

    }

    override fun onFailure(messsage: String?) {
        Log.d("shijo", "shijo")
        messsage?.let { toast(it) }
    }
}
