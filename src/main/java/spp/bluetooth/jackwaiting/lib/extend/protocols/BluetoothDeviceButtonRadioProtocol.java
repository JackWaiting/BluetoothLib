package spp.bluetooth.jackwaiting.lib.extend.protocols;

/**
 * Created by JackWaiting on 2018/7/10.
 */
public class BluetoothDeviceButtonRadioProtocol {

    /**
     * <p>
     * 控制命令Value。
     * </p>
     *
     * @since 1.0.12
     */
    public static class CommandKeyValue {

        /**
         * <p>
         * 下一个频道。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_NEXT_CHANNEL = 0x01;

        /**
         * <p>
         * 上一个频道。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_PREV_CHANNEL = 0x02;


        /**
         * <p>
         * 点击设备按键的各种状态。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_ONCLICK = 0x03;

        /**
         * <p>
         * 长按放松
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_RELEASE = 0x04;

    }


    /**
     * <p>
     * 控制命令Type。
     * </p>
     *
     * @since 1.0.12
     */
    public static class CommandKeyType {

        /**
         * <p>
         * 按键短按。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_SHORT = 0x02;

        /**
         * <p>
         * 按键保持长按，当按键按键300MS后会发送此类型，并且之后每160ms会发送一次。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_HOLD = 0x04;


        /**
         * <p>
         * 按键长按。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_LONG = 0x08;

        /**
         * <p>
         * 按键双击。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_DOUBLE = 0x09;

    }


    /**
     * <p>
     * 控制命令Upload。
     * </p>
     *
     * @since 1.0.12
     */
    public static class CommandUploadType {

        /**
         * <p>
         * 叮当自定义命令。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_NOMAL = 0x21;

        /**
         * <p>
         * 音量自定义命令。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_VOLUME = 0x32;

        /**
         * <p>
         * 提示音自定义命令。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int KEY_TYPE_TIP_VOLUME = 0x31;

        /**
         * <p>
         * 版本号查询。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int INQUIRY_VERSION = 0x30;

        /**
         * <p>
         * 打开功放 20s。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int OPEN_PA = 0x29;

        /**
         * <p>
         * APP校验命令。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int DEVICE_CHECK = 0x28;

        /**
         * <p>
         * 设置低电量提示音。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int SET_LOW_BATTERY_TONE = 0x26;

        /**
         * <p>
         * 查询低电量提示音。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int INQUIRY_LOW_BATTERY_TONE = 0x25;

        public static final String SMART_A2F = "smart_A2F";
    }
}
