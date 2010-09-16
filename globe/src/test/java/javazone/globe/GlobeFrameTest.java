package javazone.globe;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;

import org.junit.Test;

public class GlobeFrameTest {

    @org.junit.Ignore
    @Test
    public void testRender() throws Exception {
        GlobeFrame frame = new GlobeFrame(800, 600);
        Thread.sleep(5000);
        frame.dispose();
    }

    @Test
    public void testRotation() throws Exception {
        GlobeFrame frame = new GlobeFrame(800, 600);
        WorldWindowGLCanvas canvas = frame.getCanvasPanel().getWorldCanvas();
        Thread.sleep(1000);

        Position pos = Position.fromDegrees(60.0, 20.0, 0);
        BasicOrbitView orbitView = (BasicOrbitView) canvas.getView();
        orbitView.addCenterAnimator(orbitView.getEyePosition(), pos, 2000, true);

        Thread.sleep(3000);
        frame.dispose();
    }

}
