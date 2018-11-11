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
    
    // clever check if root converges to zero
    boolean isRoot(int function, double currentPoint){
        double funCurrent = getFunctionOfX(function, currentPoint);
        if (funCurrent > -1 && funCurrent < 1) {
            System.out.println("Root found at x = " + currentPoint);
            return true;
        } 
        return false;
    }
    
    public void doBisection(int function, double leftInterval, 
            double rightInterval, double desiredError) {
        double funCurrent, funLeft, previousPoint;
        double currentPoint = 1, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            previousPoint = currentPoint;
            currentPoint = (leftInterval + rightInterval) / 2;
            if (i == 100) {
                System.out.println("Root Not Found.");
                return;
            }
            // Ea = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((currentPoint - previousPoint) / currentPoint);
            funCurrent = getFunctionOfX(function, currentPoint);
            funLeft = getFunctionOfX(function, leftInterval);
            if (sameSign(funCurrent, funLeft))
                leftInterval = currentPoint;
            else
                rightInterval = currentPoint;
        }   
        if(!isRoot(function, currentPoint))
            System.out.println("Root is an inflection point.");
    }
    
    public void doNewtonRaphson(int function, double currentPoint, double desiredError) {
        double previousPoint, funCurrent, derivePoint, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            previousPoint = currentPoint;
            funCurrent = getFunctionOfX(function, currentPoint);
            derivePoint = getDerivativeFunctionOfX(function, currentPoint);
            currentPoint = previousPoint - (funCurrent / derivePoint);
            if (i == 100) {
                System.out.println("Root Not Found.");
                return;
            }
            // Ea = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((currentPoint - previousPoint) / currentPoint);
        }
        if(!isRoot(function, currentPoint))
            System.out.println("Root is an inflection point.");
    }
    
    public void doSecant(int function, double previousPoint, double currentPoint, 
            double desiredError) {
        double nextPoint, funCurrent, funPrevious, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            funCurrent = getFunctionOfX(function, currentPoint);
            funPrevious = getFunctionOfX(function, previousPoint);
            // xi+1 = xi - (f(xi) * (xi-xi-1)) / (f(xi) - f(xi-1))
            nextPoint = currentPoint - ((funCurrent * (currentPoint - 
                    previousPoint)) / (funCurrent - funPrevious));
            if (i == 100) {
                System.out.println("Root Not Found.");
                return;
            }
            // Ea = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((nextPoint - currentPoint) / nextPoint);
            previousPoint = currentPoint;
            currentPoint = nextPoint;
        }
        if(!isRoot(function, currentPoint))
            System.out.println("Root is an inflection point.");
    }
    
    public void doFalsePosition(int function, double leftInterval, 
            double rightInterval, double desiredError) {
        double funCurrent, funLeft, funRight, previousPoint;
        double currentPoint = 1, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            funLeft = getFunctionOfX(function, leftInterval);
            funRight = getFunctionOfX(function, rightInterval);
            previousPoint = currentPoint;
            // c = b - ((f(b)*(b-a)) / (f(b) - f(a)))
            currentPoint = rightInterval - ((funRight * (rightInterval - leftInterval)) / 
                    (funRight - funLeft)); 
            if (i == 100) {
                System.out.println("Root Not Found.");
                return;
            }
            // Ea = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((currentPoint - previousPoint) / currentPoint);
            funCurrent = getFunctionOfX(function, currentPoint);
            funLeft = getFunctionOfX(function, leftInterval);
            if (sameSign(funCurrent, funLeft))
                leftInterval = currentPoint;
            else
                rightInterval = currentPoint;
        }
        if(!isRoot(function, currentPoint)) 
            System.out.println("Root is an inflection point.");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Michael Trinh's Root Locator");
        // range in function 1: [0,4]
        // range in function 2: [120,130]
        RootLocator rootLocator = new RootLocator();
        // Bisection
        System.out.println("\nBisection:");
        rootLocator.doBisection(1, 0, 4, .01);
        rootLocator.doBisection(2, 120, 130, .01);
        // Newton-Raphson
        System.out.println("\nNewton-Raphson:");
        rootLocator.doNewtonRaphson(1, 4, .01);
        rootLocator.doNewtonRaphson(2, 125, .01);
        // Secant
        System.out.println("\nSecant:");
        rootLocator.doSecant(1, 3, 4, .01);
        rootLocator.doSecant(2, 120, 130, .01);
        // False-Position
        System.out.println("\nFalse-Position:");
        rootLocator.doFalsePosition(1, 2, 4, .01);
        rootLocator.doFalsePosition(2, 120, 130, .01);
        // Modified Secant
        System.out.println("\nModified Secant:");
    }
    
}
