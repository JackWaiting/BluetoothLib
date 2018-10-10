package spp.bluetooth.jackwaiting.lib.services;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

import spp.bluetooth.jackwaiting.lib.status.ConnectionState;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * Created by JackWaiting on 2018/7/2.
 */
public class BluetoothChatHfpService {

    private BluetoothHeadset mHeadset;
    private String TAG = "BluetoothChatHfpService";
    private BluetoothDevice mConnectDevice;
    private BluetoothAdapter mBtAdapter;
    private static Context mContext;
    private static BluetoothChatHfpService bluetoothChatHfpService;
    private int callBackState = 0;

    public interface OnConnectionListener {

        void onConnectionStateChanged(
                BluetoothDevice bluetoothDevice, int state);
    }

    public interface OnBluetoothHfpReadyListener {
        void onBluetoothHfpReady();
    }

    private OnBluetoothHfpReadyListener onBluetoothHfpReadyListener;

    public void setOnBluetoothHfpReadyListener(OnBluetoothHfpReadyListener listener) {
        onBluetoothHfpReadyListener = listener;
    }

    private OnConnectionListener onConnectionListener;

    public void setOnConnectionListener(OnConnectionListener listener) {
        onConnectionListener = listener;
    }

    private BluetoothChatHfpService() {
        initReceiver();
        initBluetooth();

    }

    public int getBluetoothDeviceHfpStatus(BluetoothDevice bluetoothDevice) {
        if (mHeadset == null) {
            return -2;
        }
        return mHeadset.getConnectionState(bluetoothDevice);
    }

