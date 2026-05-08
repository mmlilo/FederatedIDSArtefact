/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.app.federatedidsartefact;

/**
 *
 * @author mthobi
 * This module monitors performance and flags drift when accuracy degrades
 * 
 */


public class DriftDetector {

    private double threshold;

    public DriftDetector(double threshold) {
        this.threshold = threshold;
    }

    public boolean detectDrift(double accuracy) {
        return accuracy < threshold;
    }
}

