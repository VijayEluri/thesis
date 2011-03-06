package se.lnu.thesis.utils;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 06.03.11
 * Time: 01:16
 * <p/>
 * Utility methods to get file CRC32 and MD5 information
 */
public class FileChecksumUtil {

    public static final Logger LOGGER = Logger.getLogger(FileChecksumUtil.class);

    public static long crc32(File file) {
        CRC32 crc32 = new CRC32();

        byte[] bytes = new byte[1024];
        int len = 0;

        try {
            BufferedInputStream io = new BufferedInputStream(new FileInputStream(file));

            while ((len = io.read(bytes)) >= 0) {
                crc32.update(bytes, 0, len);
            }

            io.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }


        return crc32.getValue();
    }

}
