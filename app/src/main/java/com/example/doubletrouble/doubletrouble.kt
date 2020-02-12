package com.example.doubletrouble

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_play.*
import kotlin.properties.Delegates

class doubletrouble:AppCompatActivity(){
    var gamestartbt:Button by Delegates.notNull<Button>()
    var titlebt:Button by Delegates.notNull<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play)
        gamestartbt = findViewById(R.id.gamebutton)
        titlebt = findViewById(R.id.titlebutton)
        val name = intent.getStringExtra("name1")
        Log.d("TAG",name)


        gamestartbt.setOnClickListener { GamePlay(name) }
        titlebutton.setOnClickListener { finish() }

    }



    fun GamePlay(name:String){
        val intent1 = Intent(this,Game::class.java)
        intent1.putExtra("name1",name)
        Log.d("TAG","aaa")
        startActivity(intent1)
    }

}