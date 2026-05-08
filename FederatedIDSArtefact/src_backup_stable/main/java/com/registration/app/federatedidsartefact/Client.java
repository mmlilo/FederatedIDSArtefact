/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.app.federatedidsartefact;
import java.util.Random;
/**
 *
 * @author mthobi Local training - Each client represents an IoT gateway
 * training on private local data
 */
public class Client {

    public Model localModel;
    public double[][] X;
    public int[] y;
    public boolean isMalicious = false;

    public Client(double[][] X, int[] y, boolean isMalicious) {
        this.X = X;
        this.y = y;
        this.isMalicious = isMalicious;
        this.localModel = new Model(X[0].length);
    }

    public Model trainLocalModel() {

        if (isMalicious) {
            System.out.println("⚠️ Malicious client sending poisoned update...");
            poisonModel();
            return localModel;
        }

        localModel.train(X, y, 0.01, 10);
        return localModel;
    }

    private void poisonModel() {
        Random rand = new Random();
        for (int i = 0; i < localModel.weights.length; i++) {
            localModel.weights[i] = rand.nextDouble() * 10 - 5;
        }
        localModel.bias = rand.nextDouble() * 10 - 5;
    }
}