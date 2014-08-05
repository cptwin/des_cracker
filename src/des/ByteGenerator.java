package des;

import static des.Main.ciphertext;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Dajne Win
 */
public class ByteGenerator {
    
    public static void main(String[] args) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            ArrayList<String> characterList = new ArrayList<>();
            for (char ch = 'a'; ch <= 'z'; ch++) {
                characterList.add(ch + "");
            }
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                characterList.add(ch + "");
            }
            for (int i = 0; i < 10; i++) {
                characterList.add(i + "");
            }
            characterList.add("`");
            characterList.add("~");
            characterList.add("!");
            characterList.add("@");
            characterList.add("#");
            characterList.add("$");
            characterList.add("%");
            characterList.add("^");
            characterList.add("&");
            characterList.add("*");
            characterList.add("(");
            characterList.add(")");
            characterList.add("-");
            characterList.add("_");
            characterList.add("=");
            characterList.add("+");
            characterList.add("/");
            characterList.add("[");
            characterList.add("{");
            characterList.add("}");
            characterList.add("]");
            characterList.add("\\");
            characterList.add("|");
            characterList.add(":");
            characterList.add(";");
            characterList.add("'");
            characterList.add("\"");
            characterList.add(",");
            characterList.add("<");
            characterList.add(".");
            characterList.add(">");
            characterList.add("/");
            characterList.add("?");
            characterList.add(" ");
            
            /*for(String s : characterList)
            {
                String keyString = "";
                for(int i = 0; i < 8; i++)
                {
                    keyString += s;
                }
                for(String d : characterList)
                {
                    String plainTextString = "";
                    for(int x = 0; x < 8; x++)
                    {
                        plainTextString += d;
                    }
                    byte[] keyStringBytes = keyString.getBytes();
                    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                    SecretKey key = new SecretKeySpec(keyStringBytes, 0, keyStringBytes.length, "DES");
                    byte[] plaintext = plainTextString.getBytes();
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    byte[] ciphertext = cipher.doFinal(plaintext);
                    String[] temp_array = bytesToString(ciphertext).split(" ");
                    System.out.println("Key: " + s + " Plaintext: " + d + " Ciphertext: " + temp_array[0] + " " + temp_array[1]);
                }
            }*/
            
            byte[] keyStringBytes = "aaaaaaaa".getBytes();
            for(byte b : keyStringBytes)
            {
                System.out.print(Integer.toBinaryString(b) + " ");
            }
            System.out.println("");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            SecretKey key = new SecretKeySpec(keyStringBytes, 0, keyStringBytes.length, "DES");
            byte[] plaintext = "abcdefghabcdefgh".getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] ciphertext = cipher.doFinal(plaintext);
            for(byte b : ciphertext)
            {
                System.out.print(Integer.toBinaryString(b) + " ");
            }
            System.out.println("");
            //System.out.println("Ciphertext: " + bytesToString(ciphertext));
            
            keyStringBytes = "aaaaaaab".getBytes();
            for(byte b : keyStringBytes)
            {
                System.out.print(Integer.toBinaryString(b) + " ");
            }
            System.out.println("");
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            key = new SecretKeySpec(keyStringBytes, 0, keyStringBytes.length, "DES");
            plaintext = "abcdefghabcdefgh".getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            ciphertext = cipher.doFinal(plaintext);
            for(byte b : ciphertext)
            {
                System.out.print(Integer.toBinaryString(b) + " ");
            }
            System.out.println("");
            //System.out.println("Ciphertext: " + bytesToString(ciphertext));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
            Logger.getLogger(ByteGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String bytesToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3)).append(" ");
        }
        return sb.toString();
    }
    
}
