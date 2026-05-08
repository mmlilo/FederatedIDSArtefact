/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.registration.app.federatedidsartefact;

/**
 *29/01/2026
 * @author mthobi
 */
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FederatedIDSArtefact
{

	public static void main(String[] args)
	{
		String path = "C:/Users/mthob/OneDrive/Documents/NetBeansProjects/FederatedIDSArtefact/data/iot_dataset.csv";
		System.out.println("Reached main() start");
		System.out.println("JVM working directory: " + System.getProperty("user.dir"));
		//System.out.println("");
		System.out.println("Starting Federated IDS Artefact...\n");
		System.out.println("Dataset exists? " + new java.io.File(path).exists());
// ===============================
		// 1. LOAD DATASET (THIS IS WHERE YOUR CODE GOES)
		// ===============================
		//String path = "data/iot_dataset.csv";
		//String path = "C:/Users/mthob/OneDrive/Documents/NetBeansProjects/FederatedIDSArtefact/data/iot_dataset.csv";

		List<double[]> features;
		List<Integer> labels;

		try
			{
			features = DataLoader.loadFeatures(path);
			labels = DataLoader.loadLabels(path);
			} catch (Exception e)
			{
			System.err.println("❌ Failed to load dataset. Check file path: " + path);
			e.printStackTrace();
			return; // STOP execution safely
			}

		// ===============================
		// 2. CREATE FEDERATED CLIENTS
		// ===============================
		int numClients = 3;
		int total = features.size();
		int splitSize = total / numClients;

		List<Client> clients = new ArrayList<>();

		for (int i = 0; i < numClients; i++)
			{

			double[][] X = new double[splitSize][];
			int[] y = new int[splitSize];

			for (int j = 0; j < splitSize; j++)
				{
				X[j] = features.get(i * splitSize + j);
				y[j] = labels.get(i * splitSize + j);
				}

			boolean malicious = (i == 0); // first client malicious
			clients.add(new Client(X, y, malicious));

			System.out.println(
				"Client " + (i + 1)
				+ (malicious ? " (MALICIOUS)" : " (BENIGN)")
				+ " samples: " + splitSize
			);
			}
		
		
		

		

		// ===============================
		// 3. DRIFT DETECTOR
		// ===============================
		DriftDetector driftDetector = new DriftDetector(0.95); // high threshold to force demo

		// ===============================
		// 4. FEDERATED TRAINING ROUNDS
		// ===============================
		for (int round = 1; round <= 3; round++)
			{

			List<Model> localModels = new ArrayList<>();

			for (Client c : clients)
				{
				localModels.add(c.trainLocalModel());
				}

			Model globalModel = FederatedServer.federatedAverage(localModels);

			double acc = Metrics.accuracy(
				globalModel,
				features.toArray(new double[0][]),
				labels.stream().mapToInt(i -> i).toArray()
			);

			System.out.println("Round " + round + " accuracy: " + acc);

			if (driftDetector.detectDrift(acc))
				{
				System.out.println("⚠️ Concept drift detected! Triggering adaptation...");

				for (Client c : clients)
					{
					c.localModel = new Model(c.X[0].length);
					}

				System.out.println("✅ Model adaptation completed.");
				}
			}

		System.out.println("\nFederated IDS Artefact Execution Completed.");
	}
}
