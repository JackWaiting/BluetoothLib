package spp.bluetooth.jackwaiting.lib.managers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import spp.bluetooth.jackwaiting.lib.extend.devices.BluetoothDeviceButtonRadioManager;
import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceConnectionStateChangedListener;
import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceDiscoveryListener;
import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceGlobalUIChangedListener;
import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceManagerReadyListener;
import spp.bluetooth.jackwaiting.lib.services.BluetoothChatService;
import spp.bluetooth.jackwaiting.lib.status.ConnectionState;
import spp.bluetooth.jackwaiting.lib.status.ConnectionType;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * Created by JackWaiting on 2018/6/29.
 */
public class BluetoothDeviceManager implements OnBluetoothDeviceConnectionStateChangedListener, OnBluetoothDeviceManagerReadyListener {
    public static final String TAG = "BluetoothDeviceManager";
    public static BluetoothChatService bluetoothChatService;
    public int connectType = 0;

    /**
     * <p>
     * 设置连接的类型。
     * </p>
     * <p>
     * <strong>说明：可为连接过程设置连接的协议</strong>
     * </p>
     * @param type 设置连接的类型。
     * @since 1.0.0
     */
    public void setConnectType(int type) {
        this.connectType = type;
        bluetoothChatService.setConnectType(type);
    }

    /**
     * <p>
     * 默认的蓝牙适配器对象。
     * </p>
     * <p>
     * <strong>说明：创建这个蓝牙适配器对象是用来弥补标准蓝牙库缺失功能的。</strong>
     * </p>
     * <p>
     * <strong>备注：{@link BluetoothAdapter#getDefaultAdapter()}
     * 需要注册权限：&lt;uses-permission
     * android:name="android.permission.BLUETOOTH"&gt;。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    private static BluetoothAdapter sBluetoothAdapter;


    /**
     * <p>
     * 蓝牙设备管理器对象。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private static BluetoothDeviceManager sBluetoothDeviceManager;

    /**
     * <p>
     * 外部传入的{@link Context}对象。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private static Context sContext;

    /**
     * <p>
     * 传入的设置蓝牙设备MAC地址过滤前缀。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private static String[] sMacAddressFilterPrefix;


    /**
     * <p>
     *
     * @param context 上下文对象。
     * @return 蓝牙设备管理器对象。
     * @since 1.0.0
     */
    public static BluetoothDeviceManager getInstance(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();

            if (sBluetoothDeviceManager == null) {

                sBluetoothDeviceManager = new BluetoothDeviceManager();
            }
        } else {

            throw new RuntimeException("The context is null!");
        }

