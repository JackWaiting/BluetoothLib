package spp.bluetooth.jackwaiting.lib.extend.listeners;

/**
 * <p>
 * 蓝牙版本号获取监听器。
 * </p>
 *
 * @author Created by JackWaiting on 2018/6/29.
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceButtonRadioVersionChangeListener {

    /**
     * <p>
     * 波动设备触发提示音加载成功
     * </p>
     * @param version 版本。
     */
    void onBluetoothDeviceCurrentVersion(int version);
}
