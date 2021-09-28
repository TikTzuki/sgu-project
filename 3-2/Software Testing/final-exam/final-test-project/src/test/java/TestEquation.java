import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runners.MethodSorters;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestEquation {
    private QuadraticEquations calculator;
    private SimpleEquation simpleCalculator;

    @Test
    public void testSimpleEquationsNoResult() {
        int expectedRootQuation = 0;
        Float expectedX1 = null;
        float a = 0;
        float b = 1;

        simpleCalculator = new SimpleEquation(a, b);
        int actualRootQuation = simpleCalculator.solveEquation();

        Assert.assertEquals(expectedRootQuation, actualRootQuation);
        Assert.assertEquals(expectedX1, simpleCalculator.x1);
    }

    @Test
    public void testSimpleEquationsInfinityResult() {
        int expectedRootQuation = Integer.MAX_VALUE;
        Float expectedX1 = null;
        float a = 0;
        float b = 0;

        simpleCalculator = new SimpleEquation(a, b);
        int actualRootQuation = simpleCalculator.solveEquation();

        Assert.assertEquals(expectedRootQuation, actualRootQuation);
        Assert.assertEquals(expectedX1, simpleCalculator.x1);
    }

    @Test
    public void testSimpleEquationsOneResult() {
        int expectedRootQuation = 1;
        Float expectedX1 = Float.valueOf("-2");
        float a = 3;
        float b = 6;

        simpleCalculator = new SimpleEquation(a, b);
        int actualRootQuation = simpleCalculator.solveEquation();

        Assert.assertEquals(expectedRootQuation, actualRootQuation);
        Assert.assertEquals(expectedX1, simpleCalculator.x1);
    }

    @Test
    public void testQuadraEquationsNoResult() {
        int expectedRootQuation = 0;
        Float expectedX1 = null;
        Float expectedX2 = null;
        float a = 3;
        float b = 2;
        float c = 5;

        calculator = new QuadraticEquations(a, b, c);
        int actualRootQuation = calculator.solveEquation();

        Assert.assertEquals(expectedRootQuation, actualRootQuation);
        Assert.assertEquals(expectedX1, calculator.x1);
        Assert.assertEquals(expectedX2, calculator.x2);
    }

    @Test
    public void testQuadraEquationsDoubleResult() {
        int expectedRootQuation = 1;
        Float expectedX1 = Float.valueOf("4");
        Float expectedX2 = Float.valueOf("4");
        float a = 1;
        float b = -8;
        float c = 16;

        calculator = new QuadraticEquations(a, b, c);
        int actualRootQuation = calculator.solveEquation();

        Assert.assertEquals(expectedRootQuation, actualRootQuation);
        Assert.assertEquals(expectedX1, calculator.x1);
        Assert.assertEquals(expectedX2, calculator.x2);
    }

    @Test
    public void testQuadraEquationsTwoResult() {
        int expectedRootQuation = 2;
        Float expectedX1 = Float.valueOf("-0.666666666");
        Float expectedX2 = Float.valueOf("-1");
        float a = 3;
        float b = 5;
        float c = 2;

        calculator = new QuadraticEquations(a, b, c);
        int actualRootQuation = calculator.solveEquation();

        Assert.assertEquals(expectedRootQuation, actualRootQuation);
        Assert.assertEquals(expectedX1, calculator.x1);
        Assert.assertEquals(expectedX2, calculator.x2);
    }

    @Test(expected = FileNotFoundException.class)
    public void testImportFile() {
        try {
            File file = new File("/home/web/telegram/data.csv");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
