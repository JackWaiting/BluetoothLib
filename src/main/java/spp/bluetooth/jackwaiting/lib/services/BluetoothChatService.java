package spp.bluetooth.jackwaiting.lib.services;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.reflect.Method;

import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceConnectionStateChangedListener;
import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceManagerReadyListener;
import spp.bluetooth.jackwaiting.lib.status.ConnectionState;
import spp.bluetooth.jackwaiting.lib.status.ConnectionType;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * Created by JackWaiting on 2018/7/2.
 */
public class BluetoothChatService implements BluetoothChatA2dpService.OnBluetoothA2dpReadyListener, BluetoothChatSppService.OnConnectionListener, BluetoothChatA2dpService.OnConnectionListener, BluetoothChatHfpService.OnConnectionListener, BluetoothChatHfpService.OnBluetoothHfpReadyListener {

    private String TAG = "BluetoothChatService";

    private static Context mContext;
    private static BluetoothChatService bluetoothChatService;
    private static BluetoothChatA2dpService bluetoothChatA2dpService;
    private static BluetoothChatSppService bluetoothChatSppService;
    private static BluetoothChatHfpService bluetoothChatHfpService;
    private int connectType = 0;

    private BluetoothChatService() {
        bluetoothChatA2dpService = BluetoothChatA2dpService.getInstance(mContext);
        bluetoothChatSppService = BluetoothChatSppService.getInstance();
        bluetoothChatHfpService = BluetoothChatHfpService.getInstance(mContext);
        bluetoothChatHfpService.setOnBluetoothHfpReadyListener(this);
        bluetoothChatHfpService.setOnConnectionListener(this);
        bluetoothChatA2dpService.setOnConnectionListener(this);
        bluetoothChatA2dpService.setOnBluetoothA2dpReadyListener(this);
        bluetoothChatSppService.setOnConnectionListener(this);
    }

