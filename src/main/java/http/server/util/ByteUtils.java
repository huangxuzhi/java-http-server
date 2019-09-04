package http.server.util;

public class ByteUtils {

    public static boolean arrayEquals(byte[] b1, int offset1, byte[] b2, int offset2, int length) {
        Assert.assertPositive(length, "Argument 'length' can not be negative");
        Assert.assertPositive(length, "Argument 'offset1' can not be negative");
        Assert.assertPositive(length, "Argument 'offset2' can not be negative");
        Assert.assertNotNull(b1,"byte array can not be null");
        Assert.assertNotNull(b2,"byte array can not be null");
        int i = offset1,j = offset2, k = 0;
        for (;k < length && i < b1.length && j < b2.length; i++,j++,k++) {
            if (b1[i] != b2[j]) {
                return false;
            }
        }
        return true;
    }
}
