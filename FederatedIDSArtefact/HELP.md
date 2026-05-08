## 4. `HELP.md`

```markdown
# Help and Troubleshooting Guide

## 1. Java GUI Does Not Launch

### Problem

The artefact does not open.

### Possible Causes

- Java is not installed
- Maven is not installed
- Wrong folder location
- Build errors

### Fix

Run:

```powershell
java -version
mvn -version

Then launch manually:

mvn exec:java "-Dexec.mainClass=com.registration.app.federatedidsartefact.ui.AppLauncher"

2. ML Pipeline Does Not Run
Problem

run_ids_pipeline.bat fails.

Possible Causes
Python is not installed
Python packages are missing
Dataset file is missing
Fix

Install dependencies:

pip install pandas numpy scikit-learn matplotlib

Confirm the dataset exists:

Get-ChildItem data

The file should include:

KDDTrain.txt

3. Results Tab Shows “metrics.json not found”
Cause

The ML pipeline has not been run yet.

Fix

Run:

run_ids_pipeline.bat

Then reopen or refresh the Results tab.

4. Charts Do Not Display
Cause

Charts have not been generated.

Fix

Run:

python ml_engine\generate_charts.py

Then click:

Load Visual Charts

inside the Results tab.

5. Python Says Label Column Not Found
Cause

The selected dataset does not contain the expected label column.

Fix

Check dataset columns:

python -c "import pandas as pd; df=pd.read_csv(r'data\your_dataset.csv', nrows=1); print(list(df.columns))"

Use the correct label column when running the ML script.

6. Very High Accuracy or F1-score
Explanation

Some IDS datasets are highly imbalanced or contain dominant attack classes. High weighted scores can occur when the model performs very well on majority classes.

Always inspect:

confusion matrix
per-class F1-score
attack category distribution

7. Rare Attack Classes Have Low F1-score
Explanation

Rare classes such as spy, ftp_write, or rootkit may have very few samples. The model may struggle to learn these patterns.

This is a known class imbalance challenge in IDS datasets.

8. Laptop Becomes Slow
Possible Causes
Large datasets
NetBeans indexing
OneDrive syncing
Multiple Java or Python processes
Fix

Close unnecessary applications.

Check Java/Python processes:

Get-Process java,python -ErrorAction SilentlyContinue

If necessary, stop them using Task Manager.

9. GitHub Push Fails
Common Causes
Wrong remote URL
Authentication issue
Repository name mismatch

Check remote:

git remote -v

Expected format:

https://github.com/mmlilo/FederatedIDSArtefact.git

10. Recommended Clean Run

For a clean artefact demonstration:

run_ids_pipeline.bat
launch_gui.bat

Then in the GUI:

Open Results tab
Click Evaluate Detection Results
Click Load Visual Charts

---

## Also create these two `.bat` files

### `launch_gui.bat`

```bat
@echo off
echo Starting Federated IDS Artefact...
mvn exec:java "-Dexec.mainClass=com.registration.app.federatedidsartefact.ui.AppLauncher"
pause
run_ids_pipeline.bat
@echo off

echo ============================================
echo Running FL-IDS ML Evaluation Pipeline
echo ============================================

python ml_engine\train_evaluate.py "data\KDDTrain.txt" "label"

echo.
echo Generating charts...
python ml_engine\generate_charts.py

echo.
echo Pipeline completed successfully.
pause