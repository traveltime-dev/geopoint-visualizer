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

```bash
Usage: geojson-formatter plot [--swap] [--download] [--browser] --arg1 <string> [--arg2 <string>] [--arg3 <string>] [--arg4 <string>] [--arg5 <string>]

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
sbt:geojson-formatter> run plot --swap --download --browser --arg1 "[[12.4324, 21.23123], [12.5324, 21.23123]]" --arg2 "[[13.4324, 21.23123], [13.5324, 21.23123]]"
```

## Result example

![image](https://github.com/arnasbr/geojsonFormatter/assets/140691866/2cc9d5ae-46f5-4c3b-9db3-0776b12db80f)
