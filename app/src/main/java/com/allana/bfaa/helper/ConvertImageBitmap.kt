package com.allana.bfaa.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL

object ConvertImageBitmap {
    fun getBitmapFromUrl(urlImage: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(urlImage)
            val connection = url.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            val bufferedInputStream = BufferedInputStream(inputStream)
            bitmap = BitmapFactory.decodeStream(bufferedInputStream)
            bufferedInputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            Log.e("Error", "Error getting image bitmap", e)
        }
        return bitmap
    }
}