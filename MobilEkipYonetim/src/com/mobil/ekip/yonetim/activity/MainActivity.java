package com.mobil.ekip.yonetim.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.mobil.ekip.yonetim.R;
import com.mobil.ekip.yonetim.application.MyApplication;
import com.mobil.ekip.yonetim.service.GCMRegisterCheckTask;
import com.mobil.ekip.yonetim.service.GCMRegisterTask;
import com.mobil.ekip.yonetim.service.GetOrderListTask;
import com.mobil.ekip.yonetim.util.Util;

public class MainActivity extends TabActivity{
	
	public ListView mainListView;
	 public ArrayList<HashMap<String, String>> orderList;
	 
	 public String getSENDER_ID(){
		  return getApplication().getString(R.string.sender);
	  }
	 
	 public String getServerUrl() {
			return getApplication().getString(R.string.serverUrl);
		}
	 
	 public String getDeviceId(){
		  return getMyApplication().getDeviceId();
	  }
	 
	 public MyApplication getMyApplication(){
		  return (MyApplication) getApplication();
	  }
//
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return true;
		}
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.regControl) {
				new GCMRegisterCheckTask(this).execute(getServerUrl()+"/hello/checkRegister/"+getDeviceId());
			}
			
			if (item.getItemId() == R.id.register) {
				new GCMRegisterTask(getApplication(),getSENDER_ID()).execute(getServerUrl()+"/hello/RegisterId/"+getDeviceId());
			}
			return super.onOptionsItemSelected(item);
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost(); 
     // Android tab
     		Intent intentAndroid = new Intent().setClass(this, ManuelSelectionActivity.class);
     		TabSpec tabSpecAndroid = tabHost
     			.newTabSpec("Secim")
     			.setIndicator("Manuel Secim")
     			.setContent(intentAndroid);

     		// Apple tab
     		Intent intentApple = new Intent().setClass(this, OrderListActivity.class);
     		TabSpec tabSpecApple = tabHost
     			.newTabSpec("Apple")
     			.setIndicator("Gorev Listesi")
     			.setContent(intentApple);
     		
   		tabHost.addTab(tabSpecAndroid);
    		tabHost.addTab(tabSpecApple);

    		
    		//set Windows tab as default (zero based)
//    		tabHost.setCurrentTab(1);	
	}   
	
}


