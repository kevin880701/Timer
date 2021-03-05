package com.hr.timer.Util.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.notesk.Adapter.TimeAdapter
import com.hr.timer.Model.TimeRecord
import android.text.SpannableStringBuilder
import android.graphics.Color
import android.text.Spannable

import android.text.style.ForegroundColorSpan
import android.view.View.OnAttachStateChangeListener
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest

import com.google.android.gms.ads.MobileAds
import com.hr.timer.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var presenter: MainPresenter
    lateinit var adapter : TimeAdapter

    companion object {
        //    0是初始 1是開始 2是暫停
        var status = 0
        var recordCount = 1
        var timeCalc = 0
        var timeArray = ArrayList<TimeRecord>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        presenter = MainPresenter(this)

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")

        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        presenter.adViewSetting()

        status = 0
        recordCount = 1
        timeCalc = 0
        timeArray = ArrayList()


//        val builder = SpannableStringBuilder(textChronometer.text)
//        val redSpan = ForegroundColorSpan(Color.RED)
//        builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        textChronometer.text = builder


        adapter = TimeAdapter(this)
        listTime.setLayoutManager(LinearLayoutManager(this))
        listTime.setAdapter(adapter)

        btnStart.setOnClickListener(this)
        btnRecorder.setOnClickListener(this)
        btnPause.setOnClickListener(this)
        btnRestart.setOnClickListener(this)
        btnContinue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnStart -> {
                presenter.timeStart()
            }
            R.id.btnRecorder -> {
                presenter.timeRecord()
            }
            R.id.btnPause -> {
                presenter.timePause()
            }
            R.id.btnRestart -> {
                presenter.timeRestart()
            }
            R.id.btnContinue -> {
                presenter.timeContinue()
            }
        }
    }
}