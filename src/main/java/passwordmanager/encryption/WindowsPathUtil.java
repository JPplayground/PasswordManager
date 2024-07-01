package passwordmanager.encryption;

import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.Native;

public class WindowsPathUtil {
    public static String getAppDataLocalFolder() {
        char[] path = new char[WinDef.MAX_PATH];
        if (Shell32.INSTANCE.SHGetFolderPath(null, ShlObj.CSIDL_LOCAL_APPDATA, null, ShlObj.SHGFP_TYPE_CURRENT, path).intValue() != WinNT.S_OK.intValue()) {
            throw new RuntimeException("Failed to get the local application data folder path");
        }
        return Native.toString(path);
    }
}
