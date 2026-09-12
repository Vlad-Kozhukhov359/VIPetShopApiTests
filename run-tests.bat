@echo off
setlocal

echo Starting Java tests build...

cd /d "%~dp0"

echo Cleaning previous builds...
call gradlew.bat clean

echo Running tests...
call gradlew.bat test

echo Build completed successfully!
echo Allure results saved to: allure-results/

endlocal
