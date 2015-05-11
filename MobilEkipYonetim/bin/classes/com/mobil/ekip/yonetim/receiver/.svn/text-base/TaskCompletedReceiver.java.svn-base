package com.mobil.ekip.yonetim.receiver;

import com.mobil.ekip.yonetim.application.MyApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TaskCompletedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		MyApplication myApplication = (MyApplication)context.getApplicationContext() ;
		Log.i("mobilEkip","task tamamlandi bilgisi alindi");
		String orderId = intent.getStringExtra("orderId");
		myApplication.completedTraking(orderId);
		
	}

}
