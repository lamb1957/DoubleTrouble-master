package com.example.doubletrouble

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.properties.Delegates

class Game: AppCompatActivity(),SurfaceHolder.Callback{
    var text:TextView by Delegates.notNull<TextView>()
    var Playflg:Boolean = false
    var tapflg:Boolean = false
    var startBt:Button by Delegates.notNull<Button>()
    var countdownTx:TextView by Delegates.notNull<TextView>()
    var remaining_timeTx:TextView by Delegates.notNull<TextView>()
    var scoreTx:TextView by Delegates.notNull<TextView>()
    var targetImage:ImageView by Delegates.notNull<ImageView>()
    var rightBt:ImageButton by Delegates.notNull<ImageButton>()
    var leftBt:ImageButton by Delegates.notNull<ImageButton>()
    var playSequence:Int =0
    var lastImagename:String by Delegates.notNull<String>()
    var score:Int = 0
    var resImageRight:String by Delegates.notNull<String>()
    var resImageLeft:String by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val size = Point().also {
            (this.getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.apply { getSize(it) }
        }
        startBt = findViewById(R.id.start) as Button
        countdownTx = findViewById(R.id.count) as TextView
        remaining_timeTx = findViewById(R.id.remaining_time) as TextView
        scoreTx = findViewById(R.id.score) as TextView
        targetImage = findViewById(R.id.target) as ImageView
        rightBt = findViewById(R.id.rightbtn) as ImageButton
        leftBt = findViewById(R.id.leftbtn) as ImageButton
        lastImagename ="wasureteta"
        var name:String = "ユーザ名:"
            name = name + intent.getStringExtra("name1")
        textView2.setText(name)
        text = findViewById(R.id.count)
        startBt.setOnClickListener {
            finish()
        }
        rightBt.setOnClickListener {
            choiceFit("right")
        }
        leftBt.setOnClickListener {
            choiceFit("left")
        }


    }

