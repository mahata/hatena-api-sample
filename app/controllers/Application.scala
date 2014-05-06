package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current

import java.util.Date
import org.apache.abdera.Abdera
import org.apache.abdera.protocol.client.AbderaClient
import org.apache.abdera.ext.wsse.WSSEAuthScheme
import org.apache.commons.httpclient.UsernamePasswordCredentials

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def api = Action {
    val username = current.configuration.getString("hatena.uname").get
    val blogDomain = current.configuration.getString("hatena.blogDomain").get
    val apiKey = current.configuration.getString("hatena.wsse").get

    val abdera = new Abdera
    val abderaClient = new AbderaClient(abdera)
    WSSEAuthScheme.register(abderaClient, true)
    abderaClient.addCredentials("https://blog.hatena.ne.jp", null, "WSSE", new UsernamePasswordCredentials(username, apiKey))

    val factory = abdera.getFactory

    // 記事の記述
    val entry = factory.newEntry
    entry.setTitle("タイトル")
    entry.setUpdated(new Date())
    entry.addAuthor(username)
    entry.setContent("本文")

    // 記事の投稿
    val response = abderaClient.post(s"https://blog.hatena.ne.jp/$username/$blogDomain/atom/entry", entry)
    Ok("STATUS CODE : " + response.getStatus)
  }
}
