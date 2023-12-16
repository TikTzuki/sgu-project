import java.util.HashMap;
import java.util.Map;

public class SubtitutionCipher {
    public static String encode(Map<String, String> keyMap, String message) {
        StringBuilder encodeString = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            for (Map.Entry<String, String> keyTwinOfString : keyMap.entrySet()) {
                String key = keyTwinOfString.getKey();
                String target = keyTwinOfString.getValue();
                assert key.length() == 26;
                assert target.length() == 26;
                for (int j = 0; j < key.length(); j++) {
                    if (key.charAt(j) == c) {
                        encodeString.append(target.charAt(j));
                        break;
                    }
                }
            }
        }
        return encodeString.toString();
    }

    public static Map<String, String> switchKey(Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> set : map.entrySet()) {
            result.put(set.getValue(), set.getKey());
        }
        return result;
    }

    public static String decode(Map<String, String> key, String message) {
        key = switchKey(key);
        return encode(key, message);
    }

}
