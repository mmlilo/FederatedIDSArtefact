
/* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registration.app.federatedidsartefact;

/**
 * 28/01/2026
 *
 * @author mthobi Data loading (CSV--> Memory) - The artefact uses CSV-based
 * data-sets for transparency and reproducibility
 */

import java.io.*;

import java.util.*;

public class DataLoader {

	public static List<double[]> loadFeatures(String filePath) throws IOException {
		List<double[]> features = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;

		// Skip header
		br.readLine();

		while ((line = br.readLine()) != null)
			{
			String[] parts = line.split(",");
			double[] row = new double[parts.length - 1];

			for (int i = 0; i < parts.length - 1; i++)
				{
				row[i] = Double.parseDouble(parts[i]);
				}
			features.add(row);
			}
		br.close();
		return features;
	}

	public static List<Integer> loadLabels(String filePath) throws IOException {
		List<Integer> labels = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;

		br.readLine(); // header
		while ((line = br.readLine()) != null)
			{
			String[] parts = line.split(",");
			labels.add(Integer.parseInt(parts[parts.length - 1]));
			}
		br.close();
		return labels;
	}
}
