/**
 * Michael Trinh
 * CS 3010
 * Programming Project 2
 * Description: Write a program that locates the roots in equations using the 
 *              methods: Bisection, Newton-Raphson, Secant, False-Position 
 *              and Modified Secant.
 */

import java.util.Random;

/**
 *
 * @author Michael Trinh
 */
public class RootLocator {
    
    public boolean sameSign(double num1, double num2){
        return ((num1 < 0) == (num2 < 0));
    }
    
    // f1(x) = 2x^3 – 11.7x^2 + 17.7x – 5;
    // f2(x) = x + 10 – xcosh(50/x)
    double getFunctionOfX(int function, double x) {
        if (function == 1)
            return ((2 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5);
        else {
            if (x == 0) throw new IllegalArgumentException("Divide by zero error.");
            return (x + 10 - (x * Math.cosh(50 / x)));
        }    
    }
    
    // f1'(x) = 6x^2 - 23.4x + 17.7
    // f2'(x) = (50sinh(50/x) / x) - cosh(50/x) + 1
    double getDerivativeFunctionOfX(int function, double x) {
        if (function == 1)
            return ((6 * Math.pow(x, 2)) - (23.4 * x) + 17.7);
        else {
            if (x == 0) throw new IllegalArgumentException("Divide by zero error.");
            return (((50 * Math.sinh(50 / x)) / x) - (Math.cosh(50 / x)) + 1 );
        }
    }
    
    public void doBisectionMethod(int function, double leftInterval, 
            double rightInterval, double desiredError) {
        double currentRoot = (leftInterval + rightInterval) / 2;
        double previousRoot, funRoot, funLeft, approximateError = 1;
        for(int i = 0; desiredError < approximateError; i++) {
            previousRoot = currentRoot;
            currentRoot = (leftInterval + rightInterval) / 2;
            if (i == 100) {
                System.out.println("Root Not Found.");
                return;
            }
            // Ea = (current - previous) / current
            else if (i != 0) {
                approximateError = Math.abs((currentRoot-previousRoot) / 
                    currentRoot);
            }
            funRoot = getFunctionOfX(function, currentRoot);
            funLeft = getFunctionOfX(function, leftInterval);
            if (sameSign(funRoot, funLeft))
                leftInterval = currentRoot;
            else
                rightInterval = currentRoot;
        }   
        // clever check if root converges to zero
        funRoot = getFunctionOfX(function, currentRoot);
        if (funRoot > -1 && funRoot < 1) {
            System.out.println("Root found at x = " + currentRoot);
            return;
        }
        System.out.println("Root is an inflection point.");
    }
    
    public void doNewtonRaphson(int function, double initialPoint, double desiredError) {
        double funRoot, DeriveRoot;
        double approximateError = 1;
        double currentRoot = initialPoint;
        double previousRoot;
        for(int i = 0; desiredError < approximateError; i++) {
            previousRoot = currentRoot;
            funRoot = getFunctionOfX(function, currentRoot);
            DeriveRoot = getDerivativeFunctionOfX(function, currentRoot);
            currentRoot = previousRoot - (funRoot / DeriveRoot);
            if (i == 100) {
                System.out.println("Root Not Found.");
                return;
            }
            // Ea = (current - previous) / current
            if (i != 0) {
                approximateError = Math.abs((currentRoot-previousRoot) / 
                    currentRoot);
            }
        }
        // clever check if root converges to zero
        funRoot = getFunctionOfX(function, currentRoot);
        if (funRoot > -1 && funRoot < 1) {
            System.out.println("Root found at x = " + currentRoot);
            return;
        }
        System.out.println("Inflection point found at x = " + currentRoot);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Michael Trinh's Root Locator");
        RootLocator rootLocator = new RootLocator();
        // Bisection
        System.out.println("\nBisection:");
        rootLocator.doBisectionMethod(1, 0, 4, .01);
        rootLocator.doBisectionMethod(2, 120, 130, .01);
        // Newton Raphson
        System.out.println("\nNewton-Raphson:");
        // get random point in range (0,4) & (120,130)
        double randomStartingPoint1 = 0 + (Math.random() * ((4 - 0)));
        double randomStartingPoint2 = 120 + (Math.random() * ((130 - 120)));
        System.out.println("random 1 = " + randomStartingPoint1);
        System.out.println("random 2 = " + randomStartingPoint2);
        rootLocator.doNewtonRaphson(1, randomStartingPoint1, .01);
        rootLocator.doNewtonRaphson(2, randomStartingPoint2, .01);
        // Secant
        System.out.println("\n Secant:");
    }
    
}
