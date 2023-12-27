package com.example.dynamic_app_icon

import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val channel = "dynamic_app_icon_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channel)
            .setMethodCallHandler { call, result ->
                if (call.method == "changeAppIcon") {
                    val isFirstIcon = call.argument<Boolean>("isFirstIcon")
                    changeAppIcon(isFirstIcon!!)
                    result.success("Success : $isFirstIcon")
                } else {
                    result.notImplemented()
                }
            }
    }
}

fun getComponentNames(isFirstIcon: Boolean, packageName: String) : Pair<String, String>{
    val enabled = when (isFirstIcon) {
        true -> "${packageName}.MainActivity"
        false -> "${packageName}.MainActivityAlias"
    }

    val disabled = when (isFirstIcon) {
        true -> "${packageName}.MainActivityAlias"
        false -> "${packageName}.MainActivity"
    }
    return Pair(enabled, disabled)
}

fun Activity.changeAppIcon(
    isFirstIcon : Boolean
) {
    //to get component names to enabled and disable activities
    val (enabled, disabled) = getComponentNames(isFirstIcon, packageName)

    packageManager.setComponentEnabledSetting(
        ComponentName(
            this,
            enabled
        ),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
    packageManager.setComponentEnabledSetting(
        ComponentName(
            this,
            disabled
        ),
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}




