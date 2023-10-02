#!/bin/bash

# Check if any arguments are passed
if [ "$#" -ne 1 ]; then
  echo "Usage: ./integrationTest.sh <MAPBOX_API_TOKEN>"
  echo "You must provide the Mapbox API token as an argument."
  exit 1
fi

# Check if outputDir/output.png exists and delete it
if test -f "outputDir/output.png"; then
  echo "Deleting existing outputDir/output.png."
  rm -f "outputDir/output.png"
fi

token=$1

# Run the Scala app with sbt
sbt "run plot --token $token --swap --input integrationTestData/integrationTestInput.json"

# Check if the output.png file exists
if test -f "outputDir/output.png"; then
  echo "outputDir/output.png exists."
  exit 0
else
  echo "outputDir/output.png does not exist."
  exit 1
fi
