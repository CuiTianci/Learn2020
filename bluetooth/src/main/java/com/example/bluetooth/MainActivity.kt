package com.example.bluetooth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var adapter: DeviceAdapter
    private lateinit var mDeviceExtraSet: MutableSet<MyBluetoothManager.BluetoothDeviceExtra>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MyBluetoothManager.REQUEST_CODE_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                toast("ok")
            } else {
                toast("canceled")
            }
        }
    }

    private fun initView() {
        enableBt.setOnClickListener {
            MyBluetoothManager.getInstance().requestEnableBluetooth(this)
        }
        getBonded.setOnClickListener {
            val bondedDevices = MyBluetoothManager.getInstance().bondedDevices
            if (bondedDevices.isEmpty()) {
                logger("no device bonded")
            }
            bondedDevices.forEach {
                logger(it.name)
                logger(it.address)
                logger(it.toString())
            }
        }
        registerReceiver.setOnClickListener {
            MyBluetoothManager.getInstance().registerReceiver(this)
            MyBluetoothManager.getInstance().setOnDeviceDiscoveredListener {
                if (!this::mDeviceExtraSet.isInitialized) mDeviceExtraSet = HashSet()
                mDeviceExtraSet.add(it)
                adapter.updateData(mDeviceExtraSet.toList())
            }
        }
        startDiscover.setOnClickListener {
            if (!this::mDeviceExtraSet.isInitialized) mDeviceExtraSet = HashSet()
            mDeviceExtraSet.clear()
            adapter.updateData(mDeviceExtraSet.toList())
            MyBluetoothManager.getInstance().startDiscovery()
        }


        rvDevices.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = DeviceAdapter {
            if (it?.name?.contains("_t") == true || it?.name?.contains("狼") == true) {
                if (swMusic.isChecked) {
                    toast("tryToPlyMusic:${it.name}")
                    MyBluetoothManager.getInstance().playMusic(this, it)
                } else {
                    toast("tryToConnect:${it.name}")
                    MyBluetoothManager.getInstance().connectDevice(it)
                }
            }

        }
        rvDevices.adapter = adapter
    }
}