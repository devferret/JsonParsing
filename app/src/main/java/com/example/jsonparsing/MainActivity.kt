package com.example.jsonparsing

import android.os.NetworkOnMainThreadException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MainActivity : AppCompatActivity() {

    internal var client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //example api --> https://api.androidhive.info/contacts/
        //and library --> OkHttp

        try {
            run("https://api.androidhive.info/contacts/")
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun run(url: String) {
        val request = Request.Builder()
                .url(url)
                .build()

        //Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //Log.e("JSON", response.body().string());
                displayView(response.body()!!.string())
            }
        })
    }

    private fun displayView(data: String) {
        try {
            val jsObject = JSONObject(data)

            val jsArray = jsObject.getJSONArray("contacts")

            Log.e("#1 ID", jsArray.getJSONObject(1).getString("id"))
            Log.e("#1 NAME", jsArray.getJSONObject(1).getString("name"))
            Log.e("#1 EMAIL", jsArray.getJSONObject(1).getString("email"))
            Log.e("#1 ADDRESS", jsArray.getJSONObject(1).getString("address"))
            Log.e("#1 GENDER", jsArray.getJSONObject(1).getString("gender"))
            Log.e("#1 MOBILE", jsArray.getJSONObject(1).getJSONObject("phone").getString("mobile"))
            Log.e("#1 HOME", jsArray.getJSONObject(1).getJSONObject("phone").getString("home"))
            Log.e("#1 OFFICE", jsArray.getJSONObject(1).getJSONObject("phone").getString("office"))


        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}
