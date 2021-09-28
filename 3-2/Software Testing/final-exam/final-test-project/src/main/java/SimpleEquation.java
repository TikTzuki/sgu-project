import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleEquation {
    float a, b;
    Float x1;

    public SimpleEquation(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public Integer solveEquation() {
        if (a == 0 && b == 0) {
            return Integer.MAX_VALUE;
        } else if (a == 0 && b != 0) {
            return 0;
        } else {
            x1 = -b / a;
            return 1;
        }
    }

    @Override
    public String toString() {
        return String.format("x1=%s", x1);
    }

    public static void main(String[] args) {
        SimpleEquation qe = new SimpleEquation(1, 1);
        int result = qe.solveEquation();
        System.out.println(result);
        System.out.println(qe);
    }


}
