public class QuadraticEquations extends SimpleEquation {
    float c;
    Float x2;

    public QuadraticEquations(float a, float b, float c) {
        super(a, b);
        this.c = c;
    }

    private float calcDelta() {
        return b * b - 4 * a * c;
    }

    /**
     * Nếu delta < 0 vô nghiện thì giaiPT sẽ trả về 0 , đồng thời gán x1 = x2 =null .
     * Nếu delta == 0 có nghiệm kép thì giaiPT sẽ trả về 1 , đồng thời gán x1 = x2 = -b/2a .
     * Nếu delta > 0 có 2 nghiệm thì giaiPT sẽ trả về 2 , gán x1 = (-b+√delta ) / 2a  x = (-b-√delta ) / 2a .
     */
    @Override
    public Integer solveEquation() {
        float delta = calcDelta();

        if (delta < 0) {
            x1 = x2 = null;
            return 0;
        }

        if (delta == 0) {
            x1 = x2 = -b / (2 * a);
            return 1;
        }

        float sqrtDelta = (float) Math.sqrt(delta);
        x1 = (-b + sqrtDelta) / (2 * a);
        x2 = (-b - sqrtDelta) / (2 * a);
        return 2;
    }

    @Override
    public String toString() {
        return String.format("x1=%s, x2=%s", x1, x2);
    }

    public static void main(String[] args) {
        QuadraticEquations qe = new QuadraticEquations(1, 1, 1);
        int result = qe.solveEquation();
        System.out.println(result);
        System.out.println(qe);
    }
}
