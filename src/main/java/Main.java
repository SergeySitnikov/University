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
        DoubleUnaryOperator function = null; /*x -> Math.atan(x) - 1 / (3 * Math.pow(x, 3))*/;
        double[] Xs1 = new double[1];
        double[] Xs2 = new double[2];
        int choice = 3;
        HalfDivision h = null;
        switch (choice) {
            case 0:
                function = x -> Math.atan(x) - 1 / (3 * Math.pow(x, 3));
                Xs1[0] = -1.5;
                h = new HalfDivision(function, Xs1);
                break;
            case 1:
                function = x -> Math.pow(x, 4) - x - 1;
                Xs1[0] = 0.693;
                h = new HalfDivision(function, Xs1);
                break;
            case 2:
                function = x -> (x - 1) * (x - 1) * Math.pow(2, x) - 1;
                Xs2[0] = -1.885;
                Xs2[1] = 1;
                h = new HalfDivision(function, Xs2);
                break;
            case 3:
                function = x -> Math.pow(Math.tan(x), 3) - x + 1;
                Xs2[0] = -0.47;
                Xs2[1] = 0.47;
                h = new HalfDivision(function, Xs2);
                break;
        }

        h.solve();
        String[] names = {"arctg(x); y = 1/(3*x^3)", "x^4 - x - 1", "(x-1)^2*2^x - 1", "tg^3(x); y = x - 1"};
        double[] maxValues = {4, 1.7, 2, 1.2};
        double[] minValues = {-4, -1.7, -6, -1.2};

        XYSeries series = new XYSeries("series1");
        XYSeries series1;
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries xEqualsZeroSeries = new XYSeries("zero");
        if (choice == 0) {
            Xs1[0] = 0.1;
            h = new HalfDivision(function, Xs1);
            h.solve();
            series1 = new XYSeries(names[0]);
            XYSeries series2 = new XYSeries("left");
            for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                if (i < -0.4) {
                    series.add(i, Math.atan(i));
                    series1.add(i, 1 / (3 * Math.pow(i, 3)));
                }
                if (i > 0.4) {
                    series.add(i, Math.atan(i));
                    series2.add(i, 1 / (3 * Math.pow(i, 3)));
                }
            }
            dataset.addSeries(series);
            dataset.addSeries(series1);
            dataset.addSeries(series2);
        } else {
            if (choice == 3) {
                series1 = new XYSeries(names[3]);
                for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                    series.add(i, Math.pow(Math.tan(i), 3));
                    series1.add(i, i - 1);
                }
                dataset.addSeries(series);
                dataset.addSeries(series1);
            } else {
                for (double i = minValues[choice]; i < maxValues[choice]; i += 0.1) {
                    xEqualsZeroSeries.add(i, 0);
                    series.add(i, function.applyAsDouble(i));
                }
                dataset.addSeries(series);
            }
        }
        dataset.addSeries(xEqualsZeroSeries);
        JFreeChart chart = ChartFactory
                .createXYLineChart("y = " + names[choice], "x", "y",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, true, true);
        JFrame frame =
                new JFrame("Lab_6");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(700, 700);
        frame.show();

    }
}

