#!/bin/sh
mvn clean install -Plife;
java -jar target/Unit7.jar;
mvn clean
