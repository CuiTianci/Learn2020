package com.example.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED;
import static android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_STARTED;
import static android.bluetooth.BluetoothAdapter.ACTION_SCAN_MODE_CHANGED;
import static android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED;
import static android.bluetooth.BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE;
import static android.bluetooth.BluetoothAdapter.EXTRA_PREVIOUS_STATE;
import static android.bluetooth.BluetoothAdapter.EXTRA_SCAN_MODE;
import static android.bluetooth.BluetoothAdapter.EXTRA_STATE;
import static android.bluetooth.BluetoothDevice.ACTION_FOUND;

/**
 * 蓝牙连接管理。
 * https://developer.android.google.cn/guide/topics/connectivity/bluetooth#SettingUp
 * 1.注意：Android 设备默认处于不可检测到状态。用户可通过系统设置将设备设为在有限的时间内处于可检测到状态，或者，应用可请求用户在不离开应用的同时启用可检测性。如需了解更多信息，请参阅本页面的启用可检测性部分。
 * 2.注意!!!：执行设备发现将消耗蓝牙适配器的大量资源。在找到要连接的设备后，请务必使用 cancelDiscovery() 停止发现，然后再尝试连接。此外，您不应在连接到设备的情况下执行设备发现，因为发现过程会大幅减少可供任何现有连接使用的带宽。
 */
public class MyBluetoothManager {

    private static final String TAG = "cui";

    public static final int REQUEST_CODE_ENABLE_BT = 1;
    private static final int DISCOVERABLE_DURATION_UNSET = -1;

    private static MyBluetoothManager instance;
    private final BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver receiver;
    private DeviceDiscoveredListener deviceDiscoveredListener;
    private final Set<BluetoothDevice> discoveredDeviceSet;

