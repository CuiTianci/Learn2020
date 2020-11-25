package com.example.bluetooth

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_device.view.*

class DeviceAdapter(private val deviceItemClickListener: (device: BluetoothDevice?) -> Unit) :
    RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    var mList: MutableList<MyBluetoothManager.BluetoothDeviceExtra>? = null

    fun updateData(list: List<MyBluetoothManager.BluetoothDeviceExtra>?) {
        if (mList == null) mList = ArrayList()
        mList?.clear()
        if (list != null) {
            mList?.addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mList?.get(position), listener = deviceItemClickListener)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(
            deviceExtra: MyBluetoothManager.BluetoothDeviceExtra?,
            listener: (device: BluetoothDevice?) -> Unit
        ) {
            val device = deviceExtra?.bluetoothDevice
            device?.let {
                itemView.tvDeviceName.text = device.name ?: "null"
                itemView.tvDeviceAddress.text = device.address ?: "null"
                itemView.setOnClickListener {
                    listener(device)
                }
                val colorRes = when (device.bondState) {
                    BluetoothDevice.BOND_BONDED -> R.color.purple_200
                    BluetoothDevice.BOND_BONDING -> R.color.teal_200
                    BluetoothDevice.BOND_NONE -> R.color.black
                    else -> R.color.black
                }
                itemView.tvDeviceName.setTextColor(colorRes)
                itemView.tvClass.text = deviceClass(it)
                itemView.tvStrength.text = signalStrength(deviceExtra.getRssi().toInt()).toString()
            }
        }

        private fun signalStrength(rssi: Int): Int {
//            return when {
//                rssi >= -55 -> 4
//                rssi in -55 downTo -66 -> 3
//                rssi in -66 downTo -71 -> 2
//                rssi in -72 downTo -88 -> 1
//                else -> 0
//            }
            return rssi
        }

        private fun deviceClass(device: BluetoothDevice): String {
            Log.e(TAG, "device:class:${device.bluetoothClass.deviceClass}")
            return when (device.bluetoothClass.majorDeviceClass) {
                BluetoothClass.Device.Major.COMPUTER -> "computer"
                BluetoothClass.Device.Major.PHONE -> "phone"
                BluetoothClass.Device.Major.WEARABLE -> "wearable"
                BluetoothClass.Device.Major.AUDIO_VIDEO -> {
                    when (device.bluetoothClass.deviceClass) {
                        BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> "headphone"
                        else -> "audio_video:${device.bluetoothClass.deviceClass}"
                    }
                }
                else -> "others：${device.bluetoothClass.deviceClass}"
            }
        }
    }
}