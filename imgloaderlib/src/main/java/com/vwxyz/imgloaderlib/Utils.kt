package com.vwxyz.imgloaderlib

import java.io.InputStream
import java.io.OutputStream

object Utils {
    fun CopyStream(inputStream: InputStream, os: OutputStream) {
        val buffer_size = 1024
        try {
            val bytes = ByteArray(buffer_size)
            while (true) {
                val count = inputStream.read(bytes, 0, buffer_size)
                if (count == -1)
                    break
                os.write(bytes, 0, count)
            }
        } catch (ex: Exception) {

        }

    }
}