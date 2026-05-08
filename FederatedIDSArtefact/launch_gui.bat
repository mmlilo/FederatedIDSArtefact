@echo off
echo Starting Federated IDS Artefact...
mvn exec:java "-Dexec.mainClass=com.registration.app.federatedidsartefact.ui.AppLauncher"
pause