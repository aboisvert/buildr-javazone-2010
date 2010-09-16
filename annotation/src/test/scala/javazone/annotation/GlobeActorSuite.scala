package javazone.annotation

import javazone.globe.GlobeFrame

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

class GlobeActorSuite extends WordSpec {

  "Tweet Globe" should {

    "display two tweets and rotate the globe for each" in {
        val frame = new GlobeFrame(400, 400)
        val globe = new GlobeActor(frame.getCanvasPanel)

        val t1 = new Tweet(
          author = "duke",
          text = "Need to check out #buildr",
          latitude = 60.0,
          longitude = 30.0
        )

        val t2 = new Tweet(
          author = "LadyJava",
          text = "Welcome to JavaZone!",
          latitude = 32.0,
          longitude = 55.0
        )

        globe showTweet t1
        globe showTweet t2

        Thread.sleep(5000)
        frame.dispose()
      }

  }
}
