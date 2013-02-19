package com.decooda.dropbox

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import dispatch._
import dispatch.oauth._
import dispatch.oauth.OAuth._
import com.decooda.dropBox.DropBoxOAuthUtil

@RunWith(classOf[JUnitRunner])
class DropBoxUtilTest extends FunSuite {

  test("dropBoxAuthInitializer is done correctly") {
    val(token,url)=DropBoxOAuthUtil.dropBoxAuthInitializer("http://www.knoldus.com")
    println(token,url)
  }
  
  test("dropboxAccessToken is done correctly") {
    val requestToken = Token("k4yv06jzw9bumt5","2zh57i5ckbwqovj")
    val accessToken = DropBoxOAuthUtil.dropBoxAccessToken(requestToken)
    println(accessToken)
  }
  
}