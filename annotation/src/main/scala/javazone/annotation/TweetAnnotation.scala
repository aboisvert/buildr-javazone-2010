package javazone.annotation

import gov.nasa.worldwind.render.GlobeAnnotation
import gov.nasa.worldwind.geom.Position
import java.awt.{Color, Font}

/** Extend build-in GlobeAnnotation to customize appearance */
class Annotation (
  text: String,
  var position: Position,
  val color: Color
) extends GlobeAnnotation(text, position, new Font("SansSerif", Font.PLAIN, 14)) {

  var annoAttr = getAttributes
  annoAttr.setBorderColor(Color.BLACK)
  annoAttr.setTextColor(Color.BLACK)
  annoAttr.setBackgroundColor(new Color(color.getRed, color.getGreen, color.getBlue, 200))
  setAttributes(annoAttr)

  /**
   * Update annotation's opacity.
   */
  def updateAnnotationOpacity(alpha: Int) = {
    def calcAlpha(a: Int): Int = 0 max (a - alpha)

    val attrs = getAttributes

    val bc = attrs.getBorderColor
    attrs.setBorderColor(new Color(bc.getRed, bc.getGreen, bc.getBlue, calcAlpha(bc.getAlpha)))

    val bgc = attrs.getBackgroundColor
    attrs.setBackgroundColor(new Color(bgc.getRed, bgc.getGreen, bgc.getBlue, calcAlpha(bgc.getAlpha)))

    val tc = attrs.getTextColor
    attrs.setTextColor(new Color(tc.getRed, tc.getGreen, tc.getBlue, calcAlpha(tc.getAlpha)))

    setAttributes(attrs)
  }
}
