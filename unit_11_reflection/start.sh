#!/bin/sh
mvn clean package
java -jar target/Reflection.jar application.properties
mvn clean