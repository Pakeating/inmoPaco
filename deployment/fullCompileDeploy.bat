@echo off

:: Compila y despliega toda la infraestructura de INMOPACO en modo nativo usando Docker Compose.

echo ======================================================
echo            Compilando y Desplegando:
echo            INMOPACO - Despliegue Nativo
echo ======================================================

::echo Compilando Eureka Server...
::call docker-compose -f deployment/docker-compose.yml build --no-cache eureka-server

:: OPCIONES DE BUILD:
::--no-cache

echo Compilando Config Service...
::call docker-compose -f docker-compose.yml build config-service|| (echo Error en Config Service && pause && exit /b)

echo Compilando Admin...
::call docker-compose -f docker-compose.yml build  admin-server|| (echo Error en Admin && pause && exit /b)

::echo Compilando API Gateway...
::call docker-compose -f docker-compose.yml build --no-cache api-gateway|| (echo Error en API Gateway && pause && exit /b)

echo Compilando API Gateway V3...
call docker-compose -f docker-compose.yml build  api-gateway-v3|| (echo Error en API Gateway && pause && exit /b)

echo Compilando Auction Service...
call docker-compose -f docker-compose.yml build --no-cache auction-service|| (echo Error en Auction Service && pause && exit /b)

echo Levantando infraestructura completa...
call docker-compose -f docker-compose.yml up -d

echo ======================================================
echo   Despliegue finalizado
echo   Eureka: http://localhost:8761
echo   Admin: http://localhost:9090/admin
echo   API Gateway: http://localhost:8080
echo ======================================================
pause