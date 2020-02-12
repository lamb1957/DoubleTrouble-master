package com.example.doubletrouble

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.text.RelativeDateTimeFormatter
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_game.view.*
import kotlin.properties.Delegates

class CountDown:AsyncTask<Context,Int,Int>{
    private var count:Int
    private var countflg:Boolean = true
    var c:Canvas by Delegates.notNull<Canvas>()
    constructor(i:Int){
        this.count =i

    }

    override fun doInBackground(vararg p1: Context?): Int {
        for (i in 0..5 step 1) {

        }
        return 0
        }


    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }
    fun drawcanvas(){

    }

}