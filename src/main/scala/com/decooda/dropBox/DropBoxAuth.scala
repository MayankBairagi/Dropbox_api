package com.decooda.dropBox
import dispatch._
import dispatch.oauth._
import dispatch.oauth.OAuth._

object DropBoxAuth extends App {
  def getSignedURL(method: String, url: String, user_params: Map[String, Any], requestToken: Consumer, accessToken: Option[Token] = None, verifier: Option[String] = None, callback: Option[String] = None): String = {
    def parseMap(str: String, tpl: (String, Any)): String = str + "&" + tpl._1 + "=" + tpl._2
    val oauthMap = OAuth.sign(method, url, user_params, requestToken, accessToken, verifier, callback)
    oauthMap.foldLeft(url + "?")(parseMap)
  }

  def getRequestToken(consumer: Consumer): Token = {
    val requestURL = "https://api.dropbox.com/1/oauth/request_token"
    val signedURL = getSignedURL("GET", requestURL, Map(), consumer)
    println(signedURL)
    val promise4TokenURL = for (str <- Http(url(signedURL) OK as.String)) yield str
    val tokenString = promise4TokenURL.apply
    val token = Token(tokenString.substring(tokenString.lastIndexOf("=") + 1), tokenString.substring(tokenString.indexOf("=") + 1, tokenString.indexOf("&")))
    token
  }

  def getAuthenticationURL(consumer: Consumer, requestToken: Token, redirectURL: String) = {
    val authenticationURL = "https://www.dropbox.com/1/oauth/authorize"
    getSignedURL("GET", authenticationURL, Map(), consumer, Option(requestToken), None, Some(redirectURL))
  }

  def getAccessToken(consumer: Consumer, token: Token): Token = {
    val accessTokenURL = "https://api.dropbox.com/1/oauth/access_token"
    val accessSignedURL = getSignedURL("POST", accessTokenURL, Map(), consumer, Option(token))
    val promise4AccessToken = for (str <- Http(url(accessSignedURL).POST OK as.String)) yield str
    val accessTokenString = promise4AccessToken.apply
    val tokenString = accessTokenString.substring(accessTokenString.indexOf("oauth_token=") + "oauth_token=".length, accessTokenString.indexOf("&uid"))
    val secretString = accessTokenString.substring(accessTokenString.indexOf("oauth_token_secret=") + "oauth_token_secret=".length, accessTokenString.indexOf("&oauth_token="))
    Token(tokenString, secretString)
  }

  //  val consumer = Consumer("38cg8jkpl6ycxyt", "zi4gtmnd44sqklr")
  //  val requestToken = getRequestToken(consumer)
  //  println(requestToken)
  //  val authenticationURL = getAuthenticationURL(consumer, requestToken, "http://www.knoldus.com/home.knol")
  //  println(authenticationURL)
  //  Console.readLine
  //  val accessToken = getAccessToken(consumer, requestToken)
  //  println(accessToken)
}