package spp.bluetooth.jackwaiting.lib.listeners;

import android.bluetooth.BluetoothDevice;

/**
 * Created by JackWaiting on 2018/7/2.
 */
public interface OnBluetoothDeviceDiscoveryListener {

    /**
     * <p>
     * 蓝牙设备扫描已经开始。
     * </p>
     * <p>
     * 备注：比较常见的操作是在这里提醒下用户，蓝牙设备扫描已经开始。
     * </p>
     *
     * @since 1.0.0
     */
     void onBluetoothDeviceDiscoveryStarted();

    /**
     * <p>
     * <strong>扫描出的蓝牙设备，每扫描到一个蓝牙设备，就返回一个蓝牙设备。调用者请自己维护一个列表，请注意
     * {@link #onBluetoothDeviceDiscoveryStarted()}和
     * {@link #onBluetoothDeviceDiscoveryFinished()} 之间作为一个列表。 </strong>
     * </p>
     * <p>
     * <strong> 备注：为了优化用户体验，可以在扫描到属于自己的设备的时候，就停止扫描，让用户可以操作连接。</strong>
     * </p>
     *
     * @param bluetoothDevice
     *            扫描出的蓝牙设备。注意：如果你通过
     *            {@link spp.bluetooth.jackwaiting.lib.managers.BluetoothDeviceManager#setMacAddressFilterPrefix(String...)} (String)}
     *            方法设置了需要扫描出来的蓝牙设备MAC地址前缀的话，那么这里回调的设备也只有你想要过滤出的设备。另外，
     *            有部分厂商的本地Android设备扫描出来的 {@link BluetoothDevice#getName()}
     *            为null，但是实际上不为null，这种情况下，请调用者自行根据产品需求显示成“未知”或者MAC地址。
     *
     * @since 1.0.0
     */
     void onBluetoothDeviceDiscoveryFound(
            BluetoothDevice bluetoothDevice);

    /**
     * <p>
     * 蓝牙设备扫描已经结束。
     * </p>
     * <p>
     * <strong>说明：根据实际的经验，本地Android设备的普通蓝牙扫描从开始到结束的这个时间大致在10-13s左右，
     * 根据设备的不同而不同。 这个过程比较长，建议调用者优化UI和产品逻辑，给用户更好的体验。 </strong>
     * </p>
     *
     * @since 1.0.0
     */
     void onBluetoothDeviceDiscoveryFinished();
}
