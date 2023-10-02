#!/bin/bash

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
