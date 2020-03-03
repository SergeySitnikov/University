import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class HalfDivision implements Solver {
    private DoubleUnaryOperator function;
    private DoubleUnaryOperator derivative;
    private double[] xOfDerivativeEqualsZero;
    private List<X> answer;


    public HalfDivision(DoubleUnaryOperator function, DoubleUnaryOperator derivative, double[] xOfDerivativeEqualsZero) {
        this.function = function;
        this.derivative = derivative;
        this.xOfDerivativeEqualsZero = xOfDerivativeEqualsZero;
        answer = new ArrayList<>();
    }

    public void solve() {
        boolean[] borders = isCorrectBorders();
        findBordersOfSolutions(borders);
        narrowBorders();
        for (X x : answer) {
            dividedByHalf(x);
            System.out.println(x.value);
        }

    }

    private void goldenSection() {

    }



    private void dividedByHalf(X x) {
        while (x.rightBorder - x.leftBorder > 0.01) {
            double tmp = (x.leftBorder + x.rightBorder) / 2;
            if (getSignOfFunction(tmp) != getSignOfFunction(x.leftBorder)) {
                x.rightBorder = tmp;
            } else {
                x.leftBorder = tmp;
            }
        }
        x.value = x.leftBorder;
    }


    private void narrowBorders() {
        for (X x : answer) {
            if (x.rightBorder - x.leftBorder > 1) {
               if (getSignOfFunction(x.rightBorder - 1) == getSignOfFunction(x.rightBorder)) {
                   x.rightBorder--;
               }
               if (getSignOfFunction(x.leftBorder + 1) == getSignOfFunction(x.leftBorder)) {
                   x.leftBorder++;
               }
            }
        }
    }

    private void findBordersOfSolutions(boolean[] generalBorder) {
        for (int i = 1; i < xOfDerivativeEqualsZero.length; i++) {
            if (getSignOfFunction(xOfDerivativeEqualsZero[i - 1]) != getSignOfFunction(xOfDerivativeEqualsZero[i])) {
                answer.add(new X(xOfDerivativeEqualsZero[i - 1], xOfDerivativeEqualsZero[i]));
            }
        }
        if (generalBorder[0]) {
            answer.add(new X(getBorder(xOfDerivativeEqualsZero[0], -1.0, getSignOfFunction(xOfDerivativeEqualsZero[0])), xOfDerivativeEqualsZero[0]));
        }
        if (generalBorder[1]) {
            int size = xOfDerivativeEqualsZero.length - 1;
            answer.add(new X(xOfDerivativeEqualsZero[size], getBorder(xOfDerivativeEqualsZero[size], 1.0,
                    getSignOfFunction(xOfDerivativeEqualsZero[size]))));

        }

    }

    private double getBorder(double startX, double delta, boolean signOfXFunction) {
        while (true) {
            startX += delta;
            if (getSignOfFunction(startX) != signOfXFunction) {
                return startX;
            }
        }
    }



    private boolean[] isCorrectBorders() {
        boolean[] isCorrect = {true, true};
        if (getSignOfFunction(xOfDerivativeEqualsZero[0])) {
            if (!getSignOfDerivative(xOfDerivativeEqualsZero[0], 1.0, false)) {
                isCorrect[0] = false;
            }
        } else {
            if (getSignOfDerivative(xOfDerivativeEqualsZero[0], 1.0, false)) {
                isCorrect[0] = false;
            }
        }
        if (xOfDerivativeEqualsZero.length > 1) {
            if (getSignOfFunction(xOfDerivativeEqualsZero[xOfDerivativeEqualsZero.length - 1])) {
                if (getSignOfDerivative(xOfDerivativeEqualsZero[xOfDerivativeEqualsZero.length - 1], 1.0, true)) {
                    isCorrect[1] = false;
                }
            } else {
                if (!getSignOfDerivative(xOfDerivativeEqualsZero[xOfDerivativeEqualsZero.length - 1], 1.0, true)) {
                    isCorrect[1] = false;
                }
            }
        }
//        for (int i = 1; i < xOfDerivativeEqualsZero.length - 1; i++) {
//            if (getSignOfFunction(i) != getSignOfFunction(i - 1)) {
//
//            }
//        }
        return isCorrect;
    }

    private boolean getSignOfFunction(double x) {
        return function.applyAsDouble(x) > 0;
    }

    private boolean getSignOfDerivative(double x, double delta, boolean isRight) {
        if (!isRight) delta = -delta;
        return derivative.applyAsDouble(x + delta) > 0;
    }

    private class X {
        private double leftBorder;
        private double rightBorder;
        private double value;

        private X(double leftBorder, double rightBorder) {
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }
    }

}
