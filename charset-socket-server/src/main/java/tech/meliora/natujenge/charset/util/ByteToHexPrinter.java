package tech.meliora.natujenge.charset.util;

public class ByteToHexPrinter {

    //source https://www.programiz.com/java-programming/examples/convert-byte-array-hexadecimal

    public static String bytesToHex(byte[] bytes) {

        String str  = "";

        for (byte b : bytes) {
            String st = String.format("%02X", b);
            str = str+" "+st;
        }

        return str;

    }
}
