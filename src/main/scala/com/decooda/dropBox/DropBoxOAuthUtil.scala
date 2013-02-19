package com.decooda.dropBox
import dispatch._
import dispatch.oauth._
import dispatch.oauth.OAuth._

object DropBoxOAuthUtil {
  val consumer = Consumer("38cg8jkpl6ycxyt", "zi4gtmnd44sqklr")
   
  def dropBoxAuthInitializer(redirectURL:String) : (Token,String) = {
    val requestToken=DropBoxAuth.getRequestToken(consumer)
    val authenticationURL = DropBoxAuth.getAuthenticationURL(consumer, requestToken, redirectURL)
    (requestToken,authenticationURL)
  }
  
  def dropBoxAccessToken(requestToken:Token):Token={
    DropBoxAuth.getAccessToken(consumer,requestToken)
  }
}