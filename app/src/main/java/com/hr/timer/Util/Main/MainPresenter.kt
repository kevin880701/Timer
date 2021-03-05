package com.hr.timer.Util.Main

import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.hr.timer.Model.TimeRecord
import com.hr.timer.Util.Main.MainActivity.Companion.recordCount
import com.hr.timer.Util.Main.MainActivity.Companion.status
import com.hr.timer.Util.Main.MainActivity.Companion.timeArray
import com.hr.timer.Util.Main.MainActivity.Companion.timeCalc
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import androidx.constraintlayout.widget.ConstraintLayout

import android.R




class MainPresenter(mainActivity : MainActivity)  {
    val mContext = mainActivity

    fun timeStart(){
        MainActivity.status = 1
        mContext.btnStart.visibility = View.GONE
        mContext.constrainPause.visibility = View.GONE
        mContext.constrainRun.visibility = View.VISIBLE
        mContext.textChronometer.start()
    }

    fun timeRecord(){
        var nowTime = SystemClock.elapsedRealtime().toInt() /10*10
        var baseTime = mContext.textChronometer.base / 10*10
        var timeRecord =  TimeRecord()
        timeRecord.no = recordCount
        timeRecord.time = milliSecondsCalc(nowTime - baseTime.toInt())
        if(timeArray.size == 0){
            timeRecord.timeGap = "+" + milliSecondsCalc(nowTime - baseTime.toInt())
        }else{
            timeRecord.timeGap = "+" + (milliSecondsCalc(nowTime -  timeCalc))
        }
        timeCalc = nowTime
        timeArray.add(0,timeRecord)
        mContext.adapter.notifyItemInserted(0)
        mContext.adapter.notifyItemChanged(1)
        mContext.listTime.scrollToPosition(0)
        recordCount++
    }

    fun timePause(){
        MainActivity.status = 2
        mContext.btnStart.visibility = View.GONE
        mContext.constrainPause.visibility = View.VISIBLE
        mContext.constrainRun.visibility = View.GONE
        mContext.textChronometer.stop()
    }

    fun timeContinue(){
        MainActivity.status = 1
        mContext.btnStart.visibility = View.GONE
        mContext.constrainPause.visibility = View.GONE
        mContext.constrainRun.visibility = View.VISIBLE
        mContext.textChronometer.start()
    }

    fun timeRestart(){
        status = 0
        recordCount = 1
        timeCalc = 0
        timeArray = ArrayList<TimeRecord>()
        mContext.adapter.notifyDataSetChanged()

        mContext.btnStart.visibility = View.VISIBLE
        mContext.constrainPause.visibility = View.GONE
        mContext.constrainRun.visibility = View.GONE
        mContext.textChronometer.stop()
        mContext.textChronometer.base = SystemClock.elapsedRealtime()
    }

    fun adViewSetting(){
        mContext.adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                val lp = mContext.listTime.layoutParams as ConstraintLayout.LayoutParams
                lp.matchConstraintPercentHeight = 0.325.toFloat()
//                lp.matchConstraintPercentHeight = 0.4.toFloat()
                mContext.listTime.layoutParams = lp

//                mContext.listTime
            }

            override fun onAdFailedToLoad(adError : Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    fun milliSecondsCalc(milliSeconds :Int): String{
        val df = DecimalFormat("00")
        val dfMilliseconds = DecimalFormat("000")
        val hours = milliSeconds/60/60/1000
        var remaining = milliSeconds % (3600 * 1000)
        var min =  remaining/60/1000
        remaining = remaining % (60 * 1000)
        var sec =  remaining/1000
        remaining = remaining % 1000
        var text = ""
        if (hours > 0) {
            text += df.format(hours.toLong()) + ":"
        }
        text += df.format(min.toLong()) + ":"
        text += df.format(sec.toLong()) + "."
        text += dfMilliseconds.format(remaining.toLong()).substring(0, 2)
        return text
    }
}