package com.example.contacts

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.backgroundlaunch.IRemoteService

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        requestContactPermission()
        doBindService()
    }

    private fun requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS), 1);
        } else {
//            readContacts();
        }
    }

    private fun readContacts() {
        val contactUri = Uri.withAppendedPath(
            Uri.parse("content://com.google.contacts/1"),
            ContactsContract.Contacts.Entity.CONTENT_DIRECTORY
        )
        val projection: Array<String> = arrayOf(
            ContactsContract.Contacts.Entity.RAW_CONTACT_ID,
            ContactsContract.Contacts.Entity.DATA1,
            ContactsContract.Contacts.Entity.MIMETYPE,
        )
        val cursor = contentResolver.query(contactUri, projection, null, null, null)
        cursor?.apply {
            val displayNameColumnIndex =
                getColumnIndex(ContactsContract.Contacts.Entity.DISPLAY_NAME)
            while (moveToNext()) {
                val displayName = getString(displayNameColumnIndex)
                Log.e("cui", displayName)
            }
        }
        Toast.makeText(this, "readContacts", Toast.LENGTH_SHORT).show()
        val cr: ContentResolver = contentResolver
        val cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null)
        if (cur?.count ?: 0 > 0) {
            while (cur?.moveToNext() == true) {
                val id: String = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name: String =
                    cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt() > 0
                ) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null)
                    while (pCur?.moveToNext() == true) {
                        val phoneNo: String? =
                            pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        Log.e("cui", "Name:$name")
                        Log.e("cui", "PhoneNo: $phoneNo")
                    }
                    pCur?.close()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContacts()
        } else {
            requestContactPermission()
        }
    }


    var iRemoteService: IRemoteService? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName?,
            service: IBinder?,
        ) {
            Log.e("cui", "onServiceConnected")
            iRemoteService = IRemoteService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            Log.e("cui", "onServiceDisconnected")
            iRemoteService = null
        }
    }

    private fun doBindService() {
        try {
            val intent = Intent().apply {
                action = "com.example.backgroundlaunch.IRemoteService"
                `package` = "com.example.backgroundlaunch"
            }
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.e("cui", "securityException")
        }
    }

}