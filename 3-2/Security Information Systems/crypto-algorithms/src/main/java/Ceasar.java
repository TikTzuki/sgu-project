public class Ceasar {
    public static String encode(String message, int key) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        message = message.toLowerCase();
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int index = (alphabet.indexOf(c) + key) % 26;
            if (index < 0) {
                index += 26;
            }
            encoded.append(alphabet.charAt(index));
        }
        return encoded.toString();
    }

    public static String decoded(String message, int key) {
        return encode(message, -key);
    }

    public static void main(String[] args) {
        String message = "hentoithubay";
        int key = -100;
        String encode = encode(message, key);
        String decoded = decoded(encode, key);
        System.out.println(encode);
        System.out.println(decoded);
    }
}
