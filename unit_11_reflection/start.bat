call CHCP 1251
call mvn clean package
call java -jar target/Reflection.jar application.properties
call mvn clean