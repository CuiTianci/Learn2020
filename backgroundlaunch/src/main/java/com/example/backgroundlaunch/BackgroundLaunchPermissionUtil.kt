package com.example.backgroundlaunch

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.text.TextUtils
import java.lang.reflect.Method

/**
 * 常规手机：
 * Android9.0及以下，可以从后台启动Activity。
 * Android10.0及以上，需要SYSTEM_ALERT_WINDOW权限。
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 * 官方文档总介绍了其他特殊情况：
 * 小米手机：
 * 在系统中存在一个特殊权限：后台弹出页面权限。
 * 小米手机（无论Android及MIUI版本）能否后台启动Activity取决于上述权限。
 * 官方文档说明：https://dev.mi.com/console/doc/detail?pId=1735
 */
class BackgroundLaunchPermissionUtil {

    companion object {

        /**
         * 判断是否可以后台启动Activity。
         */
        fun isPermissionGranted(context: Context): Boolean =
            isCommonDevicePermissionGranted(context) || isMiPermissionGranted(context)

        /**
         * 常规设备判断条件：API29以下不需权限。API29及以上需要OverLay权限。
         */
        private fun isCommonDevicePermissionGranted(context: Context) =
            Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || Settings.canDrawOverlays(context)

        /**
         * 打开系统授权页。
         * @param activity 上下文。
         * @param requestCode 启动权限页面的请求码。
         */
        fun startPermissionGrantActivity(activity: Activity, requestCode: Int) {
            if (isMi()) {
                openMiPermissionSettings(activity, requestCode)
            } else {
                openCommonSettings(activity, requestCode)
            }
        }

        /**
         * 启动小米手机授权。
         * @param activity 上下文。
         * @param requestCode 启动权限页面的请求码。
         * @return 是否正常启动Activity。
         */
        private fun openMiPermissionSettings(activity: Activity, requestCode: Int): Boolean {
            return try {
                val intent = Intent()
                intent.action = "miui.intent.action.APP_PERM_EDITOR"
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.putExtra("extra_pkgname", activity.packageName)
                activity.startActivityForResult(intent, requestCode)
                true
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                openCommonSettings(activity, requestCode)
            }
        }

        /**
         * 启动“应用上层显示”--SYSTEM_ALERT_WINDOW授权页。
         * @param activity 上下文。
         * @param requestCode 启动权限页面的请求码。
         *  @return 是否正常启动Activity。
         */
        private fun openCommonSettings(activity: Activity, requestCode: Int): Boolean {
            return try {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.packageName))
                activity.startActivityForResult(intent, requestCode)
                true
            } catch (e: java.lang.Exception) {
                false
            }
        }

        /**
         * 判断是否为小米手机。
         */
        private fun isMi(): Boolean = Build.BRAND.equals("xiaomi", true)

        /**
         * 小米手机权限单独判断。
         */
        private fun isMiPermissionGranted(context: Context): Boolean {
            if (!isMi()) return false
            return try {
                val ops: AppOpsManager =
                    context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                val op = 10021
                val method: Method = ops.javaClass.getMethod("checkOpNoThrow",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java)
                method.invoke(ops,
                    op,
                    Process.myUid(),
                    context.packageName) == AppOpsManager.MODE_ALLOWED
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        /**
         * 判断是否开启AccessibilityService
         * 开启下列受系统约束的服务时，不需要任何其他权限，即拥有后台启动Activity的能力。
         * AccessibilityService、AutofillService、CallRedirectionService、HostApduService、InCallService、TileService、VoiceInteractionService 和 VrListenerService
         * @param service 包名+“/”+service类（包路径+类名）
         */
        fun isAccessibilitySettingsOn(context: Context, service: String): Boolean {
            val mStringColonSplitter: TextUtils.SimpleStringSplitter =
                TextUtils.SimpleStringSplitter(':')
            val settingValue = Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService: String = mStringColonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}