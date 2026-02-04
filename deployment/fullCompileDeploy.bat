@echo off

:: Compila y despliega toda la infraestructura de INMOPACO en modo nativo usando Docker Compose.

echo ======================================================
echo            Compilando y Desplegando:
echo            INMOPACO - Despliegue Nativo
echo ======================================================

::echo Compilando Eureka Server...
::call docker-compose -f deployment/docker-compose.yml build --no-cache eureka-server
::if %errorlevel% neq 0 (echo Error en Eureka Server && pause && exit /b)

echo Compilando Config Service...
call docker-compose -f docker-compose.yml build --no-cache config-service|| (echo Error en Config Service && pause && exit /b)
::if %errorlevel% neq 0 (echo Error en Config Service && pause && exit /b)

echo Compilando Admin...
call docker-compose -f docker-compose.yml build --no-cache admin-server|| (echo Error en Admin && pause && exit /b)

echo Compilando API Gateway...
call docker-compose -f docker-compose.yml build --no-cache api-gateway|| (echo Error en API Gateway && pause && exit /b)
::if %errorlevel% neq 0 (echo Error en API Gateway && pause && exit /b)

echo Levantando infraestructura completa...
call docker-compose -f docker-compose.yml up -d

echo ======================================================
echo   Despliegue finalizado
echo   Eureka: http://localhost:8761
echo ======================================================
pause