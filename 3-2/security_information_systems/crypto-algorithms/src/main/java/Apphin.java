public class Apphin {
    final static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    static char decodeChar(int a, int b, char c) {
        int indexC = alphabet.indexOf(c);
        System.out.println(indexC);
        int aReverse = Euclidean.calReversePositive(26, a);
        int decoded = aReverse * (indexC - b) % 26;
        if (decoded < 0) {
            decoded += 26;
        }
        return alphabet.charAt(decoded);
    }

    static char encodeChar(int a, int b, int c) {
        int indexC = alphabet.indexOf(c);
        int encoded = a * indexC + b;
        if (encoded > 25) {
            encoded = encoded % 26;
        }
        return alphabet.charAt(encoded);
    }

    /**
     * y-encode = key1*x + key2 mod 26
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    static String encoded(int a, int b, String mess) {
        mess = mess.toLowerCase();
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < mess.length(); i++) {
            encoded.append(encodeChar(a, b, mess.charAt(i)));
        }
        return encoded.toString();
    }

    /**
     * y-decode =  reverse(26, key1)*(y-key2) mod 26
     *
     * @param a
     * @param b
     * @param mess
     * @return
     */
    static String decoded(int a, int b, String mess) {
        mess = mess.toLowerCase();
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < mess.length(); i++) {
            decoded.append(decodeChar(a, b, mess.charAt(i)));
        }
        return decoded.toString();
    }

    public static void main(String[] args) {
        String plainText = "hentoithubay";
        String encoded = Apphin.encoded(5, 6, plainText);
        System.out.println(encoded);
        String cypherText = "by";
        String decoded = Apphin.decoded(3, 4, cypherText);
        System.out.println(alphabet.indexOf("9"));
    }
}
