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
Usage:  geopoint-visualizer plot [--swap] [--download] [--browser] [--img_size <integer>] --arg1 <string> [--arg2 <string>] [--arg3 <string>] [--arg4 <string>] [--arg5 <string>]

Plots given points on a map

Options and flags:
    --help
        Display this help text.
    --swap
        Flag to indicate if latitude and longitude should be swapped (default - false)
    --download
        Flag to indicate if image should be downloaded to outputDir
    --browser
        Flag to indicate if image should be opened in browser
    --img_size <integer>
        Image size 1-1280 (default - 1000)
    --arg1 <string>
        Input coordinates
    --arg2 <string>
        Input coordinates (optional)
    --arg3 <string>
        Input coordinates (optional)
    --arg4 <string>
        Input coordinates (optional)
    --arg5 <string>
        Input coordinates (optional)
```

If neither --download or --browser flags are chosen, by default only --download will happen

#### Example
```bash
$ sbt
sbt: geopoint-visualizer> run plot --swap --download --browser --img_size 1280 --arg1 "[[12.4324, 21.23123], [12.5324, 21.23123]]" --arg2 "[[13.4324, 21.23123], [13.5324, 21.23123]]"
```

## Result example

![image](https://github.com/arnasbr/geopoint-visualizer/assets/140691866/dd311615-b5e2-4135-b26b-3f69e4e73a20)



