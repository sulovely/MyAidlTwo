package com.hd.myaidlone;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "dingwanshun";

    private Button btGetDemand;
    private Button btRegister;
    private Button btUnregister;
    private Button btAdd;
    private Button btJiami;
    private Button btJiemi;

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;
    private IDemandManager mDemandManager;
    private BinderPool binderPool;


    private void findBinder(BinderPool binderPool) {
        if (binderPool != null) {
            mCompute = ICompute.Stub.asInterface(binderPool.queryBinder(0));
            mSecurityCenter = ISecurityCenter.Stub.asInterface(binderPool.queryBinder(1));
            mDemandManager = IDemandManager.Stub.asInterface(binderPool.queryBinder(2));
        }
    }


    private IDemandListener.Stub listener = new IDemandListener.Stub() {
        @Override
        public void onDemandReceiver(MessageBean msg) throws RemoteException {
            Log.e(TAG, "onDemandReceiver: 收到服务端的信息 msg.content = "
                    + msg.getContent() + ",msg.level = " + msg.getLevel());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btGetDemand = findViewById(R.id.bt_getDemand);
        btRegister = findViewById(R.id.bt_register);
        btUnregister = findViewById(R.id.bt_unresigter);
        btAdd = findViewById(R.id.bt_add);
        btJiemi = findViewById(R.id.bt_jiemi);
        btJiami = findViewById(R.id.bt_jiami);
        btGetDemand.setOnClickListener(this);
        btUnregister.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btJiemi.setOnClickListener(this);
        btJiami.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bind();
            }
        }).start();
    }

    private void bind() {
        binderPool = BinderPool.getInstance(this);
        findBinder(binderPool);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getDemand:
                try {
                    MessageBean messageBean = mDemandManager.getDemand();
                    Log.e(TAG, "D: messageBean.getContent = " + messageBean.getContent());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bt_register:
                try {
                    mDemandManager.registerListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_unresigter:
                try {
                    mDemandManager.unregisterListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bt_add:
                try {
                    int result = mCompute.add(5, 18);
                    Log.e(TAG, "dingwanshun: " + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_jiami:
                try {
                    String result = mSecurityCenter.encypt("hello");
                    Log.e(TAG, "dingwanshun: " + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_jiemi:
                try {
                    String result = mSecurityCenter.decrypt("6;221");
                    Log.e(TAG, "dingwanshun:" + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binderPool.unBinderService();
    }
}
