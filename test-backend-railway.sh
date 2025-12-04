#!/bin/bash

# Script para probar el backend en Railway
# Uso: ./test-backend-railway.sh

BASE_URL="https://proyecto-nuclear-veterinaria-production.up.railway.app/api"

echo "🧪 Probando Backend en Railway"
echo "================================"
echo ""

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verificar si jq está instalado
if ! command -v jq &> /dev/null; then
    echo -e "${YELLOW}⚠️  jq no está instalado. Instalando formato JSON básico...${NC}"
    USE_JQ=false
else
    USE_JQ=true
fi

# Función para formatear JSON
format_json() {
    if [ "$USE_JQ" = true ]; then
        jq '.'
    else
        cat
    fi
}

# 1. Ping
echo -e "${YELLOW}1. 🔍 Probando Ping...${NC}"
PING_RESPONSE=$(curl -s -w "\n%{http_code}" "$BASE_URL/auth/ping")
HTTP_CODE=$(echo "$PING_RESPONSE" | tail -n1)
BODY=$(echo "$PING_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ]; then
    echo -e "${GREEN}✅ Ping exitoso (HTTP $HTTP_CODE)${NC}"
    echo "$BODY" | format_json
else
    echo -e "${RED}❌ Ping falló (HTTP $HTTP_CODE)${NC}"
    echo "$BODY"
    exit 1
fi

echo ""
echo -e "${YELLOW}2. 🔐 Haciendo Login...${NC}"
LOGIN_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@veterinaria.com",
    "password": "admin123"
  }')

HTTP_CODE=$(echo "$LOGIN_RESPONSE" | tail -n1)
BODY=$(echo "$LOGIN_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ]; then
    echo -e "${GREEN}✅ Login exitoso (HTTP $HTTP_CODE)${NC}"
    echo "$BODY" | format_json
    
    # Extraer token
    if [ "$USE_JQ" = true ]; then
        TOKEN=$(echo "$BODY" | jq -r '.token // empty')
    else
        # Extracción básica sin jq
        TOKEN=$(echo "$BODY" | grep -o '"token":"[^"]*' | cut -d'"' -f4)
    fi
    
    if [ -n "$TOKEN" ] && [ "$TOKEN" != "null" ] && [ "$TOKEN" != "" ]; then
        echo ""
        echo -e "${GREEN}✅ Token obtenido: ${TOKEN:0:50}...${NC}"
        
        # 3. Obtener usuario actual
        echo ""
        echo -e "${YELLOW}3. 👤 Obteniendo información del usuario...${NC}"
        ME_RESPONSE=$(curl -s -w "\n%{http_code}" "$BASE_URL/auth/me" \
          -H "Authorization: Bearer $TOKEN")
        
        HTTP_CODE=$(echo "$ME_RESPONSE" | tail -n1)
        BODY=$(echo "$ME_RESPONSE" | sed '$d')
        
        if [ "$HTTP_CODE" = "200" ]; then
            echo -e "${GREEN}✅ Usuario obtenido (HTTP $HTTP_CODE)${NC}"
            echo "$BODY" | format_json
        else
            echo -e "${RED}❌ Error al obtener usuario (HTTP $HTTP_CODE)${NC}"
            echo "$BODY"
        fi
        
        # 4. Listar clientes
        echo ""
        echo -e "${YELLOW}4. 👥 Listando clientes...${NC}"
        CLIENTES_RESPONSE=$(curl -s -w "\n%{http_code}" "$BASE_URL/v1/clientes" \
          -H "Authorization: Bearer $TOKEN")
        
        HTTP_CODE=$(echo "$CLIENTES_RESPONSE" | tail -n1)
        BODY=$(echo "$CLIENTES_RESPONSE" | sed '$d')
        
        if [ "$HTTP_CODE" = "200" ]; then
            echo -e "${GREEN}✅ Clientes obtenidos (HTTP $HTTP_CODE)${NC}"
            if [ "$USE_JQ" = true ]; then
                CLIENTES_COUNT=$(echo "$BODY" | jq 'if type == "array" then length else 0 end')
                echo "Total de clientes: $CLIENTES_COUNT"
                echo "$BODY" | jq 'if type == "array" then .[0:3] else . end' # Mostrar solo los primeros 3
            else
                echo "$BODY"
            fi
        else
            echo -e "${RED}❌ Error al obtener clientes (HTTP $HTTP_CODE)${NC}"
            echo "$BODY"
        fi
        
        # 5. Listar pacientes
        echo ""
        echo -e "${YELLOW}5. 🐾 Listando pacientes...${NC}"
        PACIENTES_RESPONSE=$(curl -s -w "\n%{http_code}" "$BASE_URL/v1/pacientes" \
          -H "Authorization: Bearer $TOKEN")
        
        HTTP_CODE=$(echo "$PACIENTES_RESPONSE" | tail -n1)
        BODY=$(echo "$PACIENTES_RESPONSE" | sed '$d')
        
        if [ "$HTTP_CODE" = "200" ]; then
            echo -e "${GREEN}✅ Pacientes obtenidos (HTTP $HTTP_CODE)${NC}"
            if [ "$USE_JQ" = true ]; then
                PACIENTES_COUNT=$(echo "$BODY" | jq 'if type == "array" then length else 0 end')
                echo "Total de pacientes: $PACIENTES_COUNT"
                echo "$BODY" | jq 'if type == "array" then .[0:3] else . end' # Mostrar solo los primeros 3
            else
                echo "$BODY"
            fi
        else
            echo -e "${RED}❌ Error al obtener pacientes (HTTP $HTTP_CODE)${NC}"
            echo "$BODY"
        fi
        
        echo ""
        echo -e "${GREEN}================================"
        echo "✅ Pruebas completadas"
        echo "================================"
        echo ""
        echo "Token para usar en otros requests:"
        echo "$TOKEN"
        echo ""
        
    else
        echo -e "${RED}❌ No se pudo extraer el token de la respuesta${NC}"
        exit 1
    fi
else
    echo -e "${RED}❌ Login falló (HTTP $HTTP_CODE)${NC}"
    echo "$BODY"
    exit 1
fi

