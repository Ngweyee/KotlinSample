package com.androiddev.ngweyeeshoon.kotlinsample.retrofit

import com.androiddev.ngweyeeshoon.kotlinsample.data.Android
import io.reactivex.Observable
import retrofit2.http.GET

interface RequestInterface {

    @GET("api/android")
    fun getData() : Observable<List<Android>>


}