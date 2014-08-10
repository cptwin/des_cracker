package des;

/**
 *
 * @author Dajne Win
 */
public class DESManualExample {
      
    public static void main(String[] args) {
        byte[] keyBytes = "1234567891".getBytes();
        String keyBinary = "";
        for(byte b : keyBytes)
        {
            keyBinary += Integer.toBinaryString(b);
        }
        System.out.println("Key Binary: " + keyBinary);
        String s = "0110000101100010011000110110010001100101011001100110011101101000";
        String initialPermutation = initialMessagePermutation(s);
        System.out.println("Initial Permutation " + initialPermutation);
        String[] LeftAndRight = splitLeftandRight(initialPermutation);
        System.out.println("Left 1: " + LeftAndRight[0] + " Right 1: " + LeftAndRight[1]);
        String expansionOfRight = expansionFunction(LeftAndRight[1]);
        System.out.println("Expansion of right: " + expansionOfRight);
        String k0permutatedKey = permutateKeyString(keyBinary);
        System.out.println("K0 Permutated Key: " + k0permutatedKey);
        String XORD = Long.toBinaryString(Long.parseLong(expansionOfRight, 2) ^ Long.parseLong(k0permutatedKey, 2));
        System.out.println("XOR Expansion with 48bit Key: " + XORD);
        String sbox1 = SBox1(XORD.substring(0, 6));
        String sbox2 = SBox2(XORD.substring(6, 12));
        String sbox3 = SBox3(XORD.substring(12, 18));
        String sbox4 = SBox4(XORD.substring(18, 24));
        String sbox5 = SBox5(XORD.substring(24, 30));
        String sbox6 = SBox6(XORD.substring(30, 36));
        String sbox7 = SBox7(XORD.substring(36, 42));
        String sbox8 = SBox8(XORD.substring(42, 48));
        while(sbox1.length() != 4)
        {
            sbox1 = "0" + sbox1;
        }
        while(sbox2.length() != 4)
        {
            sbox2 = "0" + sbox2;
        }
        while(sbox3.length() != 4)
        {
            sbox3 = "0" + sbox3;
        }
        while(sbox4.length() != 4)
        {
            sbox4 = "0" + sbox4;
        }
        while(sbox5.length() != 4)
        {
            sbox5 = "0" + sbox5;
        }
        while(sbox6.length() != 4)
        {
            sbox6 = "0" + sbox6;
        }
        while(sbox7.length() != 4)
        {
            sbox7 = "0" + sbox7;
        }
        while(sbox8.length() != 4)
        {
            sbox8 = "0" + sbox8;
        }
        
        String SBOXED = sbox1 + sbox2 + sbox3 + sbox4 + sbox5 + sbox6 + sbox7 + sbox8;
        System.out.println("SBox Conversion from 48bit to 32bit: " + SBOXED);
        
        String f = halfBlockPermutation(SBOXED);
        System.out.println("Half Block Permutation: " + f);
        String L2 = LeftAndRight[1];
        String R2 = Long.toBinaryString(Long.parseLong(LeftAndRight[0], 2) ^ Long.parseLong(f, 2));
        System.out.println("Left 2: " + L2 + " Right 2: " + R2);
        String finalSwap = R2 + L2;
        System.out.println("Final Swap Before Final Permutation: " + finalSwap);
        String finalPermutation = finalMessagePermutation(finalSwap);
        System.out.println("Final Cipher Bits: " + finalPermutation);
        
    }
    
    public static String finalMessagePermutation(String s)
    {
        String[] array = s.split("");
        String output = "";
        output += array[40];
        output += array[8];
        output += array[48];
        output += array[16];
        output += array[56];
        output += array[24];
        output += array[64];
        output += array[32];
        output += array[39];
        output += array[7];
        output += array[47];
        output += array[15];
        output += array[55];
        output += array[23];
        output += array[63];
        output += array[31];
        output += array[38];
        output += array[6];
        output += array[46];
        output += array[14];
        output += array[54];
        output += array[22];
        output += array[62];
        output += array[30];
        output += array[37];
        output += array[5];
        output += array[45];
        output += array[13];
        output += array[53];
        output += array[21];
        output += array[61];
        output += array[29];
        output += array[36];
        output += array[4];
        output += array[44];
        output += array[12];
        output += array[52];
        output += array[20];
        output += array[60];
        output += array[28];
        output += array[35];
        output += array[3];
        output += array[43];
        output += array[11];
        output += array[51];
        output += array[19];
        output += array[59];
        output += array[27];
        output += array[34];
        output += array[2];
        output += array[42];
        output += array[10];
        output += array[50];
        output += array[18];
        output += array[58];
        output += array[26];
        output += array[33];
        output += array[1];
        output += array[41];
        output += array[9];
        output += array[49];
        output += array[17];
        output += array[57];
        output += array[25];
        return output;
    }
    
