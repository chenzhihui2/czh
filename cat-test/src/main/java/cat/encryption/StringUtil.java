package cat.encryption;


import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String ABC_CHARSET = "GB2312";

    public static String escape(String paramString, HashMap<String, String> paramHashMap) {
        if ((paramString == null) || (paramString.trim().length() == 0)) {
            return "";
        }
        StringBuffer localStringBuffer = new StringBuffer();
        StringCharacterIterator localStringCharacterIterator = new StringCharacterIterator(paramString);
        int j;
        for (int i = localStringCharacterIterator.first(); i != 65535; j = localStringCharacterIterator.next()) {
            String str = String.valueOf(i);
            if (paramHashMap.containsKey(str)) {
                str = (String) paramHashMap.get(str);
            }
            localStringBuffer.append(str);
        }
        return localStringBuffer.toString();
    }

    public static String escapeSQL(String paramString) {
        HashMap localHashMap = new HashMap();
        localHashMap.put("'", "''");
        return escape(paramString, localHashMap);
    }

    public static String escapeXML(String paramString) {
        HashMap localHashMap = new HashMap();
        localHashMap.put("<", "&lt;");
        localHashMap.put(">", "&gt;");
        localHashMap.put("'", "&apos;");
        localHashMap.put("\"", "&quot;");
        localHashMap.put("&", "&amp;");
        return escape(paramString, localHashMap);
    }

    public static String removeComma(String paramString) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramString.length(); i++) {
            if (',' != paramString.charAt(i)) {
                localStringBuffer.append(paramString.charAt(i));
            }
        }
        return localStringBuffer.toString();
    }

    public static String toLetterOrDigit(String paramString) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramString.length(); i++) {
            if (Character.isLetterOrDigit(paramString.charAt(i))) {
                localStringBuffer.append(paramString.charAt(i));
            } else {
                localStringBuffer.append("X");
            }
        }
        return localStringBuffer.toString();
    }

    public static String toLetter(String paramString) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramString.length(); i++) {
            if (Character.isLetter(paramString.charAt(i))) {
                localStringBuffer.append(paramString.charAt(i));
            } else if (Character.isDigit(paramString.charAt(i))) {
                switch (paramString.charAt(i)) {
                    case '0':
                        localStringBuffer.append("A");
                        break;
                    case '1':
                        localStringBuffer.append("B");
                        break;
                    case '2':
                        localStringBuffer.append("C");
                        break;
                    case '3':
                        localStringBuffer.append("D");
                        break;
                    case '4':
                        localStringBuffer.append("E");
                        break;
                    case '5':
                        localStringBuffer.append("F");
                        break;
                    case '6':
                        localStringBuffer.append("G");
                        break;
                    case '7':
                        localStringBuffer.append("H");
                        break;
                    case '8':
                        localStringBuffer.append("I");
                        break;
                    case '9':
                        localStringBuffer.append("J");
                }
            } else {
                localStringBuffer.append("M");
            }
        }
        return localStringBuffer.toString();
    }

    public static String bytes2hex(byte[] paramArrayOfByte) {
        String str1 = "";
        String str2 = "";
        if (null == paramArrayOfByte) {
            return null;
        }
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            str2 = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            if (str2.length() == 1) {
                str2 = "0" + str2;
            }
            str1 = str1 + str2;
        }
        return str1.toUpperCase();
    }

    public static byte[] hex2bytes(String paramString) {
        paramString = paramString.toUpperCase();

        char[] arrayOfChar = paramString.toCharArray();
        byte[] arrayOfByte = new byte[arrayOfChar.length / 2];

        int i = 0;
        for (int j = 0; j < arrayOfChar.length; j += 2) {
            int k = 0;

            k = (byte) (k | char2byte(arrayOfChar[j]));
            k = (byte) (k << 4);

            k = (byte) (k | char2byte(arrayOfChar[(j + 1)]));

            arrayOfByte[i] = (byte) k;

            i++;
        }
        return arrayOfByte;
    }

    public static byte char2byte(char paramChar) {
        switch (paramChar) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
        }
        return 0;
    }

    private static void byte2hex(byte paramByte, StringBuffer paramStringBuffer) {
        char[] arrayOfChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int i = (paramByte & 0xF0) >> 4;
        int j = paramByte & 0xF;
        paramStringBuffer.append(arrayOfChar[i]);
        paramStringBuffer.append(arrayOfChar[j]);
    }

    public static String toHexString(byte[] paramArrayOfByte, char paramChar) {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = paramArrayOfByte.length;
        for (int j = 0; j < i; j++) {
            byte2hex(paramArrayOfByte[j], localStringBuffer);
            if (j < i - 1) {
                localStringBuffer.append(paramChar);
            }
        }
        return localStringBuffer.toString();
    }

    public static String toHexString(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = paramArrayOfByte.length;
        for (int j = 0; j < i; j++) {
            byte2hex(paramArrayOfByte[j], localStringBuffer);
        }
        return localStringBuffer.toString();
    }

    public static boolean isEmpty(String paramString) {
        if ((null == paramString) || ("".equals(paramString.trim()))) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String paramString) {
        if ((paramString != null) && (!"".equals(paramString.trim()))) {
            return true;
        }
        return false;
    }

    public static String replace(String paramString1, String paramString2) {
        if (paramString1 != null) {
            return paramString1.replaceAll(paramString2, "");
        }
        return null;
    }

    public static String trim(String paramString) {
        if (isEmpty(paramString)) {
            return "";
        }
        return paramString.trim();
    }

    public static String trimNum(String paramString) {
        if (isEmpty(paramString)) {
            return "0";
        }
        return paramString.trim();
    }



    public static byte[] byteXORbyte(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
        int i = paramArrayOfByte1.length;
        int j = paramArrayOfByte2.length;
        if ((null == paramArrayOfByte1) || (null == paramArrayOfByte2)) {
            return null;
        }
        if (i != j) {
            return null;
        }
        byte[] arrayOfByte = new byte[i];
        for (int k = 0; k < i; k++) {
            arrayOfByte[k] = ((byte) (paramArrayOfByte1[k] ^ paramArrayOfByte2[k]));
        }
        return arrayOfByte;
    }

    public static String rightPad(String paramString, char paramChar, int paramInt) {
        if (paramString == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(paramInt);
        localStringBuilder.append(paramString);
        while (localStringBuilder.length() < paramInt) {
            localStringBuilder.append(paramChar);
        }
        return localStringBuilder.toString();
    }

    public static String leftPad(String paramString, char paramChar, int paramInt) {
        if (paramString == null) {
            return null;
        }
        int i = paramInt - paramString.length();
        if (i <= 0) {
            return paramString;
        }
        StringBuilder localStringBuilder = new StringBuilder(paramInt);
        while (localStringBuilder.length() < i) {
            localStringBuilder.append(paramChar);
        }
        localStringBuilder.append(paramString);
        return localStringBuilder.toString();
    }
}
