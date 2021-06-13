import java.math.*;

public class Optimazer {
    private double[] a;

    public double[] getA() {
        return a;
    }

    public Optimazer(Glass[] gl, int n, double lambda, double E, double D) {
        int k = 2;
        a = new double[k];
        double f = 0;
        for (int j = 0; j < 1000; ++j) {
            for (int i = k - 1; i < n - 1; ++i) {
                f = 0;
                for (int l = 0; l < k; ++l){
                    f += a[l] * (gl[i - l].getAskPrice()[0] - E) / Math.sqrt(D);
                }
                for (int l = 0; l < k; ++l){
                    a[l] -= 2 * lambda * (Math.tanh(f) * (gl[i + 1].getAskPrice()[0] - gl[i].getAskPrice()[0]) -
                            Math.abs(gl[i + 1].getAskPrice()[0] - gl[i].getAskPrice()[0])) *
                            (gl[i + 1].getAskPrice()[0] - gl[i].getAskPrice()[0]) / Math.cosh(f) / Math.cosh(f) / D *
                            (gl[i - l].getAskPrice()[0] - E) / Math.sqrt(D);
                }
            }
        }
    }
}