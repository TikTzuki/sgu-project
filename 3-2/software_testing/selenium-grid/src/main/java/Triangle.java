public class Triangle {
    public void testTriangle(int a, int b, int c) {
        if (a < b + c && b < a + c && c < a + b) {
            if (a * a == b * b + c * c || b * b == a * a + c * c || c * c == a * a + b * b) {
                System.out.println("Day la tam giac vuong");
                return;
            }
            if (a == b && b == c) {
                System.out.println("Day la tam giac deu");
                return;
            }
            if (a == b || a == c || b == c) {
                System.out.println("Day la giam giac can");
                return;
            }
            System.out.println("Day la tam giac thuong");
            return;
        }
        System.out.println("Day khong phai la tam giac");
    }

    public int getMaximun(int[] numbers) {
        int maximun = Integer.MIN_VALUE;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > maximun) {
                maximun = numbers[i];
            }
        }
        return maximun;
    }

    public static float modexp(long a, long x, long n) {
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

    public static void main(String[] args) {
        float rs = Triangle.modexp(57, 230, 1024);
        System.out.println(rs);
    }

}
