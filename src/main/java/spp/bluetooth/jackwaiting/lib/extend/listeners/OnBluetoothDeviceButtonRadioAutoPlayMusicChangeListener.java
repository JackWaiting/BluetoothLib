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
public interface OnBluetoothDeviceButtonRadioAutoPlayMusicChangeListener {

    /**
     * <p>
     * 波动设备触发提示音加载成功
     * </p>
     * @param feedBack 反馈。
     * @param powerSwitch 开关。
     */
    void onBluetoothDeviceAutoPlayMusic(int  feedBack, int powerSwitch);
}
