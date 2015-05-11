package com.mobil.ekip.yonetim.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;






import com.mobil.ekip.yonetim.R;
import com.mobil.ekip.yonetim.R.id;
import com.mobil.ekip.yonetim.R.layout;
import com.mobil.ekip.yonetim.service.GCMRegisterCheckTask;
import com.mobil.ekip.yonetim.service.GCMRegisterTask;
import com.mobil.ekip.yonetim.service.GetOrderListTask;
import com.mobil.ekip.yonetim.util.Util;

public class OrderListActivity extends BaseActivity{
	
	public ListView mainListView;
	 public ArrayList<HashMap<String, String>> orderList;

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
        setContentView(R.layout.order_list_activity);
        
        Bundle bundle = getIntent().getExtras();
        
        if(bundle!=null){
        String messageType = (String) bundle.get("messageType");	
        if(messageType != null && !messageType.equals(""))
        	Log.i("mobilEkip", "message Type = "+messageType);
        
        String message =(String) bundle.get("message");
        if(message != null && !message.equals("")){
        	if("newOrder".equals(message)){
        		Log.i("mobilEkip", "Yeni order geldi");
        	}if("cancelled".equals(message)){
        		Log.i("mobilEkip", "order silindi");
        		 String deletedOrderId =(String) bundle.get("orderId");
        		 String currentOrderId = getGlobalCurrentOrderId();
        		 String currentOrderStatus = getGlobalCurrentOrderStatus();
        		 if(currentOrderId.equals(deletedOrderId) && currentOrderStatus.endsWith(Util.ORDER_STATUS_STARTED)){
        			 getMyApplication().stopTracking();
        			 //update user
        		 }
        	}
        }
        }
        
        orderList = new ArrayList<HashMap<String, String>>();
        
        mainListView = (ListView) findViewById( R.id.list );   
        mainListView.setOnItemClickListener(new OnItemClickListener()
        {
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
        {
        	Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        	String orderId = ((TextView) v.findViewById(R.id.id)).getText().toString();
        	
        	
        	intent.putExtra("order", Util.findHashMap(orderList, orderId));               
            startActivity(intent);
        }
        });
        
       

//new RequestItemsServiceTask().execute(getServerUrl()+"/hello/"+getDeviceId());
      try {
		new GetOrderListTask(this).execute(getServerUrl()+"/hello/"+getDeviceId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}   
	
	public void refreshPage(View v){
		new GetOrderListTask(this).execute(getServerUrl()+"/hello/"+getDeviceId());
		Log.i("mobilEkip","Refresh Yapıldı.");
	}
}


