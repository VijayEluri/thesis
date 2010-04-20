package se.lnu.thesis.test;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.04.2010
 * Time: 19:31:54
 */
public class TestChecksum {

    @Test
    public void crc32() {
        CRC32 crc32 = new CRC32();

        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream("cluster.graphml"));
            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = is.read(bytes)) >= 0) {
                crc32.update(bytes, 0, len);
            }

            is.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(crc32.getValue());

    }

    @Test
    public void md5() throws NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update("string".getBytes());

        BigInteger bigInteger = new BigInteger(digest.digest());

        System.out.println(bigInteger.toString());
    }

}