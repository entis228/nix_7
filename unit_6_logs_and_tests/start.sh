#!/bin/sh
mvn clean install -Plife -DskipTests;
java -jar target/Unit6.jar;
mvn clean
