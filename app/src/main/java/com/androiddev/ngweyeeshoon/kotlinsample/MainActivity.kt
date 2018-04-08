package com.androiddev.ngweyeeshoon.kotlinsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.androiddev.ngweyeeshoon.kotlinsample.adapter.DataAdapter
import com.androiddev.ngweyeeshoon.kotlinsample.data.Android
import com.androiddev.ngweyeeshoon.kotlinsample.retrofit.RequestInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), DataAdapter.Listener {
    override fun onItemClick(android: Android) {
        Toast.makeText(this, "${android.apiLevel} Clicked ", Toast.LENGTH_SHORT).show()
    }


    private val TAG = MainActivity::class.java.simpleName

    private val BASE_URL = "https://learn2crack-json.herokuapp.com"

    private var compositeDisposable: CompositeDisposable? = null

    private var mandroidList: ArrayList<Android>? = null

    private var adapter: DataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()

        initRecyclerView()

        loadJson()
    }


    private fun initRecyclerView() {
        rv_android_list.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_android_list.layoutManager = layoutManager

    }


    private fun loadJson() {

        val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)

        compositeDisposable?.add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))

    }

    private fun handleResponse(androidList: List<Android>) {
        mandroidList = ArrayList(androidList)
        adapter = DataAdapter(mandroidList!!, this)
        rv_android_list.adapter = adapter


    }

    private fun handleError(error: Throwable) {

        Log.d(TAG, error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }


}
