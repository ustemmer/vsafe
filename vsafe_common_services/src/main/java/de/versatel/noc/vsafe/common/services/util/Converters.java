package de.versatel.noc.vsafe.common.services.util;

import java.util.BitSet;

/**
 *
 * @author ulrich.stemmer
 */
public class Converters {

    public static BitSet byteArrayToBitSet(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            BitSet bitSet = new BitSet(bytes.length * 8);
            for (int i = 0; i < bytes.length * 8; i++) {
                if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) != 0) {
                    bitSet.set(i);
                }
            }
            return bitSet;
        }
        return null;
    }

    public static byte[] bitSetToByteArray(BitSet bitSet) {
        if (bitSet != null && !bitSet.isEmpty()) {
            byte[] bytes = new byte[bitSet.length() / 8 + 1];
            for (int i = 0; i < bitSet.length(); i++) {
                if (bitSet.get(i)) {
                    bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
                }
            }
            return bytes;
        }
        return null;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hexStringToByteArray(String hexString) {
        if (hexString != null && !hexString.isEmpty()) {
            hexString = hexString.toUpperCase();

            byte[] bytes = new byte[hexString.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                int index = i * 2;
                int value = Integer.parseInt(hexString.substring(index, index + 2), 16);
                bytes[i] = (byte) value;
            }
            return bytes;
        }
        return null;
    }

    public static String bitSetToHexString(BitSet bitSet) {
        if (bitSet != null && !bitSet.isEmpty()) {
            int modulo = bitSet.length()%4;
            int stringLength = bitSet.length() / 4;
            if (modulo>0){
                stringLength++;
            }
            StringBuilder sb = new StringBuilder(stringLength);
            for (int i = 0; i < stringLength; i++) {
                int decimal = 0;
                if (bitSet.get(i*4)) {
                    decimal += 8;
                }
                try {
                    if (bitSet.get(i*4 + 1)) {
                        decimal += 4;
                    }
                    if (bitSet.get(i*4 + 2)) {
                        decimal += 2;
                    }
                    if (bitSet.get(i*4 + 3)) {
                        decimal += 1;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
                sb.append(Integer.toHexString(decimal));
            }
            return sb.toString().toUpperCase();
        }
        return null;
    }

    public static String binStringToHexString(String binaryToHex) {
        String a = binaryToHex;
        String output = "";
        int buff = 0;
        while (a.length() % 4 != 0) {
            a = new StringBuffer(a).insert(0, "0").toString();
        }
        for (int i = 0; i < a.length(); i += 4) {
            for (int j = 0; j < 4; j++) {
                if (a.charAt(i + j) == '1') {
                    switch (j) {
                        case 0:
                            buff += 8;
                            break;
                        case 1:
                            buff += 4;
                            break;
                        case 2:
                            buff += 2;
                            break;
                        case 3:
                            buff += 1;
                            break;
                    }
                }
            }
            output += decimalToHexString(buff);
            buff = 0;
        }
        return output;
    }

    public static String decimalToHexString(int decimalToConvert) {
        return Integer.toHexString(decimalToConvert);
    }

    public static String hexStringToBinaryString(String hexToBinary) {
        hexToBinary = hexToBinary.toUpperCase();
        StringBuilder sb = new StringBuilder(hexToBinary.length() * 4);

        for (int i = 0; i < hexToBinary.length(); i++) {
            switch (hexToBinary.charAt(i)) {
                case '0':
                    sb.append("0000");
                    break;
                case '1':
                    sb.append("0001");
                    break;
                case '2':
                    sb.append("0010");
                    break;
                case '3':
                    sb.append("0011");
                    break;
                case '4':
                    sb.append("0100");
                    break;
                case '5':
                    sb.append("0101");
                    break;
                case '6':
                    sb.append("0110");
                    break;
                case '7':
                    sb.append("0111");
                    break;
                case '8':
                    sb.append("1000");
                    break;
                case '9':
                    sb.append("1001");
                    break;
                case 'A':
                    sb.append("1010");
                    break;
                case 'B':
                    sb.append("1011");
                    break;
                case 'C':
                    sb.append("1100");
                    break;
                case 'D':
                    sb.append("1101");
                    break;
                case 'E':
                    sb.append("1110");
                    break;
                case 'F':
                    sb.append("1111");
                    break;
            }
        }
        return sb.toString();
    }

    public static BitSet hexStringToBitSet(String hexToBinary) {
        hexToBinary = hexToBinary.toUpperCase();
        BitSet bitSet = new BitSet(hexToBinary.length() * 4);

        for (int i = 0; i < hexToBinary.length(); i++) {
            switch (hexToBinary.charAt(i)) {
                case '0':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, false);
                    break;
                case '1':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, true);
                    break;
                case '2':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case '3':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, true);

                    break;
                case '4':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case '5':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, true);

                    break;
                case '6':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case '7':
                    bitSet.set(i * 4, false);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, true);

                    break;
                case '8':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case '9':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, true);

                    break;
                case 'A':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case 'B':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, false);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, true);

                    break;
                case 'C':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case 'D':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, false);
                    bitSet.set(i * 4 + 3, true);

                    break;
                case 'E':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, false);

                    break;
                case 'F':
                    bitSet.set(i * 4, true);
                    bitSet.set(i * 4 + 1, true);
                    bitSet.set(i * 4 + 2, true);
                    bitSet.set(i * 4 + 3, true);
                    break;
            }
        }
        return bitSet;
    }
}
