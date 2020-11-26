# 后台启动Activity的限制说明及权限申请


## 参考文档
    https://developer.android.google.cn/guide/components/activities/background-starts

## 限制说明（官方文档）
    在 Android 10 或更高版本上运行的应用只有在满足以下一项或多项条件时，才能启动 Activity：
    1. 应用具有可见窗口，例如前台 Activity。
    2. 应用在前台任务的返回栈中拥有 Activity。
    3. 应用在 Recents 屏幕上现有任务的返回栈中拥有 Activity。
    4. 应用的某个 Activity 刚在不久前启动。
    5. 应用最近为某个 Activity 调用了 finish()。这仅适用于在调用 finish() 时，应用在前台或前台任务的返回栈中拥有 Activity 的情况。
    6. 应用具有受系统约束的服务。此情况仅适用于以下服务，这些服务可能需要启动界面：AccessibilityService、AutofillService、CallRedirectionService、HostApduService、InCallService、TileService、VoiceInteractionService 和 VrListenerService。
    7. 应用中的某个服务受另一个可见应用约束。请注意，绑定到服务的应用必须保持可见，以便后台应用成功启动 Activity。
    8. 应用收到系统的 PendingIntent 通知。对于服务和广播接收器的PendingIntent，应用可在该PendingIntent 发送几秒钟后启动 Activity。
    9. 应用收到另一个可见应用发送的 PendingIntent。
    10. 应用收到它应该在其中启动界面的系统广播。示例包括 ACTION_NEW_OUTGOING_CALL 和 SECRET_CODE_ACTION。应用可在广播发送几秒钟后启动 Activity。
    11. 应用通过 CompanionDeviceManager API 与配套硬件设备相关联。此 API 支持应用启动 API，以响应用户在配对设备上执行的操作。
    12. 应用是在设备所有者模式下运行的设备政策控制器。示例用例包括完全托管的企业设备，以及数字标识牌和自助服务终端等专用设备。
    13. 用户已向应用授予 SYSTEM_ALERT_WINDOW 权限。
### Google推荐做法
    FullScreenIntent，这是Google推荐的后台启动Activity的方式，其表现形式分为以下两种。
    1. 未锁屏状态下，弹出通知，用户通过点击通知，启动Activity。
    2. 锁屏状态下，Activity会立即启动。
### 特殊情况
#### 小米设备
    小米设备中有这样两个权限：Start in Background和Display pop-up window。  在不同系统版本上有可能存在差异。
    但至少存在Start in Background权限，且至少取得Start in background权限，应用才能从后台启动页面。
    虽然在不同版本上，权限并不一致（可能存在权限数量不同和必须授予的权限不同），但好在无论哪种情况，小米给出的Api都能够对此进行统一判断。
## 权限申请
### 通常做法
- 常规设备(Android10及以上)通常通过申请授予SYSTEM_ALERT_WINDOW权限，获得后台启动Activity的能力。
- 小米设备（无关Android版本）需要授予Start in Background和Display pop-up window权限。

### 判断是否具备后台启动Activity的条件
- 常规设备：
```js
private fun isCommonDevicePermissionGranted(context: Context) =
        !isMi() && (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || Settings.canDrawOverlays(context))
```    
- 小米设备：
```js
 fun isMiPermissionGranted(context: Context): Boolean {
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
```

### 启动设置页，请求授予权限
- 常规设备：
```xml
  <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```
```js
 fun openCommonSettings(activity: Activity, requestCode: Int): Boolean {
            return try {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.packageName))
                activity.startActivityForResult(intent, requestCode)
                true
            } catch (e: java.lang.Exception) {
                false
            }
        }
```
- 小米设备：
```js
 fun openMiPermissionSettings(activity: Activity, requestCode: Int): Boolean {
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
```

## 其他方案
### 满足限制说明第六点，以[AccessibilityService](https://developer.android.google.cn/reference/android/accessibilityservice/AccessibilityService)为例
- 继承AccessibilityService类
```js
class MyAccessibilityService : AccessibilityService() {

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
}
```
- 在AndroidManifest.xml中声明Service。
```xml
<service
      android:name=".MyAccessibilityService"
      android:label="testAccessibilityService"
      android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
          <intent-filter>
            <action android:name="android.accessibilityservice.AccessibilityService" />
          </intent-filter>
          <meta-data
            android:name="android.accessibilityservice"
            android:resource="@xml/accessibility_service_config" />
</service>
```
- accessibility_service_config.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFeedbackType="feedbackSpoken"
    android:accessibilityFlags="flagDefault"
    android:canRetrieveWindowContent="true"
    android:notificationTimeout="100"
    android:packageNames="com.example.android.apis"
    android:settingsActivity="com.example.android.accessibility.ServiceSettingsActivity" />
```
- 判断是否开启AccessibilityService
```js
//@param service:包名 + “/” + Service完整名称（包路径 + 类名）
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
```
- 启动设置页，请求开启AccessibilityService
```js
private fun toAccessibilityServiceSettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }
```
- 备注：每次安装AccessibilityService都会被关闭（包括覆盖安装）。
## 满足限制条件第7点：应用中的某个服务受另一个可见应用约束。
通过[AIDL](https://developer.android.google.cn/guide/components/aidl?hl=zh_cn)将B应用绑定到A应用的服务上，当B应用可见时，A应用可以从后台启动Activity，可参考文章末尾提供的Demo(BackgroundLaunch模块对应A应用，Contact模块对应B应用)。当然，这种方式虽然可行，但显然十分不实用。
# 总结
虽然文章开头给出的后台启动Activity的条件有很多，但绝大部分条件都比较苛刻，不太实用。  
应用具有如AccessibilityService等受系统约束的服务尚能解决问题，但这些Service会在应用覆盖安装甚至杀死进程时关闭，这种方案又显得差强人意；授权SYSTEM_ALERT_WINDOW可以说是其中最靠谱的方案了，但也只是相对而言，毕竟没办法保证跳转系统设置中授权的成功转化率。对于Android10.0以上的手机（小米手机特殊处理），以上两种方式虽然都有各自的不足，但可以说是唯二的后台启动Activity的可靠解决方案了。  
如若没有十分必要直接启动页面的话，推荐使用FullScreenIntent的方式，以带来更好的用户体验。
# Demo
 - [项目地址](https://github.com/CuiTianci/Learn2020/tree/master/backgroundlaunch)  

 - [工具类](https://github.com/CuiTianci/Learn2020/blob/master/backgroundlaunch/src/main/java/com/example/backgroundlaunch/BackgroundLaunchPermissionUtil.kt)
