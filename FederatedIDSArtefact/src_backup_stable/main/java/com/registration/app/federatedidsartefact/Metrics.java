/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.app.federatedidsartefact;

/**
 *
 * @author mthobi
 * Accuracy addressed in this class
 */


public class Metrics {

    public static double accuracy(Model model, double[][] X, int[] y) {
        int correct = 0;
        for (int i = 0; i < X.length; i++) {
            if (model.predict(X[i]) == y[i]) {
                correct++;
            }
        }
        return (double) correct / X.length;
    }
}
