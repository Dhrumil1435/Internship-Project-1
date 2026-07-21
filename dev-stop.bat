@echo off
title Stop Development Environment

REM ==========================================
REM Environment Configuration (Docker PATH)
REM ==========================================
if exist "C:\Program Files\Docker\Docker\resources\bin\docker.exe" (
    set "PATH=C:\Program Files\Docker\Docker\resources\bin;%PATH%"
)

echo ==========================================
echo Stopping Microservice Windows...
echo ==========================================
taskkill /FI "WINDOWTITLE eq Config Server*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Eureka Server*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq API Gateway*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Order Service*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Inventory Service*" /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq Payment Service*" /F >nul 2>&1

echo.
echo ==========================================
echo Stopping Docker Containers...
echo ==========================================

cd /d "%~dp0docker"
docker compose down

echo.
echo ==========================================
echo Development Environment Stopped Successfully
echo ==========================================
pause