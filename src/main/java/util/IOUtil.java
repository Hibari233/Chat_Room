package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  IO Util
 *
 * @author Hibari
 * @version 1.0
 */

public class IOUtil {
    /** 关闭字节输入流 */
    public static void close(InputStream is){
        if(null != is){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** 关闭字节输出流 */
    public static void close(OutputStream os){
        if(null != os){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 关闭字节输入流和输出流 */
    public static void close(InputStream is, OutputStream os){
        close(is);
        close(os);
    }
}
