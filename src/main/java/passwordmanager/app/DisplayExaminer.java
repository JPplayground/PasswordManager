package passwordmanager.app;

import java.awt.*;

public class DisplayExaminer {

    private final int width;
    private final int height;

    public DisplayExaminer() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
    }

    public int totalPixels() {
        return height * width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
