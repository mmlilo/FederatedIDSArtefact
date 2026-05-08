



package com.registration.app.federatedidsartefact;

/**
 *
 * @author mthobi
 * SIMPLE IDS MODEL - This is a lightweight linear classifier suitable for resource-constrained IoT gateways.
 */


public class Model {

    public double[] weights;
    public double bias;

    public Model(int numFeatures) {
        weights = new double[numFeatures];
        bias = 0.0;
    }

    public int predict(double[] x) {
        double sum = bias;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * x[i];
        }
        return sum >= 0 ? 1 : 0;
    }

    //model training method
    public void train(double[][] X, int[] y, double lr, int epochs) {
        for (int e = 0; e < epochs; e++) {
            for (int i = 0; i < X.length; i++) {
                int pred = predict(X[i]);
                int error = y[i] - pred;

                for (int j = 0; j < weights.length; j++) {
                    weights[j] += lr * error * X[i][j];
                }
                bias += lr * error;
            }
        }
    }
}

