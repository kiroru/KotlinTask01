package jp.kiroru.kotlintask01

import retrofit2.Call
import retrofit2.http.GET

interface WebApi {
    @GET("/share/scc2018/countries.json")
    fun getCountries(): Call<List<CountryEntity>>
}