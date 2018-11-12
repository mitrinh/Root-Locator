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
    
    boolean atMaximumIterations(int iterations){
        if(iterations == 100) {
            System.out.println("Root Not Found.");
            return true;
        }
        return false;
    }
    
    // clever check if root converges to zero
    boolean isRoot(int function, double currentPoint){
        double funCurrent = getFunctionOfX(function, currentPoint);
        if (funCurrent > -.5 && funCurrent < .5) {
            System.out.println("Root found at x = " + currentPoint + "\n");
            return true;
        } 
        return false;
    }
    
    public void doBisection(int function, double leftInterval, 
            double rightInterval, double desiredError) {
        double funCurrent, funLeft, funRight, previousPoint;
        double currentPoint = 1, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            funLeft = getFunctionOfX(function, leftInterval);
            funRight = getFunctionOfX(function, rightInterval);
            previousPoint = currentPoint;
            currentPoint = (leftInterval + rightInterval) / 2;
            if(atMaximumIterations(i)) 
                return;
            // En = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((currentPoint - previousPoint) / currentPoint);
            funCurrent = getFunctionOfX(function, currentPoint);
            // print all data
            printBracketingFunction(i, leftInterval, rightInterval, 
                    currentPoint, funLeft, funRight, funCurrent, approximateError);
            if (sameSign(funCurrent, funLeft))
                leftInterval = currentPoint;
            else
                rightInterval = currentPoint;
        }   
        if(!isRoot(function, currentPoint))
            System.out.println("Root is an inflection point.");
    }
    
    private void printBracketingFunction(int i, double leftInterval, 
            double rightInterval, double currentPoint, double funLeft, 
            double funRight, double funCurrent, double approximateError) {
        System.out.print("n: " + i + ", an: " + leftInterval + ", bn: " + 
                rightInterval + ", cn: " + currentPoint + ", f(an): " + funLeft + 
                ", f(bn): " + funRight + 
                ", f(cn): " + funCurrent);
        if(i != 0)
            System.out.print(", En: " + approximateError);
        System.out.println("");
    }
    
    public void doNewtonRaphson(int function, double currentPoint, double desiredError) {
        double funCurrent, deriveCurrent, approximateError = 1;
        double nextPoint = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            funCurrent = getFunctionOfX(function, currentPoint);
            deriveCurrent = getDerivativeFunctionOfX(function, currentPoint);
            nextPoint = currentPoint - (funCurrent / deriveCurrent);
            if(atMaximumIterations(i)) 
                return;
            // En = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((nextPoint - currentPoint) / nextPoint);
            printNewtonRaphson(i, currentPoint, funCurrent, deriveCurrent, approximateError);
            currentPoint = nextPoint;
        }
        if(!isRoot(function, nextPoint))
            System.out.println("Root is an inflection point.");
    }
    
    private void printNewtonRaphson(int i, double currentPoint, double funCurrent, 
            double deriveCurrent, double approximateError) {
        System.out.print("n: " + i + ", xn: " + currentPoint + ", f(xn): " + 
                funCurrent + ", f'(xn)" + deriveCurrent);
        if(i != 0)
            System.out.print(", En: " + approximateError);
        System.out.println("");
    }
    
    public void doSecant(int function, double previousPoint, double currentPoint, 
            double desiredError) {
        double nextPoint, funCurrent, funPrevious, funNext, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            funCurrent = getFunctionOfX(function, currentPoint);
            funPrevious = getFunctionOfX(function, previousPoint);
            // xi+1 = xi - (f(xi) * (xi-xi-1)) / (f(xi) - f(xi-1))
            nextPoint = currentPoint - ((funCurrent * (currentPoint - 
                    previousPoint)) / (funCurrent - funPrevious));
            funNext = getFunctionOfX(function, nextPoint);
            if(atMaximumIterations(i)) 
                return;
            // En = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((nextPoint - currentPoint) / nextPoint);
            printSecant(i, previousPoint, currentPoint, nextPoint, funPrevious, 
                    funCurrent, funNext, approximateError);
            previousPoint = currentPoint;
            currentPoint = nextPoint;
        }
        if(!isRoot(function, currentPoint))
            System.out.println("Root is an inflection point.");
    }
    
    private void printSecant(int i, double previousPoint, double currentPoint, 
            double nextPoint, double funPrevious, double funCurrent, 
            double funNext, double approximateError) {
        System.out.print("n: " + i + ", xi-1: " + previousPoint + ", xi: " + 
                currentPoint + ", xi+1: " + nextPoint + ", f(xi-1): " + 
                funPrevious + ", f(xi): " + funCurrent + ", f(xi+1): " + funNext);
        if(i != 0)
            System.out.print(", En: " + approximateError);
        System.out.println("");
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
            if(atMaximumIterations(i)) 
                return;
            // En = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((currentPoint - previousPoint) / currentPoint);
            funCurrent = getFunctionOfX(function, currentPoint);
            printBracketingFunction(i, leftInterval, rightInterval, currentPoint, 
                    funLeft, funRight, funCurrent, approximateError);
            if (sameSign(funCurrent, funLeft))
                leftInterval = currentPoint;
            else
                rightInterval = currentPoint;
        }
        if(!isRoot(function, currentPoint)) 
            System.out.println("Root is an inflection point.");
    }
    
    public void doModifiedSecant(int function, double currentPoint, double delta, 
            double desiredError) {
        double funCurrent, funSum, funNext, nextPoint, approximateError = 1;
        for(int i = 0; !(approximateError < desiredError); i++) {
            funCurrent = getFunctionOfX(function, currentPoint);
            funSum = getFunctionOfX(function, currentPoint + (delta * currentPoint));
            // xi+1 = xi - f(xi) * ((delta * xi) / (f(xi + delta * xi) - f(xi)))
            nextPoint = currentPoint - funCurrent * ((delta * currentPoint) / 
                    (funSum - funCurrent));
            funNext = getFunctionOfX(function, nextPoint);
            if(atMaximumIterations(i)) 
                return;
            // En = (current - previous) / current
            else if (i != 0)
                approximateError = Math.abs((nextPoint - currentPoint) / nextPoint);
            printModifiedSecant(i, currentPoint, nextPoint, funCurrent, funSum, 
                    funNext, approximateError);
            currentPoint = nextPoint;
        }
        if(!isRoot(function, currentPoint))
            System.out.println("Root is an inflection point.");
    }
    
    private void printModifiedSecant(int i, double currentPoint, double nextPoint, 
            double funCurrent, double funSum, double funNext, double approximateError) {
        System.out.print("n: " + i + ", xi: " + currentPoint + ", xi+1: " + 
                nextPoint + ", f(xi): " + funCurrent + ", f(xi+delta*xi): " + 
                funSum + ", f(xi+1): " + funNext);
        if(i != 0)
            System.out.print(", En: " + approximateError);
        System.out.println("");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Michael Trinh's Root Locator");
        // range in function 1: [0,4], 3 roots
        // range in function 2: [120,130], 1 root
        // desired error = 1%
        RootLocator rootLocator = new RootLocator();
        // Bisection
        System.out.println("\nBisection:");
        rootLocator.doBisection(1, 0, 4, .01);
        rootLocator.doBisection(2, 120, 130, .01);
        // Newton-Raphson
        System.out.println("\nNewton-Raphson:");
        rootLocator.doNewtonRaphson(1, 3, .01);
        rootLocator.doNewtonRaphson(2, 120, .01);
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
        rootLocator.doModifiedSecant(1, 3, .01, .01);
        rootLocator.doModifiedSecant(2, 120, .01, .01);
    }
    
}
