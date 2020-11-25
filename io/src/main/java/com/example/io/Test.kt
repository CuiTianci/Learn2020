package com.example.io

import java.io.*

fun main() {
//    io1()
//    io2()
//    io3()
//    io4()
//    io3()
    io5()
}


private fun io1() {
    val outputStream = FileOutputStream("./io/text.txt")
    outputStream.write(1)
    outputStream.write(22)
    outputStream.close()
}

private fun io2() {
    val inputStream = FileInputStream("./io/text.txt")
    println(inputStream.read().toChar())
    println(inputStream.read().toChar())
    inputStream.close()
}

private fun io3() {
    val inputStream = FileInputStream("./io/text.txt")
    val reader = inputStream.bufferedReader()
    println(reader.readLine())
    inputStream.close()
    reader.close()
}

private fun io4() {
    val outputStream = FileOutputStream("./io/text.txt")
    val writer = outputStream.bufferedWriter()
    writer.append("i am studying too2")
    writer.flush()
    writer.close()
}

private fun io5() {
    val inputStream = FileInputStream("./io/text.txt")
    val outputStream = FileOutputStream("./io/new_text.txt")
    val data = ByteArray(1024)
    var readCount = inputStream.read(data)
    while (readCount != -1) {
        outputStream.write(data, 0, readCount)
        readCount = inputStream.read(data)
    }



    inputStream.close()
    outputStream.close()


}