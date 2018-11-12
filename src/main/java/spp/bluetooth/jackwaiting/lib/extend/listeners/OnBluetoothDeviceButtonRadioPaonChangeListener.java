package spp.bluetooth.jackwaiting.lib.extend.listeners;

/**
 * <p>
 * 蓝牙功放打开是否准备好监听器。
 * </p>
 *
 * @author Created by JackWaiting on 2018/6/29.
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceButtonRadioPaonChangeListener {

    /**
     * <p>
     * 波动设备触发提示音加载成功
     * </p>
     */
    void onBluetoothDevicePaonReady();

    void onBluetoothDevicePaonReadyByType(int type);
}
