package useful_classes;

public class Encryption {
	/* Retorna un hash a partir de un tipo y un texto */
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /* Retorna un hash MD5 a partir de un texto */
    public static String md5(String txt) {
        return Encryption.getHash(txt, "MD5");
    }
    
    public static String md5(char[] txt) {
        return Encryption.getHash(String.valueOf(txt), "MD5");
    }
 
    /* Retorna un hash SHA1 a partir de un texto */
    public static String sha1(String txt) {
        return Encryption.getHash(txt, "SHA1");
    }
    
    public static String sha1(char[] txt) {
        return Encryption.getHash(String.valueOf(txt), "SHA1");
    }
}
