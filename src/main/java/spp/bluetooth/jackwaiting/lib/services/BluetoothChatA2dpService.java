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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.lang.reflect.Method;

import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceManagerReadyListener;
import spp.bluetooth.jackwaiting.lib.status.ConnectionState;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * Created by JackWaiting on 2018/7/2.
 */
public class BluetoothChatA2dpService {

    private BluetoothA2dp mA2dp;
    private String TAG = "BluetoothChatA2dpService";
    private BluetoothDevice mConnectDevice;
    private BluetoothAdapter mBtAdapter;
    private static Context mContext;
    private static BluetoothChatA2dpService bluetoothA2dpChatService;
    private int callBackState = 0;

    public interface OnConnectionListener {

        void onConnectionStateChanged(
                BluetoothDevice bluetoothDevice, int state);
    }

    public interface OnBluetoothA2dpReadyListener {

        void onBluetoothA2dpReady();
    }

    private OnBluetoothA2dpReadyListener onBluetoothA2dpReadyListener;

    public void setOnBluetoothA2dpReadyListener(OnBluetoothA2dpReadyListener listener) {
        onBluetoothA2dpReadyListener = listener;
    }

    private OnConnectionListener onConnectionListener;

    public void setOnConnectionListener(OnConnectionListener listener) {
        onConnectionListener = listener;
    }

    private BluetoothChatA2dpService() {
        initReceiver();
        initBluetooth();

    }

    public int getBluetoothDeviceA2dpStatus(BluetoothDevice bluetoothDevice) {
        if (mA2dp == null) {
            return -2;
        }
        return mA2dp.getConnectionState(bluetoothDevice);
    }