    private void initBluetooth() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBtAdapter.isEnabled()) {
            return;
        }
        //获取Hfp代理对象
        mBtAdapter.getProfileProxy(mContext, mListener, BluetoothProfile.HEADSET);
    }

    private void initReceiver() {
        //广播接收者监听状态
        IntentFilter filter = new IntentFilter(BluetoothHeadset.
                ACTION_CONNECTION_STATE_CHANGED);
        mContext.registerReceiver(mReceiver, filter);
    }

    public static BluetoothChatHfpService getInstance(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();

            if (bluetoothChatHfpService == null) {
                bluetoothChatHfpService = new BluetoothChatHfpService();
            }
        } else {

            throw new RuntimeException("The context is null!");
        }

        return bluetoothChatHfpService;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Hfp连接状态改变
            if (action != null) {
                if (action.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_DISCONNECTING);
                    callBackHfpConnectState(state);

                }
            }
        }
    };

    private int callBackHfpConnectState(int state) {
        if (onConnectionListener != null) {
            if (mHeadset == null) {
                //获取HFP代理对象
                mBtAdapter.getProfileProxy(mContext, mListener, BluetoothProfile.HEADSET);
            }
            if (mHeadset != null) {
                List<BluetoothDevice> bluetoothDevices = mHeadset.getConnectedDevices();
                if(bluetoothDevices != null && bluetoothDevices.size() > 0){
                    for (int i = 0; i < bluetoothDevices.size(); i++) {
                        Log.i(TAG, "connect device=" + bluetoothDevices.get(i));

                    }
                    mConnectDevice = bluetoothDevices.get(0);
                }
            }
            if (state == BluetoothHeadset.STATE_DISCONNECTED) {
                callBackState = ConnectionState.HFP_DISCONNECTED;
            } else if (state == BluetoothHeadset.STATE_CONNECTING) {
                callBackState = ConnectionState.HFP_CONNECTING;
            } else if (state == BluetoothHeadset.STATE_CONNECTED) {
                callBackState = ConnectionState.HFP_CONNECTED;
            } else {
                callBackState = state;
                Log.i(TAG, "connect state=" + "其他状态 " + state);
            }
            if(onConnectionListener != null){
                onConnectionListener.onConnectionStateChanged(mConnectDevice, callBackState);
            }
            Log.i(TAG, "connect state=" + callBackState + "mConnectDevice = " + mConnectDevice);
        }
        return callBackState;
    }

    private void getConnectedDevices() {
        if (mHeadset != null && mHeadset.getConnectedDevices() != null && mHeadset.getConnectedDevices().size() > 0) {
            for (int i = 0; i < mHeadset.getConnectedDevices().size(); i++) {
                Log.i(TAG, "connect device=" + mHeadset.getConnectedDevices().get(i));
                mConnectDevice = mHeadset.getConnectedDevices().get(i);
            }
        }
    }

    private void getDisconnectDevices() {
        if (mHeadset != null && mHeadset.getConnectedDevices() != null && mHeadset.getConnectedDevices().size() > 0) {
            for (int i = 0; i < mHeadset.getConnectedDevices().size(); i++) {
                Log.i(TAG, "connect device=" + mHeadset.getConnectedDevices().get(i));
                mConnectDevice = mHeadset.getConnectedDevices().get(i);
                getHfpConnectState();
            }
        }
    }

    private BluetoothProfile.ServiceListener mListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceDisconnected(int profile) {
            Log.i(TAG, "onServiceDisconnected profile=" + profile);
            if (profile == BluetoothProfile.HEADSET) {
                mHeadset = null;
            }
        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            Log.i(TAG, "hfp onServiceConnected  profile=" + profile);
            if (profile == BluetoothProfile.HEADSET) {
                mHeadset = (BluetoothHeadset) proxy; //转换
                if (onBluetoothHfpReadyListener != null) {
                    onBluetoothHfpReadyListener.onBluetoothHfpReady();
                }
            }
        }
    };


    public BluetoothDevice getCurrentConnectedHfpDevice() {
        return mConnectDevice;
    }


    public void connectHfp(BluetoothDevice device) {
        Log.i(TAG, "connect to device :" + device);
        mConnectDevice = device;
        setPriority(device, 100); //设置priority
        try {
            //通过反射获取BluetoothHfp中connect方法（hide的），进行连接。
            Method connectMethod = BluetoothHeadset.class.getMethod("connect",
                    BluetoothDevice.class);
            connectMethod.invoke(mHeadset, device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disConnectHfp(BluetoothDevice device) {
        setPriority(device, 0);
        try {
            //通过反射获取BluetoothHfp中connect方法（hide的），断开连接。
            Method connectMethod = BluetoothHeadset.class.getMethod("disconnect",
                    BluetoothDevice.class);
            connectMethod.invoke(mHeadset, device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPriority(BluetoothDevice device) {
        int priority = 0;
        if (mHeadset == null) return priority;
        try {//通过反射获取BluetoothHfp中getPriority方法（hide的），获取优先级
            Method connectMethod = BluetoothHeadset.class.getMethod("getPriority",
                    BluetoothDevice.class);
            priority = (Integer) connectMethod.invoke(mHeadset, device);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priority;
    }

    public void setPriority(BluetoothDevice device, int priority) {
        if (mHeadset == null) return;
        try {//通过反射获取BluetoothHfp中setPriority方法（hide的），设置优先级
            Method connectMethod = BluetoothHeadset.class.getMethod("setPriority",
                    BluetoothDevice.class, int.class);
            connectMethod.invoke(mHeadset, device, priority);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isHfpConnect(){
        if(mHeadset!= null && callBackState == ConnectionState.HFP_CONNECTED){
            return true;
        }
        return false;
    }

    public boolean isNativeHfpConnect(){
        LogManager.i(TAG, "isNativeHfpConnect =  " + mBtAdapter.getProfileConnectionState(BluetoothProfile.HEADSET));
        if(mHeadset!= null && mBtAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothHeadset.STATE_CONNECTED){
            return true;
        }
        return false;
    }

    public int getHfpConnectState() {
        int hfpState = 0;
        if (mBtAdapter.isEnabled()) {
            hfpState = mBtAdapter.getProfileConnectionState(BluetoothProfile.HEADSET); // 可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
            LogManager.i(TAG, "getHfpConnectState  当前hfp状态为 =  " + hfpState);
        } else {
            LogManager.e(TAG, "蓝牙未开启，查询蓝牙hfp状态失败");
        }

        return callBackHfpConnectState(hfpState);
    }
}
