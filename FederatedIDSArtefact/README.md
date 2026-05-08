# Federated IDS Artefact

## Overview

This project is a Federated Intrusion Detection System (FL-IDS) artefact developed for MSc Computer Science research. The artefact integrates a Java Swing graphical interface with a Python-based machine learning engine for intrusion detection evaluation.

It implements a Federated Learning-based Intrusion Detection System (FL-IDS) integrating:

- Random Forest machine learning
- IDS evaluation metrics
- Visual analytics
- Multi-class intrusion detection
- NSL-KDD dataset evaluation
- Java Swing visual interface


The system supports:

- Dataset partitioning or loading and preview
- Federated IDS simulation
- Intrusion classification: IID and Non-IID data partitioning
- Random Forest-based intrusion classification
- Accuracy, precision, recall and F1-score computation
- Confusion matrix generation
- Attack category analysis
- Visual analytics including heatmaps and bar charts or Automated chart generation

## Technologies Used

- Java
- Java Swing
- Apache Maven
- Python
- Pandas
- NumPy
- Scikit-learn
- Matplotlib
- NSL-KDD intrusion detection dataset


## Project Structure

```text
FederatedIDSArtefact/
│
├── src/                     Java source code
├── data/                    Sample IDS datasets
├── ml_engine/               Python machine learning engine
│   ├── train_evaluate.py
│   ├── generate_charts.py
│   └── charts/
├── README.md
├── INSTALLATION_GUIDE.md
├── USER_GUIDE.md
├── HELP.md
├── launch_gui.bat
├── run_ids_pipeline.bat
└── pom.xml

## Running the Project

### Java GUI

```bash
mvn clean compile exec:java "-Dexec.mainClass=com.registration.app.federatedidsartefact.ui.AppLauncher"
```

### ML Evaluation Pipeline

```bash
run_ids_pipeline.bat
```

## Dataset

- NSL-KDD intrusion detection dataset



Main Features
Java GUI

The Java interface provides:

Dataset selection
Partition generation
Federated training simulation
Results display
Visual chart loading
Python ML Engine

The Python engine performs:

Random Forest training
Multi-class intrusion classification
Confusion matrix computation
Attack summary generation
Visual chart generation
Quick Start
1. Run the ML evaluation pipeline
run_ids_pipeline.bat

This generates:

ml_engine/metrics.json
ml_engine/predictions.csv
ml_engine/confusion_matrix.csv
ml_engine/attack_summary.csv
ml_engine/charts/
2. Launch the Java GUI
launch_gui.bat

Or run manually:

mvn exec:java "-Dexec.mainClass=com.registration.app.federatedidsartefact.ui.AppLauncher"
Dataset Used

The system currently evaluates the NSL-KDD dataset using the KDDTrain.txt file placed in the data/ folder.

The dataset is used for multi-class intrusion detection and includes categories such as:

normal
neptune
smurf
satan
ipsweep
portsweep
nmap
back
teardrop
rootkit
guess_passwd
buffer_overflow

## Author
Mthobi Mlilo 
N02427294S
MSc Computer Science
National University of Science and Technology (NUST)