package passwordmanager.app;

import java.awt.*;

/**
 * Utility class for examining display properties such as screen dimensions.
 */
public class DisplayExaminer {

    private final int width;
    private final int height;

    /**
     * Constructs a DisplayExaminer object and initializes the screen dimensions.
     */
    public DisplayExaminer() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
    }

    /**
     * Calculates the total number of pixels on the screen.
     *
     * @return the total number of pixels
     */
    public int totalPixels() {
        return height * width;
    }

    /**
     * Retrieves the screen width.
     *
     * @return the screen width in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the screen height.
     *
     * @return the screen height in pixels
     */
    public int getHeight() {
        return height;
    }
}
