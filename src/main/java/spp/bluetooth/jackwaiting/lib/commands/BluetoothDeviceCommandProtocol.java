package spp.bluetooth.jackwaiting.lib.commands;


/**
 * <p>
 * 蓝牙设备命令协议。
 * </p>
 * <p>
 * 备注：外部请勿调用！
 * </p>
 *
 * @author JackWaiting
 *
 * @since 1.0.0
 */
public class BluetoothDeviceCommandProtocol {

    /**
     * <p>
     * 自定义命令的最小长度。
     * </p>
     *
     * @since 1.0.0
     */
    public static final int COMMAND_LENGTH_MINIMUM = 7;


    /**
     * <p>
     * 蓝牙设备类型。
     * </p>
     *
     * @author ifeegoo
     *
     * @since 1.0.0
     *
     */
    public static final class DeviceType {
        /**
         * <p>
         * 默认蓝牙设备。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int DEFAULT = 0x00;

        /**
         * <p>
         * 纽扣电台。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int BUTTON_RADIO = 0x01;

    }


    /**
     * <p>
     * 蓝牙控制命令类型。
     * </p>
     *
     * @author ifeegoo
     *
     * @since 1.0.0
     *
     */
    public static final class CommandType {
        /**
         * <p>
         * 查询。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int INQUIRY = 0x32;

        /**
         * <p>
         * APP 控制。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int CONTROL = 0x33;

        /**
         * <p>
         * APP 控制。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int CONTROL_TTS = 0x31;

        /**
         * <p>
         * 设备反馈。
         * </p>
         *
         * @since 1.0.0
         */
        public static final int FEEDBACK = 0x32;



    }
}
