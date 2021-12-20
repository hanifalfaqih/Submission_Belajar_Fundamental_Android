package com.allana.bfaa.stackview

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.allana.bfaa.R
import com.allana.bfaa.contentprovider.UserContentProvider.Companion.CONTENT_URI
import com.allana.bfaa.database.UserFavorite
import com.allana.bfaa.helper.ConvertImageBitmap
import com.allana.bfaa.helper.MappingHelper.userFavoriteList

class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private var userList = ArrayList<UserFavorite>()
    private val widgetItems = ArrayList<Bitmap>()
    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {
        cursor?.close()
        val identityToken = Binder.clearCallingIdentity()
        cursor = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)

        userList = cursor?.userFavoriteList()!!
        userList.forEach { userFavorite ->
            ConvertImageBitmap.getBitmapFromUrl(userFavorite.avatar_url.toString())?.let { bitmap ->
                widgetItems.add(bitmap)
            }
        }

        Binder.restoreCallingIdentity(identityToken)

    }

    override fun onDestroy() {}

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteView = RemoteViews(mContext.packageName, R.layout.widget_item)
        remoteView.setImageViewBitmap(R.id.image_user_widget, widgetItems[position])

        val extras = bundleOf(
            UserFavoriteWidget.EXTRA_ITEM to userList[position].username
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        remoteView.setOnClickFillInIntent(R.id.image_user_widget, fillInIntent)
        return remoteView
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}