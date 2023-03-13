package com.xebiaassignment.app.helper


import java.io.InputStreamReader

object FileReader {

    fun readFileFromResource(fileName : String) : String{
        val inputStream = FileReader::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream,"UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }

}