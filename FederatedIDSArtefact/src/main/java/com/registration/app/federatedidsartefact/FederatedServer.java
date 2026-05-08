/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.app.federatedidsartefact;

/**
 *
 * @author mthobi
 * Aggregation - Only model parameters are aggregated; raw data never leaves the clients
 * that spells out the data safety and security in this model training.
 */


import java.util.*;

public class FederatedServer {

    public static Model federatedAverage(List<Model> models) {
        int numFeatures = models.get(0).weights.length;
        Model globalModel = new Model(numFeatures);

        for (Model m : models) {
            for (int i = 0; i < numFeatures; i++) {
                globalModel.weights[i] += m.weights[i];
            }
            globalModel.bias += m.bias;
        }

        int n = models.size();
        for (int i = 0; i < numFeatures; i++) {
            globalModel.weights[i] /= n;
        }
        globalModel.bias /= n;

        return globalModel;
    }
}

