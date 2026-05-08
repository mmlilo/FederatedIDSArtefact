---

##  `INSTALLATION_GUIDE LINE`

```markdown
# Installation Guide

## 1. System Requirements

The artefact requires the following software:

- Windows 10 or later
- Java JDK 17 or later
- Apache Maven
- Python 3.10 or later
- Git

Recommended hardware:

- Minimum 8GB RAM
- At least 2GB free disk space
- Internet connection for dependency installation

## 2. Required Software Installation

### 2.1 Install Java

Confirm Java is installed:

```powershell
java -version

If Java is not installed, install a JDK such as JDK 17 or later.

2.2 Install Maven

Confirm Maven is installed:

mvn -version

If Maven is missing, install Apache Maven and ensure it is added to the system PATH.

2.3 Install Python

Confirm Python is installed:

python --version

If Python is missing, install Python 3.10 or later.

3. Install Python Dependencies

From the project root folder, run:

pip install pandas numpy scikit-learn matplotlib

These libraries are required by the ML engine.

4. Project Folder Location

The project root should contain:

pom.xml
src/
data/
ml_engine/
README.md
INSTALLATION_GUIDE.md
USER_GUIDE.md
HELP.md

Example path:

C:\Users\YOUR_NAME\OneDrive\Documents\NetBeansProjects\FederatedIDSArtefact

5. Dataset Setup

Place the dataset file in:

data/KDDTrain.txt

The current ML pipeline expects this file.

6. Running the ML Pipeline

From the project root, run:

run_ids_pipeline.bat

This executes:

ml_engine/train_evaluate.py
ml_engine/generate_charts.py

Expected outputs:

ml_engine/metrics.json
ml_engine/predictions.csv
ml_engine/confusion_matrix.csv
ml_engine/attack_summary.csv
ml_engine/charts/
7. Running the Java GUI

Run:

launch_gui.bat

Or manually:

mvn exec:java "-Dexec.mainClass=com.registration.app.federatedidsartefact.ui.AppLauncher"

8. Verification

After launch:

Open the Results tab
Click Evaluate Detection Results
Confirm that Random Forest IDS metrics appear
Click Load Visual Charts
Confirm that the charts are displayed

9. Notes

The Java GUI reads the ML results from:

ml_engine/metrics.json

The charts are read from:

ml_engine/charts/

Therefore, the ML pipeline should be run before loading results in the GUI.