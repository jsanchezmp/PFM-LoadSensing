package com.uoc.loadsensing;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QRCodeActivity extends Activity {

    TextView showScan;
    Button btnQRCode;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.qrcode_layout);
            
            showScan = (TextView) findViewById(R.id.mresult);
            
            btnQRCode = (Button)findViewById(R.id.btnQRDecode);
            
            btnQRCode.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {

    	            if ( isIntentAvailable(getApplicationContext(), "com.google.zxing.client.android.SCAN") ) {
    		            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    		            intent.setPackage("com.google.zxing.client.android");
    		
    		            intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
    		            startActivityForResult(intent, 0);       
    	            } else {
    	            	Toast.makeText(getApplicationContext(), "Instale Barcode Scanner desde Android Market", Toast.LENGTH_LONG).show();
    	            }
    			}
    		});
            

    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
       
            if (requestCode == 0) {
                    if (resultCode == RESULT_OK) {
                            String contents = intent.getStringExtra("SCAN_RESULT");
                            showScan.setText(contents);
                            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                            // Handle successful scan
                            
                    } else if (resultCode == RESULT_CANCELED) {
                            //showScan.setText("Please try again");
                            // Handle cancel
               }
            }
    }
    
    public boolean isIntentAvailable( Context context, String action ) {
    	final PackageManager packageManager = context.getPackageManager();
    	final Intent intent = new Intent(action);
    	List<ResolveInfo> list = 
    			packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    	
    	return list.size() >0;
    }
}