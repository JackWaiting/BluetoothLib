package spp.bluetooth.jackwaiting.lib.commands;

import java.util.Arrays;

import spp.bluetooth.jackwaiting.lib.services.BluetoothChatSppService;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * <p>
 * 蓝牙设备命令管理器。
 * </p>
 * <p>
 * 备注：外部请勿调用！
 * </p>
 *
 * @author JackWaiting
 * @since 1.0.0
 */
public class BluetoothDeviceCommandManager {

    public static final String TDDH = "TDDH";
    private static final String TAG = "BluetoothDeviceCommandManager";

    /**
     * <p>
     * 判断自定义命令是否有效。
     * </p>
     *
     * @param command 自定义通信命令 new byte[]{*********}。
     * @return TRUE：自定义命令有效。FALSE：自定义命令无效。
     */
    public static boolean isCommandValid(byte[] command) {
        if (((command != null)
                && (command.length >= BluetoothDeviceCommandProtocol.COMMAND_LENGTH_MINIMUM))) {

            return true;
        }
        return false;
    }

    public static void sendCommand(int commandType,
                                   int... otherCommand) {
        BluetoothChatSppService.getInstance().write(build(commandType, otherCommand));
    }

    public static void sendCommand(int commandType,int length,
                                   byte[] otherCommand) {
        BluetoothChatSppService.getInstance().write(build(commandType,length, otherCommand));
    }


    /**
     * <p>
     * 生成自定义命令。
     * </p>
     *
     * @param commandType  命令类型。
     * @param otherCommand 除去以上命令的剩余命令。
     * @return 完整的用于通信的自定义命令。
     * @since 1.0.0
     */
    public static byte[] build(int commandType,
                               int... otherCommand) {

        // 自定义命令的总长度 ＝ (头标识：TDDH +版本号 + 总长度 ) + ( commandType + otherCommand.length
        int length = 4 + 1 + 1 + 1 + otherCommand.length;

        byte[] command = new byte[length];

        // 自定义命令头部。
        command[0] = (byte) 0x54;
        command[1] = (byte) 0x44;
        command[2] = (byte) 0x44;
        command[3] = (byte) 0x48;
        //版本号
        command[4] = (byte) 0x1;
        // 自定义命令长度。
        command[5] = (byte) length;

        // 自定义命令类型
        command[6] = (byte) commandType;
        // 其他命令处理。
        if (otherCommand.length > 0) {
            for (int i = 0; i < otherCommand.length; i++) {
                command[i + 7] = (byte) otherCommand[i];
            }
        }

        LogManager.debug(TAG, bytesToHexString(command) +"");
        return command;
    }

    /**
     * <p>
     * 生成自定义命令。
     * </p>
     *
     * @param commandType  命令类型。
     * @param commandLength 命令长度。
     * @param otherCommand 除去以上命令的剩余命令。
     * @return 完整的用于通信的自定义命令。
     * @since 1.0.0
     */
    public static byte[] build(int commandType,int commandLength,
                               byte[] otherCommand) {

        // 自定义命令的总长度 ＝ (头标识：TDDH +版本号 + 总长度 ) + ( commandType + otherCommand.length
        int length = 4 + 1 + 1 + 1 + commandLength +otherCommand.length;

        byte[] command = new byte[length];

        // 自定义命令头部。
        command[0] = (byte) 0x54;
        command[1] = (byte) 0x44;
        command[2] = (byte) 0x44;
        command[3] = (byte) 0x48;
        //版本号
        command[4] = (byte) 0x1;
        // 自定义命令长度。
        command[5] = (byte) length;

        // 自定义命令类型
        command[6] = (byte) commandType;
        command[7] = (byte) commandLength;
        // 其他命令处理。
        if (otherCommand.length > 0) {
            for (int i = 0; i < otherCommand.length; i++) {
                command[i + 8] = otherCommand[i];
            }
        }

        LogManager.debug(TAG, bytesToHexString(command) +"");
        return command;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * <p>
     * 通过头命令判断是属于哪种类型的设备。
     * </p>
     *
     * @param command 自定义通信命令 new byte[]{*********}。
     * @return TRUE：自定义命令是纽扣电台。FALSE：自定义命令不是纽扣电台。
     */
    public static int isHeadCommandToButtonRadioValid(byte[] command) {
        byte[] headCommand = new byte[4];

        for (int i = 0; i < headCommand.length; i++) {
            headCommand[i] = command[i];
        }
        LogManager.i(new String(command));
        if (new String(command).contains(TDDH)) {
            LogManager.debug(TAG, "当前设备类型为纽扣电台");
            return BluetoothDeviceCommandProtocol.DeviceType.BUTTON_RADIO;
        }
        return BluetoothDeviceCommandProtocol.DeviceType.DEFAULT;
    }

    public static String byte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }

    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;

    }


}