    private MyBluetoothManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        discoveredDeviceSet = new HashSet<>();
    }

    public static MyBluetoothManager getInstance() {
        if (instance == null) {
            synchronized (MyBluetoothManager.class) {
                if (instance == null) {
                    instance = new MyBluetoothManager();
                }
            }
        }
        return instance;
    }

    /**
     * 当前设备是否支持蓝牙。
     */
    public boolean isBluetoothSupported() {
        return bluetoothAdapter != null;
    }

    /**
     * 是否已经开启蓝牙。
     */
    public boolean isBluetoothEnabled() {
        if (!isBluetoothSupported()) return false;
        return bluetoothAdapter.isEnabled();
    }

    /**
     * 开启蓝牙
     * 在onActivity中拿到结果。
     */
    public void requestEnableBluetooth(Activity activity) {
        if (isBluetoothSupported() && !isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, REQUEST_CODE_ENABLE_BT);
        }
    }

    /**
     * 直接开启蓝牙。
     * 在测试机三星S10上有效。
     */
    public void enableBluetooth() {
        if (bluetoothAdapter == null) return;
        bluetoothAdapter.enable();
    }

    /**
     * 查询已配对设备。
     */
    public Set<BluetoothDevice> getBondedDevices() {
        if (bluetoothAdapter == null) return new HashSet<>();
        return bluetoothAdapter.getBondedDevices();
    }

    /**
     * 开始查找附近蓝牙设备。
     * 需要位置权限。
     */
    public boolean startDiscovery() {
        if (bluetoothAdapter == null) return false;
        cancelDiscovery();
        return bluetoothAdapter.startDiscovery();
    }

    /**
     * 注册广播，发现蓝牙设备。
     */
    public void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FOUND);
        intentFilter.addAction(ACTION_STATE_CHANGED);
        intentFilter.addAction(ACTION_SCAN_MODE_CHANGED);//当前设备是否可检测到。
        intentFilter.addAction(ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case ACTION_FOUND:
                        if (null != deviceDiscoveredListener) {
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            boolean added = discoveredDeviceSet.add(device);
                            if (added && device.getName() != null) {//过滤到重复的设备，以及名称为空的设备。
                                final short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);//rssi
                                BluetoothDeviceExtra deviceExtra = new BluetoothDeviceExtra(device, rssi);
                                deviceDiscoveredListener.onDeviceDiscovered(deviceExtra);
                            }
                        }
                        break;
                    case ACTION_STATE_CHANGED:
                        int curState = intent.getIntExtra(EXTRA_STATE, -1);
                        int prevState = intent.getIntExtra(EXTRA_PREVIOUS_STATE, -1);
                        break;
                    case ACTION_SCAN_MODE_CHANGED:
                        int curScanMode = intent.getIntExtra(EXTRA_SCAN_MODE, -1);
                        int prevScanMode = intent.getIntExtra(EXTRA_PREVIOUS_SCAN_MODE, -1);
                        break;
                    case ACTION_DISCOVERY_STARTED:
                        Log.e(TAG, "扫描开始");
                        break;
                    case ACTION_DISCOVERY_FINISHED:
                        Log.e(TAG, "扫描完成");
                        break;
                    case BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED://A2dp连接。
                        int state = intent.getIntExtra(BluetoothA2dp.EXTRA_STATE, BluetoothA2dp.STATE_DISCONNECTED);
                        Log.e(TAG, "connectState:" + state);
                        if (state == BluetoothA2dp.STATE_CONNECTED)
                            music(context);
                        break;
                }
            }
        };
        context.registerReceiver(receiver, intentFilter);
    }

    /**
     * 取消注册广播。
     */
    public void unregisterReceiver(Context context) {
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    /**
     * 设置发现设备监听。
     *
     * @param deviceDiscoveredListener {@link DeviceDiscoveredListener}
     */
    public void setOnDeviceDiscoveredListener(DeviceDiscoveredListener deviceDiscoveredListener) {
        this.deviceDiscoveredListener = deviceDiscoveredListener;
    }

    /**
     * 取消查找设备。
     */
    public void cancelDiscovery() {
        if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    /**
     * 发现蓝牙设备监听。
     */
    public interface DeviceDiscoveredListener {
        void onDeviceDiscovered(BluetoothDeviceExtra device);
    }

    /**
     * 将设备设置为可被检测到。
     * 默认为120秒
     * EXTRA_DISCOVERABLE_DURATION 自定义时长最高为3600秒，设置为0的话，始终可检测到，安全性低，不建议使用。
     */
    public void setDeviceDiscoverable(Activity activity, int duration) {
        Intent intent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (DISCOVERABLE_DURATION_UNSET != duration)
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, duration);
        activity.startActivity(intent);
    }

    /**
     * 将设备设置为可被检测到，并在activityResult回调中处理结果。
     *
     * @param requestCode 请求码。
     * @see #setDeviceDiscoverable(Activity, int)
     */
    public void setDeviceDiscoverable(Activity activity, int duration, int requestCode) {
        Intent intent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (DISCOVERABLE_DURATION_UNSET != duration)
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, duration);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @see #setDeviceDiscoverable(Activity, int)
     * 系统默认时间120秒。
     */
    public void setDeviceDiscoverable(Activity activity) {
        setDeviceDiscoverable(activity, DISCOVERABLE_DURATION_UNSET);
    }

    /**
     * 与目标设备进行配对
     */
    public void bondDevice(BluetoothDevice device) {
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            cancelDiscovery();
            device.createBond();
        }
    }

    /**
     * 解除与设备的配对。
     */
    public void removeBond(BluetoothDevice device) {
        if (device.getBondState() == BluetoothDevice.BOND_BONDED)
            try {
                Method method = BluetoothDevice.class.getMethod("createBond");
                method.setAccessible(true);
                method.invoke(device);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
    }


    /**
     * 连接设备。
     */
    public void connectDevice(BluetoothDevice device) {
        if (device.getName() != null && device.getName().contains("_t")) {
            new ConnectThread(device).start();
        }
    }


    /**
     * 设备连接任务
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;

        public ConnectThread(final BluetoothDevice device) {
            BluetoothSocket tmp = null;
            //安卓系统4.2以后的蓝牙通信端口为 1 ，但是默认为 -1，所以只能通过反射修改，才能成功
            try {
                tmp = (BluetoothSocket) device.getClass()
                        .getDeclaredMethod("createRfcommSocket", new Class[]{int.class})
                        .invoke(device, 1);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();//耗时。
                Log.e(TAG, "connected");
            } catch (NullPointerException | IOException e) {
                Log.e(TAG, "取消或者连接失败。");
                e.printStackTrace();
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
            }
        }

    }

    /**
     * 让蓝牙设备播放音乐。
     * 需要先进性配对。
     */
    public void playMusic(Context context, BluetoothDevice device) {
        if (bluetoothAdapter == null) return;
        if (!(device.getBondState() == BluetoothDevice.BOND_BONDED)) {
            Toast.makeText(context, "device not bonded", Toast.LENGTH_SHORT).show();
            bondDevice(device);
        }
        BluetoothProfile.ServiceListener bs = new BluetoothProfile.ServiceListener() {

            BluetoothA2dp bluetoothA2dp;

            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                bluetoothA2dp = (BluetoothA2dp) proxy;
                setPriority(bluetoothA2dp, device, 100);
                try {
                    //通过反射获取BluetoothA2dp中connect方法（hide的），进行连接。
                    Method connectMethod = BluetoothA2dp.class.getMethod("connect",
                            BluetoothDevice.class);
                    connectMethod.invoke(bluetoothA2dp, device);
                    Log.e(TAG, "connectMethod");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "connectException");
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
            }
        };
        bluetoothAdapter.getProfileProxy(context, bs, BluetoothProfile.A2DP);
//        bluetoothAdapter.getProfileProxy(context, bs, BluetoothProfile.HEADSET);
    }

    public void setPriority(BluetoothA2dp a2dp, BluetoothDevice device, int priority) {
        if (a2dp == null) return;
        try {//通过反射获取BluetoothA2dp中setPriority方法（hide的），设置优先级
            Method priorityMethod = BluetoothA2dp.class.getMethod("setPriority",
                    BluetoothDevice.class, int.class);
            priorityMethod.invoke(a2dp, device, priority);
        } catch (Exception e) {
            Log.e("cui", "noSetPriority");
            e.printStackTrace();
        }
    }


    /**
     * 播放音乐。
     */
    private void music(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ring);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private UUID getUuid() {
        return UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    }

    public static class BluetoothDeviceExtra {
        BluetoothDevice bluetoothDevice;
        short rssi;

        public BluetoothDeviceExtra(BluetoothDevice device, short rssi) {
            this.bluetoothDevice = device;
            this.rssi = rssi;
        }

        public BluetoothDevice getBluetoothDevice() {
            return bluetoothDevice;
        }

        public short getRssi() {
            return rssi;
        }
    }
}
