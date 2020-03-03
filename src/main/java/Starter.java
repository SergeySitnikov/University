public class Starter implements Finder {
    private static final double E = 0.05;

    private double[][] table;

    public Starter(double[] x, double[] y) {
        table = new double[x.length][5];
        for (int i = 0; i < x.length; i++) {
            table[i][0] = x[i];
            table[i][1] = y[i];

        }
    }

    public double[] find(double x) {
        double x0;
        double t;
        double[] solution = new double[4];
        for (int i = 0; i < table.length; i++) {
            if (x - table[i][0] < E) {
                x0 = table[i][0];
                t = (x - x0) / E;
                solution[0] = this.Gauss(t, i);
                solution[1] = this.Bessel(t, i);
                solution[2] = this.Stirling(t, i);
                solution[3] = this.SecondGauss(t, i);
                break;
            }
        }


        return solution;
    }

    private double Gauss(double t, int i) { //i is position of x0 in first column
        if (table[i + 1][2] == 0) this.fillCellInSecondColumn(i + 1);
        if (table[i + 1][3] == 0) this.fillCellInThirdColumn(i + 1);
        if (table[i + 2][4] == 0) this.fillCellInFourthColumn(i + 2);

        return table[i][1]
                + t * table[i + 1][2]
                + t * (t - 1) / 2 * table[i + 1][3]
                + t * (t - 1) * (t + 1) / 6 * table[i + 2][4];
    }

    private double Bessel(double t, int i) {
        if (table[i + 1][2] == 0) this.fillCellInSecondColumn(i + 1);
        if (table[i + 1][3] == 0) this.fillCellInThirdColumn(i + 1);
        if (table[i + 2][3] == 0) this.fillCellInThirdColumn(i + 1);
        if (table[i + 2][4] == 0) this.fillCellInFourthColumn(i + 1);

        return (table[i][1] + table[i + 1][1]) / 2
                + (t - 0.5) * table[i + 1][2]
                + t * (t - 1) / 2 * (table[i + 1][3] - table[i + 2][3]) / 2
                + t * (t - 0.5) * (t - 1) / 6 * table[i + 2][4];
    }

    private double Stirling(double t, int i) {
        if (table[i][2] == 0) this.fillCellInSecondColumn(i);
        if (table[i + 1][2] == 0) this.fillCellInSecondColumn(i + 1);
        if (table[i + 1][3] == 0) this.fillCellInThirdColumn(i + 1);
        if (table[i + 1][4] == 0) this.fillCellInFourthColumn(i + 1);
        if (table[i + 2][4] == 0) this.fillCellInFourthColumn(i + 2);

        return table[i][1]
                + t * (table[i][2] + table[i + 1][2]) / 2
                + t / 2 * table[i + 1][3]
                + t * (t + 1) * (t - 1) / 12 * (table[i + 1][4] + table[i + 2][4]);
    }

    private double SecondGauss(double t, int i) {
        if (table[i][2] == 0) this.fillCellInSecondColumn(i);
        if (table[i + 1][3] == 0) this.fillCellInThirdColumn(i + 1);
        if (table[i + 1][4] == 0) this.fillCellInFourthColumn(i + 1);
        return table[i][1]
                + t * table[i][2]
                + t * (t - 1) / 2 * table[i + 1][3]
                + t * (t - 1) * (t + 1)/ 6 * table[i + 1][4];
    }

    private void fillCellInSecondColumn(int row) {
        table[row][2] = table[row][1] - table[row - 1][1];
    }

    private void fillCellInThirdColumn(int row) {
        if (table[row][2] == 0) this.fillCellInSecondColumn(row);
        if (table[row - 1][2] == 0) this.fillCellInSecondColumn(row - 1);
        table[row][3] = table[row][2] - table[row - 1][2];
    }

    private void fillCellInFourthColumn(int row) {
        if (table[row][3] == 0) this.fillCellInThirdColumn(row);
        if (table[row - 1][3] == 0) this.fillCellInThirdColumn(row - 1);
        table[row][4] = table[row][3] - table[row - 1][3];
    }


}
