package com.example.doubletrouble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    var name ="Sugi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bt:Button = findViewById(R.id.button)
        bt.setOnClickListener {
            onPlay(it)
        }



    }
    fun onPlay(view: View?){
        val intent = Intent(this,doubletrouble::class.java)
        intent.putExtra("name1",name)
        startActivity(intent)
    }

}
