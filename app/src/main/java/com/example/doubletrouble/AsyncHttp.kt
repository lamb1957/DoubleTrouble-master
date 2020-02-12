package com.example.doubletrouble


import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.MalformedURLException
import kotlin.properties.Delegates

class AsyncHttp: AsyncTask<String,Int, Boolean> {
    var urlConnection : HttpURLConnection by Delegates.notNull<HttpURLConnection>()
    var flg: Boolean = false

    var name: String = ""
    var score: Int = 0

    constructor(name: String, score: Int) {
        this.name = name
        this.score= score
    }

    override fun doInBackground(vararg content: String?): Boolean {
        var urlinput: String = "http://192.168.100.108/doubletrouble/post.php"
        try {
            var url: URL = URL(urlinput)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "POST"
            urlConnection.doOutput = true

            var postDataSample: String = "name=" + name + "&text=" + score
            var out: OutputStream = urlConnection.outputStream
            out.write(postDataSample.toByteArray())
            out.flush()
            out.close()
            Log.d("test", postDataSample)
            urlConnection.inputStream
            flg = true
        }catch (e: MalformedURLException){
            e.printStackTrace()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return flg
    }

}