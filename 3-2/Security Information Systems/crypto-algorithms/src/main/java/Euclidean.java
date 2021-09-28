public class Euclidean {
    /**
     * ax + by = gcd(a,b)
     *
     * @param a
     * @param b
     * @return
     */
    public static int greatestCommonDivisor(int a, int b) {
        int bigger = Math.max(a, b);
        int smaller = Math.min(a, b);
        while (smaller != 0) {
            int r = bigger % smaller;
            bigger = smaller;
            smaller = r;
        }
        return bigger;
    }

    /**
     * d=gcd(a,b) and ax+by=d; <br>
     * 1. if b=0, d<-a, x<-1, y<-0, return (d,x,y) <br>
     * 2. x2 = 1, x1 = 0, y2 = 0, y1 = 1 <br>
     * 3. while b>0 do <br>
     * 3.1. q<-floor(a/b), r<-a-q*b, x<-x2-q*x1, y<-y2-q*y1 <br>
     * 3.2. a<-b, b<-r, x2<-x1. x1<-x, y2<-y1, y1<-y <br>
     * 4. d<-a, x<-x2, y<-y2, return (d,x,y) <br>
     * m    a   r   q   y0  y1  y <br>
     * 88   17  3   5   0   1   -5 <br>
     *
     * @param a
     * @param b
     * @return Unit(a, b, x, y, d);
     */
    public static Unit euclideanExtend(int a, int b) {
        int currentA = a, currentB = b;
        int d, x, y;
        if (b == 0) {
            d = a;
            x = 1;
            y = 0;
            System.out.println(String.format("d=%s x= %s y=%s ", d, x, y));
            return new Unit(currentA, currentB, x, y, d);
        }
        int x2 = 1, x1 = 0, y2 = 0, y1 = 1;
        int q, r;
        while (b > 0) {
            q = Math.floorDiv(a, b);
            r = a - q * b;
            x = x2 - q * x1;
            y = y2 - q * y1;

            a = b;
            b = r;
            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
//            System.out.println(String.format("q:%-10s r:%-10s x:%-10s y:%-10s a:%-10s b:%-10s x2:%-10s x1:%-10s y2:%-10s y1:%-10s", q, r, x, y, a, b, x2, x1, y2, y1));
        }
        d = a;
        x = x2;
        y = y2;
        return new Unit(currentA, currentB, x, y, d);
    }

    public static int calReverse(int a, int n) {
        Unit unit = euclideanExtend(a, n);
        if (unit.d > 1)
            System.out.println("a^1 mod n does not exist");
        return unit.y;
    }

    public static int calReversePositive(int a, int n) {
        int rs = calReverse(a, n);
        if (rs < 0)
            rs = a + rs;
        return rs;
    }

    public static void main(String[] args) {
        int x = 1, y = 1, a = 17, b = 6;
//        System.out.println(euclideanExtend(26, 5));
        System.out.println(calReversePositive(120, 7));
        int n = 11;
        for (int i = 0; i < n; i++) {
            System.out.println(i + "---" + calReversePositive(n, i));
        }
//        for (int i = 0; i < 91; i++) {
//            int rs = greatestCommonDivisor(i, 91);
//            if (rs == 1)
//                System.out.print(i +", ");
//        }
    }
}

class Unit {
    int a, b, x, y, d;

    public Unit(int a, int b, int x, int y, int d) {
        this.a = a;
        this.b = b;
        this.x = x;
        this.y = y;
        this.d = d;
    }

    @Override
    public String toString() {
        return a + "*" + x + "+" + b + "*" + y + "=" + d;
    }
}