    fun Play(){
       //var task:CountDown = CountDown(5)
        //task.execute(1)
        if (tapflg == false){
            tapflg = true
            dcd(count,3,"Start")
        }
        if(Playflg){
            when(playSequence){
                0-> {               //start up
                    Log.d("PLAY", "Play初期設定")
                    count.setVisibility(View.INVISIBLE)     //bye count
                    visibleObj()    //visible??
                    dcd(remaining_timeTx,30,"Play") //remaining time .. Here,set 30
                    playSequence = 1
                    Play()
                }
                1->{                //button Management
                    Log.d("PLAY","ボタン入れ替えました")
                    createQuestion()
                }
                2->{
                    //ここにスコアが入る
                    invisibleObj()
                    drawending()
                    //ああああああああああ
                }
            }

        }







    }
    fun drawending(){

        this.runOnUiThread(
            Runnable {
                db.setVisibility(View.VISIBLE)
                db.setText("お疲れさまでした データベースに登録します")
                val post: AsyncHttp = AsyncHttp("name", score )
                post.execute()
            })
    }
    fun createQuestion(){
        var resImageTarget:String
        var resPair = leftandrightChange()
        resImageTarget = randTargetChange()
        resImageLeft = resPair.first
        resImageRight = resPair.second


        this.runOnUiThread(
            Runnable {
                targetImage.setImageResource(getResources().getIdentifier(resImageTarget, "drawable", getPackageName()))
                leftBt.setImageResource(getResources().getIdentifier(resImageLeft, "drawable", getPackageName()))
                rightBt.setImageResource(getResources().getIdentifier(resImageRight, "drawable", getPackageName()))
            })
    }
    fun choiceFit(str:String){
        var Answer: Boolean = false
        var bluered: String = lastImagename.substring(0, 3) //加工
        if (str=="left") {

            when (bluered) {
                "blu" -> {
                    if (resImageLeft == "blue_blue" || resImageLeft == "red_blue") score++
                    else score--
                }
                "red" -> {
                    if (resImageLeft == "blue_red" || resImageLeft == "red_red") score++
                    else score--
                }
            }

        }else if (str == "right"){
            when (bluered) {
                "blu" -> {
                    if (resImageRight == "blue_blue" || resImageRight == "red_blue") score++
                    else score--
                }
                "red" -> {
                    if (resImageRight == "blue_red" || resImageRight == "red_red") score++
                    else score--
                }
            }
        } else Log.d("えらー","不正な引数")
        var scoreStr: String = "スコア:" + score.toString()
        this.runOnUiThread(
            Runnable {
                scoreTx.setText(scoreStr)
            })

        createQuestion()
    }
    fun randTargetChange():String{
        var tStr:String = ""
        for (i in 0..30) {
            Log.d("A",i.toString())
            when ((0 until 5).random()) {
                1 -> tStr = "blue_blue"
                2 -> tStr = "blue_red"
                3 -> tStr = "red_blue"
                4 -> tStr = "red_red"
            }
            Log.d("中身",tStr)
            if (tStr != lastImagename && (tStr =="blue_blue"||
                        tStr == "blue_red"||
                        tStr == "red_blue"||
                        tStr == "red_red")) {
                lastImagename = tStr
                return tStr
            }
        }
        return tStr

    }
    fun leftandrightChange():Pair<String,String>{
        var blue:String = ""
        var red: String = ""
        if(((0 until 5).random() % 2) == 0){                //偶数かどうかで完全ランダムで色と文字を指定　
            if(((0 until 5).random()% 2) == 0){         //文字は必ず両方存在する
                blue = "blue_blue"
                if(((0 until 5).random()% 2) == 0){ red = "blue_red" } else red ="red_red"

                }else {
                blue = "red_blue"
                if (((0 until 5).random() % 2) == 0) {red = "blue_red" } else red = "red_red"
            }
            }else{
            if(((0 until 5).random()% 2) == 0){             //2true
                blue = "blue_red"
                if(((0 until 5).random()% 2) == 0) {red ="blue_blue" }else red = "red_blue"
            }else blue = "red_red"
            if(((0 until 5).random()% 2) == 0) { red ="blue_blue"}else red = "red_blue"
        }
        return Pair(blue,red)
    }
    fun visibleObj(){
        this.runOnUiThread(
            Runnable {
                remaining_timeTx.setVisibility(View.VISIBLE)
                scoreTx.setVisibility(View.VISIBLE)
                targetImage.setVisibility(View.VISIBLE)
                rightBt.setVisibility(View.VISIBLE)
                leftBt.setVisibility(View.VISIBLE)
            })
    }
    fun invisibleObj(){
        this.runOnUiThread(
            Runnable {
                remaining_timeTx.setVisibility(View.INVISIBLE)
                targetImage.setVisibility(View.INVISIBLE)
                rightBt.setVisibility(View.INVISIBLE)
                leftBt.setVisibility(View.INVISIBLE)
            })
    }
    fun dcd(Tcount:TextView,i:Int,choice:String){             //count down for start up
        var c:Int=i
        Timer().schedule(0,1000,{
                Log.v("TAG", c.toString())
                Tcount.post(
                Runnable() {
                    run(){
                        if(c>=0){
                            Tcount.setText(c.toString())
                        }
                        c--
                    }
                }
            )

            if (c == -1 ) {
                when(choice){
                "Start"-> Playflg = true
                "Play" -> playSequence = 2
                }
                this.cancel()
                Play()
            }
        })
    }
   /* 記念品
   fun DrawStartCountDown(ti :Int){
        var count:Int = ti
        Timer().schedule(0, 1000, {
            Log.v("TAG", count.toString())
            var c: Canvas = countHolder.lockCanvas()
            c.drawColor(Color.WHITE)
            mPaint.setColor(Color.BLACK)
            var rect:Rect = Rect(50,50,50,50)
            c.drawRect(rect,mPaint)
            mPaint.setTextSize(60F)
            c.drawText(count.toString(),mWidth/2-30,60F,mPaint)
            countHolder.unlockCanvasAndPost(c)
            count--
            if (count ==-1 ) {
                this.cancel()
                tapflg = true
                Play()
            }
        })

    }
    fun DrawCountDown(ti :Int){
        var count:Int = ti
        Timer().schedule(0, 1000, {

            Log.v("TAG", count.toString())
            var c: Canvas = countHolder.lockCanvas()
            c.drawColor(Color.WHITE)
            mPaint.setColor(Color.BLACK)
            var rect:Rect = Rect(50,50,50,50)
            c.drawRect(rect,mPaint)
            mPaint.setTextSize(60F)
            if(count >=0)
                c.drawText(count.toString(),mWidth/2-30,60F,mPaint)
            countHolder.unlockCanvasAndPost(c)
            count--
            if (count ==-2 ) {
                this.cancel()
                Playflg =false
                Play()
            }
        })

    }
    fun Drawstr(str:String){
        var c: Canvas = countHolder.lockCanvas()
        c.drawColor(Color.WHITE)
        mPaint.setColor(Color.BLACK)
        var rect:Rect = Rect(50,50,50,50)
        mPaint.setTextSize(60F)
        c.drawRect(rect,mPaint)
        c.drawText(str,10F,60F,mPaint)
        countHolder.unlockCanvasAndPost(c)
    }
    */

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (tapflg != true) {
            when (event!!.action) {
                MotionEvent.ACTION_DOWN -> Play()
                MotionEvent.ACTION_OUTSIDE -> Log.d("TAG","画面外タップ")
            }
        }

        return super.onTouchEvent(event)
    }

}