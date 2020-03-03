public class Main {
    public static void main(String[] args) {
        double[] x = {1.5, 1.55, 1.6, 1.65, 1.7, 1.75, 1.8, 1.85, 1.9, 1.95, 2.0, 2.05, 2.1};
        double[] y = {15.132, 17.442, 20.393, 23.994, 28.16, 32.812, 37.857, 43.189, 48.689, 54.225, 59.653, 64.817, 69.55};
        Starter starter = new Starter(x, y);
        for (int i = 1; i < 30 ; i++) {
            double[] doubles = starter.find(2 - i * 0.013);
            System.out.printf("x = %.3f\n",(2 - i * 0.013));
            System.out.printf("Gauss y =  %.4f\n", doubles[0]);
            System.out.printf("Bessel y = %.4f\n", doubles[1]);
            System.out.printf("Stirling y = %.4f\n", doubles[2]);
            System.out.printf("Second Gauss y = %.4f\n", doubles[3]);
            System.out.println("----------------------");
        }

    }

}
