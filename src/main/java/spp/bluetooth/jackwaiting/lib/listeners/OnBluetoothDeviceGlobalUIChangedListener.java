package spp.bluetooth.jackwaiting.lib.listeners;

/**
 * <p>
 * 蓝牙设备全局UI变化监听器。
 * </p>
 *
 * @author Created by JackWaiting on 2018/6/29.
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceGlobalUIChangedListener {


    /**
     * <p>
     * 波动设备触发上一频道
     * </p>
     */
    void onBluetoothDeviceChannelPre();

    /**
     * <p>
     * 波动设备触发下一频道
     * </p>
     */
    void onBluetoothDeviceChannelNext();

    /**
     * <p>
     * 波动设备打开APP
     * </p>
     */
    void onBluetoothDeviceOpenApplication();

    /**
     * <p>
     * 波动设备触发下一节目
     * </p>
     */
    void onBluetoothDeviceMusicNext();

    /**
     * <p>
     * 波动设备触发上一节目
     * </p>
     */
    void onBluetoothDeviceMusicPre();

    /**
     * <p>
     * 波动设备触发开始快进节目
     * </p>
     */
    void onBluetoothDeviceSeekToStart();

    /**
     * <p>
     * 波动设备触发停止快进节目
     * </p>
     */
    void onBluetoothDeviceSeekToStop();
}
