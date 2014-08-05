package des;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
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
public class Main {

    public static Cipher cipher;
    public static ArrayList<String> characterList;
    public static ArrayList<String> numberList;
    public static ArrayList<String> alphaLowList;
    public static ArrayList<String> alphaHighList;
    public static ArrayList<String> alphaList;
    public static ArrayList<String> alphaNumericList;
    public static HashMap<String, String> dictionary;
    public static byte[] ciphertext;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            String keyString = " ";
            String plainTextString = "";
            
            Scanner scan = new Scanner(System.in);
            while(keyString.length() != 8)
            {
                System.out.print("Please enter an 8 character key: ");
                keyString = scan.nextLine();
            }
            System.out.println("");
            
            while(plainTextString.length() == 0)
            {
                System.out.print("Please enter a message to encrypt: ");
                plainTextString = scan.nextLine();
            }
            System.out.println("");
            
            System.out.println("Key: " + keyString);
            System.out.println("Key Bytes: " + bytesToString(keyString.getBytes()));
            byte[] keyStringBytes = keyString.getBytes();
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            SecretKey key = new SecretKeySpec(keyStringBytes, 0, keyStringBytes.length, "DES");
            System.out.println("Plaintext: " + plainTextString);
            byte[] plaintext = plainTextString.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);

            ciphertext = cipher.doFinal(plaintext);
            System.out.println("Ciphertext: " + bytesToString(ciphertext));
            /*String temp = "";
             for (int i = 0; i < ciphertext.length; i++) {
             int unsignedByte = ciphertext[i] & 0xFF;
             temp += Integer.toHexString(unsignedByte) + " ";
             }
             System.out.print("Cipher Text: " + temp);
             System.out.println("");*/
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] deciphertext = cipher.doFinal(ciphertext);
            System.out.println("Deciphered Text: " + new String(deciphertext));

            System.out.println("Starting Brute Force!");
            long startTime = System.currentTimeMillis();

            characterList = new ArrayList<>();
            numberList = new ArrayList<>();
            alphaLowList = new ArrayList<>();
            alphaHighList = new ArrayList<>();
            alphaList = new ArrayList<>();
            alphaNumericList = new ArrayList<>();
            dictionary = new HashMap<>();
            for (char ch = 'a'; ch <= 'z'; ch++) {
                characterList.add(ch + "");
                alphaLowList.add(ch + "");
                alphaList.add(ch + "");
                alphaNumericList.add(ch + "");
            }
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                characterList.add(ch + "");
                alphaHighList.add(ch + "");
                alphaList.add(ch + "");
                alphaNumericList.add(ch + "");
            }
            for (int i = 0; i < 10; i++) {
                characterList.add(i + "");
                numberList.add(i + "");
                alphaNumericList.add(i + "");
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

            System.out.println("Loading Dictionary!");
            BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                byte[] md5_array = MessageDigest.getInstance("MD5").digest(line.getBytes("UTF-8"));
                dictionary.put(bytesToString(md5_array), line);
            }
            System.out.println("Loaded " + dictionary.size() + " Words!");

            System.out.println("Brute force starting...");
            
            System.out.println("Trying dictionary 8 character word keys...");

            int count = 0;
            int totalCount = 0;
            boolean found = false;
            
            for(String s : dictionary.values())
            {
                if(s.length() == 8)
                {
                    count++;
                    totalCount++;
                    byte[] tempKeyStringBytes = s.getBytes();
                    SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                    cipher.init(Cipher.DECRYPT_MODE, tempKey);
                    try {
                        byte[] tempDeciphertext = cipher.doFinal(ciphertext);
                        for (String d : new String(tempDeciphertext).split(" ")) {
                            if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(d.getBytes("UTF-8"))))) {
                                System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + s + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                found = true;
                            }
                        }

                    } catch (BadPaddingException ex) {

                    }
                    if (found) {
                        break;
                    }
                }
            }

            if (!found) {
            System.out.println("Trying numeric only keys...");
            System.out.println("Number of combos to try: 10^8 = 100,000,000");

            count = 0;

            for (int a = 0; a < numberList.size(); a++) {
                for (int b = 0; b < numberList.size(); b++) {
                    for (int c = 0; c < numberList.size(); c++) {
                        for (int d = 0; d < numberList.size(); d++) {
                            for (int e = 0; e < numberList.size(); e++) {
                                for (int f = 0; f < numberList.size(); f++) {
                                    for (int g = 0; g < numberList.size(); g++) {
                                        for (int h = 0; h < numberList.size(); h++) {
                                            count++;
                                            totalCount++;
                                            byte[] tempKeyStringBytes = (numberList.get(a) + numberList.get(b) + numberList.get(c) + numberList.get(d) + numberList.get(e) + numberList.get(f) + numberList.get(g) + numberList.get(h)).getBytes();
                                            SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                                            cipher.init(Cipher.DECRYPT_MODE, tempKey);
                                            try {
                                                byte[] tempDeciphertext = cipher.doFinal(ciphertext);
                                                for (String s : new String(tempDeciphertext).split(" ")) {
                                                    if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                                                        System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + numberList.get(a) + numberList.get(b) + numberList.get(c) + numberList.get(d) + numberList.get(e) + numberList.get(f) + numberList.get(g) + numberList.get(h) + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                                        found = true;
                                                    }
                                                }

                                            } catch (BadPaddingException ex) {

                                            }
                                            if (found) {
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        }
                                    }
                                    if (found) {
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }

                    }
                    if (found) {
                        break;
                    }
                }
                System.out.println("Number of attempts: " + count);
                if (found) {
                    break;
                }
            }
        }

            if (!found) {
                System.out.println("Trying lowercase alpha keys...");
                System.out.println("Number of combos to try: 26^8 = 208,827,064,576");

                count = 0;
                for (int a = 0; a < alphaLowList.size(); a++) {
                    for (int b = 0; b < alphaLowList.size(); b++) {
                        for (int c = 0; c < alphaLowList.size(); c++) {
                            for (int d = 0; d < alphaLowList.size(); d++) {
                                for (int e = 0; e < alphaLowList.size(); e++) {
                                    for (int f = 0; f < alphaLowList.size(); f++) {
                                        for (int g = 0; g < alphaLowList.size(); g++) {
                                            for (int h = 0; h < alphaLowList.size(); h++) {
                                                count++;
                                                totalCount++;
                                                byte[] tempKeyStringBytes = (alphaLowList.get(a) + alphaLowList.get(b) + alphaLowList.get(c) + alphaLowList.get(d) + alphaLowList.get(e) + alphaLowList.get(f) + alphaLowList.get(g) + alphaLowList.get(h)).getBytes();
                                                SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                                                Cipher tempCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                                                tempCipher.init(Cipher.DECRYPT_MODE, tempKey);
                                                try {
                                                    byte[] tempDeciphertext = tempCipher.doFinal(ciphertext);
                                                    String[] tempStringArray = new String(tempDeciphertext).split(" ");
                                                    boolean wordFound = false;

                                                    for (String s : tempStringArray) {
                                                        if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                                                            System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + alphaLowList.get(a) + alphaLowList.get(b) + alphaLowList.get(c) + alphaLowList.get(d) + alphaLowList.get(e) + alphaLowList.get(f) + alphaLowList.get(g) + alphaLowList.get(h) + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                                            found = true;
                                                        }
                                                    }

                                                } catch (BadPaddingException ex) {

                                                }
                                                if (found) {
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        }
                                    }
                                    if (found) {
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }

                        }
                        if (found) {
                            break;
                        }
                    }
                    System.out.println("Number of attempts: " + count);
                    if (found) {
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Trying uppercase alpha keys...");
                System.out.println("Number of combos to try: 26^8 = 208,827,064,576");

                count = 0;
                for (int a = 0; a < alphaHighList.size(); a++) {
                    for (int b = 0; b < alphaHighList.size(); b++) {
                        for (int c = 0; c < alphaHighList.size(); c++) {
                            for (int d = 0; d < alphaHighList.size(); d++) {
                                for (int e = 0; e < alphaHighList.size(); e++) {
                                    for (int f = 0; f < alphaHighList.size(); f++) {
                                        for (int g = 0; g < alphaHighList.size(); g++) {
                                            for (int h = 0; h < alphaHighList.size(); h++) {
                                                count++;
                                                totalCount++;
                                                byte[] tempKeyStringBytes = (alphaHighList.get(a) + alphaHighList.get(b) + alphaHighList.get(c) + alphaHighList.get(d) + alphaHighList.get(e) + alphaHighList.get(f) + alphaHighList.get(g) + alphaHighList.get(h)).getBytes();
                                                SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                                                Cipher tempCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                                                tempCipher.init(Cipher.DECRYPT_MODE, tempKey);
                                                try {
                                                    byte[] tempDeciphertext = tempCipher.doFinal(ciphertext);
                                                    String[] tempStringArray = new String(tempDeciphertext).split(" ");
                                                    boolean wordFound = false;

                                                    for (String s : tempStringArray) {
                                                        if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                                                            System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + alphaHighList.get(a) + alphaHighList.get(b) + alphaHighList.get(c) + alphaHighList.get(d) + alphaHighList.get(e) + alphaHighList.get(f) + alphaHighList.get(g) + alphaHighList.get(h) + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                                            found = true;
                                                        }
                                                    }

                                                } catch (BadPaddingException ex) {

                                                }
                                                if (found) {
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        }
                                    }
                                    if (found) {
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }

                        }
                        if (found) {
                            break;
                        }
                    }
                    System.out.println("Number of attempts: " + count);
                    if (found) {
                        break;
                    }
                }
            }

            if (!found) {

                System.out.println("Trying uppercase and lowercase alpha keys...");
                System.out.println("Number of combos to try: 52^8 = 53,459,728,531,456");

                count = 0;
                for (int a = 0; a < alphaList.size(); a++) {
                    for (int b = 0; b < alphaList.size(); b++) {
                        for (int c = 0; c < alphaList.size(); c++) {
                            for (int d = 0; d < alphaList.size(); d++) {
                                for (int e = 0; e < alphaList.size(); e++) {
                                    for (int f = 0; f < alphaList.size(); f++) {
                                        for (int g = 0; g < alphaList.size(); g++) {
                                            for (int h = 0; h < alphaList.size(); h++) {
                                                count++;
                                                totalCount++;
                                                byte[] tempKeyStringBytes = (alphaList.get(a) + alphaList.get(b) + alphaList.get(c) + alphaList.get(d) + alphaList.get(e) + alphaList.get(f) + alphaList.get(g) + alphaList.get(h)).getBytes();
                                                SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                                                Cipher tempCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                                                tempCipher.init(Cipher.DECRYPT_MODE, tempKey);
                                                try {
                                                    byte[] tempDeciphertext = tempCipher.doFinal(ciphertext);
                                                    String[] tempStringArray = new String(tempDeciphertext).split(" ");
                                                    boolean wordFound = false;

                                                    for (String s : tempStringArray) {
                                                        if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                                                            System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + alphaList.get(a) + alphaList.get(b) + alphaList.get(c) + alphaList.get(d) + alphaList.get(e) + alphaList.get(f) + alphaList.get(g) + alphaList.get(h) + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                                            found = true;
                                                        }
                                                    }

                                                } catch (BadPaddingException ex) {

                                                }
                                                if (found) {
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        }
                                    }
                                    if (found) {
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }

                        }
                        if (found) {
                            break;
                        }
                    }
                    System.out.println("Number of attempts: " + count);
                    if (found) {
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Trying uppercase, lowercase alpha and numeric keys, this could take a while...");
                System.out.println("Number of combos to try: 62^8 = 218,340,105,584,896");

                count = 0;
                for (int a = 0; a < alphaNumericList.size(); a++) {
                    for (int b = 0; b < alphaNumericList.size(); b++) {
                        for (int c = 0; c < alphaNumericList.size(); c++) {
                            for (int d = 0; d < alphaNumericList.size(); d++) {
                                for (int e = 0; e < alphaNumericList.size(); e++) {
                                    for (int f = 0; f < alphaNumericList.size(); f++) {
                                        for (int g = 0; g < alphaNumericList.size(); g++) {
                                            for (int h = 0; h < alphaNumericList.size(); h++) {
                                                count++;
                                                totalCount++;
                                                byte[] tempKeyStringBytes = (alphaNumericList.get(a) + alphaNumericList.get(b) + alphaNumericList.get(c) + alphaNumericList.get(d) + alphaNumericList.get(e) + alphaNumericList.get(f) + alphaNumericList.get(g) + alphaNumericList.get(h)).getBytes();
                                                SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                                                Cipher tempCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                                                tempCipher.init(Cipher.DECRYPT_MODE, tempKey);
                                                try {
                                                    byte[] tempDeciphertext = tempCipher.doFinal(ciphertext);
                                                    String[] tempStringArray = new String(tempDeciphertext).split(" ");
                                                    boolean wordFound = false;

                                                    for (String s : tempStringArray) {
                                                        if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                                                            System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + alphaNumericList.get(a) + alphaNumericList.get(b) + alphaNumericList.get(c) + alphaNumericList.get(d) + alphaNumericList.get(e) + alphaNumericList.get(f) + alphaNumericList.get(g) + alphaNumericList.get(h) + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                                            found = true;
                                                        }
                                                    }

                                                } catch (BadPaddingException ex) {

                                                }
                                                if (found) {
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        }
                                    }
                                    if (found) {
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }

                        }
                        if (found) {
                            break;
                        }
                    }
                    System.out.println("Number of attempts: " + count);
                    if (found) {
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Trying uppercase, lowercase alpha and numeric keys and symbol keys...");
                System.out.println("Seriously this could take forever...");
                System.out.println("Number of combos to try: 96^8 = 7,213,895,789,838,336");

                count = 0;
                for (int a = 0; a < characterList.size(); a++) {
                    for (int b = 0; b < characterList.size(); b++) {
                        for (int c = 0; c < characterList.size(); c++) {
                            for (int d = 0; d < characterList.size(); d++) {
                                for (int e = 0; e < characterList.size(); e++) {
                                    for (int f = 0; f < characterList.size(); f++) {
                                        for (int g = 0; g < characterList.size(); g++) {
                                            for (int h = 0; h < characterList.size(); h++) {
                                                count++;
                                                totalCount++;
                                                byte[] tempKeyStringBytes = (characterList.get(a) + characterList.get(b) + characterList.get(c) + characterList.get(d) + characterList.get(e) + characterList.get(f) + characterList.get(g) + characterList.get(h)).getBytes();
                                                SecretKey tempKey = new SecretKeySpec(tempKeyStringBytes, 0, tempKeyStringBytes.length, "DES");
                                                Cipher tempCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                                                tempCipher.init(Cipher.DECRYPT_MODE, tempKey);
                                                try {
                                                    byte[] tempDeciphertext = tempCipher.doFinal(ciphertext);
                                                    String[] tempStringArray = new String(tempDeciphertext).split(" ");
                                                    boolean wordFound = false;

                                                    for (String s : tempStringArray) {
                                                        if (dictionary.containsKey(bytesToString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"))))) {
                                                            System.out.println("Potential Message: " + new String(tempDeciphertext) + " Potential Key: " + characterList.get(a) + characterList.get(b) + characterList.get(c) + characterList.get(d) + characterList.get(e) + characterList.get(f) + characterList.get(g) + characterList.get(h) + " Key Bytes: " + bytesToString(tempKeyStringBytes));
                                                            found = true;
                                                        }
                                                    }

                                                } catch (BadPaddingException ex) {

                                                }
                                                if (found) {
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                break;
                                            }
                                        }
                                        if (found) {
                                            break;
                                        }
                                    }
                                    if (found) {
                                        break;
                                    }
                                }
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }

                        }
                        if (found) {
                            break;
                        }
                    }
                    System.out.println("Number of attempts: " + count);
                    if (found) {
                        break;
                    }
                }
            }

            System.out.println("Found plaintext and key in " + totalCount + " number of attempts!");
            long endTime = System.currentTimeMillis();
            long timeDiff = (endTime - startTime)/1000;
            if(timeDiff > 3600) {
                timeDiff = ((timeDiff/60)/60);
                System.out.println("Brute Force took " + timeDiff + " hours!");
            }
            else if(timeDiff > 60)
            {
                timeDiff = (timeDiff/60);
                System.out.println("Brute Force took " + timeDiff + " minutes!");
            }
            else
            {
                System.out.println("Brute Force took " + timeDiff + " seconds!");
            }
            

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String bytesToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}
