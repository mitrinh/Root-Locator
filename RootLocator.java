/**
 * Michael Trinh
 * CS 3010
 * Programming Project 2
 * Description: Write a program that locates the roots in equations using the 
 *              methods: Bisection, Newton-Raphson, Secant, False-Position 
 *              and Modified Secant.
 */

/**
 *
 * @author Michael Trinh
 */
public class RootLocator {
    
    public boolean sameSign(double num1, double num2){
        return ((num1 < 0) == (num2 < 0));
    }
    
     // f1(x) = 2x^3 – 11.7x^2 + 17.7x – 5;
    double getFunction1OfX(double x) {
        return ((2 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5);
    }
    
    // f2(x) = x + 10 – xcosh(50/x)
    double getFunction2OfX(double x) {
        if (x == 0) throw new IllegalArgumentException("Divide by zero error.");
        return (x + 10 - (x * Math.cosh(50 / x)));
    }
    
    public void doBisectionMethod(int function, double leftInterval, 
            double rightInterval, double desiredError) {
        // so that loop doesn't break
        double approximateError = 1;
        double currentRoot = (leftInterval + rightInterval) / 2;
        double previousRoot;
        double funRoot;
        double funLeft;
        for(int i = 0; approximateError < desiredError; i++) {
            previousRoot = currentRoot;
            currentRoot = (leftInterval + rightInterval) / 2;
            if (i != 0) {
                approximateError = Math.abs((currentRoot-previousRoot) / 
                        currentRoot);
            }
            if (function == 1) {
                funRoot = getFunction1OfX(currentRoot);
                funLeft = getFunction1OfX(leftInterval);
            }
            else {
                funRoot = getFunction2OfX(currentRoot);
                funLeft = getFunction2OfX(leftInterval);
            }
            if (sameSign(funRoot, funLeft))
                leftInterval = currentRoot;
            else
                rightInterval = currentRoot;
        }   
        System.out.println("Root found at x = " + currentRoot);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Michael Trinh's Root Locator");
        RootLocator rootLocator = new RootLocator();
        rootLocator.doBisectionMethod(1, 1, 5, .10);
    }
    
}
