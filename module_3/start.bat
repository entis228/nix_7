call CHCP 1251
call mvn clean package
call java -jar target/Module3.jar postgres 123456 1 operations.csv