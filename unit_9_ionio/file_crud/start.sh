#!/bin/sh
mvn clean install -Plife -DskipTests;
java -jar target/Unit9.jar;
mvn clean
