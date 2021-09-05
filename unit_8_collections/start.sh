#!/bin/sh
mvn clean install -Plife;
java -jar target/Collections.jar;
mvn clean
