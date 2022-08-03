package app.ishizaki.ryu.devgoal

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.google.android.material.resources.TypefaceUtils

class DevGoalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}