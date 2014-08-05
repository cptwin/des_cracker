package des;

import static des.Main.cipher;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Dajne Win
 */
public class ThreadedCracker implements Runnable {
    
    private int a,b,c,d,e,f,g,h;
    
    public ThreadedCracker(int a, int b, int c, int d, int e, int f, int g, int h)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        
        startup();
    }
    
    private void startup()
    {
        Thread t = new Thread(this);
        t.start();
    }
    
    private static String bytesToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            byte[] tempKeyStringBytes = (Main.numberList.get(a) + Main.numberList.get(b) + Main.numberList.get(c) + Main.numberList.get(d) + Main.numberList.get(e) + Main.numberList.get(f) + Main.numberList.get(g) + Main.numberList.get(h)).getBytes();
            SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
            Cipher.getInstance("DES/ECB/PKCS5Padding").init(Cipher.DECRYPT_MODE, tempKey);
            try {
                byte[] tempDeciphertext = cipher.doFinal(Main.ciphertext);
                for (String s : new String(tempDeciphertext).split(" ")) {
                    if (Main.dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                        System.out.println("Cipher Text: " + new String(tempDeciphertext) + "Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + Main.numberList.get(a) + Main.numberList.get(b) + Main.numberList.get(c) + Main.numberList.get(d) + Main.numberList.get(e) + Main.numberList.get(f) + Main.numberList.get(g) + Main.numberList.get(h));
                        break;
                    }
                }
                
            } catch (BadPaddingException ex) {
                
            } catch (IllegalBlockSizeException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(ThreadedCracker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(ThreadedCracker.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
