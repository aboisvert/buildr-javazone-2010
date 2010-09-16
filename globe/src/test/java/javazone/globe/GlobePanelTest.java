package javazone.globe;

import org.junit.Test;
import static org.junit.Assert.*;

public class GlobePanelTest {

	@Test public void testCanvasPreferredSize() {
		GlobePanel panel = new GlobePanel();
		panel.initialize();
		assertEquals(panel.getPreferredSize(), panel.getWorldCanvas().getPreferredSize());
	}

}
