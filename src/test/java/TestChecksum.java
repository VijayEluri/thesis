
import org.slf4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.utils.FileChecksumUtil;

import java.io.File;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.04.2010
 * Time: 19:31:54
 */
public class TestChecksum {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestChecksum.class);

    @Test
    public void crc32() throws URISyntaxException {

        File file = new File(getClass().getResource("data/cluster.graphml").toURI());

        long checksum = FileChecksumUtil.crc32(file);

        Assert.assertEquals(73970639L, checksum);

        LOGGER.info("Checksum {}", checksum);

    }

    @Test
    public void md5() throws NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update("string".getBytes());

        BigInteger bigInteger = new BigInteger(digest.digest());

        System.out.println(bigInteger.toString());
    }

}