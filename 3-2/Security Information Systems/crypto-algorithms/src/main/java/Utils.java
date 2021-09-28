public class Utils {
    public static long modexp(long a, long x, long n) {
        long r = 1;
        while (x > 0) {
            if (x % 2 == 1) {
                r = r * a % n;
            }
            a = a * a % n;
            x /= 2;
        }
        return r;
    }

    public static String toBit(long a) {
        StringBuilder rs = new StringBuilder();
        while (a > 0) {
            int bit = (int) (a % 2);
            rs.insert(0, bit);
            a /= 2;
        }
        return rs.toString();
    }

    public static void main(String[] args) {
        long modexp = modexp(7, 59, 91);
        System.out.println(modexp);
        int rs = Integer.parseInt("111", 2);
        System.out.println(rs);
        String bits = toBit(1);
//        System.out.println(bits);
    }
}
