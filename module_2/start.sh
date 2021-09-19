#!/bin/sh
while [ 1 = 1 ]
 do
echo "Select task (1-date formats 2-distinct names 3-towns, ctrl+C-exit)";
read level;
if [ $level = 1 ]
 then
    cd date_formats;
    mvn clean install;
    java -jar target/DateFormats.jar;
    mvn clean;
    cd ..;
  elif [ $level = 2 ]
   then
      cd distinct_name;
      mvn clean install;
      java -jar target/DisNames.jar;
      mvn clean;
      cd ..;
      elif [ $level = 3 ]
      then
           cd towns;
           mvn clean install;
           java -jar target/Towns.jar;
           mvn clean;
           cd ..;
           else
             break
  fi
  done