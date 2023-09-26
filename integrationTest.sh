#!/bin/bash

# Run the Scala app with sbt
sbt "run plot --token pk.eyJ1IjoiYXJuYXNiciIsImEiOiJjbG00dXY1MDAybGJrM2RwNnE2dmo1NW01In0.XC_idJ6KnMWc1N-MX-Ry7A --swap --input integrationTestData/integrationTestInput.json"

# Check if the output.png file exists
if test -f "output.png"; then
   echo "outputDir/output.png exists."
   exit 0
else
   echo "output.png does not exist."
   exit 1
fi
