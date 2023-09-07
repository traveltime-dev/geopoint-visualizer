# GeoJson Formatter

## Project Description

Project to convert a list of point coordinates into GeoJson format.

## Prerequisites
You need to have [SBT](https://www.scala-sbt.org/download.html) installed on your machine to run this project.

## Installation

To install GeoJson Formatter, follow these steps:

Windows/Mac/Linux:

```bash
git clone https://github.com/arnasbr/geojson-formatter.git
cd geojson-formatter
```

## Usage
Start sbt, pass in input (up to 5 lists). `swap=true` swaps longitude and latitude. `swap=true` or `swap=false` is mandatory.
```bash
sbt
run swap=true "[[12.4324, 21.23123], [12.5324, 21.23123]]" "[[13.4324, 21.23123], [13.5324, 21.23123]]"
```

## Result

![image](https://github.com/arnasbr/geojsonFormatter/assets/140691866/2cc9d5ae-46f5-4c3b-9db3-0776b12db80f)