    public static BluetoothChatService getInstance(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();

            if (bluetoothChatService == null) {
                bluetoothChatService = new BluetoothChatService();
            }
        } else {

            throw new RuntimeException("The context is null!");
        }
        return bluetoothChatService;
    }

    /**
     * <p>
     * 连接蓝牙A2dp
     * </p>
     * @param device 设备。
     * @param connectType 连接类型。
     */
    public void connectDevice(BluetoothDevice device, int connectType) {
        if (bluetoothChatA2dpService != null) {
            Log.i(TAG, "当前a2dp的状态" + bluetoothChatA2dpService.getBluetoothDeviceA2dpStatus(device));
            if (bluetoothChatA2dpService.getBluetoothDeviceA2dpStatus(device) == 2) { //2表示当前已经连接上了a2dp
                if (bluetoothChatSppService != null && !bluetoothChatSppService.isConnected()) {
                    connectSppDevice(device);
                }
                /*if (bluetoothChatHfpService != null && bluetoothChatHfpService.getBluetoothDeviceHfpStatus(device) != 2) {
                    connectHfpDevice(device, connectType);
                }*/
            } else {
                connectA2dpDevice(device, connectType);
            }

        }
    }

    /**
     * <p>
     * 连接蓝牙设备
     * </p>
     * @param device 设备。
     * @param connectType 连接类型。
     */
    public void connectA2dpDevice(BluetoothDevice device, int connectType) {
        if (bluetoothChatA2dpService != null) {
            this.connectType = connectType;
            bluetoothChatA2dpService.connectA2dp(device);
        }
    }

    /**
     * <p>
     * 连接蓝牙设备
     * </p>
     * @param device 设备。
     * @param connectType 连接类型。
     */
    public void connectHfpDevice(BluetoothDevice device, int connectType) {
        if (bluetoothChatHfpService != null) {
            this.connectType = connectType;
            bluetoothChatHfpService.connectHfp(device);
        }
    }

    /**
     * <p>
     * 断开连接蓝牙设备
     * </p>
     * @param device 设备。
     * @param connectType 连接类型。
     */
    public void disconnectHfpDevice(BluetoothDevice device, int connectType) {
        if (bluetoothChatHfpService != null) {
            this.connectType = connectType;
            bluetoothChatHfpService.disConnectHfp(device);
        }
    }

    /**
     * <p>
     * 断开连接蓝牙设备
     * </p>
     * @param connectType 连接类型。
     */
    public void disconnectSppDevice(int connectType) {
        if (bluetoothChatSppService != null) {
            this.connectType = connectType;
            bluetoothChatSppService.stopSpp();
        }
    }

    /**
     * <p>
     * 获取a2dp连接状态
     * </p>
     * @return a2dp连接状态
     */
    public int getA2dpConnectState() {
        int a2dpState = ConnectionState.INIT_CONNECT;
        if (bluetoothChatA2dpService != null) {
            a2dpState = bluetoothChatA2dpService.getA2dpConnectState();
        }
        return a2dpState;
    }

    /**
     * <p>
     * 获取hfp连接状态
     * </p>
     * @return hfp连接状态
     */
    public boolean isHfpConnect() {
        boolean hfpState = false;
        if (bluetoothChatHfpService != null) {
            hfpState = bluetoothChatHfpService.isHfpConnect();
        }
        return hfpState;
    }

    /**
     * <p>
     * 获取hfp连接状态
     * </p>
     * @return hfp连接状态
     */
    public int getHfpConnectState() {
        int hfpState = ConnectionState.INIT_CONNECT;
        if (bluetoothChatHfpService != null) {
            hfpState = bluetoothChatHfpService.getHfpConnectState();
        }
        return hfpState;
    }


    /**
     * <p>
     * 连接蓝牙设备类型
     * </p>
     * @param connectType 连接类型。
     */
    public void setConnectType(int connectType) {
        this.connectType = connectType;
    }

    /**
     * <p>
     * 连接蓝牙Spp
     * </p>
     */
    private void connectSppDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothChatSppService != null) {
            bluetoothChatSppService.initConnectionFailedCount();
            bluetoothChatSppService.connect(bluetoothDevice);
        }
    }

    /**
     * <p>
     * 获取连接上的A2dp蓝牙设备
     * </p>
     * @return 连接上的A2dp蓝牙设备
     */
    public BluetoothDevice getConnectedA2dpDevice() {
        return bluetoothChatA2dpService.getCurrentConnectedA2dpDevice();
    }

    /**
     * <p>
     * 获取连接上的蓝牙设备
     * </p>
     * @return 连接上的蓝牙设备
     */
    public BluetoothDevice getConnectedDevice() {
        return bluetoothChatSppService.getCurrentConnectedDevice();
    }

    /**
     * <p>
     * 设置蓝牙设备连接状态监听器。
     * </p>
     *
     * @param onBluetoothDeviceConnectionStateChangedListener 蓝牙设备连接状态变化监听器。
     * @since 1.0.0
     */
    public void setOnBluetoothChatConnectionStateChangedListener(
            OnBluetoothDeviceConnectionStateChangedListener onBluetoothDeviceConnectionStateChangedListener) {
        sOnBluetoothDeviceConnectionStateChangedListener = onBluetoothDeviceConnectionStateChangedListener;

    }

    /**
     * <p>
     * 传入的蓝牙设备连接状态变化的监听器。
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceConnectionStateChangedListener sOnBluetoothDeviceConnectionStateChangedListener;


    /**
     * <p>
     * 外部传入的蓝牙设备管理器准备好的监听器对象。
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceManagerReadyListener sOnBluetoothDeviceManagerReadyListener;

    /**
     * <p>
     * 设置蓝牙设备管理器准备好的监听器。
     * </p>
     *
     * @param onBluetoothDeviceManagerReadyListener 蓝牙设备管理器准备好的监听器对象。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceManagerReadyListener(
            OnBluetoothDeviceManagerReadyListener onBluetoothDeviceManagerReadyListener) {
        sOnBluetoothDeviceManagerReadyListener = onBluetoothDeviceManagerReadyListener;
    }


    @Override
    public void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int state) {
        if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
            sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, state);
        }
        if (state == 4 && connectType != ConnectionType.ONLY_A2DP) { //a2dp已经连接成功，开始连接spp
            if (bluetoothChatSppService != null) {
                if (bluetoothChatSppService.isConnected() && bluetoothChatSppService.getCurrentConnectedDevice() != null
                        && bluetoothDevice != null) {
                    if(bluetoothChatSppService.getCurrentConnectedDevice().getAddress().equals(bluetoothDevice.getAddress())){
                        if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
                            sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, ConnectionState.SPP_CONNECTED);
                        }
                        Log.i(TAG, "SPP已经连接，不需要连接。直接返回");
                    }else{
                        bluetoothChatSppService.stopSpp();
                        connectSppDevice(bluetoothDevice);
                    }
                } else {
                    connectSppDevice(bluetoothDevice);
                }
            }
        }
        if (bluetoothDevice != null) {
            Log.i(TAG, "BluetoothChatService state = " + state + "address = " + bluetoothDevice.getAddress() + "connectType = " + connectType);
        } else {
            Log.i(TAG, "连接出现异常 state = " + state + "connectType = " + connectType);
            return;
        }
        if (state == ConnectionState.A2DP_CONNECTING || state == ConnectionState.SPP_CONNECTING || state == ConnectionState.HFP_CONNECTING) {
            return;
        }
        if (connectType != ConnectionType.ONLY_A2DP) {
            if (bluetoothChatSppService != null && bluetoothChatSppService.isConnected() && bluetoothChatA2dpService.isA2dpConnect()) {
                if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
                    Log.i(TAG, "所有协议已经连接成功，返回ConnectionState.CONNECTED");
                    Log.i(TAG, "BluetoothChatService state = " + ConnectionState.CONNECTED + "address = " + bluetoothDevice.getAddress() + "connectType = " + connectType);
                    sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, ConnectionState.CONNECTED);
                    if (sOnBluetoothDeviceManagerReadyListener != null) {
                        Log.i(TAG, "onBluetoothDeviceManagerReady");

                        sOnBluetoothDeviceManagerReadyListener.onBluetoothDeviceManagerReady();
                    }
                }
            }
            if (bluetoothChatSppService != null && !bluetoothChatSppService.isConnected() && !bluetoothChatA2dpService.isA2dpConnect()) {
                if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
                    Log.i(TAG, "所有协议已经连接断开，返回ConnectionState.CONNECTED");
                    Log.i(TAG, "BluetoothChatService state = " + ConnectionState.DISCONNECTED + "address = " + bluetoothDevice.getAddress() + "connectType = " + connectType);
                    sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, ConnectionState.DISCONNECTED);
                }
            }
        } else {
            if (bluetoothChatA2dpService.isA2dpConnect()) {
                if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
                    Log.i(TAG, "A2DP协议已经连接成功，返回ConnectionState.CONNECTED");
                    Log.i(TAG, "BluetoothChatService state = " + ConnectionState.CONNECTED + "address = " + bluetoothDevice.getAddress() + "connectType = " + connectType);
                    sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, ConnectionState.CONNECTED);
                }
            } else {
                if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
                    Log.i(TAG, "A2DP协议已经连接断开，返回ConnectionState.CONNECTED");
                    Log.i(TAG, "BluetoothChatService state = " + ConnectionState.DISCONNECTED + "address = " + bluetoothDevice.getAddress() + "connectType = " + connectType);
                    sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, ConnectionState.DISCONNECTED);
                }
            }
        }
    }

    @Override
    public void onBluetoothA2dpReady() {
        if (bluetoothChatA2dpService.isNativeA2dpConnect()) {
            getA2dpConnectState();
        }

    }

    @Override
    public void onBluetoothHfpReady() {
        if (bluetoothChatHfpService.isNativeHfpConnect()) {
            getHfpConnectState();
        }
    }
}
