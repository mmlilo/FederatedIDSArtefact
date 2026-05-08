@echo off
echo Running FL-IDS ML Evaluation Pipeline...

python ml_engine\train_evaluate.py "data\KDDTrain.txt" "label"

echo Generating charts...
python ml_engine\generate_charts.py

echo Pipeline completed successfully.
pause