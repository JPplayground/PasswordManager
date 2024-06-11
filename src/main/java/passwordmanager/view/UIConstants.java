package passwordmanager.view;

import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.logging.Logger;

public class UIConstants {

    // Logger
    private static final Logger logger = Logger.getLogger(UIConstants.class.getName());

    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static double user_screen_width = gd.getDisplayMode().getWidth();
    private static double user_screen_height = gd.getDisplayMode().getHeight();

    static {
        // If 1080p resolution
        if (user_screen_width == 1920 && user_screen_height == 1080) {
            initialize1080p();
        } else {
            logger.severe("Unsupported screen resolution. Program exiting.");
            System.exit(1);
        }
    }

    private static void initialize1080p() {
        logger.info("User screen resolution detected as 1920x1080");
    }

}
