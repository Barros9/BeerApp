package com.barros.beerapp.libraries.beer.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object FileReader {

    @Throws(IOException::class)
    fun readFileFromResources(fileName: String): String {
        var inputStream: InputStream? = null
        return try {
            inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.readLines().forEach {
                builder.append(it)
            }
            builder.toString()
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream?.close()
        }
    }
}
