package com.apptt.axdecor.utilities

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast

class ARCoreUtils {
    companion object{
         fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Log.e("LOGAXDECOR", "Sceneform requires Android N or later")
                Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
                    .show()
                activity.finish()
                return false
            }
            val openGlVersionString =
                (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                    .deviceConfigurationInfo
                    .glEsVersion
            if (java.lang.Double.parseDouble(openGlVersionString) < 3.0) {
                Log.e("LOGAXDECOR", "Sceneform requires OpenGL ES 3.0 later")
                Toast.makeText(
                    activity,
                    "Sceneform requires OpenGL ES 3.0 or later",
                    Toast.LENGTH_LONG
                )
                    .show()
                activity.finish()
                return false
            }
            return true
        }

    }
}