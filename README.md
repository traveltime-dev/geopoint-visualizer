# GeoJson Formatter

## Project Description

Project to convert a list of point coordinates into GeoJson format.

## Prerequisites
You need to have [SBT](https://www.scala-sbt.org/download.html) installed on your machine to run this project.

## Installation

To install GeoJson Formatter, follow these steps:

Windows/Mac/Linux:

```bash
$ git clone https://github.com/arnasbr/geojson-formatter.git
$ cd geojson-formatter
```

## Usage
Start sbt, pass in input. `run plot --help` for help

```
Usage: geojson-formatter plot [--swap] [--download] [--browser] [--img_size <integer>] --arg1 <string> [--arg2 <string>] [--arg3 <string>] [--arg4 <string>] [--arg5 <string>]

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
sbt:geojson-formatter> run plot --swap --download --browser --img_size 1280 --arg1 "[[12.4324, 21.23123], [12.5324, 21.23123]]" --arg2 "[[13.4324, 21.23123], [13.5324, 21.23123]]"
```

## Result example

![image](https://github.com/arnasbr/geojson-formatter/assets/140691866/3a3d1aff-77b2-4b05-beb4-e52c529c0963)

