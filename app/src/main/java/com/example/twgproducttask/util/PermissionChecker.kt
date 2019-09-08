package com.example.twgproducttask.util

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class PermissionChecker(context: Context) {
    private var context: Context?= null

    init {
        this.context = context
    }

    fun ifLackPermissions(permissions: Array<String>): Boolean? {
        var i = 0
        while (i < permissions.size) {
            if (ifLackPermission(permissions[i])!!) {
                return true
            }
            i++
        }
        return false
    }

    internal fun ifLackPermission(permission: String): Boolean? {
        return ContextCompat.checkSelfPermission(context!!, permission) == PackageManager.PERMISSION_DENIED
    }
}