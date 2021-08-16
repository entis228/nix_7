#!/bin/sh
cd ..;
mvn clean test -Ptest;
cd unit_6_logs_and_tests;
./start.sh
