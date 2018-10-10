package spp.bluetooth.jackwaiting.lib.extend.devices;

import spp.bluetooth.jackwaiting.lib.commands.BluetoothDeviceCommandManager;
import spp.bluetooth.jackwaiting.lib.commands.BluetoothDeviceCommandProtocol;
import spp.bluetooth.jackwaiting.lib.extend.listeners.OnBluetoothDeviceButtonRadioPaonChangeListener;
import spp.bluetooth.jackwaiting.lib.extend.listeners.OnBluetoothDeviceButtonRadioVersionChangeListener;
import spp.bluetooth.jackwaiting.lib.extend.listeners.OnBluetoothDeviceButtonRadiolUIChangedListener;
import spp.bluetooth.jackwaiting.lib.extend.protocols.BluetoothDeviceButtonRadioProtocol;
import spp.bluetooth.jackwaiting.lib.listeners.OnBluetoothDeviceManagerReadyListener;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * <p>
 * 蓝牙设备音箱管理器。
 * </p>
 * <strong>备注：请勿调用{@link BluetoothDeviceButtonRadioManager#getInstance()}获取
 * {@link BluetoothDeviceButtonRadioManager}对象！</strong>
 *
 * @author JackWaiting
 * @since 1.0.0
 */
public class BluetoothDeviceButtonRadioManager {

    private static final String TAG = "BluetoothDeviceButtonRadioManager";
    /**
     * <p>
     * 蓝牙设备纽扣电台管理器对象。
     * </p>
     *
     * @since 1.0.0
     */
    private static BluetoothDeviceButtonRadioManager bluetoothDeviceButtonRadioManager;

    /**
     * <p>
     * 获取蓝牙设备音箱管理器对象。
     * </p>
     * <p>
     * <strong>备注：此方法只是由{@link spp.bluetooth.jackwaiting.lib.managers.BluetoothDeviceManager}内部调用！</strong>
     * </p>
     *
     * @return 蓝牙设备音箱管理器对象。
     * @since 1.0.0
     */
    public static BluetoothDeviceButtonRadioManager getInstance() {
        if (bluetoothDeviceButtonRadioManager == null) {
            bluetoothDeviceButtonRadioManager = new BluetoothDeviceButtonRadioManager();
        }

        return bluetoothDeviceButtonRadioManager;
    }

    /**
     * <p>
     * 外部传入的蓝牙设备全局UI变化监听器。
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceButtonRadiolUIChangedListener sOnBluetoothDeviceButtonRadiolUIChangedListener;

    /**
     * <p>
     * 设置蓝牙设备全局UI变化监听器。
     * </p>
     *
     * @param onBluetoothDeviceButtonRadiolUIChangedListener 蓝牙设备全局UI变化监听器。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceGlobalUIChangedListener(
            OnBluetoothDeviceButtonRadiolUIChangedListener onBluetoothDeviceButtonRadiolUIChangedListener) {
        sOnBluetoothDeviceButtonRadiolUIChangedListener = onBluetoothDeviceButtonRadiolUIChangedListener;
    }


    /**
     * <p>
     * 外部传入的蓝牙功放打开是否准备好监听器。
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceButtonRadioPaonChangeListener sOnBluetoothDeviceButtonRadioPaonChangeListener;

    /**
     * <p>
     * 设置蓝牙功放打开是否准备好监听器。
     * </p>
     *
     * @param onBluetoothDeviceButtonRadioPaonChangeListener 蓝牙设备全局UI变化监听器。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceButtonRadioPaonChangeListener(
            OnBluetoothDeviceButtonRadioPaonChangeListener onBluetoothDeviceButtonRadioPaonChangeListener) {
        sOnBluetoothDeviceButtonRadioPaonChangeListener = onBluetoothDeviceButtonRadioPaonChangeListener;
    }

    /**
     * <p>
     * 外部传入的蓝牙版本号获取监听器
     * </p>
     *
     * @since 1.0.0
     */
    private static OnBluetoothDeviceButtonRadioVersionChangeListener sOnBluetoothDeviceButtonRadioVersionChangeListener;

    /**
     * <p>
     * 设置蓝牙版本号获取监听器
     * </p>
     *
     * @param onBluetoothDeviceButtonRadioVersionChangeListener 蓝牙设备全局UI变化监听器。
     * @since 1.0.0
     */
    public void setOnBluetoothDeviceButtonRadioVersionChangeListener(
            OnBluetoothDeviceButtonRadioVersionChangeListener onBluetoothDeviceButtonRadioVersionChangeListener) {
        sOnBluetoothDeviceButtonRadioVersionChangeListener = onBluetoothDeviceButtonRadioVersionChangeListener;
    }


    /**
     * <p>
     * 单例模式，构造器私有化。
     * </p>
     *
     * @since 1.0.0
     */
    private BluetoothDeviceButtonRadioManager() {
    }


    /**
     * <p>
     * 传入自定义命令。
     * </p>
     *
     * @param command 自定义命令。
     * @since 1.0.12
     */
    public void setCommand(byte[] command) {
        this.parse(command);
    }

    /**
     * <p>
     * 设置设备音量。
     * </p>
     *
     * @param volume 设备音量。
     * @since 1.0.0
     */
    public void setVolume(int volume) {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceCommandProtocol.CommandType.CONTROL, 1, volume);
    }

    /**
     * <p>
     * 设置提示音。
     * </p>
     *
     * @since 1.0.0
     */
    public void setTipVolume() {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.KEY_TYPE_TIP_VOLUME, 1, 0);
    }

    /**
     * <p>
     * 设置低电量提示音。
     * </p>
     * @param power 是否打开提示音
     * @since 1.0.0
     */
    public void setLowBatteryTone(int power) {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.SET_LOW_BATTERY_TONE, 1, power);
    }

    /**
     * <p>
     * 查询低电量提示音。
     * </p>
     *
     * @since 1.0.0
     */
    public void inquireLowBatteryTone() {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.INQUIRY_LOW_BATTERY_TONE, 0);
    }
    /**
     * <p>
     * 设置设备校验。
     * </p>
     *
     * @since 1.0.0
     */
    public void setDeviceCheck() {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.DEVICE_CHECK,
                BluetoothDeviceButtonRadioProtocol.CommandUploadType.SMART_A2F.length(),
                BluetoothDeviceCommandManager.strToByteArray(BluetoothDeviceButtonRadioProtocol.CommandUploadType.SMART_A2F));
    }

    /**
     * <p>
     * 查询设备音量。
     * </p>
     *
     * @since 1.0.0
     */
    public void inquireVolume() {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.KEY_TYPE_VOLUME, 0);
    }


    /**
     * <p>
     * 查询当前固件版本号。
     * </p>
     *
     * @since 1.0.0
     */
    public void inquireCurrentVersion() {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.INQUIRY_VERSION, 1, 0);
    }

    /**
     * <p>
     * 打开功放20s。
     * </p>
     *
     * @since 1.0.0
     */
    public void openPA() {
        BluetoothDeviceCommandManager.sendCommand(BluetoothDeviceButtonRadioProtocol.CommandUploadType.OPEN_PA, 1, 0);
    }

    /**
     * <p>
     * 解析自定义命令。
     * </p>
     *
     * @param command 自定义命令。
     * @since 1.0.12
     */
    private void parse(byte[] command) {
        // 返回的命令
        if (command.length >= 4) {
            LogManager.d(TAG, "type command:" + (command[0] & 0xff));
            if (command[0] == BluetoothDeviceButtonRadioProtocol.CommandUploadType.KEY_TYPE_NOMAL) {
                if (command[1] == 2) {
                    if (sOnBluetoothDeviceButtonRadiolUIChangedListener != null) {
                        if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_NEXT_CHANNEL
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_SHORT) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceChannelNext();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_PREV_CHANNEL
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_SHORT) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceChannelPre();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_ONCLICK
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_SHORT) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceMusicNext();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_ONCLICK
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_DOUBLE) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceMusicPre();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_PREV_CHANNEL
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_LONG) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceSeekBackStart();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_PREV_CHANNEL
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_HOLD) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceSeekBackStop();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_RELEASE
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_HOLD) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceStopRecord();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_NEXT_CHANNEL
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_LONG) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceSeekToStart();
                        } else if (command[2] == BluetoothDeviceButtonRadioProtocol.CommandKeyValue.KEY_NEXT_CHANNEL
                                && command[3] == BluetoothDeviceButtonRadioProtocol.CommandKeyType.KEY_TYPE_HOLD) {
                            sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceSeekToStop();
                        }
                    }
                }
            } else if (command[0] == BluetoothDeviceButtonRadioProtocol.CommandUploadType.KEY_TYPE_VOLUME) {
                if (command[1] == 2) {
                    LogManager.d(TAG, "volume command:" + (command[2]));
                    if (sOnBluetoothDeviceButtonRadiolUIChangedListener != null) {
                        sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceVolume(command[2]);
                    }
                }
            } else if (command[0] == 1) { //特殊命令
                if (sOnBluetoothDeviceButtonRadiolUIChangedListener != null) {
                    sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceStartRecord();
                }
            }
        } else if (command.length > 0) {
            if (command[0] == BluetoothDeviceButtonRadioProtocol.CommandUploadType.KEY_TYPE_TIP_VOLUME) {
                LogManager.d(TAG, "tipVolume command:");
                if (sOnBluetoothDeviceButtonRadiolUIChangedListener != null) {
                    sOnBluetoothDeviceButtonRadiolUIChangedListener.onBluetoothDeviceTipSuccess();
                }
            }else if (command[0] == BluetoothDeviceButtonRadioProtocol.CommandUploadType.INQUIRY_VERSION) {
                if (command[1] == 1) {
                    LogManager.d(TAG, "version command:" + (command[2]));
                    if (sOnBluetoothDeviceButtonRadioVersionChangeListener != null) {
                        sOnBluetoothDeviceButtonRadioVersionChangeListener.onBluetoothDeviceCurrentVersion(command[2]);
                    }
                }
            }else if (command[0] == BluetoothDeviceButtonRadioProtocol.CommandUploadType.OPEN_PA) {
                if (command[1] == 1) {
                    LogManager.d(TAG, "pa command:" + (command[2]));
                    if (sOnBluetoothDeviceButtonRadioPaonChangeListener != null) {
                        sOnBluetoothDeviceButtonRadioPaonChangeListener.onBluetoothDevicePaonReady();
                    }
                }
            }
        }

    }

}
