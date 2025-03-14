@echo off
echo === Dang chay Game PVP ===

if exist GamePVP.jar (
    java -jar GamePVP.jar
) else (
    echo Khong tim thay file GamePVP.jar!
    echo Vui long chay rebuild.bat truoc.
    pause
)
