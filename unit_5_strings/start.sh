#!/bin/sh
cd reversestring/;
mvn clean install;
cp target/ReverseString.jar ../LibraryTest/lib;
mvn clean;
cd ../LibraryTest;
mvn clean;
mvn validate;
mvn install:install-file -Dfile=lib/ReverseString.jar -DgroupId=com.entis -DartifactId=ReverseString -Dversion=1.0-SNAPSHOT -Dpackaging=jar;
mvn clean install;
java -jar target/ReverseTest.jar;
mvn clean