package com.hr.timer;

/*
 * The Android chronometer widget revised so as to count milliseconds
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chronometer extends androidx.appcompat.widget.AppCompatTextView {
  @SuppressWarnings("unused")
  private static final String TAG = "Chronometer";

  public interface OnChronometerTickListener {

    void onChronometerTick(Chronometer chronometer);
  }

  private long mBase;
  private boolean mVisible;
  private boolean mStarted;
  private boolean mRunning;
  private OnChronometerTickListener mOnChronometerTickListener;

  private static final int TICK_WHAT = 2;

  private long timeElapsed;

  public Chronometer(Context context) {
    this (context, null, 0);
  }

  public Chronometer(Context context, AttributeSet attrs) {
    this (context, attrs, 0);
  }

  public Chronometer(Context context, AttributeSet attrs, int defStyle) {
    super (context, attrs, defStyle);

    init();
  }

  private void init() {
    mBase = SystemClock.elapsedRealtime();
    updateText(mBase);
  }

  public void setBase(long base) {
    mBase = base;
    dispatchChronometerTick();
    updateText(SystemClock.elapsedRealtime());
  }

  public long getBase() {
    return mBase;
  }

  public void setOnChronometerTickListener(
      OnChronometerTickListener listener) {
    mOnChronometerTickListener = listener;
  }

  public OnChronometerTickListener getOnChronometerTickListener() {
    return mOnChronometerTickListener;
  }

  public void start() {
    mStarted = true;
    updateRunning();
  }

  public void stop() {
    mStarted = false;
    updateRunning();
  }


  public void setStarted(boolean started) {
    mStarted = started;
    updateRunning();
  }

  @Override
  protected void onDetachedFromWindow() {
    super .onDetachedFromWindow();
    mVisible = false;
    updateRunning();
  }

  @Override
  protected void onWindowVisibilityChanged(int visibility) {
    super .onWindowVisibilityChanged(visibility);
    mVisible = visibility == VISIBLE;
    updateRunning();
  }

  private synchronized void updateText(long now) {
    timeElapsed = now - mBase;

    DecimalFormat df = new DecimalFormat("00");
    DecimalFormat dfMilliseconds = new DecimalFormat("000");

    int hours = (int)(timeElapsed / (3600 * 1000));
    int remaining = (int)(timeElapsed % (3600 * 1000));

    int minutes = (int)(remaining / (60 * 1000));
    remaining = (int)(remaining % (60 * 1000));

    int seconds = (int)(remaining / 1000);
    remaining = (int)(remaining % (1000));
//    int milliseconds = (int)(((int)timeElapsed % 100) );

    String text = "";

    if (hours > 0) {
      text += df.format(hours) + ":";
    }

    text += df.format(minutes) + ":";
    text += df.format(seconds) + ".";
    text += dfMilliseconds.format(remaining).substring(0,2);
//    text += Integer.toString(milliseconds);
    setText(text);
  }

  private void updateRunning() {
    boolean running = mVisible && mStarted;
    if (running != mRunning) {
      if (running) {
        updateText(SystemClock.elapsedRealtime());
        dispatchChronometerTick();
        mHandler.sendMessageDelayed(Message.obtain(mHandler,
            TICK_WHAT), 50);
      } else {
        mHandler.removeMessages(TICK_WHAT);
      }
      mRunning = running;
    }
  }

  private Handler mHandler = new Handler() {
    public void handleMessage(Message m) {
      if (mRunning) {
        updateText(SystemClock.elapsedRealtime());
        Log.v("LLLLLLLLLLLLLLLL", "" + SystemClock.elapsedRealtime());
        dispatchChronometerTick();
        sendMessageDelayed(Message.obtain(this , TICK_WHAT),
            50);
      }
    }
  };

  void dispatchChronometerTick() {
    if (mOnChronometerTickListener != null) {
      mOnChronometerTickListener.onChronometerTick(this);
    }
  }

  public long getTimeElapsed() {
    return timeElapsed;
  }

}