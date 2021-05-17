import static org.junit.Assert.assertEquals;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;

public class NavigationPanelControllerTest {
    @Test
    public void testArrowKeyStrokesHandler() {
        NavigationPanelController navigationPanelController = new NavigationPanelController();
        navigationPanelController
                .arrowKeyStrokesHandler(new KeyEvent(null, "foo", "foo", KeyCode.ENTER, true, true, true, true));
        assertEquals(KeyCode.ENTER, navigationPanelController.lastKey);
        assertEquals(KeyCode.ENTER, navigationPanelController.currKey);
    }

    @Test
    public void testArrowKeyStrokesHandler2() {
        NavigationPanelController navigationPanelController = new NavigationPanelController();
        navigationPanelController
                .arrowKeyStrokesHandler(new KeyEvent(null, "foo", "foo", KeyCode.SPACE, true, true, true, true));
        assertEquals(KeyCode.SPACE, navigationPanelController.lastKey);
        assertEquals(KeyCode.SPACE, navigationPanelController.currKey);
    }
}