    private void initBluetooth() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBtAdapter.isEnabled()) {
            return;
        }
        //获取A2DP代理对象
        mBtAdapter.getProfileProxy(mContext, mListener, BluetoothProfile.A2DP);
    }

    private void initReceiver() {
        //广播接收者监听状态
        IntentFilter filter = new IntentFilter(BluetoothA2dp.
                ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothA2dp.ACTION_PLAYING_STATE_CHANGED);
        mContext.registerReceiver(mReceiver, filter);
    }

    public static BluetoothChatA2dpService getInstance(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();

            if (bluetoothA2dpChatService == null) {
                bluetoothA2dpChatService = new BluetoothChatA2dpService();
            }
        } else {

            throw new RuntimeException("The context is null!");
        }

        return bluetoothA2dpChatService;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //A2DP连接状态改变
            if (action != null) {
                if (action.equals(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothA2dp.EXTRA_STATE, BluetoothA2dp.STATE_DISCONNECTED);
                    callBackA2dpConnectState(state);

                } else if (action.equals(BluetoothA2dp.ACTION_PLAYING_STATE_CHANGED)) {
                    //A2DP播放状态改变
                    int state = intent.getIntExtra(BluetoothA2dp.EXTRA_STATE, BluetoothA2dp.STATE_NOT_PLAYING);
                    Log.i(TAG, "play state=" + state);
                }
            }
        }
    };

    private int callBackA2dpConnectState(int state) {
        if (onConnectionListener != null) {
            if (mA2dp == null) {
                //获取A2DP代理对象
                mBtAdapter.getProfileProxy(mContext, mListener, BluetoothProfile.A2DP);
            }
            if (state == BluetoothA2dp.STATE_DISCONNECTED) {
                getDisconnectDevices();
                callBackState = ConnectionState.A2DP_DISCONNECTED;
            } else if (state == BluetoothA2dp.STATE_CONNECTING) {
                callBackState = ConnectionState.A2DP_CONNECTING;
            } else if (state == BluetoothA2dp.STATE_CONNECTED) {
                getConnectedDevices();
                callBackState = ConnectionState.A2DP_CONNECTED;
            } else {
                callBackState = state;

            }
            Log.i(TAG, "connect state=" + state);
            if (onConnectionListener != null) {
                onConnectionListener.onConnectionStateChanged(mConnectDevice, callBackState);
            }

        }
        return callBackState;
    }

    private void getConnectedDevices() {
        if (mA2dp != null && mA2dp.getConnectedDevices() != null && mA2dp.getConnectedDevices().size() > 0) {
            for (int i = 0; i < mA2dp.getConnectedDevices().size(); i++) {
                Log.i(TAG, "connect device=" + mA2dp.getConnectedDevices().get(i));
                mConnectDevice = mA2dp.getConnectedDevices().get(i);
            }
        }
    }

    private void getDisconnectDevices() {
        if (mA2dp != null && mA2dp.getConnectedDevices() != null && mA2dp.getConnectedDevices().size() > 0) {
            for (int i = 0; i < mA2dp.getConnectedDevices().size(); i++) {
                Log.i(TAG, "connect device=" + mA2dp.getConnectedDevices().get(i));
                mConnectDevice = mA2dp.getConnectedDevices().get(i);
                getA2dpConnectState();
            }
        }
    }

    private BluetoothProfile.ServiceListener mListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceDisconnected(int profile) {
            Log.i(TAG, "onServiceDisconnected profile=" + profile);
            if (profile == BluetoothProfile.A2DP) {
                mA2dp = null;
            }
        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            Log.i(TAG, "onServiceConnected profile=" + profile);
            if (profile == BluetoothProfile.A2DP) {
                mA2dp = (BluetoothA2dp) proxy; //转换
                if (onBluetoothA2dpReadyListener != null) {
                    onBluetoothA2dpReadyListener.onBluetoothA2dpReady();
                }
            }
        }
    };

    public BluetoothDevice getCurrentConnectedA2dpDevice() {
        return mConnectDevice;
    }


    public void connectA2dp(BluetoothDevice device) {
        Log.i(TAG, "connect to device :" + device);
        mConnectDevice = device;
        setPriority(device, 100); //设置priority
        try {
            //通过反射获取BluetoothA2dp中connect方法（hide的），进行连接。
            Method connectMethod = BluetoothA2dp.class.getMethod("connect",
                    BluetoothDevice.class);
            connectMethod.invoke(mA2dp, device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disConnectA2dp(BluetoothDevice device) {
        mConnectDevice = null;
        setPriority(device, 0);
        try {
            //通过反射获取BluetoothA2dp中connect方法（hide的），断开连接。
            Method connectMethod = BluetoothA2dp.class.getMethod("disconnect",
                    BluetoothDevice.class);
            connectMethod.invoke(mA2dp, device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPriority(BluetoothDevice device) {
        int priority = 0;
        if (mA2dp == null) return priority;
        try {//通过反射获取BluetoothA2dp中getPriority方法（hide的），获取优先级
            Method connectMethod = BluetoothA2dp.class.getMethod("getPriority",
                    BluetoothDevice.class);
            priority = (Integer) connectMethod.invoke(mA2dp, device);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priority;
    }

    public void setPriority(BluetoothDevice device, int priority) {
        if (mA2dp == null) return;
        try {//通过反射获取BluetoothA2dp中setPriority方法（hide的），设置优先级
            Method connectMethod = BluetoothA2dp.class.getMethod("setPriority",
                    BluetoothDevice.class, int.class);
            connectMethod.invoke(mA2dp, device, priority);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isA2dpConnect(){
        if(mA2dp != null && callBackState == ConnectionState.A2DP_CONNECTED){
            return true;
        }
        return false;
    }

    public boolean isNativeA2dpConnect(){
        LogManager.i(TAG, "isNativeA2dpConnect =  " + mBtAdapter.getProfileConnectionState(BluetoothProfile.A2DP));
        if(mA2dp!= null && mBtAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothA2dp.STATE_CONNECTED){
            return true;
        }
        return false;
    }

    public int getA2dpConnectState() {
        int a2dpState = 0;
        if (mBtAdapter.isEnabled()) {

            a2dpState = mBtAdapter.getProfileConnectionState(BluetoothProfile.A2DP); // 可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
            LogManager.i(TAG, "getA2dpConnectState  当前a2dp状态为 =  " + a2dpState);
        } else {
            LogManager.e(TAG, "蓝牙未开启，查询蓝牙a2dp状态失败");
        }

        return callBackA2dpConnectState(a2dpState);
    }
}
