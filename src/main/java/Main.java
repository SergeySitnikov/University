import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.function.DoubleUnaryOperator;

public class Main {
    public static void main(String[] args) {
        DoubleUnaryOperator function = null;
        double[] Xs1 = new double[1];
        double[] Xs2 = new double[2];
        int choice = 2;
        HalfDivision h = null;
        String[] names = {"arctg(x) ", "1/(3x^2)", "x^4 - x - 1", "0", "2^x * (x-1)^2 - 1", "0", "tg^3(x)", "x - 1"};
        double[] maxValues = {4, 1.7, 2, 1.2};
        double[] minValues = {-4, -1.7, -6, -1.2};
        XYSeries first = new XYSeries(names[choice * 2]);
        XYSeries second = new XYSeries(names[choice * 2 + 1]);
        XYSeriesCollection dataset = new XYSeriesCollection();

        switch (choice) {
            case 0:
                function = x -> Math.atan(x) - 1 / (3 * Math.pow(x, 3));
                Xs1[0] = -1.5;
                h = new HalfDivision(function, Xs1);
                XYSeries rightGraph = new XYSeries(names[1] + " ");
                for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                    if (i < -0.4) {
                        first.add(i, Math.atan(i));
                        second.add(i, 1 / (3 * Math.pow(i, 3)));
                    }
                    if (i > 0.4) {
                        first.add(i, Math.atan(i));
                        rightGraph.add(i, 1 / (3 * Math.pow(i, 3)));
                    }
                }
                dataset.addSeries(first);
                dataset.addSeries(second);
                dataset.addSeries(rightGraph);
                break;
            case 1:
                function = x -> Math.pow(x, 4) - x - 1;
                Xs1[0] = 0.693;
                h = new HalfDivision(function, Xs1);
                for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                    first.add(i, function.applyAsDouble(i));
                }
                dataset.addSeries(first);
                break;
            case 2:
                function = x -> (x - 1) * (x - 1) * Math.pow(2, x) - 1;
                Xs2[0] = -1.885;
                Xs2[1] = 1;
                h = new HalfDivision(function, Xs2);
                for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                    if (i > 0.99 && i < 1.001) {
                        continue;
                    }
                    first.add(i, function.applyAsDouble(i));
                }
                dataset.addSeries(first);
                break;
            case 3:
                function = x -> Math.pow(Math.tan(x), 3) - x + 1;
                Xs2[0] = -0.47;
                Xs2[1] = 0.47;
                h = new HalfDivision(function, Xs2);
                for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                    first.add(i, Math.pow(Math.tan(i), 3));
                    second.add(i, i - 1);
                }
                dataset.addSeries(first);
                dataset.addSeries(second);
                break;
        }

        h.solve();


        if (choice == 0) {
            Xs1[0] = 0.1;
            h = new HalfDivision(function, Xs1);
            h.solve();
        }

        XYSeries OX = new XYSeries("Ox");
        XYSeries OY = new XYSeries("Oy");
        for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
            OX.add(i, 0);
            OY.add(0, i);
        }
        dataset.addSeries(OX);
        dataset.addSeries(OY);
        JFreeChart chart = ChartFactory
                .createXYLineChart("", "x", "y",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("Lab_6");
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(700, 700);
        frame.show();

    }
}

