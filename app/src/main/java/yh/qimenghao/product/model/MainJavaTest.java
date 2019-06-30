package yh.qimenghao.product.model;


import yh.qimenghao.product.util.BufChangeHex;

/**
 * Created by Joy on 2018/12/12.09:26
 */
public class MainJavaTest {
    public static void main(String[] args) {
        int i=12345678;
        bytesToInt(intToByteArray(i));
        System.out.println(BufChangeHex.encodeHexStr(intToByteArray(i)));
        System.out.println(bytesToInt(intToByteArray(i)));

    }
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[3];
        result[0] = (byte) ((i >> 16) & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[2] = (byte) (i & 0xFF);
        return result;
    }
    public static int bytesToInt(byte[] src) {
        int value;
        value =((src[0] & 0xFF)<<16)
                |((src[1] & 0xFF)<<8)
                |(src[2] & 0xFF);
        return value;
    }
}
