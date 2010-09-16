package javazone.globe;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.render.Annotation;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.terrain.SectorGeometryList;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

public class GlobePanel extends JPanel {
    private int opacityLevels = 7;
    private int initLayerCount = 0;
    private final WorldWindowGLCanvas canvas = new WorldWindowGLCanvas();

    public GlobePanel() {
        super(new BorderLayout());
    }

    void initialize() {
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        canvas.setModel(m);
        add(canvas, BorderLayout.CENTER);
        canvas.setPreferredSize(getPreferredSize());

        DrawContext context = canvas.getSceneController().getDrawContext();
        context.setModel(m);
        context.setSurfaceGeometry(new SectorGeometryList());
        context.setGLContext(canvas.getContext());

        // keeping only the layers we will need
        List<Layer> layers = new ArrayList<Layer>();
        for (Layer l : canvas.getModel().getLayers()) {
            if (l instanceof gov.nasa.worldwind.layers.StarsLayer
                    || l instanceof gov.nasa.worldwind.layers.SkyGradientLayer
                    || l instanceof gov.nasa.worldwind.layers.Earth.BMNGOneImage
                    || l instanceof gov.nasa.worldwind.layers.Earth.BMNGWMSLayer) {
                layers.add(l);
            }
        }
        initLayerCount = layers.size();
        canvas.getModel().setLayers(new LayerList(layers.toArray(new Layer[layers.size()])));
    }

    /**
     * Fade annotations by decreasing their opacity.
     */
    public void fadeAnnotations() {
        LayerList initLayers = canvas.getModel().getLayers();
        List<Layer> finalLayers = new ArrayList<Layer>();
        for (int i = 0; i < initLayerCount; i++) {
            Layer l = initLayers.get(i);
            finalLayers.add(l);
        }

        int alpha = (255 / opacityLevels);

        int startIndex = initLayers.size();
        startIndex += (initLayers.size() - (opacityLevels * 2));
        startIndex = Math.max(startIndex, 0);

        for (int i = startIndex; i < initLayers.size(); i++) {
            Layer l = initLayers.get(i);
            if (l instanceof AnnotationLayer) {
                setAnnotationOpacity((AnnotationLayer) l, alpha);
            }
            alpha = alpha / 2;
            finalLayers.add(l);
        }

        canvas.getModel().setLayers(new LayerList(finalLayers.toArray(new Layer[finalLayers.size()])));
    }

    private void setAnnotationOpacity(AnnotationLayer l, int alpha) {
        Iterator<Annotation> iter = l.getAnnotations().iterator();
        while (iter.hasNext()) {
            Annotation a = iter.next();
            if (a instanceof AlphaAnnotation) {
                ((AlphaAnnotation) a).setAnnotationOpacity(alpha);
            }
        }
    }

    public WorldWindowGLCanvas getWorldCanvas() {
        return canvas;
    }

    public interface AlphaAnnotation {
        void setAnnotationOpacity(int alpha);
    }
}