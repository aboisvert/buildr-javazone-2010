package javazone.globe;

/**
 * Application frame with WorldWind Globe.
 */
public class GlobeFrame extends AppFrame {
    private GlobePanel panel;

    public GlobeFrame() {
        super();
        initalize();
    }

    public GlobeFrame(int width, int height) {
        super(width, height);
        initalize();
    }

    private void initalize() {
        panel.initialize();
        setVisible(true);
    }

    @Override
    public synchronized GlobePanel getCanvasPanel() {
        if (panel == null) {
            panel = new GlobePanel();
        }
        return panel;
    }
}