        return sBluetoothDeviceManager;
    }

    /**
     * <p>
     * 初始化管理类
     * </p>
     *
     * @since 1.0.0
     */
    private BluetoothDeviceManager() {
        bluetoothChatService = BluetoothChatService.getInstance(sContext);
        sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * <p>
     * 获取当前已经连接上A2DP但没有连接上SPP的远程蓝牙设备对象。
     * </p>
     *
     * @return null：当前没有已经连接上A2DP但没有连接上SPP的远程蓝牙设备对象。非null：
     * 当前有已经连接上A2DP但没有连接上SPP的远程蓝牙设备对象。
     * @since 1.0.0
     */
    public BluetoothDevice getBluetoothDeviceConnectedA2dp() {
        if (bluetoothChatService != null) {
            return bluetoothChatService.getConnectedA2dpDevice();
        }

        return null;
    }

    /**
     * <p>
     * 获取a2dp连接状态
     * </p>
     * @return a2dp连接状态
     * @since 1.0.0
     */
    public int getA2dpConnectState() {
        int a2dpState = ConnectionState.INIT_CONNECT;
        if (bluetoothChatService != null) {
            a2dpState = bluetoothChatService.getA2dpConnectState();
        }
        return a2dpState;
    }

    /**
     * <p>
     * 获取hfp连接状态
     * </p>
     * @return a2dp连接状态
     * @since 1.0.0
     */
    public int getHfpConnectState() {
        int hfpState = ConnectionState.INIT_CONNECT;
        if (bluetoothChatService != null) {
            hfpState = bluetoothChatService.getHfpConnectState();
        }
        return hfpState;
    }

    /**
     * <p>
     * 获取当前已经连接上A2DP而且连接上SPP的远程蓝牙设备对象。
     * </p>
     *
     * @return null：当前没有已经连接上A2DP而且连接上SPP的远程蓝牙设备对象。非null：
     * 当前有已经连接上A2DP而且连接上SPP的远程蓝牙设备对象。
     * @since 1.0.0
     */
    public BluetoothDevice getBluetoothDeviceConnected() {
        if (bluetoothChatService != null) {
            return bluetoothChatService.getConnectedDevice();
        }

        return null;
    }

    /**
     * <p>
     * 设置需要过滤出的蓝牙设备MAC地址前缀。
     * </p>
     * <p>
     * <strong>说明：一旦你设置了你需要过滤出的蓝牙设备，在扫描出设备的回调接口中只返回你需要过滤出的蓝牙设备。比如需要过滤出MAC地址前缀为
     * "9A:2C:"的蓝牙设备，那么在扫描蓝牙设备的时候，所有不以"9A:2C:" 前缀的MAC地址的蓝牙设备，都不会返回。</strong>
     * </p>
     * <p>
     * <strong>备注：本地Android设备系统自带的蓝牙设置中是可以扫描出任何可见的蓝牙设备的。</strong>
     * </p>
     *
     * @param macAddressFilterPrefix 蓝牙设备MAC地址前缀，例如 "9A:2C:"。
     * @version 1.0.5
     * @since 1.0.0
     */
    public void setMacAddressFilterPrefix(
            String... macAddressFilterPrefix) {
        sMacAddressFilterPrefix = macAddressFilterPrefix;
    }

    /**
     * <p>
     * 获取蓝牙设备纽扣电台管理器对象，用来操作与蓝牙纽扣电台相关的功能。
     * </p>
     * <p>
     * <strong>备注：不能通过调用{@link spp.bluetooth.jackwaiting.lib.extend.devices.BluetoothDeviceButtonRadioManager#getInstance()}
     * 方法来获取对象，而是要在
     * {@link OnBluetoothDeviceManagerReadyListener#onBluetoothDeviceManagerReady()}
     * 方法回调之后，通过 {@link #getBluetoothDeviceButtonRadioManager()} 方法来获取对象。</strong>
     * </p>
     *
     * @return 蓝牙设备音箱管理器对象。
     * @since 1.0.0
     */
    public BluetoothDeviceButtonRadioManager getBluetoothDeviceButtonRadioManager() {
        return BluetoothDeviceButtonRadioManager.getInstance();
    }

    /**
     * <p>
     * 设置蓝牙设备全局UI变化监听器。
     * </p>
     *
     * @param onBluetoothDeviceGlobalUIChangedListener 蓝牙设备全局UI变化监听器。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceGlobalUIChangedListener(
            OnBluetoothDeviceGlobalUIChangedListener onBluetoothDeviceGlobalUIChangedListener) {
        sOnBluetoothDeviceGlobalUIChangedListener = onBluetoothDeviceGlobalUIChangedListener;
    }

    /**
     * <p>
     * 外部传入的蓝牙设备全局UI变化监听器。
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceGlobalUIChangedListener sOnBluetoothDeviceGlobalUIChangedListener;

    /**
     * <p>
     * 设置蓝牙设备连接状态监听器。
     * </p>
     *
     * @param onBluetoothDeviceConnectionStateChangedListener 蓝牙设备连接状态变化监听器。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceConnectionStateChangedListener(
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
     * 连接远程蓝牙设备。
     * </p>
     * <p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceConnectionStateChangedListener(OnBluetoothDeviceConnectionStateChangedListener)}
     * 方法来监听连接状态。</strong>
     * </p>
     * <p>
     * <strong>备注：如果你调用此方法去连接一个不在线的设备，会有连接超时的状态回调。</strong>
     * </p>
     *
     * @param bluetoothDevice 远程蓝牙设备对象。
     * @since 1.0.0
     */
    public void connect(final BluetoothDevice bluetoothDevice) {
        // 连接一个蓝牙设备之前，如果发现Android蓝牙设备还处于蓝牙扫描的过程中，先取消蓝牙扫描，减少性能消耗。
        if (this.isDiscovering()) {
            this.cancelDiscovery();
        }

        // 开启一个定时器，如果5秒中之内连接没有任何响应，就认为是蓝牙连接超时。
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (sTimeoutConnection) {
                    if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                sOnBluetoothDeviceConnectionStateChangedListener
                                        .onBluetoothDeviceConnectionStateChanged(
                                                bluetoothDevice,
                                                ConnectionState.TIMEOUT);
                            }
                        });

                    }
                }
            }
        }, TIMEOUT_CONNECTION);

        if (bluetoothChatService != null) {

            if (connectType == ConnectionType.ONLY_A2DP) {
                Log.i(TAG, "开始执行连接a2dp");
                bluetoothChatService.connectA2dpDevice(bluetoothDevice, connectType);
            } else {
                Log.i(TAG, "开始执行连接所有模式蓝牙");
                bluetoothChatService.connectDevice(bluetoothDevice, connectType);
            }

        } else {

            throw new RuntimeException("Bluetooth a2dp service is null!");
        }
    }

    /**
     * <p>
     * 断开已经连接的hfp协议。
     * </p>
     * <p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceConnectionStateChangedListener(OnBluetoothDeviceConnectionStateChangedListener)}
     * 来监听连接状态。</strong>
     * </p>
     *
     * @param bluetoothDevice 远程蓝牙设备对象。
     * @since 1.0.0
     */
    public void disconnectHfp(final BluetoothDevice bluetoothDevice) {
        if (bluetoothChatService != null) {
            bluetoothChatService.disconnectHfpDevice(bluetoothDevice, connectType);
        }
    }

    /**
     * <p>
     * 断开已经连接的hfp协议。
     * </p>
     * <p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceConnectionStateChangedListener(OnBluetoothDeviceConnectionStateChangedListener)}
     * 来监听连接状态。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public void disconnectSpp() {
        if (bluetoothChatService != null) {
            bluetoothChatService.disconnectSppDevice( connectType);
        }
    }

    /**
     * <p>
     * 断开已经连接的hfp协议。
     * </p>
     * @return hfp连接状态
     * @since 1.0.0
     */
    public boolean isHfpConnect() {
        boolean hfpState = false;
        if (bluetoothChatService != null) {
            hfpState = bluetoothChatService.isHfpConnect();
        }
        return hfpState;
    }

    /**
     * <p>
     * 连接hfp协议。
     * </p>
     * <p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceConnectionStateChangedListener(OnBluetoothDeviceConnectionStateChangedListener)}
     * 来监听连接状态。</strong>
     * </p>
     *
     * @param bluetoothDevice 远程蓝牙设备对象。
     * @since 1.0.0
     */
    public void connectHfp(final BluetoothDevice bluetoothDevice) {
        if (bluetoothChatService != null) {
            bluetoothChatService.connectHfpDevice(bluetoothDevice, connectType);
        }
    }

    /**
     * <p>
     * 断开已经连接的远程蓝牙设备。
     * </p>
     * <p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceConnectionStateChangedListener(OnBluetoothDeviceConnectionStateChangedListener)}
     * 来监听连接状态。</strong>
     * </p>
     *
     * @param bluetoothDevice 远程蓝牙设备对象。
     * @since 1.0.0
     */
    public void disconnect(final BluetoothDevice bluetoothDevice) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //等待开发
            }
        }).start();
    }

    /**
     * <p>
     * 初始化{@link BluetoothDeviceManager}对象所处线程的{@link Handler}对象。
     * </p>
     *
     * @since 1.0.0
     */
    private static Handler sHandler;

    /**
     * <p>
     * 获取蓝牙设备管理器对象最后必须调用的方法。
     * </p>
     *
     * @return 蓝牙设备管理器对象。
     * @since 1.0.0
     */

    public BluetoothDeviceManager build() {
        /*if (!BluetoothDeviceCommandManager.isValidDeviceType(sDeviceTypes)) {
            throw new RuntimeException(
                    "When you try to initialize BluetoothDeviceManager, you should call the method setDeviceType(int... deviceType) after the method getIntance(Context context) and before the method build() !");
        }*/

        if (sBluetoothAdapter == null) {
            LogManager.e("Your device doesn't support Bluetooth!");

            return null;
        }
        if (bluetoothChatService == null) {

            throw new RuntimeException("Bluetooth a2dp service init exception!");
        }
        bluetoothChatService.setOnBluetoothChatConnectionStateChangedListener(this);

        // 创建用户线程通信的Handler对象
        sHandler = new Handler();

        return sBluetoothDeviceManager;
    }

    /**
     * <p>
     * 一次扫描过程中扫描到的设备。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private List<BluetoothDevice> sBluetoothDevicesFound = new ArrayList<BluetoothDevice>();

    /**
     * <p>
     * 判断连接是否超时的标记位。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private static boolean sTimeoutConnection = true;

    /**
     * <p>
     * 蓝牙设备连接超时时间。
     * </p>
     *
     * @since 1.0.0
     */
    private static final long TIMEOUT_CONNECTION = 5 * 1000;

    /**
     * <p>
     * 最大的连接重试次数。
     * </p>
     *
     * @since 1.0.0
     */
    private static final int MAX_CONNECTION_RETRY_TIMES = 3;

    /**
     * <p>
     * A2DP连接失败重试次数。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private static int sConnectionFailureTimesRetryA2dp;

    /**
     * <p>
     * 记录SPP连接失败重试次数。
     * </p>
     *
     * @reset The variable must be reset at the proper time!
     * @since 1.0.0
     */
    private static int sConnectionFailureTimesRetrySpp;

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
        if (bluetoothChatService != null) {
            bluetoothChatService.setOnBluetoothDeviceManagerReadyListener(this);
        }
    }

    /**
     * <p>
     * <strong>外部传入的蓝牙设备扫描监听器。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceDiscoveryListener sOnBluetoothDeviceDiscoveryListener;

    /**
     * <p>
     * 设置蓝牙设备扫描监听器。
     * </p>
     *
     * @param onBluetoothDeviceDiscoveryListener 蓝牙设备扫描监听器对象。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceDiscoveryListener(
            OnBluetoothDeviceDiscoveryListener onBluetoothDeviceDiscoveryListener) {
        sOnBluetoothDeviceDiscoveryListener = onBluetoothDeviceDiscoveryListener;
    }

    /**
     * <p>
     * 判断当前本地Android设备的蓝牙是否处于蓝牙扫描的状态中。
     * </p>
     * <p>
     * <strong>备注：蓝牙扫描过程是一个耗时消耗的动作，在不需要蓝牙扫描的时候，应当调用{@link #cancelDiscovery()}
     * 取消这个扫描过程。</strong>
     * </p>
     *
     * @return TRUE：当前本地Android设备的蓝牙正在处于扫描过程中。FALSE：当前本地Android设备的蓝牙不是处于蓝牙扫描过程中。
     */
    public boolean isDiscovering() {
        if (sBluetoothAdapter != null) {
            return sBluetoothAdapter.isDiscovering();
        }

        return false;
    }

    /**
     * <p>
     * 开启当前本地Android设备的蓝牙扫描。
     * </p>
     * <p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceDiscoveryListener(OnBluetoothDeviceDiscoveryListener)}
     * 来监听扫描状态。 </strong>
     * </p>
     *
     * @return TRUE：成功开启当前本地Android设备的蓝牙扫描。 FALSE：开启当前本地的Android设备的蓝牙扫描失败。
     * @since 1.0.0
     */
    public boolean startDiscovery() {
        if (sBluetoothAdapter != null) {
            return sBluetoothAdapter.startDiscovery();
        }

        return false;
    }

    /**
     * <p>
     * 取消当前本地Android设备的蓝牙扫描。
     * </p>
     * <strong>说明：请调用
     * {@link #setOnBluetoothDeviceDiscoveryListener(OnBluetoothDeviceDiscoveryListener)}
     * 来监听扫描状态。 </strong>
     *
     * @return TRUE：成功关闭本地Android设备的蓝牙扫描。 FALSE：关闭本地Android设备的蓝牙扫描失败。
     */
    public boolean cancelDiscovery() {
        if (sBluetoothAdapter != null) {
            return sBluetoothAdapter.cancelDiscovery();
        }

        return false;
    }


    /**
     * <p>
     * 释放蓝牙设备管理器的相关资源。
     * </p>
     * <p>
     * <strong>说明：这个方法很重要。</strong>
     * </p>
     * <p>
     * <strong>备注：请在程序退出的时候调用！</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public void release() {
        System.exit(0);// 不能去掉，如果去掉会出现SPP不能通讯的问题。(此代码是为了杀死当前虚拟机，彻底释放资源)。
    }

    @Override
    public void onBluetoothDeviceConnectionStateChanged(BluetoothDevice bluetoothDevice, int state) {
        LogManager.d(TAG, "BluetoothDeviceManager state = " + state);
        if (sOnBluetoothDeviceConnectionStateChangedListener != null) {
            sOnBluetoothDeviceConnectionStateChangedListener.onBluetoothDeviceConnectionStateChanged(bluetoothDevice, state);
        }
    }

    @Override
    public void onBluetoothDeviceManagerReady() {
        if (sOnBluetoothDeviceManagerReadyListener != null) {
            sOnBluetoothDeviceManagerReadyListener.onBluetoothDeviceManagerReady();
        }
    }
}
