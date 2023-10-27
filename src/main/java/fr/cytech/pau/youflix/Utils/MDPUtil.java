package fr.cytech.pau.youflix.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MDPUtil {

    public static String hachage(String motDePasse){
            String str = motDePasse;
            try {
                MessageDigest msg = MessageDigest.getInstance("SHA-256");
                byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));
                StringBuilder s = new StringBuilder();
                for (byte b : hash) {
                    s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
                }
                return s.toString();
            } catch(NoSuchAlgorithmException e){}
            return motDePasse;
    }
    
}
