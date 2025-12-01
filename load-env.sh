#!/bin/bash
# Script para cargar variables de entorno desde archivo .env
# Uso: source load-env.sh

if [ -f .env ]; then
    export $(cat .env | grep -v '^#' | xargs)
    echo "✅ Variables de entorno cargadas desde .env"
else
    echo "⚠️  Archivo .env no encontrado. Usando valores por defecto."
fi

