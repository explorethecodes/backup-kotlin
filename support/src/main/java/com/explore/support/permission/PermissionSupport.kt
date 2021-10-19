package com.explore.support.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.qifan.powerpermission.PowerPermission
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.data.hasPermanentDenied
import com.qifan.powerpermission.data.hasRational

fun Fragment.hasPermissions(permissions : Array<String>, permissionTracker: PermissionTracker){

    PowerPermission.init().requestPermissions(context = this, permissions = *permissions) { permissionResult ->
        when {

            permissionResult.hasAllGranted() -> {
                permissionTracker.hasAllGranted()
            }

            permissionResult.hasRational() -> {
                permissionTracker.hasRational()
            }

            permissionResult.hasPermanentDenied() -> {
                permissionTracker.hasPermissionDenied()
            }
        }
    }
}

fun FragmentActivity.hasPermissions(permissions : Array<String>, permissionTracker: PermissionTracker){

    PowerPermission.init().requestPermissions(context = this, permissions = *permissions) { permissionResult ->
        when {

            permissionResult.hasAllGranted() -> {
                permissionTracker.hasAllGranted()
            }

            permissionResult.hasRational() -> {
                permissionTracker.hasRational()
            }

            permissionResult.hasPermanentDenied() -> {
                permissionTracker.hasPermissionDenied()
            }
        }
    }
}

interface PermissionTracker{
    fun hasAllGranted()
    fun hasRational()
    fun hasPermissionDenied()
}