    public static String halfBlockPermutation(String s)
    {
        String[] array = s.split("");
        String output = "";
        output += array[16];
        output += array[7];
        output += array[20];
        output += array[21];
        output += array[29];
        output += array[12];
        output += array[28];
        output += array[17];
        output += array[1];
        output += array[15];
        output += array[23];
        output += array[26];
        output += array[5];
        output += array[18];
        output += array[31];
        output += array[10];
        output += array[2];
        output += array[8];
        output += array[24];
        output += array[14];
        output += array[32];
        output += array[27];
        output += array[3];
        output += array[9];
        output += array[19];
        output += array[13];
        output += array[30];
        output += array[6];
        output += array[22];
        output += array[11];
        output += array[4];
        output += array[25];
        return output;
    }
    
    public static String permutateKeyString(String s)
    {
        String[] array = s.split("");
        String output = "";
        output += array[14];
        output += array[17];
        output += array[11];
        output += array[24];
        output += array[1];
        output += array[5];
        output += array[3];
        output += array[28];
        output += array[15];
        output += array[6];
        output += array[21];
        output += array[10];
        output += array[23];
        output += array[19];
        output += array[12];
        output += array[4];
        output += array[26];
        output += array[8];
        output += array[16];
        output += array[7];
        output += array[27];
        output += array[20];
        output += array[13];
        output += array[2];
        output += array[41];
        output += array[52];
        output += array[31];
        output += array[37];
        output += array[47];
        output += array[55];
        output += array[30];
        output += array[40];
        output += array[51];
        output += array[45];
        output += array[33];
        output += array[48];
        output += array[44];
        output += array[49];
        output += array[39];
        output += array[56];
        output += array[34];
        output += array[53];
        output += array[46];
        output += array[42];
        output += array[50];
        output += array[36];
        output += array[29];
        output += array[32];
        return output;
    }
    
    public static String[] splitLeftandRight(String s)
    {
        String[] array = new String[2];
        array[0] = s.substring(0, (s.length())/2);
        array[1] = s.substring((s.length())/2, s.length());
        return array;
    }
    
    public static String initialMessagePermutation(String s)
    {
        String[] array = s.split("");
        String output = "";
        output += array[58];
        output += array[50];
        output += array[42];
        output += array[34];
        output += array[26];
        output += array[18];
        output += array[10];
        output += array[2];
        output += array[60];
        output += array[52];
        output += array[44];
        output += array[36];
        output += array[28];
        output += array[20];
        output += array[12];
        output += array[4];
        output += array[62];
        output += array[54];
        output += array[46];
        output += array[38];
        output += array[30];
        output += array[22];
        output += array[14];
        output += array[6];
        output += array[64];
        output += array[56];
        output += array[48];
        output += array[40];
        output += array[32];
        output += array[24];
        output += array[16];
        output += array[8];
        output += array[57];
        output += array[49];
        output += array[41];
        output += array[33];
        output += array[25];
        output += array[17];
        output += array[9];
        output += array[1];
        output += array[59];
        output += array[51];
        output += array[43];
        output += array[35];
        output += array[27];
        output += array[19];
        output += array[11];
        output += array[3];
        output += array[61];
        output += array[53];
        output += array[45];
        output += array[37];
        output += array[29];
        output += array[21];
        output += array[13];
        output += array[5];
        output += array[63];
        output += array[55];
        output += array[47];
        output += array[39];
        output += array[31];
        output += array[23];
        output += array[15];
        output += array[7];
        return output;
    }
    
    public static String expansionFunction(String s)
    {
        String[] array = s.split("");
        String output = "";
        output += array[32];
        output += array[1];
        output += array[2];
        output += array[3];
        output += array[4];
        output += array[5];
        output += array[4];
        output += array[5];
        output += array[6];
        output += array[7];
        output += array[8];
        output += array[9];
        output += array[8];
        output += array[9];
        output += array[10];
        output += array[11];
        output += array[12];
        output += array[13];
        output += array[12];
        output += array[13];
        output += array[14];
        output += array[15];
        output += array[16];
        output += array[17];
        output += array[16];
        output += array[17];
        output += array[18];
        output += array[19];
        output += array[20];
        output += array[21];
        output += array[20];
        output += array[21];
        output += array[22];
        output += array[23];
        output += array[24];
        output += array[25];
        output += array[24];
        output += array[25];
        output += array[26];
        output += array[27];
        output += array[28];
        output += array[29];
        output += array[28];
        output += array[29];
        output += array[30];
        output += array[31];
        output += array[32];
        output += array[1];
        return output;
    }
    
