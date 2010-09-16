package javazone.globe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Basic Swing Frame.
 */
public abstract class AppFrame extends JFrame {
    private Dimension canvasSize;

    /**
     * Create full-screen application frame.
     */
    public AppFrame() {
        setUndecorated(true);
        setExtendedState(Frame.MAXIMIZED_BOTH);

        Toolkit tk = Toolkit.getDefaultToolkit();
        int actualWidth = ((int) tk.getScreenSize().getWidth());
        int actualHeight = ((int) tk.getScreenSize().getHeight());
        initialize(actualWidth, actualHeight);
    }

    /**
     * Create a floating window application frame
     */
    public AppFrame(int width, int height) {
        initialize(width, height);
    }

    private void initialize(int width, int height) {
        canvasSize = new Dimension(width, height);

        JPanel panel = getCanvasPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setPreferredSize(canvasSize);
        pack();

        Dimension prefSize = getPreferredSize();
        Point parentLocation = new Point(0, 0);
        Dimension parentSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = parentLocation.x + (parentSize.width - prefSize.width) / 2;
        int y = parentLocation.y + (parentSize.height - prefSize.height) / 2;
        setLocation(x, y);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Dimension getCanvasSize() {
        return canvasSize;
    }

    abstract public JPanel getCanvasPanel();
}
