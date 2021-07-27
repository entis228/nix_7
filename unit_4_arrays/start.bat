chcp 1251
call mvn clean install
echo -----------------------------------------------------------------------------------
echo ################################start of the program################################
call java -jar target\Arrays.jar
call mvn clean
PAUSE
