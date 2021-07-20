#!/bin/sh
mvn clean package;
java -jar target/BaseTypes.jar;
mvn clean