    public static String SBox1(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(14);
            case "000001":
                return Integer.toBinaryString(0);
            case "100000":
                return Integer.toBinaryString(4);
            case "100001":
                return Integer.toBinaryString(15);
            case "000010":
                return Integer.toBinaryString(4);
            case "000011":
                return Integer.toBinaryString(15);
            case "100010":
                return Integer.toBinaryString(1);
            case "100011":
                return Integer.toBinaryString(12);
            case "000100":
                return Integer.toBinaryString(13);
            case "000101":
                return Integer.toBinaryString(7);
            case "100100":
                return Integer.toBinaryString(14);
            case "100101":
                return Integer.toBinaryString(8);
            case "000110":
                return Integer.toBinaryString(1);
            case "000111":
                return Integer.toBinaryString(4);
            case "100110":
                return Integer.toBinaryString(8);
            case "100111":
                return Integer.toBinaryString(2);
            case "001000":
                return Integer.toBinaryString(2);
            case "001001":
                return Integer.toBinaryString(14);
            case "101000":
                return Integer.toBinaryString(13);
            case "101001":
                return Integer.toBinaryString(4);
            case "001010":
                return Integer.toBinaryString(15);
            case "001011":
                return Integer.toBinaryString(2);
            case "101010":
                return Integer.toBinaryString(6);
            case "101011":
                return Integer.toBinaryString(9);
            case "001100":
                return Integer.toBinaryString(11);
            case "001101":
                return Integer.toBinaryString(13);
            case "101100":
                return Integer.toBinaryString(2);
            case "101101":
                return Integer.toBinaryString(1);
            case "001110":
                return Integer.toBinaryString(8);
            case "001111":
                return Integer.toBinaryString(1);
            case "101110":
                return Integer.toBinaryString(11);
            case "101111":
                return Integer.toBinaryString(7);
            case "010000":
                return Integer.toBinaryString(3);
            case "010001":
                return Integer.toBinaryString(10);
            case "110000":
                return Integer.toBinaryString(15);
            case "110001":
                return Integer.toBinaryString(5);
            case "010010":
                return Integer.toBinaryString(10);
            case "010011":
                return Integer.toBinaryString(6);
            case "110010":
                return Integer.toBinaryString(12);
            case "110011":
                return Integer.toBinaryString(11);
            case "010100":
                return Integer.toBinaryString(6);
            case "010101":
                return Integer.toBinaryString(12);
            case "110100":
                return Integer.toBinaryString(9);
            case "110101":
                return Integer.toBinaryString(3);
            case "010110":
                return Integer.toBinaryString(12);
            case "010111":
                return Integer.toBinaryString(11);
            case "110110":
                return Integer.toBinaryString(7);
            case "110111":
                return Integer.toBinaryString(14);
            case "011000":
                return Integer.toBinaryString(5);
            case "011001":
                return Integer.toBinaryString(9);
            case "111000":
                return Integer.toBinaryString(3);
            case "111001":
                return Integer.toBinaryString(10);
            case "011010":
                return Integer.toBinaryString(9);
            case "011011":
                return Integer.toBinaryString(5);
            case "111010":
                return Integer.toBinaryString(10);
            case "111011":
                return Integer.toBinaryString(0);
            case "011100":
                return Integer.toBinaryString(0);
            case "011101":
                return Integer.toBinaryString(3);
            case "111100":
                return Integer.toBinaryString(5);
            case "111101":
                return Integer.toBinaryString(6);
            case "011110":
                return Integer.toBinaryString(7);
            case "011111":
                return Integer.toBinaryString(8);
            case "111110":
                return Integer.toBinaryString(0);
            case "111111":
                return Integer.toBinaryString(13);
        }
        return Integer.toBinaryString(13);
    }
    
    public static String SBox2(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(15);
            case "000001":
                return Integer.toBinaryString(3);
            case "100000":
                return Integer.toBinaryString(0);
            case "100001":
                return Integer.toBinaryString(13);
            case "000010":
                return Integer.toBinaryString(1);
            case "000011":
                return Integer.toBinaryString(13);
            case "100010":
                return Integer.toBinaryString(14);
            case "100011":
                return Integer.toBinaryString(8);
            case "000100":
                return Integer.toBinaryString(8);
            case "000101":
                return Integer.toBinaryString(4);
            case "100100":
                return Integer.toBinaryString(7);
            case "100101":
                return Integer.toBinaryString(10);
            case "000110":
                return Integer.toBinaryString(14);
            case "000111":
                return Integer.toBinaryString(7);
            case "100110":
                return Integer.toBinaryString(11);
            case "100111":
                return Integer.toBinaryString(1);
            case "001000":
                return Integer.toBinaryString(6);
            case "001001":
                return Integer.toBinaryString(15);
            case "101000":
                return Integer.toBinaryString(10);
            case "101001":
                return Integer.toBinaryString(3);
            case "001010":
                return Integer.toBinaryString(11);
            case "001011":
                return Integer.toBinaryString(2);
            case "101010":
                return Integer.toBinaryString(4);
            case "101011":
                return Integer.toBinaryString(15);
            case "001100":
                return Integer.toBinaryString(3);
            case "001101":
                return Integer.toBinaryString(8);
            case "101100":
                return Integer.toBinaryString(13);
            case "101101":
                return Integer.toBinaryString(4);
            case "001110":
                return Integer.toBinaryString(4);
            case "001111":
                return Integer.toBinaryString(14);
            case "101110":
                return Integer.toBinaryString(1);
            case "101111":
                return Integer.toBinaryString(2);
            case "010000":
                return Integer.toBinaryString(9);
            case "010001":
                return Integer.toBinaryString(12);
            case "110000":
                return Integer.toBinaryString(5);
            case "110001":
                return Integer.toBinaryString(11);
            case "010010":
                return Integer.toBinaryString(7);
            case "010011":
                return Integer.toBinaryString(0);
            case "110010":
                return Integer.toBinaryString(8);
            case "110011":
                return Integer.toBinaryString(6);
            case "010100":
                return Integer.toBinaryString(2);
            case "010101":
                return Integer.toBinaryString(1);
            case "110100":
                return Integer.toBinaryString(12);
            case "110101":
                return Integer.toBinaryString(7);
            case "010110":
                return Integer.toBinaryString(13);
            case "010111":
                return Integer.toBinaryString(10);
            case "110110":
                return Integer.toBinaryString(6);
            case "110111":
                return Integer.toBinaryString(12);
            case "011000":
                return Integer.toBinaryString(12);
            case "011001":
                return Integer.toBinaryString(6);
            case "111000":
                return Integer.toBinaryString(9);
            case "111001":
                return Integer.toBinaryString(0);
            case "011010":
                return Integer.toBinaryString(0);
            case "011011":
                return Integer.toBinaryString(9);
            case "111010":
                return Integer.toBinaryString(3);
            case "111011":
                return Integer.toBinaryString(5);
            case "011100":
                return Integer.toBinaryString(5);
            case "011101":
                return Integer.toBinaryString(11);
            case "111100":
                return Integer.toBinaryString(2);
            case "111101":
                return Integer.toBinaryString(14);
            case "011110":
                return Integer.toBinaryString(10);
            case "011111":
                return Integer.toBinaryString(5);
            case "111110":
                return Integer.toBinaryString(15);
            case "111111":
                return Integer.toBinaryString(9);
        }
        return Integer.toBinaryString(9);
    }
    
    public static String SBox3(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(10);
            case "000001":
                return Integer.toBinaryString(13);
            case "100000":
                return Integer.toBinaryString(13);
            case "100001":
                return Integer.toBinaryString(1);
            case "000010":
                return Integer.toBinaryString(0);
            case "000011":
                return Integer.toBinaryString(7);
            case "100010":
                return Integer.toBinaryString(6);
            case "100011":
                return Integer.toBinaryString(10);
            case "000100":
                return Integer.toBinaryString(9);
            case "000101":
                return Integer.toBinaryString(0);
            case "100100":
                return Integer.toBinaryString(4);
            case "100101":
                return Integer.toBinaryString(13);
            case "000110":
                return Integer.toBinaryString(14);
            case "000111":
                return Integer.toBinaryString(9);
            case "100110":
                return Integer.toBinaryString(9);
            case "100111":
                return Integer.toBinaryString(0);
            case "001000":
                return Integer.toBinaryString(6);
            case "001001":
                return Integer.toBinaryString(3);
            case "101000":
                return Integer.toBinaryString(8);
            case "101001":
                return Integer.toBinaryString(6);
            case "001010":
                return Integer.toBinaryString(3);
            case "001011":
                return Integer.toBinaryString(4);
            case "101010":
                return Integer.toBinaryString(15);
            case "101011":
                return Integer.toBinaryString(9);
            case "001100":
                return Integer.toBinaryString(15);
            case "001101":
                return Integer.toBinaryString(6);
            case "101100":
                return Integer.toBinaryString(3);
            case "101101":
                return Integer.toBinaryString(8);
            case "001110":
                return Integer.toBinaryString(5);
            case "001111":
                return Integer.toBinaryString(10);
            case "101110":
                return Integer.toBinaryString(0);
            case "101111":
                return Integer.toBinaryString(7);
            case "010000":
                return Integer.toBinaryString(1);
            case "010001":
                return Integer.toBinaryString(2);
            case "110000":
                return Integer.toBinaryString(11);
            case "110001":
                return Integer.toBinaryString(4);
            case "010010":
                return Integer.toBinaryString(13);
            case "010011":
                return Integer.toBinaryString(8);
            case "110010":
                return Integer.toBinaryString(1);
            case "110011":
                return Integer.toBinaryString(15);
            case "010100":
                return Integer.toBinaryString(12);
            case "010101":
                return Integer.toBinaryString(5);
            case "110100":
                return Integer.toBinaryString(2);
            case "110101":
                return Integer.toBinaryString(14);
            case "010110":
                return Integer.toBinaryString(7);
            case "010111":
                return Integer.toBinaryString(14);
            case "110110":
                return Integer.toBinaryString(12);
            case "110111":
                return Integer.toBinaryString(3);
            case "011000":
                return Integer.toBinaryString(11);
            case "011001":
                return Integer.toBinaryString(12);
            case "111000":
                return Integer.toBinaryString(5);
            case "111001":
                return Integer.toBinaryString(11);
            case "011010":
                return Integer.toBinaryString(4);
            case "011011":
                return Integer.toBinaryString(11);
            case "111010":
                return Integer.toBinaryString(10);
            case "111011":
                return Integer.toBinaryString(5);
            case "011100":
                return Integer.toBinaryString(2);
            case "011101":
                return Integer.toBinaryString(15);
            case "111100":
                return Integer.toBinaryString(14);
            case "111101":
                return Integer.toBinaryString(2);
            case "011110":
                return Integer.toBinaryString(8);
            case "011111":
                return Integer.toBinaryString(1);
            case "111110":
                return Integer.toBinaryString(7);
            case "111111":
                return Integer.toBinaryString(12);
        }
        return Integer.toBinaryString(12);
    }
    
    public static String SBox4(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(7);
            case "000001":
                return Integer.toBinaryString(13);
            case "100000":
                return Integer.toBinaryString(10);
            case "100001":
                return Integer.toBinaryString(3);
            case "000010":
                return Integer.toBinaryString(13);
            case "000011":
                return Integer.toBinaryString(8);
            case "100010":
                return Integer.toBinaryString(6);
            case "100011":
                return Integer.toBinaryString(15);
            case "000100":
                return Integer.toBinaryString(14);
            case "000101":
                return Integer.toBinaryString(11);
            case "100100":
                return Integer.toBinaryString(9);
            case "100101":
                return Integer.toBinaryString(0);
            case "000110":
                return Integer.toBinaryString(3);
            case "000111":
                return Integer.toBinaryString(5);
            case "100110":
                return Integer.toBinaryString(0);
            case "100111":
                return Integer.toBinaryString(6);
            case "001000":
                return Integer.toBinaryString(0);
            case "001001":
                return Integer.toBinaryString(6);
            case "101000":
                return Integer.toBinaryString(12);
            case "101001":
                return Integer.toBinaryString(10);
            case "001010":
                return Integer.toBinaryString(6);
            case "001011":
                return Integer.toBinaryString(15);
            case "101010":
                return Integer.toBinaryString(11);
            case "101011":
                return Integer.toBinaryString(1);
            case "001100":
                return Integer.toBinaryString(9);
            case "001101":
                return Integer.toBinaryString(0);
            case "101100":
                return Integer.toBinaryString(7);
            case "101101":
                return Integer.toBinaryString(13);
            case "001110":
                return Integer.toBinaryString(10);
            case "001111":
                return Integer.toBinaryString(3);
            case "101110":
                return Integer.toBinaryString(13);
            case "101111":
                return Integer.toBinaryString(8);
            case "010000":
                return Integer.toBinaryString(1);
            case "010001":
                return Integer.toBinaryString(4);
            case "110000":
                return Integer.toBinaryString(15);
            case "110001":
                return Integer.toBinaryString(9);
            case "010010":
                return Integer.toBinaryString(2);
            case "010011":
                return Integer.toBinaryString(7);
            case "110010":
                return Integer.toBinaryString(1);
            case "110011":
                return Integer.toBinaryString(4);
            case "010100":
                return Integer.toBinaryString(8);
            case "010101":
                return Integer.toBinaryString(2);
            case "110100":
                return Integer.toBinaryString(3);
            case "110101":
                return Integer.toBinaryString(5);
            case "010110":
                return Integer.toBinaryString(5);
            case "010111":
                return Integer.toBinaryString(12);
            case "110110":
                return Integer.toBinaryString(14);
            case "110111":
                return Integer.toBinaryString(11);
            case "011000":
                return Integer.toBinaryString(11);
            case "011001":
                return Integer.toBinaryString(1);
            case "111000":
                return Integer.toBinaryString(5);
            case "111001":
                return Integer.toBinaryString(12);
            case "011010":
                return Integer.toBinaryString(12);
            case "011011":
                return Integer.toBinaryString(10);
            case "111010":
                return Integer.toBinaryString(2);
            case "111011":
                return Integer.toBinaryString(7);
            case "011100":
                return Integer.toBinaryString(4);
            case "011101":
                return Integer.toBinaryString(14);
            case "111100":
                return Integer.toBinaryString(8);
            case "111101":
                return Integer.toBinaryString(2);
            case "011110":
                return Integer.toBinaryString(15);
            case "011111":
                return Integer.toBinaryString(9);
            case "111110":
                return Integer.toBinaryString(4);
            case "111111":
                return Integer.toBinaryString(14);
        }
        return Integer.toBinaryString(14);
    }
    
    public static String SBox5(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(2);
            case "000001":
                return Integer.toBinaryString(14);
            case "100000":
                return Integer.toBinaryString(4);
            case "100001":
                return Integer.toBinaryString(11);
            case "000010":
                return Integer.toBinaryString(12);
            case "000011":
                return Integer.toBinaryString(11);
            case "100010":
                return Integer.toBinaryString(2);
            case "100011":
                return Integer.toBinaryString(8);
            case "000100":
                return Integer.toBinaryString(4);
            case "000101":
                return Integer.toBinaryString(2);
            case "100100":
                return Integer.toBinaryString(1);
            case "100101":
                return Integer.toBinaryString(12);
            case "000110":
                return Integer.toBinaryString(1);
            case "000111":
                return Integer.toBinaryString(12);
            case "100110":
                return Integer.toBinaryString(11);
            case "100111":
                return Integer.toBinaryString(7);
            case "001000":
                return Integer.toBinaryString(7);
            case "001001":
                return Integer.toBinaryString(4);
            case "101000":
                return Integer.toBinaryString(10);
            case "101001":
                return Integer.toBinaryString(1);
            case "001010":
                return Integer.toBinaryString(10);
            case "001011":
                return Integer.toBinaryString(7);
            case "101010":
                return Integer.toBinaryString(13);
            case "101011":
                return Integer.toBinaryString(14);
            case "001100":
                return Integer.toBinaryString(11);
            case "001101":
                return Integer.toBinaryString(13);
            case "101100":
                return Integer.toBinaryString(7);
            case "101101":
                return Integer.toBinaryString(2);
            case "001110":
                return Integer.toBinaryString(6);
            case "001111":
                return Integer.toBinaryString(1);
            case "101110":
                return Integer.toBinaryString(8);
            case "101111":
                return Integer.toBinaryString(13);
            case "010000":
                return Integer.toBinaryString(8);
            case "010001":
                return Integer.toBinaryString(5);
            case "110000":
                return Integer.toBinaryString(15);
            case "110001":
                return Integer.toBinaryString(6);
            case "010010":
                return Integer.toBinaryString(5);
            case "010011":
                return Integer.toBinaryString(0);
            case "110010":
                return Integer.toBinaryString(9);
            case "110011":
                return Integer.toBinaryString(15);
            case "010100":
                return Integer.toBinaryString(3);
            case "010101":
                return Integer.toBinaryString(15);
            case "110100":
                return Integer.toBinaryString(12);
            case "110101":
                return Integer.toBinaryString(0);
            case "010110":
                return Integer.toBinaryString(15);
            case "010111":
                return Integer.toBinaryString(10);
            case "110110":
                return Integer.toBinaryString(5);
            case "110111":
                return Integer.toBinaryString(9);
            case "011000":
                return Integer.toBinaryString(13);
            case "011001":
                return Integer.toBinaryString(3);
            case "111000":
                return Integer.toBinaryString(6);
            case "111001":
                return Integer.toBinaryString(10);
            case "011010":
                return Integer.toBinaryString(0);
            case "011011":
                return Integer.toBinaryString(9);
            case "111010":
                return Integer.toBinaryString(3);
            case "111011":
                return Integer.toBinaryString(4);
            case "011100":
                return Integer.toBinaryString(14);
            case "011101":
                return Integer.toBinaryString(8);
            case "111100":
                return Integer.toBinaryString(0);
            case "111101":
                return Integer.toBinaryString(5);
            case "011110":
                return Integer.toBinaryString(9);
            case "011111":
                return Integer.toBinaryString(6);
            case "111110":
                return Integer.toBinaryString(14);
            case "111111":
                return Integer.toBinaryString(3);
        }
        return Integer.toBinaryString(3);
    }
    
    public static String SBox6(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(12);
            case "000001":
                return Integer.toBinaryString(10);
            case "100000":
                return Integer.toBinaryString(9);
            case "100001":
                return Integer.toBinaryString(4);
            case "000010":
                return Integer.toBinaryString(1);
            case "000011":
                return Integer.toBinaryString(15);
            case "100010":
                return Integer.toBinaryString(14);
            case "100011":
                return Integer.toBinaryString(13);
            case "000100":
                return Integer.toBinaryString(10);
            case "000101":
                return Integer.toBinaryString(4);
            case "100100":
                return Integer.toBinaryString(15);
            case "100101":
                return Integer.toBinaryString(2);
            case "000110":
                return Integer.toBinaryString(15);
            case "000111":
                return Integer.toBinaryString(2);
            case "100110":
                return Integer.toBinaryString(5);
            case "100111":
                return Integer.toBinaryString(12);
            case "001000":
                return Integer.toBinaryString(9);
            case "001001":
                return Integer.toBinaryString(7);
            case "101000":
                return Integer.toBinaryString(2);
            case "101001":
                return Integer.toBinaryString(9);
            case "001010":
                return Integer.toBinaryString(2);
            case "001011":
                return Integer.toBinaryString(12);
            case "101010":
                return Integer.toBinaryString(8);
            case "101011":
                return Integer.toBinaryString(5);
            case "001100":
                return Integer.toBinaryString(6);
            case "001101":
                return Integer.toBinaryString(9);
            case "101100":
                return Integer.toBinaryString(12);
            case "101101":
                return Integer.toBinaryString(15);
            case "001110":
                return Integer.toBinaryString(8);
            case "001111":
                return Integer.toBinaryString(5);
            case "101110":
                return Integer.toBinaryString(3);
            case "101111":
                return Integer.toBinaryString(10);
            case "010000":
                return Integer.toBinaryString(0);
            case "010001":
                return Integer.toBinaryString(6);
            case "110000":
                return Integer.toBinaryString(7);
            case "110001":
                return Integer.toBinaryString(11);
            case "010010":
                return Integer.toBinaryString(13);
            case "010011":
                return Integer.toBinaryString(1);
            case "110010":
                return Integer.toBinaryString(0);
            case "110011":
                return Integer.toBinaryString(14);
            case "010100":
                return Integer.toBinaryString(3);
            case "010101":
                return Integer.toBinaryString(13);
            case "110100":
                return Integer.toBinaryString(4);
            case "110101":
                return Integer.toBinaryString(1);
            case "010110":
                return Integer.toBinaryString(4);
            case "010111":
                return Integer.toBinaryString(14);
            case "110110":
                return Integer.toBinaryString(10);
            case "110111":
                return Integer.toBinaryString(7);
            case "011000":
                return Integer.toBinaryString(14);
            case "011001":
                return Integer.toBinaryString(0);
            case "111000":
                return Integer.toBinaryString(1);
            case "111001":
                return Integer.toBinaryString(6);
            case "011010":
                return Integer.toBinaryString(7);
            case "011011":
                return Integer.toBinaryString(11);
            case "111010":
                return Integer.toBinaryString(13);
            case "111011":
                return Integer.toBinaryString(0);
            case "011100":
                return Integer.toBinaryString(5);
            case "011101":
                return Integer.toBinaryString(3);
            case "111100":
                return Integer.toBinaryString(11);
            case "111101":
                return Integer.toBinaryString(8);
            case "011110":
                return Integer.toBinaryString(11);
            case "011111":
                return Integer.toBinaryString(8);
            case "111110":
                return Integer.toBinaryString(6);
            case "111111":
                return Integer.toBinaryString(13);
        }
        return Integer.toBinaryString(13);
    }
    
    public static String SBox7(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(4);
            case "000001":
                return Integer.toBinaryString(13);
            case "100000":
                return Integer.toBinaryString(1);
            case "100001":
                return Integer.toBinaryString(6);
            case "000010":
                return Integer.toBinaryString(11);
            case "000011":
                return Integer.toBinaryString(0);
            case "100010":
                return Integer.toBinaryString(4);
            case "100011":
                return Integer.toBinaryString(11);
            case "000100":
                return Integer.toBinaryString(2);
            case "000101":
                return Integer.toBinaryString(11);
            case "100100":
                return Integer.toBinaryString(11);
            case "100101":
                return Integer.toBinaryString(13);
            case "000110":
                return Integer.toBinaryString(14);
            case "000111":
                return Integer.toBinaryString(7);
            case "100110":
                return Integer.toBinaryString(13);
            case "100111":
                return Integer.toBinaryString(8);
            case "001000":
                return Integer.toBinaryString(15);
            case "001001":
                return Integer.toBinaryString(4);
            case "101000":
                return Integer.toBinaryString(12);
            case "101001":
                return Integer.toBinaryString(1);
            case "001010":
                return Integer.toBinaryString(0);
            case "001011":
                return Integer.toBinaryString(9);
            case "101010":
                return Integer.toBinaryString(3);
            case "101011":
                return Integer.toBinaryString(4);
            case "001100":
                return Integer.toBinaryString(8);
            case "001101":
                return Integer.toBinaryString(1);
            case "101100":
                return Integer.toBinaryString(7);
            case "101101":
                return Integer.toBinaryString(10);
            case "001110":
                return Integer.toBinaryString(13);
            case "001111":
                return Integer.toBinaryString(10);
            case "101110":
                return Integer.toBinaryString(14);
            case "101111":
                return Integer.toBinaryString(7);
            case "010000":
                return Integer.toBinaryString(3);
            case "010001":
                return Integer.toBinaryString(14);
            case "110000":
                return Integer.toBinaryString(10);
            case "110001":
                return Integer.toBinaryString(9);
            case "010010":
                return Integer.toBinaryString(12);
            case "010011":
                return Integer.toBinaryString(3);
            case "110010":
                return Integer.toBinaryString(15);
            case "110011":
                return Integer.toBinaryString(5);
            case "010100":
                return Integer.toBinaryString(9);
            case "010101":
                return Integer.toBinaryString(5);
            case "110100":
                return Integer.toBinaryString(6);
            case "110101":
                return Integer.toBinaryString(0);
            case "010110":
                return Integer.toBinaryString(7);
            case "010111":
                return Integer.toBinaryString(12);
            case "110110":
                return Integer.toBinaryString(8);
            case "110111":
                return Integer.toBinaryString(15);
            case "011000":
                return Integer.toBinaryString(5);
            case "011001":
                return Integer.toBinaryString(2);
            case "111000":
                return Integer.toBinaryString(0);
            case "111001":
                return Integer.toBinaryString(14);
            case "011010":
                return Integer.toBinaryString(10);
            case "011011":
                return Integer.toBinaryString(15);
            case "111010":
                return Integer.toBinaryString(5);
            case "111011":
                return Integer.toBinaryString(2);
            case "011100":
                return Integer.toBinaryString(6);
            case "011101":
                return Integer.toBinaryString(8);
            case "111100":
                return Integer.toBinaryString(9);
            case "111101":
                return Integer.toBinaryString(3);
            case "011110":
                return Integer.toBinaryString(1);
            case "011111":
                return Integer.toBinaryString(6);
            case "111110":
                return Integer.toBinaryString(2);
            case "111111":
                return Integer.toBinaryString(12);
        }
        return Integer.toBinaryString(12);
    }
    
    public static String SBox8(String s)
    {
        switch (s) {
            case "000000":
                return Integer.toBinaryString(13);
            case "000001":
                return Integer.toBinaryString(1);
            case "100000":
                return Integer.toBinaryString(7);
            case "100001":
                return Integer.toBinaryString(2);
            case "000010":
                return Integer.toBinaryString(2);
            case "000011":
                return Integer.toBinaryString(15);
            case "100010":
                return Integer.toBinaryString(11);
            case "100011":
                return Integer.toBinaryString(1);
            case "000100":
                return Integer.toBinaryString(8);
            case "000101":
                return Integer.toBinaryString(13);
            case "100100":
                return Integer.toBinaryString(4);
            case "100101":
                return Integer.toBinaryString(14);
            case "000110":
                return Integer.toBinaryString(4);
            case "000111":
                return Integer.toBinaryString(8);
            case "100110":
                return Integer.toBinaryString(1);
            case "100111":
                return Integer.toBinaryString(7);
            case "001000":
                return Integer.toBinaryString(6);
            case "001001":
                return Integer.toBinaryString(10);
            case "101000":
                return Integer.toBinaryString(9);
            case "101001":
                return Integer.toBinaryString(4);
            case "001010":
                return Integer.toBinaryString(15);
            case "001011":
                return Integer.toBinaryString(3);
            case "101010":
                return Integer.toBinaryString(12);
            case "101011":
                return Integer.toBinaryString(10);
            case "001100":
                return Integer.toBinaryString(11);
            case "001101":
                return Integer.toBinaryString(7);
            case "101100":
                return Integer.toBinaryString(14);
            case "101101":
                return Integer.toBinaryString(8);
            case "001110":
                return Integer.toBinaryString(1);
            case "001111":
                return Integer.toBinaryString(4);
            case "101110":
                return Integer.toBinaryString(2);
            case "101111":
                return Integer.toBinaryString(13);
            case "010000":
                return Integer.toBinaryString(10);
            case "010001":
                return Integer.toBinaryString(12);
            case "110000":
                return Integer.toBinaryString(0);
            case "110001":
                return Integer.toBinaryString(15);
            case "010010":
                return Integer.toBinaryString(9);
            case "010011":
                return Integer.toBinaryString(5);
            case "110010":
                return Integer.toBinaryString(6);
            case "110011":
                return Integer.toBinaryString(12);
            case "010100":
                return Integer.toBinaryString(3);
            case "010101":
                return Integer.toBinaryString(6);
            case "110100":
                return Integer.toBinaryString(10);
            case "110101":
                return Integer.toBinaryString(9);
            case "010110":
                return Integer.toBinaryString(14);
            case "010111":
                return Integer.toBinaryString(11);
            case "110110":
                return Integer.toBinaryString(13);
            case "110111":
                return Integer.toBinaryString(0);
            case "011000":
                return Integer.toBinaryString(5);
            case "011001":
                return Integer.toBinaryString(0);
            case "111000":
                return Integer.toBinaryString(15);
            case "111001":
                return Integer.toBinaryString(3);
            case "011010":
                return Integer.toBinaryString(0);
            case "011011":
                return Integer.toBinaryString(14);
            case "111010":
                return Integer.toBinaryString(3);
            case "111011":
                return Integer.toBinaryString(5);
            case "011100":
                return Integer.toBinaryString(12);
            case "011101":
                return Integer.toBinaryString(9);
            case "111100":
                return Integer.toBinaryString(5);
            case "111101":
                return Integer.toBinaryString(6);
            case "011110":
                return Integer.toBinaryString(7);
            case "011111":
                return Integer.toBinaryString(2);
            case "111110":
                return Integer.toBinaryString(8);
            case "111111":
                return Integer.toBinaryString(11);
        }
        return Integer.toBinaryString(11);
    }
    
    
}
