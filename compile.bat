@echo off
echo === Dang bien dich Game PVP ===

echo 1. Tao thu muc bin neu chua ton tai...
if not exist bin mkdir bin

echo 2. Dang bien dich...
javac -encoding UTF-8 -d bin src\main\Main.java

if %errorlevel% neq 0 (
    echo Loi bien dich!
    pause
    exit /b 1
)

echo 3. Chay game...
java -cp bin main.Main
pause
