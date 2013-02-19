package com.decooda.dropBox
import dispatch._
import dispatch.oauth._
import dispatch.oauth.OAuth._
import java.io.FileInputStream
import java.io.File
import sun.nio.ch.IOUtil
import sun.misc.IOUtils
import java.io.RandomAccessFile
import java.io.ByteArrayInputStream
import net.liftweb.json.JsonAST._
import net.liftweb.json._

case class Chunk(upload_id: String, offset: Long)

object DropBoxFileUpload extends App {
  implicit val formats = DefaultFormats
  def getSignedURL(method: String, url: String, user_params: Map[String, Any], requestToken: Consumer, accessToken: Option[Token] = None, verifier: Option[String] = None, callback: Option[String] = None): String = {
    def parseMap(str: String, tpl: (String, Any)): String = str + "&" + tpl._1 + "=" + tpl._2
    val oauthMap = OAuth.sign(method, url, user_params, requestToken, accessToken, verifier, callback)
    oauthMap.foldLeft(url + "?")(parseMap)
  }

  def uploadFile = {
    val file = new RandomAccessFile("/home/mayank/demoxx1.csv", "r")
    val accountInfoURL = "https://api-content.dropbox.com/1/files_put/sandbox/watchXXXX"
    val consumer = Consumer("38cg8jkpl6ycxyt", "zi4gtmnd44sqklr")
    val accessToken = Token("tmaezlpjx0jwja7", "caqz8z7s0nh14zz")
    val signedURL = getSignedURL("PUT", accountInfoURL, Map(), consumer, Option(accessToken))

    val inputStream = new FileInputStream(new File("/home/mayank/demoxx1.csv"))

    val result = for (str <- Http(url(signedURL).PUT.setBody(new File("/home/mayank/demoxx1.csv")) OK as.String)) yield str
    result
  }

  def getInfo = {
    val accountInfoURL = "https://api.dropbox.com/1/account/info"
    val consumer = Consumer("38cg8jkpl6ycxyt", "zi4gtmnd44sqklr")
    val accessToken = Token("tmaezlpjx0jwja7", "caqz8z7s0nh14zz")
    val signedURL = getSignedURL("GET", accountInfoURL, Map(), consumer, Option(accessToken))
    println(signedURL)
    Console.readLine
    val result = for (str <- Http(url(signedURL) OK as.String)) yield str
    result.apply
  }

  def uploadFileInChunks(file:File): IndexedSeq[Chunk] = {
    val chunkUploadURL = "https://api-content.dropbox.com/1/chunked_upload"
    val consumer = Consumer("38cg8jkpl6ycxyt", "zi4gtmnd44sqklr")
    val accessToken = Token("tmaezlpjx0jwja7", "caqz8z7s0nh14zz")
    val file = new File("/home/mayank/demoxx1.csv")
    val inputStream = new FileInputStream(file)
    val chunkSize = 20;
    val totalChunks = file.length().toInt / chunkSize

    var memoriseChunk = Chunk("", 0)
    println("total chunks " + totalChunks)
    (1 to totalChunks) map { n =>
      {
        println("memoChunk " + memoriseChunk)
        val bytes = FileChunkReader.getInputStreamAsChunk(inputStream, new Array[Byte](chunkSize), n, chunkSize)

        var paraMap = Map[String, String]()
        if (!(memoriseChunk.upload_id == "")) {
          paraMap = paraMap ++ Map("offset" -> memoriseChunk.offset.toString,
            "upload_id" -> memoriseChunk.upload_id)
        }

        val signedURL = getSignedURL("PUT", chunkUploadURL, paraMap, consumer, Option(accessToken))
        val result = for (str <- Http(url(signedURL).PUT.setBody(bytes) <<? paraMap OK as.String)) yield str
        println(result.apply + "xxxx")
        val json = parse(result.apply)
        val chunk = json.extract[Chunk]
        memoriseChunk = chunk
        Thread.sleep(3000)
        chunk
      }
    }
  }

  def uploadFileInChunksCommit = {
    val chunkUploadURL = "https://api-content.dropbox.com/1/commit_chunked_upload/sandbox/rrrr"
    val consumer = Consumer("38cg8jkpl6ycxyt", "zi4gtmnd44sqklr")
    val accessToken = Token("ojcr0eamxsqrth3", "zqxzer9b2x5itou")

    val signedURL = getSignedURL("POST", chunkUploadURL, Map("upload_id" -> "gciRRiYwDZPARoeAtfYOcw"), consumer, Option(accessToken))
    signedURL
    val result = for (str <- Http(url(signedURL).POST <<? Map("upload_id" -> "gciRRiYwDZPARoeAtfYOcw") OK as.String)) yield str
    result.apply
  }

  val u = uploadFileInChunksCommit
  println(u)

 

  // val info = getInfo
  // println(info)
}