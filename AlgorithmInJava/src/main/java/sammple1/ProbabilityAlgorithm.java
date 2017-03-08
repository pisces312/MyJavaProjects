package sammple1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pisces
 */
public class ProbabilityAlgorithm {

    /**
     * 打点法计算PI
     * @param n
     * @return
     */
    public static double dartsPI(int n) {
        double x, y;
        int circleDartCount = 0;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            x = random.nextDouble();
            y = random.nextDouble();
            if (x * x + y * y <= 1) {
                circleDartCount++;
            }

        }
        return 4 * circleDartCount / (double) n;
    }

    /**
     * 打点法计算积分
     * 仅对[0,1]上 且0<=f(x)<=1有效
     */
    public static double dartsIntegrationBasic(Function f, int n) {
        double x, y;
        int dartCount = 0;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            x = random.nextDouble();
            y = random.nextDouble();
            if (y <= f.f(x)) {
                dartCount++;
            }

        }
        return dartCount / (double) n;
    }

    public static double dartsIntegration(final double a, final double b, final Function f1, int n) {
        double c = (f1.getMaxValue() - f1.getMinValue()) * (b - a);
        double d = f1.getMinValue() * (b - a);
        return d + c * dartsIntegrationBasic(new Function() {

            @Override
            public double f(double x) {
                return (1 / (f1.getMaxValue() - f1.getMinValue())) * (f1.f(a + (b - a) * x) - f1.getMinValue());
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public double getMaxValue() {
                return f1.getMaxValue();
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public double getMinValue() {
                return f1.getMinValue();
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        }, n);
    }

    /**
     * 使用平均数法求积分
     * @param a
     * @param b
     * @param f
     * @param n
     * @return
     */
    public static double averageIntegration(double a, double b, Function f, int n) {
        Random random = new Random();
        double y = 0, x, range = b - a;
        for (int i = 0; i < n; i++) {
            x = range * random.nextDouble() + a;
            y += f.f(x);
        }
        return range * y / (double) n;
    }
}
