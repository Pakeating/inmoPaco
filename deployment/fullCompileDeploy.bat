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

echo Compilando Admin...
call docker-compose -f docker-compose.yml build admin-server|| (echo Error en Admin && pause && exit /b)

echo Compilando Config Service V3...
call docker-compose -f docker-compose.yml build  config-service-v3|| (echo Error en Config Service V3 && pause && exit /b)

echo Compilando API Gateway V3...
call docker-compose -f docker-compose.yml build  api-gateway-v3|| (echo Error en API Gateway && pause && exit /b)

echo Compilando Auction Service...
call docker-compose -f docker-compose.yml build --no-cache auction-service|| (echo Error en Auction Service && pause && exit /b)

::Limpiar tambien volumenes, etc, por si fuese necesario
::echo "Limpiando infraestructura anterior..."
::docker-compose down

::--force-recreate obliga a tumbar los contenedores que ya esten creados de antes, se puede quitar mas adelante...
echo Levantando infraestructura completa...
call docker-compose -f docker-compose.yml up -d --force-recreate

echo ======================================================
echo   Despliegue finalizado
echo   Admin:           http://localhost:9090/admin
echo   Queue Dashboard: http://localhost:8282
echo   API Gateway:     http://localhost:8080
echo ======================================================