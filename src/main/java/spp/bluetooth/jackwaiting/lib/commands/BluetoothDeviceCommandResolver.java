package spp.bluetooth.jackwaiting.lib.commands;

import android.nfc.Tag;

import spp.bluetooth.jackwaiting.lib.extend.devices.BluetoothDeviceButtonRadioManager;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;


/**
 * <p>蓝牙设备命令解析者。
 * </p>
 *
 * @author JackWaiting
 */
public class BluetoothDeviceCommandResolver {


    private static final String TAG = "BluetoothDeviceCommandResolver";

    /**
     * <p>
     * <strong>解析自定义命令。分发到各个管理器。外部请勿调用！</strong>
     * </p>
     * <p>
     * <strong>说明：调用此方法之前，已经判断了命令的有效性。</strong>
     * </p>
     *
     * @param command 命令
     * @since 1.0.0
     */
    public static void parse(byte[] command) {
        LogManager.d("command.length = " + command[5]);

        int length = command[5]; //当前命令的总长度
        // 切取命令：去掉 头+尾+长度位+设备识别位
        int notNeedHandelLength = 4 + 1 + 1;
        //4代表的是头表示，第一个1代表版本号，第二个1代表总长度
        int handleLength = length - notNeedHandelLength;
        if(handleLength > 0){
            byte[] subCommand = new byte[length];
            byte[] handleCommand = new byte[handleLength];
            System.arraycopy(command, 0, subCommand, 0, length);
            System.arraycopy(command, notNeedHandelLength, handleCommand, 0, handleLength);
            LogManager.debug(TAG, "original command:",
                    BluetoothDeviceCommandManager.byte2HexStr(subCommand));

            LogManager.debug(TAG, "Command that needs to be processed:",
                    BluetoothDeviceCommandManager.byte2HexStr(handleCommand));

            // 区分不同的设备，然后将自定义命令分发到各自的Manager中。
            switch (BluetoothDeviceCommandManager.isHeadCommandToButtonRadioValid(subCommand)) {
                case BluetoothDeviceCommandProtocol.DeviceType.BUTTON_RADIO: {
                    BluetoothDeviceButtonRadioManager.getInstance().setCommand(
                            handleCommand);
                }
                break;

                default:
                    break;
            }
        }

    }
}
