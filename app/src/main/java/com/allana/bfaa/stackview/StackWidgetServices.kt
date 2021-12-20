package com.allana.bfaa.stackview

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetServices: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}