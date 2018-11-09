/**
 * Michael Trinh
 * CS 3010
 * Programming Project 2
 * Description: Write a program that asks the user for the number of linear
 *              equations to solve using 3 methods. Write programs for all
 *              the methods (Bisection, Newton-Raphson, Secant, False-Position 
 *              and Modified Secant) for locating roots.
 */

/**
 *
 * @author Michael Trinh
 */
public class RootLocator {
    
     // f1(x) = 2x^3 – 11.7x^2 + 17.7x – 5;
    double getFunction1OfX(double x) {
        return ((2 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5);
    }
    
    // f2(x) = x + 10 – xcosh(50/x)
    double getFunction2OfX(double x) {
        if (x == 0) throw new IllegalArgumentException("Divide by zero error.");
        return (x + 10 - (x * Math.cosh(50 / x)));
    }
    
    public void doBisectionMethod(String function, double leftInterval, 
            double rightInterval) {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Michael Trinh's Root Locator");
        RootLocator rootLocator = new RootLocator();
        double x = 0;
        System.out.println("F1(" + x + ") = " + rootLocator.getFunction1OfX(x));
        System.out.println("F2(" + x + ") = " + rootLocator.getFunction2OfX(x));
    }
    
}
