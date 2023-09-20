# GeoPoint Visualizer

## Project Description

Create an image of a map with given point coordinates.

## Prerequisites
You need to have [SBT](https://www.scala-sbt.org/download.html) installed on your machine to run this project.

## Installation
```bash
$ git clone https://github.com/arnasbr/geopoint-visualizer.git
$ cd geopoint-visualizer
```

## Api Token

Create an account in https://account.mapbox.com/

Use your `Default public token` (or create a new one)

## Usage
Start sbt, pass in input. `run plot --help` for help

```
Usage: geopoint-visualizer plot --token <string> [--swap] [--download] [--browser] [--img_size <integer>] [--input <string>]

Plots given points on a map

Options and flags:
    --help
        Display this help text.
    --token <string>
        Mapbox api token
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

#### Examples
```bash
$ sbt "run plot --token <your_api_token> --swap --download --browser --img_size 1000 --input inputDir/input.json"

$ sbt "run plot --token <your_api_token> --swap --download --browser" //use default image size and input file
```

## Result

![image](https://github.com/arnasbr/geopoint-visualizer/assets/140691866/dd311615-b5e2-4135-b26b-3f69e4e73a20)



