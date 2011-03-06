
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.utils.FileChecksumUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
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

    public static final Logger LOGGER = Logger.getLogger(TestChecksum.class);

    @Test
    public void crc32() throws URISyntaxException {

        File file = new File(getClass().getResource("cluster.graphml").toURI());

        long checksum = FileChecksumUtil.crc32(file);

        Assert.assertEquals(73970639L, checksum);

        LOGGER.info(checksum);

    }

    @Test
    public void md5() throws NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update("string".getBytes());

        BigInteger bigInteger = new BigInteger(digest.digest());

        System.out.println(bigInteger.toString());
    }

}