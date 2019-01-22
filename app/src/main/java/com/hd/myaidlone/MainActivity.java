package com.hd.myaidlone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button btGetDemand;
    private IDemandManager demandManager;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: ");
            demandManager = IDemandManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IDemandListener.Stub listener = new IDemandListener.Stub() {
        @Override
        public void onDemandReceiver(MessageBean msg) throws RemoteException {
            Log.e(TAG, "onDemandReceiver: 收到服务端的信息 msg.content = "
                    + msg.getContent() + ",msg.level = " + msg.getLevel());
        }
    };
    private Button btRegister;
    private Button btUnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.hd.myaidlone");
        intent.setPackage("com.hd.myaidlone");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        btGetDemand = findViewById(R.id.bt_getDemand);
        btRegister = findViewById(R.id.bt_register);
        btUnregister = findViewById(R.id.bt_unresigter);
        btGetDemand.setOnClickListener(this);
        btUnregister.setOnClickListener(this);
        btRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getDemand:
                try {
                    MessageBean messageBean = demandManager.getDemand();
                    Log.e(TAG, "D: messageBean.getContent = " + messageBean.getContent());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bt_register:
                try {
                    demandManager.registerListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_unresigter:
                try {
                    demandManager.unregisterListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
