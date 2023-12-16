import java.util.Arrays;
import java.util.logging.Logger;

public class Vigenere {
    static Logger logger = Logger.getLogger(Vigenere.class.getName());
    final static String alphaBet = "abcdefghijklmnopqrstuvwxyz";
    static String key;

    static int[] createShiftSteps() {
        int[] keys = new int[key.length()];

        for (int i = 0; i < key.length(); i++) {
            keys[i] = alphaBet.indexOf(key.charAt(i));
        }
        return keys;
    }

    static void setKey(String key) {
        Vigenere.key = key;
    }

    static String encode(String mess) {
        StringBuilder encoded = new StringBuilder();
        int[] key = createShiftSteps();
        int j = 0;
        for (int i = 0; i < mess.length(); i++, j++) {
            if (j == key.length) {
                j = 0;
            }
            int encodedInt = alphaBet.indexOf(mess.charAt(i)) + key[j];
            logger.info(alphaBet.indexOf(mess.charAt(i)) + "");
            if (encodedInt > 25) {
                encodedInt %= 26;
            }
            System.out.println(alphaBet.indexOf(mess.charAt(i)) + ":" + encodedInt);
            encoded.append(alphaBet.charAt(encodedInt));
        }
        return encoded.toString();
    }

    static String findKey(String mess, String cypherText) {
        StringBuilder keyString = new StringBuilder();
        int key = 0;
        for (int i = 0; i < mess.length(); i++) {
            int plain = alphaBet.indexOf(mess.charAt(i));
            int cypher = alphaBet.indexOf(cypherText.charAt(i));
            if (cypher < plain) {
                key = cypher + 26 - plain;
                keyString.append(alphaBet.charAt(key));
            } else {
                key = cypher - plain;
                keyString.append(alphaBet.charAt(key));
            }

        }
        return findSameStringNeighbor(keyString.toString());
    }

    static String findSameStringNeighbor(String strg) {
        String stack = "";
        for (int i = 0; i < strg.length(); i++) {
            stack += strg.charAt(i);
            String temp = strg.replaceAll(stack, "");
            if (temp.equals("") || stack.contains(temp)) {
                return stack;
            }
        }
        return stack;
    }

    public static void main(String[] args) {
        System.out.println(alphaBet.charAt(11) + "" + alphaBet.charAt(6));
        setKey("vnu");
        int[] result = createShiftSteps();
        System.out.println(Arrays.toString(result));
        String plainText = "khoahoc";
        String cypherText = encode(plainText);
        String key = findKey(plainText, cypherText);
        System.out.println("Plain text: " + plainText);
        System.out.println("Cypher text: " + cypherText);
        System.out.println("Key is: " + key);
    }
}
