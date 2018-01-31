package com.example.ab0034.fileshare;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WiFiConnectivity extends AppCompatActivity {
    Button wifion, wifioff;
//    TextView txtwifi;
    ListView wifilist;
    WifiManager mainWifiObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_connectivity);
        wifion = (Button) findViewById(R.id.wifion);
        wifioff = (Button) findViewById(R.id.wifioff);
        wifilist = (ListView) findViewById(R.id.wifilist);
//        wifilist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Connect();
//            }
//        });

//        txtwifi =(TextView)findViewById(R.id.txtwifi);
        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
            }
        });
        wifioff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);
            }
        });

        ArrayList<ScanResult> wifiScanList = (ArrayList<ScanResult>) mainWifiObj.getScanResults();
        String data = wifiScanList.get(0).toString();
        wifilist.setAdapter(new WIfiAdapter(this,wifiScanList));
//        txtwifi.setText(data);

    }

//    private void Connect() {
//        WifiConfiguration wifiConfig = new WifiConfiguration();
//
//        wifiConfig.SSID = String.format("\"%s\"", "Wifi name");
//        wifiConfig.preSharedKey = String.format("\"%s\"", "Wifi password");
//
//        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
//        int netId = wifiManager.addNetwork(wifiConfig);
//        wifiManager.disconnect();
//        wifiManager.enableNetwork(netId, true);
//        wifiManager.reconnect();
//        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//    }

    public class WIfiAdapter extends ArrayAdapter<ArrayList<ScanResult>> {
        ArrayList<ScanResult>wifilistarray;
        Context context;
        WifiInfo info;
        public WIfiAdapter( Context context,ArrayList<ScanResult>ScanResultwifi) {
            super(context, R.layout.list_row);
            this.context=context;
            this.wifilistarray=ScanResultwifi;
            info=mainWifiObj.getConnectionInfo();
        }
        @Override
        public View getView(int position,  View convertView,ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(R.layout.list_row, null);
            }
           ScanResult p =wifilistarray.get(position);
            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.txt_list);
                TextView tt2 = (TextView) v.findViewById(R.id.txt_venue);
                TextView tt3 = (TextView) v.findViewById(R.id.txt_Capabalities);
                TextView tt4=(TextView)v.findViewById(R.id.txt_info);
                tt4.append("\nWifiStatus:"+info.toString());
                if (tt1 != null) {
                    tt1.setText(p.BSSID);
                }if (tt2 != null) {
                    tt2.setText(p.SSID);
                }if (tt3 != null) {
                    tt3.setText(p.capabilities);
                }

            }
            return v;
        }

        @Override
        public int getCount() {
            return wifilistarray.size();
        }


    }


}
//class WifiScanReceiver extends BroadcastReceiver {
//    public void onReceive(Context c, Intent intent) {
//        WifiScanReceiver wifiReciever = new WifiScanReceiver();
//        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//
//    }
//}

