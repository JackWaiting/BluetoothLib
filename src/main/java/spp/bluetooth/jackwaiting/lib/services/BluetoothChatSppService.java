package spp.bluetooth.jackwaiting.lib.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import spp.bluetooth.jackwaiting.lib.commands.BluetoothDeviceCommandManager;
import spp.bluetooth.jackwaiting.lib.commands.BluetoothDeviceCommandProtocol;
import spp.bluetooth.jackwaiting.lib.commands.BluetoothDeviceCommandResolver;
import spp.bluetooth.jackwaiting.lib.status.ConnectionState;
import spp.bluetooth.jackwaiting.lib.utils.LogManager;

/**
 * Created by JackWaiting on 2018/6/28.
 */
public class BluetoothChatSppService {
    // Debugging
    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;

    // Name for the SDP record when creating server socket
    private static final String NAME = "BluetoothChat";

    // Unique UUID for this application

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Member fields
    private final BluetoothAdapter mAdapter;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    private static BluetoothChatSppService bluetoothChatSppService;
    private BluetoothDevice mConnectDevice;
    private int connectionFailedCount = 0;
    private BluetoothSocket mmSocket;

    public boolean isConnected() {
        if (mmSocket != null) {
            return mmSocket.isConnected() && mState == ConnectionState.SPP_CONNECTED;
        }
        return false;
    }

    public void initConnectionFailedCount() {
        connectionFailedCount = 0;
    }

    public interface OnConnectionListener {

        void onConnectionStateChanged(
                BluetoothDevice bluetoothDevice, int state);
    }

    private static OnConnectionListener onConnectionListener;

    public void setOnConnectionListener(OnConnectionListener listener) {
        onConnectionListener = listener;
    }

    public BluetoothDevice getCurrentConnectedDevice() {
        return mConnectDevice;
    }


    /**
     * Constructor. Prepares a new BluetoothChat session.
     */
    public BluetoothChatSppService() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = ConnectionState.SPP_DISCONNECTED;
    }


    public static BluetoothChatSppService getInstance() {
        if (bluetoothChatSppService == null) {
            bluetoothChatSppService = new BluetoothChatSppService();
        }
        return bluetoothChatSppService;
    }

    /**
     * Set the current state of the chat connection
     *
     * @param state An integer defining the current connection state
     */
    private synchronized void setState(int state, BluetoothDevice device) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        if (onConnectionListener != null) {
            onConnectionListener.onConnectionStateChanged(device, state);
        }
    }

    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {
        if (D) Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     *
     * @param device The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        if (D) Log.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == ConnectionState.SPP_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(ConnectionState.SPP_CONNECTING, device);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     *
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (D) Log.d(TAG, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Cancel the accept thread because we only want to connect to one device
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        mConnectDevice = device;
        setState(ConnectionState.SPP_CONNECTED, device);
    }

    /**
     * Stop all threads
     */
    public synchronized void stopSpp() {
        if (D) Log.d(TAG, "stop");
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }
        mConnectDevice = null;
        mState = ConnectionState.SPP_DISCONNECTED;
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != ConnectionState.SPP_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed(BluetoothDevice bluetoothDevice) {
        if (connectionFailedCount < 3) {
            connectionFailedCount++;

        }
        setState(ConnectionState.SPP_FAILURE, bluetoothDevice);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost(BluetoothDevice bluetoothDevice) {
        setState(ConnectionState.SPP_DISCONNECTED, bluetoothDevice);
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try {
                if(mAdapter != null){
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
                }
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != ConnectionState.SPP_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    if(mmServerSocket != null){
                        socket = mmServerSocket.accept();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothChatSppService.this) {
                        switch (mState) {
                            case ConnectionState.SPP_CONNECTING:
                                // Situation normal. Start the connected thread.
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case ConnectionState.SPP_DISCONNECTED:
                            case ConnectionState.SPP_CONNECTED:
                                // Either not ready or already connected. Terminate new socket.
                                try {
                                    socket.close();
                                    mConnectDevice = null;
                                } catch (IOException e) {
                                    Log.e(TAG, "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread");
        }

        public void cancel() {
            if (D) Log.d(TAG, "cancel " + this);
            try {
                if (mmServerSocket != null) {
                    mmServerSocket.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }


    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            if (device != null) {
                mmDevice = device;
                BluetoothSocket tmp = null;

                // Get a BluetoothSocket for a connection with the
                // given BluetoothDevice
                try {
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                } catch (IOException e) {
                    Log.e(TAG, "create() failed", e);
                }
                mmSocket = tmp;
            }
        }

        public void run() {

            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                if (mmSocket != null) {
                    mmSocket.connect();
                }
            } catch (IOException e) {
                connectionFailed(mmDevice);
                // Close the socket
                try {
                    if (mmSocket != null) {
                        mmSocket.close();
                    }
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // Start the service over to restart listening mode
                BluetoothChatSppService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothChatSppService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                if (mmSocket != null) {
                    mmSocket.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {

        private InputStream mmInStream;
        private OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            if (socket != null) {
                Log.d(TAG, "create ConnectedThread");
                mmSocket = socket;

                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                // Get the BluetoothSocket input and output streams
                try {
                    tmpIn = socket.getInputStream();
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "temp sockets not created", e);
                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    if (null != mmInStream) {
                        mmInStream.read(buffer);
                    }
                    LogManager.debug("Device sends command to App", "BluetoothChat original command:",
                            BluetoothDeviceCommandManager.byte2HexStr(buffer));
                    if (BluetoothDeviceCommandManager.isCommandValid(buffer)
                            && BluetoothDeviceCommandManager.isHeadCommandToButtonRadioValid(buffer) == BluetoothDeviceCommandProtocol.DeviceType.BUTTON_RADIO) {
                        BluetoothDeviceCommandResolver.parse(buffer);
                    } else {
                        LogManager.e("设备发送命令为无效命令");
                    }

                    // Send the obtained bytes to the UI Activity
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost(mmSocket.getRemoteDevice());
                    try {
                        if (mmSocket != null) {
                            mmSocket.close();
                        }
                    } catch (IOException e2) {
                        Log.e(TAG, "unable to close() socket during connection failure", e2);
                    }
                    stopSpp();
                    break;
                }catch (Exception ignored){
                    Log.e(TAG, "出现异常");
                }

            }
        }

        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                if (mmOutStream != null) {
                    mmOutStream.write(buffer);
                    LogManager.debug("APP sends command to Device", "original command:",
                            buffer);
                }
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                if (mmSocket != null) {
                    mmSocket.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

}
