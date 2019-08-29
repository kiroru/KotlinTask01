package jp.kiroru.kotlintask01

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object WebApiManager {

    private val TAG = WebApiManager::class.java.simpleName

    private val BASEURL = "https://kiroru-inc.jp"
    private val FAILURE = 990
    private val ERROR_RESPONSE = 991

    private var api: WebApi? = null

    init {
        setup()
    }

    private fun setup() {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(WebApi::class.java)
    }

    //
    // Callback 受信時の処理を共通化し、Listener に変換する。
    //
    private class MyCallback<T>(private val listener: Listener<List<T>>) : Callback<List<T>> {

        override fun onResponse(call: Call<List<T>>, response: Response<List<T>>) {
            if (response.isSuccessful) {
                listener.completed(response.body()!!)
            } else {
                listener.error(ERROR_RESPONSE, "応答エラー： code=" + response.code())
            }
        }

        override fun onFailure(call: Call<List<T>>, t: Throwable) {
            listener.error(FAILURE, "失敗： " + t.localizedMessage)
        }

    }

    //
    // こんな感じで Listener を引数に返すメソッドを追加していくと良い。
    //
    fun getCountries(listener: Listener<List<CountryEntity>>) {
        Log.d(TAG, "=== getCountries ===")
        val call = api!!.getCountries()
        call.enqueue(MyCallback(listener))
    }

    interface Listener<T> {
        fun completed(entities: T)
        fun error(code: Int, description: String)
    }

}