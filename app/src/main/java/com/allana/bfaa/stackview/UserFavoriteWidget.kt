package com.allana.bfaa.stackview

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.allana.bfaa.R
import com.allana.bfaa.database.UserFavorite

/**
 * Implementation of App Widget functionality.
 */
class UserFavoriteWidget : AppWidgetProvider() {

    private var userList = ArrayList<UserFavorite>()
    private val widgetItems = ArrayList<Bitmap>()
    private var cursor: Cursor? = null

    companion object {
        private const val TOAST_ACTION = "com.allana.bfaa.TOAST_ACTION"
        const val EXTRA_ITEM = "com.allana.bfaa.EXTRA_ITEM"

        private const val WIDGET_CLICK = "widgetsclick"
        private const val WIDGET_ID_EXTRA = "widget_id_extra"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetServices::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.user_favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val toastIntent = Intent(context, UserFavoriteWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            
            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
//        if (intent?.action != null) {
//            if (intent.action == TOAST_ACTION) {
//                Toast.makeText(context, "Your user favorite", Toast.LENGTH_SHORT).show()
//            }
//        }
        when (intent?.action) {
            TOAST_ACTION -> Toast.makeText(context, "Your user favorite", Toast.LENGTH_SHORT).show()
            WIDGET_CLICK -> {
            }
        }
    }

    fun getLastUpdate() {


    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}