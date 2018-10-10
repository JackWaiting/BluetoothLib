package spp.bluetooth.jackwaiting.lib.listeners;

/**
 * <p>
 * 蓝牙设备管理器是否准备好的监听器。
 * </p>
 *
 * @author JackWaiting
 *
 * @since 1.0.0
 */
public interface OnBluetoothDeviceManagerReadyListener {
    /**
     * <p>
     * <strong>{@link spp.bluetooth.jackwaiting.lib.managers.BluetoothDeviceManager}已经准备好，可以去调用方法中备注了只有当
     * {@link spp.bluetooth.jackwaiting.lib.managers.BluetoothDeviceManager}准备好才能去调用的方法。</strong>
     * </p>
     * <p>
     * <strong>备注：这个方法是在设备连接之后才会被触发。</strong>
     * </p>
     *
     * @since 1.0.0
     */
     void onBluetoothDeviceManagerReady();
}
