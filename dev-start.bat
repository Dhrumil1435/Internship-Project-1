@echo off
title Spring Boot Microservices - Development Environment

REM ==========================================
REM Environment Configuration (Java & Docker)
REM ==========================================
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
if exist "C:\Program Files\Docker\Docker\resources\bin\docker.exe" (
    set "PATH=C:\Program Files\Docker\Docker\resources\bin;%JAVA_HOME%\bin;%PATH%"
) else (
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)

echo ====================================================
echo        STARTING DEVELOPMENT ENVIRONMENT
echo ====================================================
echo.
echo Using JAVA_HOME: %JAVA_HOME%
echo.

echo [1/8] Starting Docker containers...
cd /d "%~dp0docker"
docker compose up -d

echo.
echo Waiting for Docker services...
timeout /t 15 >nul

echo.
echo [2/8] Starting Config Server...
start "Config Server" cmd /k "set ""JAVA_HOME=%JAVA_HOME%"" && set ""PATH=%PATH%"" && cd /d "%~dp0config-server" && call mvnw.cmd compile spring-boot:run"

timeout /t 20 >nul

echo.
echo [3/8] Starting Eureka Server...
start "Eureka Server" cmd /k "set ""JAVA_HOME=%JAVA_HOME%"" && set ""PATH=%PATH%"" && cd /d "%~dp0eureka-server" && call mvnw.cmd compile spring-boot:run"

timeout /t 20 >nul

echo.
echo [4/8] Starting API Gateway...
start "API Gateway" cmd /k "set ""JAVA_HOME=%JAVA_HOME%"" && set ""PATH=%PATH%"" && cd /d "%~dp0api-gateway" && call mvnw.cmd compile spring-boot:run"

timeout /t 10 >nul

echo.
echo [5/8] Starting Order Service...
start "Order Service" cmd /k "set ""JAVA_HOME=%JAVA_HOME%"" && set ""PATH=%PATH%"" && cd /d "%~dp0order-service" && call mvnw.cmd compile spring-boot:run"

timeout /t 5 >nul

echo.
echo [6/8] Starting Inventory Service...
start "Inventory Service" cmd /k "set ""JAVA_HOME=%JAVA_HOME%"" && set ""PATH=%PATH%"" && cd /d "%~dp0inventory-service" && call mvnw.cmd compile spring-boot:run"

timeout /t 5 >nul

echo.
echo [7/8] Starting Payment Service...
start "Payment Service" cmd /k "set ""JAVA_HOME=%JAVA_HOME%"" && set ""PATH=%PATH%"" && cd /d "%~dp0payment-service" && call mvnw.cmd compile spring-boot:run"

timeout /t 5 >nul

echo.
echo ====================================================
echo      Development Environment Started
echo ====================================================
pause
