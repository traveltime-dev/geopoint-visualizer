# GeoPoint Visualizer

## Project Description

Create an image of a map with given point coordinates.

## Prerequisites
You need to have [SBT](https://www.scala-sbt.org/download.html) installed on your machine to run this project.

## Installation

To install GeoJson Formatter, follow these steps:

Windows/Mac/Linux:

```bash
$ git clone https://github.com/arnasbr/geopoint-visualizer.git
$ cd geopoint-visualizer
```

## Usage
Start sbt, pass in input. `run plot --help` for help

```
Usage: geopoint-visualizer plot [--swap] [--download] [--browser] [--img_size <integer>] [--input <string>]

Plots given points on a map

Options and flags:
    --help
        Display this help text.
    --swap
        Flag to indicate if latitude and longitude should be swapped
    --download
        Flag to indicate if image should be downloaded to outputDir
    --browser
        Flag to indicate if image should be opened in browser
    --img_size <integer>
        Image size 1-1280 (default - 1000)
    --input <string>
        Input file path (default - inputDir/input.json)
```

#### Example
```bash
$ sbt
sbt: geopoint-visualizer> run plot --swap --download --browser --img_size 1000 --input "inputDir/input.json"
```

## Result

![image](https://github.com/arnasbr/geopoint-visualizer/assets/140691866/dd311615-b5e2-4135-b26b-3f69e4e73a20)



