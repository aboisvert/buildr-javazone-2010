package javazone.annotation

import gov.nasa.worldwind.animation.MoveToPositionAnimator
import gov.nasa.worldwind.view.ViewPropertyAccessor
import gov.nasa.worldwind.animation.ScheduledInterpolator
import gov.nasa.worldwind.animation.PositionAnimator
import gov.nasa.worldwind.view.orbit.OrbitViewPropertyAccessor
import gov.nasa.worldwind.view.orbit.BasicOrbitView
import gov.nasa.worldwind.view.orbit.OrbitViewCenterAnimator
import javazone.globe.GlobePanel
import gov.nasa.worldwind.util.StatusBar
import gov.nasa.worldwind.avlist._
import gov.nasa.worldwind._
import gov.nasa.worldwind.geom._
import gov.nasa.worldwind.render._
import gov.nasa.worldwind.globes._
import gov.nasa.worldwind.layers._
import gov.nasa.worldwind.awt._
import gov.nasa.worldwind.examples.LineBuilder

import javax.swing._
import java.awt.{Color, Dimension}
import javax.media.opengl.GLContext
import java.util.Random
import java.util.{ ArrayList => JArrayList }
import java.lang.{ Iterable => JIterable }
import java.util.{ Iterator => JIterator }
import java.util.{ List => JList }
import java.util.{ Collection => JCollection }

import scala.actors._
import scala.actors.Actor._
import scala.collection.JavaConversions._

// This class contains the actual globe. It is initialized by the main class, and runs everything
class GlobeActor(globe: GlobePanel) extends Actor {
  val animDuration = 3000
  val readDuration = 5000

  private var lastTweetPosition: Option[Position] = None
  private val canvas = globe.getWorldCanvas

  sealed trait Message
  case object Start extends Message
  case object AnimationComplete extends Message
  case class NewTweet(t: Tweet)

  /** Actor event handler */
  def act() {
    loop {
      react {
        case Start | AnimationComplete => react {
          case NewTweet(tweet) => showTweet(tweet)
        }
      }
    }
  }

  this ! Start

  /** This displays an entire tree, starting with the root */
  def showTweet(newTweet: Tweet): Unit = {
    val newTweetPos = Position.fromDegrees(newTweet.latitude, newTweet.longitude, 0)
    val orbitView = canvas.getView.asInstanceOf[BasicOrbitView]
    val fromPosition = lastTweetPosition getOrElse orbitView.getCurrentEyePosition
    orbitView.addCenterAnimator(fromPosition, newTweetPos, animDuration, true)
    lastTweetPosition = Some(newTweetPos)
    Thread.sleep(animDuration)

    // choose a random color for this tweet, within a certain range
    val random = new Random
    def randomColor = (random.nextFloat * 65).toInt + 175
    val color = new Color(randomColor, randomColor, randomColor)

    // decrease the opacities of all previously displayed trees that are still visible
    globe.fadeAnnotations()

    // create the layer for the renderables that will be displayed, and add them to the globe
    val pos = Position.fromDegrees(newTweet.latitude, newTweet.longitude, 0)
    val annotation = new Annotation(newTweet.toString, pos, color)

    // add it to the globe
    val aLayer = new AnnotationLayer()
    aLayer.addAnnotation(annotation)
    canvas.getModel.getLayers.add(canvas.getModel.getLayers.size, aLayer)

    Thread.sleep(readDuration)
  }
}
