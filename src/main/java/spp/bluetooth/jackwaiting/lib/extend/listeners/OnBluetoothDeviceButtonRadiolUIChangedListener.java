package spp.bluetooth.jackwaiting.lib.extend.listeners;

/**
 * <p>
 * 蓝牙设备全局UI变化监听器。
 * </p>
 *
 * @author Created by JackWaiting on 2018/6/29.
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceButtonRadiolUIChangedListener {


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
     * 长按设备开始录音
     * </p>
     */
    void onBluetoothDeviceStartRecord();

    /**
     * <p>
     * 松开停止录音
     * </p>
     */
    void onBluetoothDeviceStopRecord();

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


    /**
     * <p>
     * 波动设备触发开始快退节目
     * </p>
     */
    void onBluetoothDeviceSeekBackStart();

    /**
     * <p>
     * 波动设备触发停止快退节目
     * </p>
     */
    void onBluetoothDeviceSeekBackStop();

    /**
     * <p>
     * 波动设备触发音量设置
     * </p>
     * @param volume 音量。
     */
    void onBluetoothDeviceVolume(int volume);

    /**
     * <p>
     * 波动设备触发提示音加载成功
     * </p>
     */
    void onBluetoothDeviceTipSuccess();
}
