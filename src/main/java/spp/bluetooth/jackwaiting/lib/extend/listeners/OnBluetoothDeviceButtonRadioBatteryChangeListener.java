package spp.bluetooth.jackwaiting.lib.extend.listeners;

/**
 * <p>
 * 低电量监听器。
 * </p>
 *
 * @author Created by JackWaiting on 2018/9/29.
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceButtonRadioBatteryChangeListener {

    /**
     * <p>
     * 查询低电量反馈
     * </p>
     * @param isSuccess 版本。
     */
    void onBluetoothDeviceInquireBattery(boolean isSuccess);

    /**
     * <p>
     * 设置电量是否成功反馈
     * </p>
     * @param isSuccess 版本。
     */
    void onBluetoothDeviceSuccess(boolean isSuccess);
}
