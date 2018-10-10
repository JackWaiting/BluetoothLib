package spp.bluetooth.jackwaiting.lib.status;

/**
 * <p>
 * 蓝牙设备连接状态静态类。
 * </p>
 *
 * @author JackWaiting
 *
 * @since 1.0.0
 */
public class ConnectionState {
    /**
     * <p>
     * 蓝牙设备断开连接。
     * </p>
     * <p>
     * <strong>说明：先是SPP断开、然后A2DP断开，表示蓝牙设备连接完全断开。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int DISCONNECTED = 0;

    /**
     * <p>
     * 蓝牙初始化状态。
     * </p>
     * <p>
     * <strong>说明：当前状态表示蓝牙即将开始做连接工作。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int INIT_CONNECT = -1;

    /**
     * <p>
     * 蓝牙设备已经连接。
     * </p>
     * <p>
     * <strong>说明：先A2DP连接成功、然后SPP连接成功之后，表示蓝牙设备已经完全连接。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int CONNECTED = 1;

    /**
     * <p>
     * 蓝牙设备A2DP配对中。
     * </p>
     * <p>
     * <strong>说明：连接蓝牙设备前先是A2DP配对。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int A2DP_PAIRING = 2;

    /**
     * <p>
     * 蓝牙设备A2DP连接中。
     * </p>
     * <p>
     * <strong>说明：连接蓝牙设备A2DP配对之后，A2DP连接中。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int A2DP_CONNECTING = 3;

    /**
     * <p>
     * 蓝牙设备A2DP已经连接。
     * </p>
     * <p>
     * <strong>说明：连接蓝牙设备A2DP配对、A2DP连接中，如果A2DP连接成功的话，会回调此状态。</strong>
     * </p>
     * <p>
     * <strong>备注：此状态下，A2DP模式功能可用。但是SPP控制不可用，自定义控制命令。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int A2DP_CONNECTED = 4;

    /**
     * <p>
     * 蓝牙设备A2DP连接失败。
     * </p>
     * <p>
     * <strong>说明：连接蓝牙设备A2DP配对，A2DP连接中，如果A2DP连接失败的话，会回调A2DP连接失败的状态。</strong>
     * </p>
     * <p>
     * <strong>备注：库内部会有重新尝试连接机制，如果尝试一定次数还是A2DP连接失败的话，
     * 就返回这个状态。由于未知原因导致在当前应用中无法连接蓝牙设备的A2DP协议
     * ，这个时候，可以提醒用户：由于未知原因，导致应用内无法连接上设备的A2DP协议
     * ，可以尝试去设置连接上设备的A2DP协议，跳转到系统蓝牙设置中，连接上A2DP协议之后，返回到应用中。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int A2DP_FAILURE = 5;

    /**
     * <p>
     * 蓝牙设备A2DP断开连接。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，主动或者被动断开A2DP连接，会触发此状态的回调。</strong>
     * </p>
     * <p>
     * <strong>备注：A2DP断开连接之后，蓝牙的音频推送功能失效。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int A2DP_DISCONNECTED = 6;

    /**
     * <p>
     * 蓝牙设备SPP连接中。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，会尝试连接SPP，这个时候会回调SPP连接中的状态。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int SPP_CONNECTING = 7;

    /**
     * <p>
     * 蓝牙设备SPP连接成功。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，会尝试连接SPP，SPP连接成功之后，会回调此状态。</strong>
     * </p>
     * <p>
     * <strong>备注：此状态下，A2DP(可以进行本地音频通过蓝牙推送到蓝牙设备上播放)/SPP(可以发送自定义控制命令)功能均可用。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int SPP_CONNECTED = 8;

    /**
     * <p>
     * 蓝牙设备SPP连接失败。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，会尝试连接SPP，如果SPP连接失败，会回调SPP连接失败的状态。</strong>
     * </p>
     * <p>
     * <strong>备注：库内部会有重新尝试连接机制，如果尝试一定次数还是SPP连接失败的话，
     * 就返回这个状态，而且库内部会断开A2DP连接，这样做为了避免A2DP连接上
     * ，而SPP连接不上，导致音频可以传输，但是设备不可控。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int SPP_FAILURE = 9;

    /**
     * <p>
     * 蓝牙设备SPP断开连接。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备SPP连接成功之后，主动或者被动断开SPP连接，会触发此状态的回调。</strong>
     * </p>
     * <p>
     * <strong>备注：SPP断开连接之后，设备控制功能失效。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int SPP_DISCONNECTED = 10;

    /**
     * <p>
     * 蓝牙设备HFP连接中。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，会尝试连接HFP，这个时候会回调HFP连接中的状态。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int HFP_CONNECTING = 11;

    /**
     * <p>
     * 蓝牙设备SPP连接成功。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，会尝试连接HFP，HFP连接成功之后，会回调此状态。</strong>
     * </p>
     * <p>
     * <strong>备注：此状态下，A2DP(可以进行本地音频通过蓝牙推送到蓝牙设备上播放)/HFP(语音通话)功能均可用。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int HFP_CONNECTED = 12;

    /**
     * <p>
     * 蓝牙设备HFP连接失败。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，会尝试连接HFP，如果HFP连接失败，会回调HFP连接失败的状态。</strong>
     * </p>
     * <p>
     * <strong>备注：库内部会有重新尝试连接机制，如果尝试一定次数还是HFP连接失败的话，
     * 就返回这个状态，而且库内部会断开A2DP连接，这样做为了避免A2DP连接上
     * ，而HFP连接不上，导致音频可以传输，但是设备不可控。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int HFP_FAILURE = 13;

    /**
     * <p>
     * 蓝牙设备HFP断开连接。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备HFP连接成功之后，主动或者被动断开HFP连接，会触发此状态的回调。</strong>
     * </p>
     * <p>
     * <strong>备注：HFP断开连接之后，语音通话失效。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int HFP_DISCONNECTED = 14;

    /**
     * <p>
     * 蓝牙设备连接超时。
     * </p>
     * <p>
     * <strong>说明：当外部发起连接蓝牙设备之后，如果在5秒钟之内没有任何的连接状态的回调，那么会回调此状态。告知用户，
     * 当前连接蓝牙设备超时， 请确认蓝牙设备是否打开，并且处于蓝牙模式，而且是蓝牙设备处于可连接距离范围内。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int TIMEOUT = 15;

    /**
     * <p>
     * 蓝牙设备A2DP断开连接适配华为。
     * </p>
     * <p>
     * <strong>说明：当蓝牙设备A2DP连接成功之后，主动或者被动断开A2DP连接，会触发此状态的回调。</strong>
     * </p>
     * <p>
     * <strong>备注：A2DP断开连接之后，蓝牙的音频推送功能失效。</strong>
     * </p>
     *
     * @since 1.0.0
     */
    public static final int A2DP_HUAWEI_DISCONNECTED = 16;

}
