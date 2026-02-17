@echo off

echo ======================================================
echo            Testing:
echo            INMOPACO - Despliegue Nativo
echo ======================================================

echo Compilando Auction Service Test...
call docker-compose -f docker-compose.yml build --no-cache auction-integration-test|| (echo Error en Test Auction Service && pause && exit /b)

echo Levantando infraestructura completa...
call docker-compose -f docker-compose.yml up -d --force-recreate

echo ======================================================
echo   Despliegue finalizado
echo   Admin:           http://localhost:9090/admin
echo   Queue Dashboard: http://localhost:8282
echo   API Gateway:     http://localhost:8080
echo ======================================================