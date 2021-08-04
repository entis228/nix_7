#!/bin/sh
while [ 1 = 1 ]
 do
echo "Select level (1 2 3)";
read level;
if [ $level = 1 ]
 then
    cd level1;
    mvn clean install;
    java -jar target/level1.jar;
    mvn clean;
    cd ..;
  elif [ $level = 2 ]
   then
      cd level2;
      mvn clean install;
      java -jar target/level2.jar;
      mvn clean;
      cd ..;
      elif [ $level = 3 ]
      then
           cd level3;
           mvn clean install;
           java -jar target/level3.jar;
           mvn clean;
           cd ..;
           else
             break
  fi
  done