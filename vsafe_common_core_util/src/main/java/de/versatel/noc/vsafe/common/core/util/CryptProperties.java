package de.versatel.noc.vsafe.common.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

import java.security.*;
import javax.crypto.spec.SecretKeySpec;

public class CryptProperties extends Properties {

    private static final long serialVersionUID = 6221146452471126627L;

    public enum Mode {

        ENCRYPT, DECRYPT
    };

    public static enum Algorithm {

        AES, BASE64, DES, DESede, RSA
    };
    private final byte[] cryptKey;
    private final Algorithm algorithm;
    //private final String algorithm;

    /** Constructor mit leeren Properties und BASE64 Algorhythmus*/
    public CryptProperties(byte[] key) {
        this(key, new Properties(), Algorithm.BASE64);
    }

    /** Constructor */
    public CryptProperties(byte[] key, Properties properties, Algorithm algorithm) {
        super();
        Enumeration<Object> propertyKeys = properties.keys();
        while (propertyKeys.hasMoreElements()) {
            String propertyKey = propertyKeys.nextElement().toString();
            this.setProperty(propertyKey, properties.getProperty(propertyKey));
        }
        this.cryptKey = key;
        this.algorithm = algorithm;

    }

    public CryptProperties(byte[] key, String cryptString, Algorithm algorithm) throws IOException, ManipulationException {
        this.cryptKey = key;
        this.algorithm = algorithm;
        //this(key);
        if (null != cryptString) {
            try {
                String[] decryptedStrings = this.crypt(cryptString, Mode.DECRYPT).split("&");
                for (int i = 0; i < decryptedStrings.length; i++) {
                    String[] temp = decryptedStrings[i].split("=");
                    this.setProperty(temp[0], temp[1]);
                }
            } catch (ArrayIndexOutOfBoundsException aiobExc) {
                throw new ManipulationException();
            }
        }
    }

    /**
     * Gibt die enthaltenen Properties als Verschl�sselten String zur�ck
     */
    @Override
    public String toString() {
        StringBuilder cryptBuffer = new StringBuilder();
        Enumeration<Object> propertyKeys = this.keys();
        while (propertyKeys.hasMoreElements()) {
            String propertyKey = propertyKeys.nextElement().toString();
            if (0 != cryptBuffer.length()) {
                cryptBuffer.append("&");
            }
            cryptBuffer.append(propertyKey);
            cryptBuffer.append("=");
            cryptBuffer.append(this.getProperty(propertyKey));
        }
        String returnString = null;
        try {
            returnString = this.crypt(cryptBuffer.toString().trim(), Mode.ENCRYPT);
        } catch (IOException exc) {
            //exc.printStackTrace();
        }
        return returnString;
    }

    private String crypt(String cryptString, Mode mode) throws IOException {
        if (cryptString != null) {
            if (algorithm.equals(Algorithm.BASE64)) {
                return cryptBASE64(cryptString, mode);
            } else {
                try {
                    return cryptCipher(cryptString, mode);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    private String cryptCipher(String input, Mode mode) throws InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        
        SecretKeySpec secretKeySpec = new SecretKeySpec(cryptKey, algorithm.toString());
        Cipher cipher = Cipher.getInstance(algorithm.toString());

        if (mode.equals(Mode.DECRYPT)) {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        }

        byte[] in;
        byte[] temp = input.getBytes();
        if (temp.length % cipher.getBlockSize() != 0) {
            int result = temp.length / cipher.getBlockSize();
            in = new byte[(result + 1) * cipher.getBlockSize()];
            System.arraycopy(temp, 0, in, 0, temp.length);
        } else {
            in = input.getBytes();
        }

        byte[] out = cipher.doFinal(in);
        String output = new String(out, "UTF8");
        return output;
    }

    private String cryptBASE64(String cryptString, Mode mode) throws IOException {
        byte[] cryptBytes;
        //byte[] keyBytes = getBytesFromHex(cryptKey);
        if (mode.equals(Mode.DECRYPT)) {
            cryptBytes = new BASE64Decoder().decodeBuffer(cryptString);
        } else {
            cryptBytes = cryptString.getBytes();
            //keyBytes = key.getBytes();
        }

        for (int i = 0, kyPos = 0; i < cryptBytes.length; i++, kyPos++) {
            if (cryptKey.length == kyPos) {
                kyPos = 0;
            }
            cryptBytes[i] = (byte) (cryptBytes[i] ^ cryptKey[kyPos]);
        }

        String returnString;
        if (mode.equals(Mode.ENCRYPT)) {
            returnString = new BASE64Encoder().encode(cryptBytes).replaceAll("\r\n", "");
        } else {
            returnString = new String(cryptBytes);
        }
        return returnString;
    }

    public static String encode(byte[] key, String name, String value, Algorithm algorithm) {
        Properties properties = new Properties();
        properties.put(name, value);
        return encode(key, properties, algorithm);
    }

    public static String encode(byte[] key, String props, Algorithm algorithm) {
        Properties properties = new Properties();
        Matcher matcher = Pattern.compile("([a-zA-Z0-9]*)=([a-zA-Z0-9]*)").matcher(props);
        while (matcher.find()) {
            properties.put(matcher.group(1), matcher.group(2));
        }
        return encode(key, properties, algorithm);
    }

    public static String encode(byte[] key, Properties args, Algorithm algorithm) {
        return new CryptProperties(key, args, algorithm).toString();
    }

    public static Properties decode(byte[] key, String crpt, Algorithm algorithm) {
        try {
            return new CryptProperties(key, crpt, algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean algorithmExists(String algorithm) {
        Algorithm[] algos = Algorithm.values();
        for (Algorithm e : algos) {
            if (e.toString().equals(algorithm)) {
                return true;
            }
        }
        return false;
    }

    public static Algorithm getAlgorithm(String algorithm) {
        if (algorithm == null || algorithm.isEmpty()) {
            return null;
        }
        Algorithm[] algos = Algorithm.values();
        for (Algorithm e : algos) {
            if (e.toString().equals(algorithm)) {
                return e;
            }
        }
        return null;

    }

    public static String getNewCipherKey(int len, Algorithm algo) throws Exception {
        // Generiere neuen Key
        KeyGenerator kgen = KeyGenerator.getInstance(algo.name());
        kgen.init(len); // 192 and 256 bits may not be available
        SecretKey secretKey = kgen.generateKey();
        byte[] secretKeyBytes = secretKey.getEncoded();
        String retVal = getHexFromBytes(secretKeyBytes);
        return retVal;
    }

    public byte[] getAESkey() {

        KeyGenerator kgen;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128); // 192 and 256 bits may not be available
            // Generate the secret key specs.
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            return raw;
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    private static String getHexFromBytes(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] getBytesFromHex(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public static byte[] getBytesFromHex_New(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }

    public class ManipulationException extends Exception {

        private static final long serialVersionUID = -5897201936298811861L;

        private ManipulationException() {
            super("parameters seems to be manipulated");
        }
    }
}
