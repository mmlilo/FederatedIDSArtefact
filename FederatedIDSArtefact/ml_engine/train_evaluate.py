import sys
import json
import pandas as pd
import numpy as np

from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, confusion_matrix, classification_report
from sklearn.preprocessing import LabelEncoder


def main():
    if len(sys.argv) < 3:
        print("Usage: python train_evaluate.py <csv_path> <label_column>")
        sys.exit(1)

    csv_path = sys.argv[1]
    label_column = sys.argv[2]

    if csv_path.endswith(".txt"):
        df = pd.read_csv(csv_path, header=None)
        df.rename(columns={41: "label"}, inplace=True)
    else:
        df = pd.read_csv(csv_path)

    if label_column not in df.columns:
        raise ValueError(f"Label column '{label_column}' not found.")

    df = df.dropna(axis=0)

    y_raw = df[label_column].astype(str)
    X = df.drop(columns=[label_column])

    for col in X.columns:
        if X[col].dtype == "object":
            X[col] = LabelEncoder().fit_transform(X[col].astype(str))

    X = X.select_dtypes(include=[np.number])

    if X.empty:
        raise ValueError("No numeric features available for training.")

    label_encoder = LabelEncoder()
    y = label_encoder.fit_transform(y_raw)

    X_train, X_test, y_train, y_test = train_test_split(
        X, y,
        test_size=0.30,
        random_state=42,
        stratify=y if len(set(y)) > 1 else None
    )

    model = RandomForestClassifier(
        n_estimators=100,
        random_state=42,
        class_weight="balanced"
    )

    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)

    accuracy = accuracy_score(y_test, y_pred)
    precision = precision_score(y_test, y_pred, average="weighted", zero_division=0)
    recall = recall_score(y_test, y_pred, average="weighted", zero_division=0)
    f1 = f1_score(y_test, y_pred, average="weighted", zero_division=0)

    labels = list(label_encoder.classes_)
    cm = confusion_matrix(y_test, y_pred)

    report = classification_report(
        y_test,
        y_pred,
        target_names=labels,
        output_dict=True,
        zero_division=0
    )

    attack_summary = pd.Series(label_encoder.inverse_transform(y_pred)).value_counts().to_dict()

    results = {
        "dataset": csv_path,
        "label_column": label_column,
        "rows_total": int(len(df)),
        "features_used": int(X.shape[1]),
        "accuracy": float(accuracy),
        "precision": float(precision),
        "recall": float(recall),
        "f1_score": float(f1),
        "classes": labels,
        "confusion_matrix": cm.tolist(),
        "attack_summary": attack_summary,
        "classification_report": report
    }

    with open("ml_engine/metrics.json", "w") as f:
        json.dump(results, f, indent=4)

    pd.DataFrame({
        "actual": label_encoder.inverse_transform(y_test),
        "predicted": label_encoder.inverse_transform(y_pred)
    }).to_csv("ml_engine/predictions.csv", index=False)

    pd.DataFrame(cm, index=labels, columns=labels).to_csv("ml_engine/confusion_matrix.csv")

    pd.Series(attack_summary).to_csv("ml_engine/attack_summary.csv", header=["count"])

    print("ML evaluation completed successfully.")
    print(f"Accuracy: {accuracy:.4f}")
    print(f"Precision: {precision:.4f}")
    print(f"Recall: {recall:.4f}")
    print(f"F1-score: {f1:.4f}")


if __name__ == "__main__":
    main()
