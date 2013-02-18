package com.decooda.dropBox

import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object FileChunkReader {
   def getInputStreamAsChunk(file:FileInputStream,buffer:Array[Byte],numChunk:Int,chunkSize:Int)={
     println("In file chunk reader !! "+ (numChunk-1)*chunkSize+"   size "+chunkSize)
     file.read(buffer,0*chunkSize,chunkSize)
     buffer
   }
   
//    def getBytes(file:File,buffer:Array[Byte],numChunk:Int,chunkSize:Int): Array[Byte] = {
//     val is = new FileInputStream(file)
//    int len;
//    int size = 1024;
//    byte[] buf;
//
//    if (is instanceof ByteArrayInputStream) {
//      size = is.available();
//      buf = new byte[size];
//      len = is.read(buf, 0, size);
//    } else {
//      ByteArrayOutputStream bos = new ByteArrayOutputStream();
//      buf = new byte[size];
//      while ((len = is.read(buf, 0, size)) != -1)
//        bos.write(buf, 0, len);
//      buf = bos.toByteArray();
//    }
//    return buf;
//  }
}