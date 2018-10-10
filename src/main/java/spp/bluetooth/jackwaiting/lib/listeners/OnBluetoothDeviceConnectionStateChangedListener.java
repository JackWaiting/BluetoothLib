package spp.bluetooth.jackwaiting.lib.listeners;

import android.bluetooth.BluetoothDevice;

/**
 * <p>
 * 蓝牙设备连接状态变化监听器。
 * </p>
 *
 * @author Created by JackWaiting on 2018/6/29.
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceConnectionStateChangedListener {
    /**
     * <p>
     * 蓝牙设备连接状态变化。
     * </p>
     *
     * @param bluetoothDevice
     *            连接状态发生变化的蓝牙设备。
     * @param state
     *            蓝牙连接状态。
     *
     * @see spp.bluetooth.jackwaiting.lib.status.ConnectionState
     *
     * @since 1.0.0
     */
    public void onBluetoothDeviceConnectionStateChanged(
            BluetoothDevice bluetoothDevice, int state);
}
