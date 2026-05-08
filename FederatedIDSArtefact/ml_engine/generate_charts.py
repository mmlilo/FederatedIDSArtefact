import json
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from pathlib import Path

BASE = Path("ml_engine")
OUT = BASE / "charts"
OUT.mkdir(exist_ok=True)

with open(BASE / "metrics.json", "r") as f:
    metrics = json.load(f)

classes = metrics["classes"]
cm = np.array(metrics["confusion_matrix"])

# 1. Metrics bar chart
metric_names = ["accuracy", "precision", "recall", "f1_score"]
metric_values = [metrics[m] for m in metric_names]

plt.figure(figsize=(8, 5))
plt.bar(metric_names, metric_values)
plt.ylim(0, 1.05)
plt.title("Overall Random Forest IDS Performance")
plt.ylabel("Score")
plt.xlabel("Metric")
plt.tight_layout()
plt.savefig(OUT / "overall_metrics_bar.png", dpi=300)
plt.close()

# 2. Confusion matrix heatmap
plt.figure(figsize=(14, 12))
plt.imshow(cm, interpolation="nearest")
plt.title("Confusion Matrix Heatmap")
plt.colorbar()
plt.xticks(range(len(classes)), classes, rotation=90, fontsize=7)
plt.yticks(range(len(classes)), classes, fontsize=7)
plt.xlabel("Predicted Class")
plt.ylabel("Actual Class")
plt.tight_layout()
plt.savefig(OUT / "confusion_matrix_heatmap.png", dpi=300)
plt.close()

# 3. Attack summary bar chart
attack_summary = pd.read_csv(BASE / "attack_summary.csv", index_col=0)
attack_summary = attack_summary.sort_values("count", ascending=False)

plt.figure(figsize=(12, 6))
plt.bar(attack_summary.index.astype(str), attack_summary["count"])
plt.title("Predicted Attack Category Distribution")
plt.xlabel("Attack Category")
plt.ylabel("Count")
plt.xticks(rotation=75, ha="right")
plt.tight_layout()
plt.savefig(OUT / "attack_distribution_bar.png", dpi=300)
plt.close()

# 4. Per-class F1-score bar chart
report = metrics["classification_report"]
f1_data = {
    cls: report[cls]["f1-score"]
    for cls in classes
    if cls in report
}

f1_series = pd.Series(f1_data).sort_values(ascending=False)

plt.figure(figsize=(12, 6))
plt.bar(f1_series.index.astype(str), f1_series.values)
plt.title("Per-Class F1-Score")
plt.xlabel("Class")
plt.ylabel("F1-score")
plt.ylim(0, 1.05)
plt.xticks(rotation=75, ha="right")
plt.tight_layout()
plt.savefig(OUT / "per_class_f1_bar.png", dpi=300)
plt.close()

# 5. Actual vs predicted histogram/bar comparison
predictions = pd.read_csv(BASE / "predictions.csv")

actual_counts = predictions["actual"].value_counts()
pred_counts = predictions["predicted"].value_counts()

all_labels = sorted(set(actual_counts.index).union(set(pred_counts.index)))
actual_vals = [actual_counts.get(x, 0) for x in all_labels]
pred_vals = [pred_counts.get(x, 0) for x in all_labels]

x = np.arange(len(all_labels))
width = 0.4

plt.figure(figsize=(14, 6))
plt.bar(x - width/2, actual_vals, width, label="Actual")
plt.bar(x + width/2, pred_vals, width, label="Predicted")
plt.title("Actual vs Predicted Class Distribution")
plt.xlabel("Class")
plt.ylabel("Count")
plt.xticks(x, all_labels, rotation=75, ha="right")
plt.legend()
plt.tight_layout()
plt.savefig(OUT / "actual_vs_predicted_distribution.png", dpi=300)
plt.close()

print("Charts generated successfully in ml_engine/charts")