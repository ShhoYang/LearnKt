package com.hao.learnkt.utils

import java.io.File

/**
 * @author Yang Shihao
 */
class FileUtil {

    companion object {

        @Throws(Exception::class)
        fun getFolderSize(file: File): Long {
            var size: Long = 0
            try {
                var listFiles = file.listFiles()
                for (f in listFiles) {
                    if (f.isDirectory) {
                        getFolderSize(f)
                    } else {
                        size += f.length()
                    }
                }
                size += getFolderSize(file)
            } catch (e: Exception) {
                return size
            }
            return size
        }

        fun deleteFolder(file: File): Boolean {
            try {
                var listFiles = file.listFiles()
                for (f in listFiles) {
                    if (f.isDirectory) {
                        deleteFolder(f)
                    } else {
                        f.delete()
                    }
                }
            } catch (e: Exception) {
                return false
            }
            return true
        }
    }
}