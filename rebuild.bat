@echo off
echo === Dang rebuild Game PVP ===

echo 1. Xoa cac file cu...
rmdir /s /q bin 2>nul
del /f /q GamePVP.jar 2>nul
del /f /q sources.txt 2>nul

echo 2. Tao thu muc bin...
mkdir bin

echo 3. Tim file Java...
dir /s /b src\*.java > sources.txt

echo 4. Dang bien dich...
javac -encoding UTF-8 -d bin @sources.txt
if %errorlevel% neq 0 (
    echo Loi bien dich!
    pause
    exit /b 1
)

echo 5. Tao file JAR...
jar cfe GamePVP.jar Main -C bin .

echo 6. Don dep...
del /f /q sources.txt

echo === Rebuild hoan tat! ===
echo Chay game bang: java -jar GamePVP.jar
pause