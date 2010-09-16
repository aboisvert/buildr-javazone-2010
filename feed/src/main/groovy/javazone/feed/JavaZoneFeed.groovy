package javazone.feed

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import javazone.globe.GlobeFrame
import javazone.annotation.GlobeActor

class JavaZoneFeed {

    def tweets = []
    def userLocations = {}
    def twitter = new HTTPBuilder( 'http://search.twitter.com' )
    def yahoo = new HTTPBuilder( 'http://local.yahooapis.com' )

    def run() {
        if (tweets.size == 0) {
            // load tweets
            twitter.request( GET, JSON ) {
              uri.path = 'search'
              uri.query = [
                q: 'javazone'
              ]
              response.success = { resp, json ->
                println resp.statusLine
                json.results.each {
                  println "  ${it.from_user} : ${it.text}"
                  def data = new Tweet()
                  data.user = it.from_user
                  data.text = it.text
                  tweets.add(data)

                  userLocations.put(data.user, new Location(data.location))
                }
              }
            }

            // for reach twitter user, retrieve location
            twitter = new HTTPBuilder( 'http://api.twitter.com' )
            userLocations.each { user, location ->
                twitter.request( GET, XML ) {
                  uri.path = '/1/users/show.xml'
                  uri.query = [
                    screen_name: user
                  ]
                  response.success = { resp, xml ->
                    userLocations.put(user, xml.location)
                  }
                }
            }

            // retrieve (latitude, longitude) from location
            tweets.each { data ->
                if (userLocations.get(data.user) != null) {
                    println "location " + data.location
                    yahoo.request( GET, TEXT ) {
                      uri.path = '/MapsService/V1/geocode'
                      uri.query = [
                        appid:'_uYryYbV34Epqz2qS_8wfUr9gsq5gwKEXn9kVmTlJG5Ig1NM6SCeDxd.Dz9f',
                        location: userLocations.get(data.user)
                      ]

                      response.success = { resp, xml ->
                        println resp.statusLine

                        def resultSet = new XmlSlurper().parseText(xml.getText())
                        resultSet.Result.each {
                          data.latitude = Double.parseDouble(it.Latitude.text())
                          data.longitude = Double.parseDouble(it.Longitude.text())
                        }
                      }

                      response.failure = { resp ->
                        println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
                        println resp.inspect()
                      }
                    }
                }
            }

            tweets.each {
              println it.dump()
            }

        }

        tweets = tweets.collect {
            def location = userLocations.get(it.user)
            if (location != null) {
              new javazone.annotation.Tweet(it.user, it.text, location.latitude, location.longitude)
            }
        }.findAll { it != null }

        def frame = new GlobeFrame(1024, 768)
        def globe = new GlobeActor(frame.getCanvasPanel())

        // display all tweets
        tweets.each { globe.showTweet(it) }
    }

    public static void main(String[] args) {
        def feed = new JavaZoneFeed()
        feed.run()
    }
}

class Tweet {
    String user
    String text

    public Tweet() {}

    public Tweet(String user, String text) {
        this.user = user
        this.text = text
    }
}

class Location {
    String location
    double latitude
    double longitude

    public Location() {}

    public Location(String location) {
        this.location = location
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude
        this.longitude = longitude
    }
}

