#!/bin/bash

# Create outputDir
mkdir outputDir

# Run the Scala app with sbt
sbt "run plot --token pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A --swap --input integrationTestData/integrationTestInput.json"

# Check if the newly downloaded image matches the expected image
if cmp -s "outputDir/output.png" "integrationTestData/integrationTestOutput.png"; then
   echo "App works as intended"
   exit 0
else
   echo "App does not work as intended"
   exit 1
fi