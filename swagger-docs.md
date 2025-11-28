---
title: Sistema Gestion Veterinaria - API REST v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
highlight_theme: darkula
headingLevel: 2

---

<!-- Generator: Widdershins v4.0.1 -->

<h1 id="sistema-gestion-veterinaria-api-rest">Sistema Gestion Veterinaria - API REST v1.0.0</h1>

> Scroll down for code samples, example requests and responses. Select a language for code samples from the tabs above or the mobile navigation menu.

API REST para Sistema de Gestión Veterinaria.

Incluye módulos de:
- Autenticación y Usuarios
- Gestión de Clientes
- Gestión de Pacientes (Mascotas)
- Gestión de Citas
- Historial Clínico Médico
- Facturación y Pagos

### Autenticación
La API usa JWT (JSON Web Tokens) para autenticación.

1. Obtén un token haciendo login en `/api/auth/login`
2. Usa el token en el header Authorization: `Bearer {token}`
3. Usa el botón "Authorize" arriba para configurar el token

Base URLs:

* <a href="http://localhost:8080">http://localhost:8080</a>

* <a href="https://api.veterinaria.com">https://api.veterinaria.com</a>

Email: <a href="mailto:soporte@veterinaria.com">Sistema Veterinaria</a> Web: <a href="https://veterinaria.com">Sistema Veterinaria</a> 
License: <a href="https://www.apache.org/licenses/LICENSE-2.0.html">Apache 2.0</a>

# Authentication

- HTTP Authentication, scheme: bearer Ingresa el token JWT obtenido del endpoint de login

<h1 id="sistema-gestion-veterinaria-api-rest-usuarios">Usuarios</h1>

Endpoints para gestión de usuarios

## obtenerPorId

<a id="opIdobtenerPorId"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/usuarios/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/usuarios/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/usuarios/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/usuarios/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/usuarios/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/usuarios/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /usuarios/{id}`

*Obtener usuario por ID*

Obtiene los detalles de un usuario específico

<h3 id="obtenerporid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Usuario encontrado|[UsuarioDTO](#schemausuariodto)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|No tiene permisos|[UsuarioDTO](#schemausuariodto)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Usuario no encontrado|[UsuarioDTO](#schemausuariodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizar

<a id="opIdactualizar"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/usuarios/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/usuarios/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "email": "string",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "roleIds": [
    0
  ],
  "fotoperfilUrl": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/usuarios/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/usuarios/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/usuarios/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/usuarios/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /usuarios/{id}`

*Actualizar usuario*

Actualiza los datos de un usuario existente

> Body parameter

```json
{
  "email": "string",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "roleIds": [
    0
  ],
  "fotoperfilUrl": "string"
}
```

<h3 id="actualizar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateUsuarioRequest](#schemaupdateusuariorequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Usuario actualizado|[UsuarioDTO](#schemausuariodto)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Usuario no encontrado|[UsuarioDTO](#schemausuariodto)|
|409|[Conflict](https://tools.ietf.org/html/rfc7231#section-6.5.8)|Email o DNI duplicado|[UsuarioDTO](#schemausuariodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar

<a id="opIdeliminar"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/usuarios/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/usuarios/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/usuarios/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/usuarios/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/usuarios/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/usuarios/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /usuarios/{id}`

*Eliminar usuario*

Elimina un usuario del sistema (soft delete)

<h3 id="eliminar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|Usuario eliminado|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Usuario no encontrado|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerTodos

<a id="opIdobtenerTodos"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/usuarios?pageable=page,0,size,1,sort,string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/usuarios?pageable=page,0,size,1,sort,string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios?pageable=page,0,size,1,sort,string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/usuarios',
  params: {
  'pageable' => '[Pageable](#schemapageable)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/usuarios', params={
  'pageable': {
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/usuarios', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios?pageable=page,0,size,1,sort,string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/usuarios", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /usuarios`

*Listar usuarios*

Obtiene todos los usuarios activos con paginación

<h3 id="obtenertodos-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pageable|query|[Pageable](#schemapageable)|true|none|

> Example responses

> 200 Response

<h3 id="obtenertodos-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Usuarios obtenidos exitosamente|[PageUsuarioDTO](#schemapageusuariodto)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|No tiene permisos para acceder|[PageUsuarioDTO](#schemapageusuariodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crear

<a id="opIdcrear"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/usuarios \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/usuarios HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "username": "string",
  "email": "string",
  "password": "stringst",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "tipoUsuario": "ADMINISTRADOR",
  "roleIds": [
    0
  ]
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/usuarios',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/usuarios', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/usuarios', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/usuarios", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /usuarios`

*Crear usuario*

Crea un nuevo usuario en el sistema

> Body parameter

```json
{
  "username": "string",
  "email": "string",
  "password": "stringst",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "tipoUsuario": "ADMINISTRADOR",
  "roleIds": [
    0
  ]
}
```

<h3 id="crear-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateUsuarioRequest](#schemacreateusuariorequest)|true|none|

> Example responses

> 201 Response

<h3 id="crear-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|Usuario creado exitosamente|[UsuarioDTO](#schemausuariodto)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Datos inválidos|[UsuarioDTO](#schemausuariodto)|
|409|[Conflict](https://tools.ietf.org/html/rfc7231#section-6.5.8)|El usuario ya existe|[UsuarioDTO](#schemausuariodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## desbloquear

<a id="opIddesbloquear"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/usuarios/{id}/desbloquear \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/usuarios/{id}/desbloquear HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}/desbloquear',
{
  method: 'POST',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/usuarios/{id}/desbloquear',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/usuarios/{id}/desbloquear', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/usuarios/{id}/desbloquear', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}/desbloquear");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/usuarios/{id}/desbloquear", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /usuarios/{id}/desbloquear`

*Desbloquear cuenta*

Desbloquea una cuenta bloqueada por intentos fallidos

<h3 id="desbloquear-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="desbloquear-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MessageResponse](#schemamessageresponse)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cambiarPassword

<a id="opIdcambiarPassword"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/usuarios/{id}/cambiar-password \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/usuarios/{id}/cambiar-password HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "currentPassword": "string",
  "newPassword": "stringst",
  "confirmPassword": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}/cambiar-password',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/usuarios/{id}/cambiar-password',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/usuarios/{id}/cambiar-password', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/usuarios/{id}/cambiar-password', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}/cambiar-password");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/usuarios/{id}/cambiar-password", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /usuarios/{id}/cambiar-password`

*Cambiar contraseña*

Cambia la contraseña de un usuario

> Body parameter

```json
{
  "currentPassword": "string",
  "newPassword": "stringst",
  "confirmPassword": "string"
}
```

<h3 id="cambiarpassword-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[ChangePasswordRequest](#schemachangepasswordrequest)|true|none|

> Example responses

> 200 Response

<h3 id="cambiarpassword-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Contraseña cambiada|[MessageResponse](#schemamessageresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Contraseña actual incorrecta o las nuevas no coinciden|[MessageResponse](#schemamessageresponse)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerSesiones

<a id="opIdobtenerSesiones"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/usuarios/{id}/sesiones \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/usuarios/{id}/sesiones HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}/sesiones',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/usuarios/{id}/sesiones',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/usuarios/{id}/sesiones', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/usuarios/{id}/sesiones', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}/sesiones");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/usuarios/{id}/sesiones", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /usuarios/{id}/sesiones`

*Obtener sesiones activas*

Lista las sesiones activas de un usuario

<h3 id="obtenersesiones-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenersesiones-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenersesiones-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[Sesion](#schemasesion)]|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|
|» isActive|boolean|false|none|none|
|» version|integer(int64)|false|none|none|
|» id|integer(int64)|false|none|none|
|» usuario|[Usuario](#schemausuario)|true|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedBy|string|false|none|none|
|»» isActive|boolean|false|none|none|
|»» version|integer(int64)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» username|string|true|none|none|
|»» email|string|true|none|none|
|»» password|string|true|none|none|
|»» nombre|string|true|none|none|
|»» apellido|string|true|none|none|
|»» dni|string|false|none|none|
|»» telefono|string|false|none|none|
|»» direccion|string|false|none|none|
|»» tipoUsuario|string|false|none|none|
|»» roles|[[Rol](#schemarol)]|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedBy|string|false|none|none|
|»»» isActive|boolean|false|none|none|
|»»» version|integer(int64)|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» nombre|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» permisos|[[Permiso](#schemapermiso)]|false|none|none|
|»»»» createdAt|string(date-time)|false|none|none|
|»»»» updatedAt|string(date-time)|false|none|none|
|»»»» createdBy|string|false|none|none|
|»»»» updatedBy|string|false|none|none|
|»»»» isActive|boolean|false|none|none|
|»»»» version|integer(int64)|false|none|none|
|»»»» id|integer(int64)|false|none|none|
|»»»» codigo|string|false|none|none|
|»»»» nombre|string|false|none|none|
|»»»» modulo|string|false|none|none|
|»»»» descripcion|string|false|none|none|
|»»» usuarios|[[Usuario](#schemausuario)]|false|none|none|
|»» cuentaBloqueada|boolean|false|none|none|
|»» cuentaExpirada|boolean|false|none|none|
|»» credencialesExpiradas|boolean|false|none|none|
|»» intentosFallidos|integer(int32)|false|none|none|
|»» ultimoAcceso|string(date-time)|false|none|none|
|»» fechaCambioPassword|string(date-time)|false|none|none|
|»» requiereCambioPassword|boolean|false|none|none|
|»» fotoperfilUrl|string|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» cuentaHabilitada|boolean|false|none|none|
|» token|string|true|none|none|
|» refreshToken|string|false|none|none|
|» fechaInicio|string(date-time)|true|none|none|
|» fechaExpiracion|string(date-time)|true|none|none|
|» fechaFin|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» ipAddress|string|false|none|none|
|» userAgent|string|false|none|none|
|» ultimaActividad|string(date-time)|false|none|none|
|» valid|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|tipoUsuario|ADMINISTRADOR|
|tipoUsuario|VETERINARIO|
|tipoUsuario|ASISTENTE|
|tipoUsuario|RECEPCIONISTA|
|tipoUsuario|PROPIETARIO|
|estado|ACTIVA|
|estado|EXPIRADA|
|estado|CERRADA_MANUAL|
|estado|CERRADA_POR_INACTIVIDAD|
|estado|REVOCADA|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cerrarTodasLasSesiones

<a id="opIdcerrarTodasLasSesiones"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/usuarios/{id}/sesiones \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/usuarios/{id}/sesiones HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}/sesiones',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/usuarios/{id}/sesiones',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/usuarios/{id}/sesiones', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/usuarios/{id}/sesiones', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}/sesiones");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/usuarios/{id}/sesiones", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /usuarios/{id}/sesiones`

*Cerrar todas las sesiones*

Cierra todas las sesiones activas de un usuario

<h3 id="cerrartodaslassesiones-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="cerrartodaslassesiones-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MessageResponse](#schemamessageresponse)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorTipo

<a id="opIdobtenerPorTipo"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/usuarios/tipo/{tipo}?pageable=page,0,size,1,sort,string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/usuarios/tipo/{tipo}?pageable=page,0,size,1,sort,string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/tipo/{tipo}?pageable=page,0,size,1,sort,string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/usuarios/tipo/{tipo}',
  params: {
  'pageable' => '[Pageable](#schemapageable)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/usuarios/tipo/{tipo}', params={
  'pageable': {
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/usuarios/tipo/{tipo}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/tipo/{tipo}?pageable=page,0,size,1,sort,string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/usuarios/tipo/{tipo}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /usuarios/tipo/{tipo}`

*Obtener usuarios por tipo*

Filtra usuarios por tipo (VETERINARIO, RECEPCIONISTA, etc.)

<h3 id="obtenerportipo-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|tipo|path|string|true|none|
|pageable|query|[Pageable](#schemapageable)|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|tipo|ADMINISTRADOR|
|tipo|VETERINARIO|
|tipo|ASISTENTE|
|tipo|RECEPCIONISTA|
|tipo|PROPIETARIO|

> Example responses

> 200 Response

<h3 id="obtenerportipo-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PageUsuarioDTO](#schemapageusuariodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscar

<a id="opIdbuscar"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/usuarios/buscar?termino=string&pageable=page,0,size,1,sort,string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/usuarios/buscar?termino=string&pageable=page,0,size,1,sort,string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/buscar?termino=string&pageable=page,0,size,1,sort,string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/usuarios/buscar',
  params: {
  'termino' => 'string',
'pageable' => '[Pageable](#schemapageable)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/usuarios/buscar', params={
  'termino': 'string',  'pageable': {
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/usuarios/buscar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/buscar?termino=string&pageable=page,0,size,1,sort,string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/usuarios/buscar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /usuarios/buscar`

*Buscar usuarios*

Busca usuarios por nombre o apellido

<h3 id="buscar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|termino|query|string|true|none|
|pageable|query|[Pageable](#schemapageable)|true|none|

> Example responses

> 200 Response

<h3 id="buscar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PageUsuarioDTO](#schemapageusuariodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cerrarSesion

<a id="opIdcerrarSesion"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/usuarios/{id}/sesiones/{sesionId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/usuarios/{id}/sesiones/{sesionId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/usuarios/{id}/sesiones/{sesionId}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/usuarios/{id}/sesiones/{sesionId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/usuarios/{id}/sesiones/{sesionId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/usuarios/{id}/sesiones/{sesionId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/usuarios/{id}/sesiones/{sesionId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/usuarios/{id}/sesiones/{sesionId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /usuarios/{id}/sesiones/{sesionId}`

*Cerrar sesión*

Cierra una sesión específica de un usuario

<h3 id="cerrarsesion-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|sesionId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="cerrarsesion-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MessageResponse](#schemamessageresponse)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-tipo-servicio-controller">tipo-servicio-controller</h1>

## obtenerPorId_1

<a id="opIdobtenerPorId_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tipos-servicio/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tipos-servicio/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tipos-servicio/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tipos-servicio/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tipos-servicio/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tipos-servicio/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tipos-servicio/{id}`

<h3 id="obtenerporid_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TipoServicioDTO](#schematiposerviciodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizar_1

<a id="opIdactualizar_1"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/tipos-servicio/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/tipos-servicio/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 5,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/tipos-servicio/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/tipos-servicio/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/tipos-servicio/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/tipos-servicio/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/tipos-servicio/{id}`

> Body parameter

```json
{
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 5,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true
}
```

<h3 id="actualizar_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateTipoServicioRequest](#schemaupdatetiposerviciorequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizar_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TipoServicioDTO](#schematiposerviciodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## desactivar

<a id="opIddesactivar"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/tipos-servicio/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/tipos-servicio/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/tipos-servicio/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/tipos-servicio/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/tipos-servicio/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/tipos-servicio/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/tipos-servicio/{id}`

<h3 id="desactivar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="desactivar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarTodos

<a id="opIdlistarTodos"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tipos-servicio \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tipos-servicio HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tipos-servicio',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tipos-servicio', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tipos-servicio', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tipos-servicio", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tipos-servicio`

> Example responses

> 200 Response

<h3 id="listartodos-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listartodos-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TipoServicioDTO](#schematiposerviciodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» precioBase|number(double)|false|none|none|
|» categoria|string|false|none|none|
|» requiereConfirmacion|boolean|false|none|none|
|» estaDisponible|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crear_2

<a id="opIdcrear_2"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/tipos-servicio \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/tipos-servicio HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 5,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/tipos-servicio',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/tipos-servicio', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/tipos-servicio', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/tipos-servicio", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/tipos-servicio`

> Body parameter

```json
{
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 5,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true
}
```

<h3 id="crear_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateTipoServicioRequest](#schemacreatetiposerviciorequest)|true|none|

> Example responses

> 200 Response

<h3 id="crear_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TipoServicioDTO](#schematiposerviciodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## activar

<a id="opIdactivar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/tipos-servicio/{id}/activar \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/tipos-servicio/{id}/activar HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio/{id}/activar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/tipos-servicio/{id}/activar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/tipos-servicio/{id}/activar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/tipos-servicio/{id}/activar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio/{id}/activar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/tipos-servicio/{id}/activar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/tipos-servicio/{id}/activar`

<h3 id="activar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="activar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerCategorias

<a id="opIdobtenerCategorias"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tipos-servicio/categorias \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tipos-servicio/categorias HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio/categorias',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tipos-servicio/categorias',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tipos-servicio/categorias', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tipos-servicio/categorias', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio/categorias");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tipos-servicio/categorias", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tipos-servicio/categorias`

> Example responses

> 200 Response

<h3 id="obtenercategorias-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenercategorias-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorCategoria

<a id="opIdlistarPorCategoria"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tipos-servicio/categoria/{categoria}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tipos-servicio/categoria/{categoria}`

<h3 id="listarporcategoria-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|categoria|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarporcategoria-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporcategoria-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TipoServicioDTO](#schematiposerviciodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» precioBase|number(double)|false|none|none|
|» categoria|string|false|none|none|
|» requiereConfirmacion|boolean|false|none|none|
|» estaDisponible|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-raza-controller">raza-controller</h1>

## obtenerRazaPorId

<a id="opIdobtenerRazaPorId"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/{id}`

<h3 id="obtenerrazaporid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerrazaporid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RazaDTO](#schemarazadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizarRaza

<a id="opIdactualizarRaza"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/razas/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/razas/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nombre": "string",
  "descripcion": "string",
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1,
  "isActive": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/razas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/razas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/razas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/razas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/razas/{id}`

> Body parameter

```json
{
  "nombre": "string",
  "descripcion": "string",
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1,
  "isActive": true
}
```

<h3 id="actualizarraza-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateRazaRequest](#schemaupdaterazarequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizarraza-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RazaDTO](#schemarazadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminarRaza

<a id="opIdeliminarRaza"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/razas/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/razas/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/razas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/razas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/razas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/razas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/razas/{id}`

<h3 id="eliminarraza-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminarraza-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerTodasLasRazas

<a id="opIdobtenerTodasLasRazas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas`

> Example responses

> 200 Response

<h3 id="obtenertodaslasrazas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenertodaslasrazas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crearRaza

<a id="opIdcrearRaza"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/razas \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/razas HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nombre": "string",
  "especie": "PERRO",
  "descripcion": "string",
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/razas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/razas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/razas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/razas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/razas`

> Body parameter

```json
{
  "nombre": "string",
  "especie": "PERRO",
  "descripcion": "string",
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1
}
```

<h3 id="crearraza-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateRazaRequest](#schemacreaterazarequest)|true|none|

> Example responses

> 200 Response

<h3 id="crearraza-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RazaDTO](#schemarazadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## desactivarRaza

<a id="opIddesactivarRaza"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/razas/{id}/desactivar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/razas/{id}/desactivar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/{id}/desactivar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/razas/{id}/desactivar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/razas/{id}/desactivar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/razas/{id}/desactivar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/{id}/desactivar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/razas/{id}/desactivar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/razas/{id}/desactivar`

<h3 id="desactivarraza-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="desactivarraza-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RazaDTO](#schemarazadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## activarRaza

<a id="opIdactivarRaza"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/razas/{id}/activar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/razas/{id}/activar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/{id}/activar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/razas/{id}/activar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/razas/{id}/activar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/razas/{id}/activar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/{id}/activar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/razas/{id}/activar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/razas/{id}/activar`

<h3 id="activarraza-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="activarraza-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RazaDTO](#schemarazadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerRazasPredefinidas

<a id="opIdobtenerRazasPredefinidas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/predefinidas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/predefinidas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/predefinidas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/predefinidas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/predefinidas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/predefinidas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/predefinidas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/predefinidas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/predefinidas`

> Example responses

> 200 Response

<h3 id="obtenerrazaspredefinidas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerrazaspredefinidas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerRazasPersonalizadas

<a id="opIdobtenerRazasPersonalizadas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/personalizadas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/personalizadas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/personalizadas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/personalizadas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/personalizadas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/personalizadas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/personalizadas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/personalizadas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/personalizadas`

> Example responses

> 200 Response

<h3 id="obtenerrazaspersonalizadas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerrazaspersonalizadas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerEstadisticasPorEspecie

<a id="opIdobtenerEstadisticasPorEspecie"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/estadisticas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/estadisticas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/estadisticas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/estadisticas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/estadisticas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/estadisticas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/estadisticas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/estadisticas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/estadisticas`

> Example responses

> 200 Response

<h3 id="obtenerestadisticasporespecie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerestadisticasporespecie-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|» **additionalProperties**|integer(int64)|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerRazasPorEspecie

<a id="opIdobtenerRazasPorEspecie"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/especie/{especie} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/especie/{especie} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/especie/{especie}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/especie/{especie}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/especie/{especie}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/especie/{especie}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/especie/{especie}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/especie/{especie}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/especie/{especie}`

<h3 id="obtenerrazasporespecie-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|especie|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

> Example responses

> 200 Response

<h3 id="obtenerrazasporespecie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerrazasporespecie-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerRazasEspecificasActivasPorEspecie

<a id="opIdobtenerRazasEspecificasActivasPorEspecie"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/especie/{especie}/especificas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/especie/{especie}/especificas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/especie/{especie}/especificas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/especie/{especie}/especificas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/especie/{especie}/especificas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/especie/{especie}/especificas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/especie/{especie}/especificas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/especie/{especie}/especificas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/especie/{especie}/especificas`

<h3 id="obtenerrazasespecificasactivasporespecie-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|especie|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

> Example responses

> 200 Response

<h3 id="obtenerrazasespecificasactivasporespecie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerrazasespecificasactivasporespecie-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerRazasActivasPorEspecie

<a id="opIdobtenerRazasActivasPorEspecie"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/especie/{especie}/activas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/especie/{especie}/activas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/especie/{especie}/activas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/especie/{especie}/activas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/especie/{especie}/activas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/especie/{especie}/activas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/especie/{especie}/activas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/especie/{especie}/activas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/especie/{especie}/activas`

<h3 id="obtenerrazasactivasporespecie-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|especie|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

> Example responses

> 200 Response

<h3 id="obtenerrazasactivasporespecie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerrazasactivasporespecie-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## contarRazasPorEspecie

<a id="opIdcontarRazasPorEspecie"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/contar/especie/{especie} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/contar/especie/{especie} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/contar/especie/{especie}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/contar/especie/{especie}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/contar/especie/{especie}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/contar/especie/{especie}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/contar/especie/{especie}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/contar/especie/{especie}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/contar/especie/{especie}`

<h3 id="contarrazasporespecie-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|especie|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

> Example responses

> 200 Response

<h3 id="contarrazasporespecie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|integer|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorNombre

<a id="opIdbuscarPorNombre"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/buscar?nombre=string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/buscar?nombre=string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/buscar?nombre=string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/buscar',
  params: {
  'nombre' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/buscar', params={
  'nombre': 'string'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/buscar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/buscar?nombre=string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/buscar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/buscar`

<h3 id="buscarpornombre-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|nombre|query|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarpornombre-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarpornombre-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerRazasActivas

<a id="opIdobtenerRazasActivas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/razas/activas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/razas/activas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/razas/activas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/razas/activas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/razas/activas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/razas/activas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/razas/activas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/razas/activas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/razas/activas`

> Example responses

> 200 Response

<h3 id="obtenerrazasactivas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerrazasactivas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[RazaDTO](#schemarazadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» descripcion|string|false|none|none|
|» esPredefinida|boolean|false|none|none|
|» esMestizo|boolean|false|none|none|
|» tamanioTipico|string|false|none|none|
|» pesoPromedioKg|number(double)|false|none|none|
|» isActive|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-paciente-controller">paciente-controller</h1>

## obtenerPacientePorId

<a id="opIdobtenerPacientePorId"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/{id}`

<h3 id="obtenerpacienteporid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerpacienteporid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizarPaciente

<a id="opIdactualizarPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/pacientes/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/pacientes/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "raza": "string",
  "color": "string",
  "pesoKg": 0.1,
  "estado": "ACTIVO",
  "fotoUrl": "string",
  "observaciones": "string",
  "clienteId": 0
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/pacientes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/pacientes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/pacientes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/pacientes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/pacientes/{id}`

> Body parameter

```json
{
  "raza": "string",
  "color": "string",
  "pesoKg": 0.1,
  "estado": "ACTIVO",
  "fotoUrl": "string",
  "observaciones": "string",
  "clienteId": 0
}
```

<h3 id="actualizarpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdatePacienteRequest](#schemaupdatepacienterequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizarpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminarPaciente

<a id="opIdeliminarPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/pacientes/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/pacientes/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/pacientes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/pacientes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/pacientes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/pacientes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/pacientes/{id}`

<h3 id="eliminarpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminarpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerTodosLosPacientes

<a id="opIdobtenerTodosLosPacientes"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes`

> Example responses

> 200 Response

<h3 id="obtenertodoslospacientes-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenertodoslospacientes-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crearPaciente

<a id="opIdcrearPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/pacientes \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/pacientes HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nombre": "string",
  "especie": "PERRO",
  "raza": "string",
  "fechaNacimiento": "2019-08-24",
  "sexo": "MACHO",
  "color": "string",
  "pesoKg": 0.1,
  "fotoUrl": "string",
  "observaciones": "string",
  "microchip": "string",
  "clienteId": 0
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/pacientes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/pacientes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/pacientes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/pacientes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/pacientes`

> Body parameter

```json
{
  "nombre": "string",
  "especie": "PERRO",
  "raza": "string",
  "fechaNacimiento": "2019-08-24",
  "sexo": "MACHO",
  "color": "string",
  "pesoKg": 0.1,
  "fotoUrl": "string",
  "observaciones": "string",
  "microchip": "string",
  "clienteId": 0
}
```

<h3 id="crearpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreatePacienteRequest](#schemacreatepacienterequest)|true|none|

> Example responses

> 200 Response

<h3 id="crearpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## reactivarPaciente

<a id="opIdreactivarPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/pacientes/{id}/reactivar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/pacientes/{id}/reactivar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/{id}/reactivar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/pacientes/{id}/reactivar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/pacientes/{id}/reactivar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/pacientes/{id}/reactivar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/{id}/reactivar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/pacientes/{id}/reactivar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/pacientes/{id}/reactivar`

<h3 id="reactivarpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="reactivarpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## marcarComoFallecido

<a id="opIdmarcarComoFallecido"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/pacientes/{id}/marcar-fallecido", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/pacientes/{id}/marcar-fallecido`

<h3 id="marcarcomofallecido-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="marcarcomofallecido-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cambiarEstado

<a id="opIdcambiarEstado"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nuevoEstado": "ACTIVO",
  "motivo": "string",
  "fechaCambio": "2019-08-24"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado',
{
  method: 'PATCH',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/pacientes/{id}/cambiar-estado`

> Body parameter

```json
{
  "nuevoEstado": "ACTIVO",
  "motivo": "string",
  "fechaCambio": "2019-08-24"
}
```

<h3 id="cambiarestado-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[CambiarEstadoPacienteRequest](#schemacambiarestadopacienterequest)|true|none|

> Example responses

> 200 Response

<h3 id="cambiarestado-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorMicrochip

<a id="opIdbuscarPorMicrochip"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/microchip/{microchip} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/microchip/{microchip} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/microchip/{microchip}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/microchip/{microchip}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/microchip/{microchip}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/microchip/{microchip}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/microchip/{microchip}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/microchip/{microchip}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/microchip/{microchip}`

<h3 id="buscarpormicrochip-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|microchip|path|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarpormicrochip-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PacienteDTO](#schemapacientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorEspecieYEstado

<a id="opIdbuscarPorEspecieYEstado"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/filtrar?especie=PERRO&estado=ACTIVO \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/filtrar?especie=PERRO&estado=ACTIVO HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/filtrar?especie=PERRO&estado=ACTIVO',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/filtrar',
  params: {
  'especie' => 'string',
'estado' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/filtrar', params={
  'especie': 'PERRO',  'estado': 'ACTIVO'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/filtrar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/filtrar?especie=PERRO&estado=ACTIVO");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/filtrar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/filtrar`

<h3 id="buscarporespecieyestado-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|especie|query|string|true|none|
|estado|query|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

> Example responses

> 200 Response

<h3 id="buscarporespecieyestado-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarporespecieyestado-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorEstado

<a id="opIdbuscarPorEstado"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/estado/{estado} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/estado/{estado} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/estado/{estado}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/estado/{estado}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/estado/{estado}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/estado/{estado}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/estado/{estado}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/estado/{estado}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/estado/{estado}`

<h3 id="buscarporestado-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|estado|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

> Example responses

> 200 Response

<h3 id="buscarporestado-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarporestado-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorEspecie

<a id="opIdbuscarPorEspecie"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/especie/{especie} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/especie/{especie} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/especie/{especie}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/especie/{especie}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/especie/{especie}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/especie/{especie}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/especie/{especie}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/especie/{especie}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/especie/{especie}`

<h3 id="buscarporespecie-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|especie|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

> Example responses

> 200 Response

<h3 id="buscarporespecie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarporespecie-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## contarPorEstado

<a id="opIdcontarPorEstado"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/contar/estado/{estado} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/contar/estado/{estado} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/contar/estado/{estado}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/contar/estado/{estado}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/contar/estado/{estado}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/contar/estado/{estado}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/contar/estado/{estado}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/contar/estado/{estado}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/contar/estado/{estado}`

<h3 id="contarporestado-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|estado|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

> Example responses

> 200 Response

<h3 id="contarporestado-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|integer|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorCliente

<a id="opIdbuscarPorCliente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/cliente/{clienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/cliente/{clienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/cliente/{clienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/cliente/{clienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/cliente/{clienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/cliente/{clienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/cliente/{clienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/cliente/{clienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/cliente/{clienteId}`

<h3 id="buscarporcliente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|clienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="buscarporcliente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarporcliente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPacientesActivosPorCliente

<a id="opIdbuscarPacientesActivosPorCliente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/cliente/{clienteId}/activos`

<h3 id="buscarpacientesactivosporcliente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|clienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="buscarpacientesactivosporcliente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarpacientesactivosporcliente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorNombre_1

<a id="opIdbuscarPorNombre_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/pacientes/buscar?nombre=string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/pacientes/buscar?nombre=string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/pacientes/buscar?nombre=string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/pacientes/buscar',
  params: {
  'nombre' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/pacientes/buscar', params={
  'nombre': 'string'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/pacientes/buscar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/pacientes/buscar?nombre=string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/pacientes/buscar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/pacientes/buscar`

<h3 id="buscarpornombre_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|nombre|query|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarpornombre_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarpornombre_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[PacienteDTO](#schemapacientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» especie|string|false|none|none|
|» raza|string|false|none|none|
|» fechaNacimiento|string(date)|false|none|none|
|» edadAnios|integer(int32)|false|none|none|
|» sexo|string|false|none|none|
|» color|string|false|none|none|
|» pesoKg|number(double)|false|none|none|
|» estado|string|false|none|none|
|» fotoUrl|string|false|none|none|
|» observaciones|string|false|none|none|
|» microchip|string|false|none|none|
|» cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombreCompleto|string|false|none|none|
|»» telefono|string|false|none|none|
|»» email|string|false|none|none|
|» cuidadosEspecificos|string|false|none|none|
|» dietaRecomendada|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-historial-clinico-controller">historial-clinico-controller</h1>

## obtenerPorId_2

<a id="opIdobtenerPorId_2"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/historiales-clinicos/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/historiales-clinicos/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/historiales-clinicos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/historiales-clinicos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/historiales-clinicos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/historiales-clinicos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/historiales-clinicos/{id}`

<h3 id="obtenerporid_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[HistorialClinicoDTO](#schemahistorialclinicodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizar_2

<a id="opIdactualizar_2"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/historiales-clinicos/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/historiales-clinicos/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "grupoSanguineo": "string",
  "alergias": "string",
  "condicionesCronicas": "string",
  "notasImportantes": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/historiales-clinicos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/historiales-clinicos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/historiales-clinicos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/historiales-clinicos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/historiales-clinicos/{id}`

> Body parameter

```json
{
  "grupoSanguineo": "string",
  "alergias": "string",
  "condicionesCronicas": "string",
  "notasImportantes": "string"
}
```

<h3 id="actualizar_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateHistorialClinicoRequest](#schemaupdatehistorialclinicorequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizar_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[HistorialClinicoDTO](#schemahistorialclinicodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_1

<a id="opIdeliminar_1"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/historiales-clinicos/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/historiales-clinicos/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/historiales-clinicos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/historiales-clinicos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/historiales-clinicos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/historiales-clinicos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/historiales-clinicos/{id}`

<h3 id="eliminar_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorPaciente

<a id="opIdobtenerPorPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/historiales-clinicos/paciente/{pacienteId}`

<h3 id="obtenerporpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[HistorialClinicoDTO](#schemahistorialclinicodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crear_3

<a id="opIdcrear_3"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}',
{
  method: 'POST',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/historiales-clinicos/paciente/{pacienteId}`

<h3 id="crear_3-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="crear_3-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[HistorialClinicoDTO](#schemahistorialclinicodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarTodos_1

<a id="opIdlistarTodos_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/historiales-clinicos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/historiales-clinicos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/historiales-clinicos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/historiales-clinicos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/historiales-clinicos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/historiales-clinicos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/historiales-clinicos`

> Example responses

> 200 Response

<h3 id="listartodos_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listartodos_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[HistorialClinicoDTO](#schemahistorialclinicodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaApertura|string(date)|false|none|none|
|» grupoSanguineo|string|false|none|none|
|» alergias|string|false|none|none|
|» condicionesCronicas|string|false|none|none|
|» notasImportantes|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» pacienteRaza|string|false|none|none|
|» consultas|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» fechaConsulta|string(date)|false|none|none|
|»» horaInicio|string(date-time)|false|none|none|
|»» horaFin|string(date-time)|false|none|none|
|»» motivo|string|false|none|none|
|»» anamnesis|string|false|none|none|
|»» examenFisico|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» planTratamiento|string|false|none|none|
|»» pronostico|string|false|none|none|
|»» requiereSeguimiento|boolean|false|none|none|
|»» fechaSeguimiento|string(date)|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» pacienteEspecie|string|false|none|none|
|»» veterinarioId|integer(int64)|false|none|none|
|»» veterinarioNombre|string|false|none|none|
|»» veterinarioEspecialidad|string|false|none|none|
|»» citaId|integer(int64)|false|none|none|
|»» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»»» temperatura|number(double)|false|none|none|
|»»» pesoKg|number(double)|false|none|none|
|»»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»»» temperaturaRectal|number(double)|false|none|none|
|»»» condicionCorporal|integer(int32)|false|none|none|
|»»» condicionCorporalDescripcion|string|false|none|none|
|»»» tieneParametrosAnormales|boolean|false|none|none|
|»»» tieneFiebre|boolean|false|none|none|
|»»» tieneHipotermia|boolean|false|none|none|
|»» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» tipoDiagnostico|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» codigoCie10|string|false|none|none|
|»»» gravedad|string|false|none|none|
|»»» notas|string|false|none|none|
|»»» esPrincipal|boolean|false|none|none|
|»»» consultaId|integer(int64)|false|none|none|
|»»» esPresuntivo|boolean|false|none|none|
|»»» esDefinitivo|boolean|false|none|none|
|»»» esGraveOCritico|boolean|false|none|none|
|»»» tieneCie10|boolean|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» updatedBy|string|false|none|none|
|»» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» tipoTratamiento|string|false|none|none|
|»»» nombre|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» dosis|string|false|none|none|
|»»» frecuencia|string|false|none|none|
|»»» viaAdministracion|string|false|none|none|
|»»» duracionDias|integer(int32)|false|none|none|
|»»» fechaInicio|string(date)|false|none|none|
|»»» fechaFin|string(date)|false|none|none|
|»»» indicaciones|string|false|none|none|
|»»» efectosSecundarios|string|false|none|none|
|»»» cantidad|number(double)|false|none|none|
|»»» unidad|string|false|none|none|
|»»» activo|boolean|false|none|none|
|»»» requiereSupervision|boolean|false|none|none|
|»»» consultaId|integer(int64)|false|none|none|
|»»» esMedicamento|boolean|false|none|none|
|»»» haFinalizado|boolean|false|none|none|
|»»» estaVigente|boolean|false|none|none|
|»»» resumen|string|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» updatedBy|string|false|none|none|
|»» duracionMinutos|integer(int64)|false|none|none|
|»» estaEnCurso|boolean|false|none|none|
|»» estaFinalizada|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» vacunas|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» tipoVacuna|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» fechaProximaDosis|string(date)|false|none|none|
|»» lote|string|false|none|none|
|»» laboratorio|string|false|none|none|
|»» dosis|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» numeroDosis|integer(int32)|false|none|none|
|»» intervaloDias|integer(int32)|false|none|none|
|»» esRefuerzo|boolean|false|none|none|
|»» observaciones|string|false|none|none|
|»» reaccionesAdversas|string|false|none|none|
|»» pesoAplicacion|number(double)|false|none|none|
|»» serieCompleta|boolean|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» veterinarioId|integer(int64)|false|none|none|
|»» veterinarioNombre|string|false|none|none|
|»» esObligatoria|boolean|false|none|none|
|»» esAntirrábica|boolean|false|none|none|
|»» refuerzoVencido|boolean|false|none|none|
|»» refuerzoProximo|boolean|false|none|none|
|»» tuvoReacciones|boolean|false|none|none|
|»» estado|string|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» examenes|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoExamen|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» fechaSolicitud|string(date)|false|none|none|
|»» fechaRealizacion|string(date)|false|none|none|
|»» fechaResultados|string(date-time)|false|none|none|
|»» estado|string|false|none|none|
|»» resultados|string|false|none|none|
|»» valoresReferencia|string|false|none|none|
|»» interpretacion|string|false|none|none|
|»» hallazgos|string|false|none|none|
|»» archivoRuta|string|false|none|none|
|»» archivoTipo|string|false|none|none|
|»» laboratorioExterno|string|false|none|none|
|»» referenciaExterna|string|false|none|none|
|»» costo|number(double)|false|none|none|
|»» notas|string|false|none|none|
|»» requiereAyuno|boolean|false|none|none|
|»» esUrgente|boolean|false|none|none|
|»» resultadoAnormal|boolean|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» veterinarioSolicitanteId|integer(int64)|false|none|none|
|»» veterinarioSolicitanteNombre|string|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» estaPendiente|boolean|false|none|none|
|»» estaCompletado|boolean|false|none|none|
|»» esLaboratorio|boolean|false|none|none|
|»» esImagenologia|boolean|false|none|none|
|»» tieneArchivo|boolean|false|none|none|
|»» esExterno|boolean|false|none|none|
|»» diasDesdeSolicitud|integer(int64)|false|none|none|
|»» estadoDescriptivo|string|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» ultimaConsulta|[ConsultaDTO](#schemaconsultadto)|false|none|none|
|» totalConsultas|integer(int32)|false|none|none|
|» totalVacunas|integer(int32)|false|none|none|
|» totalExamenes|integer(int32)|false|none|none|
|» tieneAlergias|boolean|false|none|none|
|» tieneCondicionesCronicas|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## existeHistorialPaciente

<a id="opIdexisteHistorialPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/historiales-clinicos/paciente/{pacienteId}/existe", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/historiales-clinicos/paciente/{pacienteId}/existe`

<h3 id="existehistorialpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="existehistorialpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|boolean|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConCondicionesCronicas

<a id="opIdlistarConCondicionesCronicas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/historiales-clinicos/con-condiciones-cronicas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/historiales-clinicos/con-condiciones-cronicas`

> Example responses

> 200 Response

<h3 id="listarconcondicionescronicas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconcondicionescronicas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[HistorialClinicoDTO](#schemahistorialclinicodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaApertura|string(date)|false|none|none|
|» grupoSanguineo|string|false|none|none|
|» alergias|string|false|none|none|
|» condicionesCronicas|string|false|none|none|
|» notasImportantes|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» pacienteRaza|string|false|none|none|
|» consultas|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» fechaConsulta|string(date)|false|none|none|
|»» horaInicio|string(date-time)|false|none|none|
|»» horaFin|string(date-time)|false|none|none|
|»» motivo|string|false|none|none|
|»» anamnesis|string|false|none|none|
|»» examenFisico|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» planTratamiento|string|false|none|none|
|»» pronostico|string|false|none|none|
|»» requiereSeguimiento|boolean|false|none|none|
|»» fechaSeguimiento|string(date)|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» pacienteEspecie|string|false|none|none|
|»» veterinarioId|integer(int64)|false|none|none|
|»» veterinarioNombre|string|false|none|none|
|»» veterinarioEspecialidad|string|false|none|none|
|»» citaId|integer(int64)|false|none|none|
|»» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»»» temperatura|number(double)|false|none|none|
|»»» pesoKg|number(double)|false|none|none|
|»»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»»» temperaturaRectal|number(double)|false|none|none|
|»»» condicionCorporal|integer(int32)|false|none|none|
|»»» condicionCorporalDescripcion|string|false|none|none|
|»»» tieneParametrosAnormales|boolean|false|none|none|
|»»» tieneFiebre|boolean|false|none|none|
|»»» tieneHipotermia|boolean|false|none|none|
|»» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» tipoDiagnostico|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» codigoCie10|string|false|none|none|
|»»» gravedad|string|false|none|none|
|»»» notas|string|false|none|none|
|»»» esPrincipal|boolean|false|none|none|
|»»» consultaId|integer(int64)|false|none|none|
|»»» esPresuntivo|boolean|false|none|none|
|»»» esDefinitivo|boolean|false|none|none|
|»»» esGraveOCritico|boolean|false|none|none|
|»»» tieneCie10|boolean|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» updatedBy|string|false|none|none|
|»» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» tipoTratamiento|string|false|none|none|
|»»» nombre|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» dosis|string|false|none|none|
|»»» frecuencia|string|false|none|none|
|»»» viaAdministracion|string|false|none|none|
|»»» duracionDias|integer(int32)|false|none|none|
|»»» fechaInicio|string(date)|false|none|none|
|»»» fechaFin|string(date)|false|none|none|
|»»» indicaciones|string|false|none|none|
|»»» efectosSecundarios|string|false|none|none|
|»»» cantidad|number(double)|false|none|none|
|»»» unidad|string|false|none|none|
|»»» activo|boolean|false|none|none|
|»»» requiereSupervision|boolean|false|none|none|
|»»» consultaId|integer(int64)|false|none|none|
|»»» esMedicamento|boolean|false|none|none|
|»»» haFinalizado|boolean|false|none|none|
|»»» estaVigente|boolean|false|none|none|
|»»» resumen|string|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» updatedBy|string|false|none|none|
|»» duracionMinutos|integer(int64)|false|none|none|
|»» estaEnCurso|boolean|false|none|none|
|»» estaFinalizada|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» vacunas|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» tipoVacuna|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» fechaProximaDosis|string(date)|false|none|none|
|»» lote|string|false|none|none|
|»» laboratorio|string|false|none|none|
|»» dosis|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» numeroDosis|integer(int32)|false|none|none|
|»» intervaloDias|integer(int32)|false|none|none|
|»» esRefuerzo|boolean|false|none|none|
|»» observaciones|string|false|none|none|
|»» reaccionesAdversas|string|false|none|none|
|»» pesoAplicacion|number(double)|false|none|none|
|»» serieCompleta|boolean|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» veterinarioId|integer(int64)|false|none|none|
|»» veterinarioNombre|string|false|none|none|
|»» esObligatoria|boolean|false|none|none|
|»» esAntirrábica|boolean|false|none|none|
|»» refuerzoVencido|boolean|false|none|none|
|»» refuerzoProximo|boolean|false|none|none|
|»» tuvoReacciones|boolean|false|none|none|
|»» estado|string|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» examenes|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoExamen|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» fechaSolicitud|string(date)|false|none|none|
|»» fechaRealizacion|string(date)|false|none|none|
|»» fechaResultados|string(date-time)|false|none|none|
|»» estado|string|false|none|none|
|»» resultados|string|false|none|none|
|»» valoresReferencia|string|false|none|none|
|»» interpretacion|string|false|none|none|
|»» hallazgos|string|false|none|none|
|»» archivoRuta|string|false|none|none|
|»» archivoTipo|string|false|none|none|
|»» laboratorioExterno|string|false|none|none|
|»» referenciaExterna|string|false|none|none|
|»» costo|number(double)|false|none|none|
|»» notas|string|false|none|none|
|»» requiereAyuno|boolean|false|none|none|
|»» esUrgente|boolean|false|none|none|
|»» resultadoAnormal|boolean|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» veterinarioSolicitanteId|integer(int64)|false|none|none|
|»» veterinarioSolicitanteNombre|string|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» estaPendiente|boolean|false|none|none|
|»» estaCompletado|boolean|false|none|none|
|»» esLaboratorio|boolean|false|none|none|
|»» esImagenologia|boolean|false|none|none|
|»» tieneArchivo|boolean|false|none|none|
|»» esExterno|boolean|false|none|none|
|»» diasDesdeSolicitud|integer(int64)|false|none|none|
|»» estadoDescriptivo|string|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» ultimaConsulta|[ConsultaDTO](#schemaconsultadto)|false|none|none|
|» totalConsultas|integer(int32)|false|none|none|
|» totalVacunas|integer(int32)|false|none|none|
|» totalExamenes|integer(int32)|false|none|none|
|» tieneAlergias|boolean|false|none|none|
|» tieneCondicionesCronicas|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConAlergias

<a id="opIdlistarConAlergias"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/historiales-clinicos/con-alergias \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/historiales-clinicos/con-alergias HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/historiales-clinicos/con-alergias',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/historiales-clinicos/con-alergias',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/historiales-clinicos/con-alergias', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/historiales-clinicos/con-alergias', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/historiales-clinicos/con-alergias");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/historiales-clinicos/con-alergias", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/historiales-clinicos/con-alergias`

> Example responses

> 200 Response

<h3 id="listarconalergias-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconalergias-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[HistorialClinicoDTO](#schemahistorialclinicodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaApertura|string(date)|false|none|none|
|» grupoSanguineo|string|false|none|none|
|» alergias|string|false|none|none|
|» condicionesCronicas|string|false|none|none|
|» notasImportantes|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» pacienteRaza|string|false|none|none|
|» consultas|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» fechaConsulta|string(date)|false|none|none|
|»» horaInicio|string(date-time)|false|none|none|
|»» horaFin|string(date-time)|false|none|none|
|»» motivo|string|false|none|none|
|»» anamnesis|string|false|none|none|
|»» examenFisico|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» planTratamiento|string|false|none|none|
|»» pronostico|string|false|none|none|
|»» requiereSeguimiento|boolean|false|none|none|
|»» fechaSeguimiento|string(date)|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» pacienteEspecie|string|false|none|none|
|»» veterinarioId|integer(int64)|false|none|none|
|»» veterinarioNombre|string|false|none|none|
|»» veterinarioEspecialidad|string|false|none|none|
|»» citaId|integer(int64)|false|none|none|
|»» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»»» temperatura|number(double)|false|none|none|
|»»» pesoKg|number(double)|false|none|none|
|»»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»»» temperaturaRectal|number(double)|false|none|none|
|»»» condicionCorporal|integer(int32)|false|none|none|
|»»» condicionCorporalDescripcion|string|false|none|none|
|»»» tieneParametrosAnormales|boolean|false|none|none|
|»»» tieneFiebre|boolean|false|none|none|
|»»» tieneHipotermia|boolean|false|none|none|
|»» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» tipoDiagnostico|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» codigoCie10|string|false|none|none|
|»»» gravedad|string|false|none|none|
|»»» notas|string|false|none|none|
|»»» esPrincipal|boolean|false|none|none|
|»»» consultaId|integer(int64)|false|none|none|
|»»» esPresuntivo|boolean|false|none|none|
|»»» esDefinitivo|boolean|false|none|none|
|»»» esGraveOCritico|boolean|false|none|none|
|»»» tieneCie10|boolean|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» updatedBy|string|false|none|none|
|»» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»»» id|integer(int64)|false|none|none|
|»»» tipoTratamiento|string|false|none|none|
|»»» nombre|string|false|none|none|
|»»» descripcion|string|false|none|none|
|»»» dosis|string|false|none|none|
|»»» frecuencia|string|false|none|none|
|»»» viaAdministracion|string|false|none|none|
|»»» duracionDias|integer(int32)|false|none|none|
|»»» fechaInicio|string(date)|false|none|none|
|»»» fechaFin|string(date)|false|none|none|
|»»» indicaciones|string|false|none|none|
|»»» efectosSecundarios|string|false|none|none|
|»»» cantidad|number(double)|false|none|none|
|»»» unidad|string|false|none|none|
|»»» activo|boolean|false|none|none|
|»»» requiereSupervision|boolean|false|none|none|
|»»» consultaId|integer(int64)|false|none|none|
|»»» esMedicamento|boolean|false|none|none|
|»»» haFinalizado|boolean|false|none|none|
|»»» estaVigente|boolean|false|none|none|
|»»» resumen|string|false|none|none|
|»»» createdAt|string(date-time)|false|none|none|
|»»» createdBy|string|false|none|none|
|»»» updatedAt|string(date-time)|false|none|none|
|»»» updatedBy|string|false|none|none|
|»» duracionMinutos|integer(int64)|false|none|none|
|»» estaEnCurso|boolean|false|none|none|
|»» estaFinalizada|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» vacunas|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» tipoVacuna|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» fechaProximaDosis|string(date)|false|none|none|
|»» lote|string|false|none|none|
|»» laboratorio|string|false|none|none|
|»» dosis|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» numeroDosis|integer(int32)|false|none|none|
|»» intervaloDias|integer(int32)|false|none|none|
|»» esRefuerzo|boolean|false|none|none|
|»» observaciones|string|false|none|none|
|»» reaccionesAdversas|string|false|none|none|
|»» pesoAplicacion|number(double)|false|none|none|
|»» serieCompleta|boolean|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» veterinarioId|integer(int64)|false|none|none|
|»» veterinarioNombre|string|false|none|none|
|»» esObligatoria|boolean|false|none|none|
|»» esAntirrábica|boolean|false|none|none|
|»» refuerzoVencido|boolean|false|none|none|
|»» refuerzoProximo|boolean|false|none|none|
|»» tuvoReacciones|boolean|false|none|none|
|»» estado|string|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» examenes|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoExamen|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» fechaSolicitud|string(date)|false|none|none|
|»» fechaRealizacion|string(date)|false|none|none|
|»» fechaResultados|string(date-time)|false|none|none|
|»» estado|string|false|none|none|
|»» resultados|string|false|none|none|
|»» valoresReferencia|string|false|none|none|
|»» interpretacion|string|false|none|none|
|»» hallazgos|string|false|none|none|
|»» archivoRuta|string|false|none|none|
|»» archivoTipo|string|false|none|none|
|»» laboratorioExterno|string|false|none|none|
|»» referenciaExterna|string|false|none|none|
|»» costo|number(double)|false|none|none|
|»» notas|string|false|none|none|
|»» requiereAyuno|boolean|false|none|none|
|»» esUrgente|boolean|false|none|none|
|»» resultadoAnormal|boolean|false|none|none|
|»» historialClinicoId|integer(int64)|false|none|none|
|»» pacienteId|integer(int64)|false|none|none|
|»» pacienteNombre|string|false|none|none|
|»» veterinarioSolicitanteId|integer(int64)|false|none|none|
|»» veterinarioSolicitanteNombre|string|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» estaPendiente|boolean|false|none|none|
|»» estaCompletado|boolean|false|none|none|
|»» esLaboratorio|boolean|false|none|none|
|»» esImagenologia|boolean|false|none|none|
|»» tieneArchivo|boolean|false|none|none|
|»» esExterno|boolean|false|none|none|
|»» diasDesdeSolicitud|integer(int64)|false|none|none|
|»» estadoDescriptivo|string|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» ultimaConsulta|[ConsultaDTO](#schemaconsultadto)|false|none|none|
|» totalConsultas|integer(int32)|false|none|none|
|» totalVacunas|integer(int32)|false|none|none|
|» totalExamenes|integer(int32)|false|none|none|
|» tieneAlergias|boolean|false|none|none|
|» tieneCondicionesCronicas|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-factura-controller">factura-controller</h1>

## obtenerPorId_3

<a id="opIdobtenerPorId_3"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/{id}`

<h3 id="obtenerporid_3-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_3-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FacturaDTO](#schemafacturadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizar_3

<a id="opIdactualizar_3"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/facturas/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/facturas/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "fechaVencimiento": "2019-08-24",
  "observaciones": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/facturas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/facturas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/facturas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/facturas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/facturas/{id}`

> Body parameter

```json
{
  "fechaVencimiento": "2019-08-24",
  "observaciones": "string"
}
```

<h3 id="actualizar_3-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateFacturaRequest](#schemaupdatefacturarequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizar_3-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FacturaDTO](#schemafacturadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_2

<a id="opIdeliminar_2"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/facturas/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/facturas/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/facturas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/facturas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/facturas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/facturas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/facturas/{id}`

<h3 id="eliminar_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crear_4

<a id="opIdcrear_4"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/facturas \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/facturas HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "clienteId": 0,
  "citaId": 0,
  "usuarioEmisorId": 0,
  "fechaEmision": "2019-08-24",
  "fechaVencimiento": "2019-08-24",
  "observaciones": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/facturas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/facturas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/facturas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/facturas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/facturas`

> Body parameter

```json
{
  "clienteId": 0,
  "citaId": 0,
  "usuarioEmisorId": 0,
  "fechaEmision": "2019-08-24",
  "fechaVencimiento": "2019-08-24",
  "observaciones": "string"
}
```

<h3 id="crear_4-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateFacturaRequest](#schemacreatefacturarequest)|true|none|

> Example responses

> 200 Response

<h3 id="crear_4-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FacturaDTO](#schemafacturadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## registrarPago

<a id="opIdregistrarPago"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/facturas/{facturaId}/pagos \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/facturas/{facturaId}/pagos HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "fechaPago": "2019-08-24",
  "metodoPago": "EFECTIVO",
  "monto": 0.1,
  "numeroReferencia": "string",
  "numeroVoucher": "string",
  "usuarioRegistroId": 0,
  "observaciones": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{facturaId}/pagos',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/facturas/{facturaId}/pagos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/facturas/{facturaId}/pagos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/facturas/{facturaId}/pagos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{facturaId}/pagos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/facturas/{facturaId}/pagos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/facturas/{facturaId}/pagos`

> Body parameter

```json
{
  "fechaPago": "2019-08-24",
  "metodoPago": "EFECTIVO",
  "monto": 0.1,
  "numeroReferencia": "string",
  "numeroVoucher": "string",
  "usuarioRegistroId": 0,
  "observaciones": "string"
}
```

<h3 id="registrarpago-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|facturaId|path|integer(int64)|true|none|
|body|body|[CreatePagoRequest](#schemacreatepagorequest)|true|none|

> Example responses

> 200 Response

<h3 id="registrarpago-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[PagoDTO](#schemapagodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## agregarDetalle

<a id="opIdagregarDetalle"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/facturas/{facturaId}/detalles \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/facturas/{facturaId}/detalles HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "tipoServicioId": 0,
  "descripcion": "string",
  "cantidad": 0,
  "precioUnitario": 0.1,
  "descuentoPorcentaje": 0.1,
  "observaciones": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{facturaId}/detalles',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/facturas/{facturaId}/detalles',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/facturas/{facturaId}/detalles', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/facturas/{facturaId}/detalles', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{facturaId}/detalles");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/facturas/{facturaId}/detalles", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/facturas/{facturaId}/detalles`

> Body parameter

```json
{
  "tipoServicioId": 0,
  "descripcion": "string",
  "cantidad": 0,
  "precioUnitario": 0.1,
  "descuentoPorcentaje": 0.1,
  "observaciones": "string"
}
```

<h3 id="agregardetalle-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|facturaId|path|integer(int64)|true|none|
|body|body|[CreateDetalleFacturaRequest](#schemacreatedetallefacturarequest)|true|none|

> Example responses

> 200 Response

<h3 id="agregardetalle-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[DetalleFacturaDTO](#schemadetallefacturadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## aplicarDescuento

<a id="opIdaplicarDescuento"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/facturas/{facturaId}/descuentos \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/facturas/{facturaId}/descuentos HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "codigo": "string",
  "descripcion": "string",
  "tipo": "PORCENTAJE",
  "valor": 0.1,
  "motivo": "string",
  "esPromocional": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{facturaId}/descuentos',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/facturas/{facturaId}/descuentos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/facturas/{facturaId}/descuentos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/facturas/{facturaId}/descuentos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{facturaId}/descuentos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/facturas/{facturaId}/descuentos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/facturas/{facturaId}/descuentos`

> Body parameter

```json
{
  "codigo": "string",
  "descripcion": "string",
  "tipo": "PORCENTAJE",
  "valor": 0.1,
  "motivo": "string",
  "esPromocional": true
}
```

<h3 id="aplicardescuento-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|facturaId|path|integer(int64)|true|none|
|body|body|[CreateDescuentoRequest](#schemacreatedescuentorequest)|true|none|

> Example responses

> 200 Response

<h3 id="aplicardescuento-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[DescuentoDTO](#schemadescuentodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## anular

<a id="opIdanular"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/facturas/{id}/anular \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/facturas/{id}/anular HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "motivoAnulacion": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{id}/anular',
{
  method: 'PATCH',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/facturas/{id}/anular',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/facturas/{id}/anular', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/facturas/{id}/anular', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{id}/anular");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/facturas/{id}/anular", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/facturas/{id}/anular`

> Body parameter

```json
{
  "motivoAnulacion": "string"
}
```

<h3 id="anular-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[AnularFacturaRequest](#schemaanularfacturarequest)|true|none|

> Example responses

> 200 Response

<h3 id="anular-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FacturaDTO](#schemafacturadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarVencidas

<a id="opIdlistarVencidas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/vencidas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/vencidas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/vencidas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/vencidas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/vencidas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/vencidas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/vencidas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/vencidas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/vencidas`

> Example responses

> 200 Response

<h3 id="listarvencidas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarvencidas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[FacturaDTO](#schemafacturadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» numeroFactura|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» usuarioEmisorId|integer(int64)|false|none|none|
|» usuarioEmisorNombre|string|false|none|none|
|» fechaEmision|string(date)|false|none|none|
|» fechaVencimiento|string(date)|false|none|none|
|» estado|string|false|none|none|
|» subtotal|number(double)|false|none|none|
|» totalDescuentos|number(double)|false|none|none|
|» totalImpuestos|number(double)|false|none|none|
|» total|number(double)|false|none|none|
|» totalPagado|number(double)|false|none|none|
|» saldoPendiente|number(double)|false|none|none|
|» observaciones|string|false|none|none|
|» motivoAnulacion|string|false|none|none|
|» fechaAnulacion|string(date)|false|none|none|
|» detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» tipoServicioId|integer(int64)|false|none|none|
|»» tipoServicioNombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» cantidad|integer(int32)|false|none|none|
|»» precioUnitario|number(double)|false|none|none|
|»» subtotal|number(double)|false|none|none|
|»» descuentoPorcentaje|number(double)|false|none|none|
|»» descuentoMonto|number(double)|false|none|none|
|»» total|number(double)|false|none|none|
|»» observaciones|string|false|none|none|
|» pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» fechaPago|string(date)|false|none|none|
|»» horaPago|string(date-time)|false|none|none|
|»» metodoPago|string|false|none|none|
|»» monto|number(double)|false|none|none|
|»» comision|number(double)|false|none|none|
|»» montoNeto|number(double)|false|none|none|
|»» numeroReferencia|string|false|none|none|
|»» numeroVoucher|string|false|none|none|
|»» usuarioRegistroId|integer(int64)|false|none|none|
|»» usuarioRegistroNombre|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» verificado|boolean|false|none|none|
|»» fechaVerificacion|string(date-time)|false|none|none|
|»» usuarioVerificacionId|integer(int64)|false|none|none|
|»» usuarioVerificacionNombre|string|false|none|none|
|» descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» codigo|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» tipo|string|false|none|none|
|»» valor|number(double)|false|none|none|
|»» monto|number(double)|false|none|none|
|»» motivo|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» esPromocional|boolean|false|none|none|
|»» esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPendientes

<a id="opIdlistarPendientes"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/pendientes \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/pendientes HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/pendientes',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/pendientes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/pendientes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/pendientes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/pendientes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/pendientes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/pendientes`

> Example responses

> 200 Response

<h3 id="listarpendientes-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarpendientes-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[FacturaDTO](#schemafacturadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» numeroFactura|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» usuarioEmisorId|integer(int64)|false|none|none|
|» usuarioEmisorNombre|string|false|none|none|
|» fechaEmision|string(date)|false|none|none|
|» fechaVencimiento|string(date)|false|none|none|
|» estado|string|false|none|none|
|» subtotal|number(double)|false|none|none|
|» totalDescuentos|number(double)|false|none|none|
|» totalImpuestos|number(double)|false|none|none|
|» total|number(double)|false|none|none|
|» totalPagado|number(double)|false|none|none|
|» saldoPendiente|number(double)|false|none|none|
|» observaciones|string|false|none|none|
|» motivoAnulacion|string|false|none|none|
|» fechaAnulacion|string(date)|false|none|none|
|» detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» tipoServicioId|integer(int64)|false|none|none|
|»» tipoServicioNombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» cantidad|integer(int32)|false|none|none|
|»» precioUnitario|number(double)|false|none|none|
|»» subtotal|number(double)|false|none|none|
|»» descuentoPorcentaje|number(double)|false|none|none|
|»» descuentoMonto|number(double)|false|none|none|
|»» total|number(double)|false|none|none|
|»» observaciones|string|false|none|none|
|» pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» fechaPago|string(date)|false|none|none|
|»» horaPago|string(date-time)|false|none|none|
|»» metodoPago|string|false|none|none|
|»» monto|number(double)|false|none|none|
|»» comision|number(double)|false|none|none|
|»» montoNeto|number(double)|false|none|none|
|»» numeroReferencia|string|false|none|none|
|»» numeroVoucher|string|false|none|none|
|»» usuarioRegistroId|integer(int64)|false|none|none|
|»» usuarioRegistroNombre|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» verificado|boolean|false|none|none|
|»» fechaVerificacion|string(date-time)|false|none|none|
|»» usuarioVerificacionId|integer(int64)|false|none|none|
|»» usuarioVerificacionNombre|string|false|none|none|
|» descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» codigo|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» tipo|string|false|none|none|
|»» valor|number(double)|false|none|none|
|»» monto|number(double)|false|none|none|
|»» motivo|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» esPromocional|boolean|false|none|none|
|»» esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorNumero

<a id="opIdobtenerPorNumero"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/numero/{numeroFactura} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/numero/{numeroFactura} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/numero/{numeroFactura}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/numero/{numeroFactura}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/numero/{numeroFactura}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/numero/{numeroFactura}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/numero/{numeroFactura}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/numero/{numeroFactura}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/numero/{numeroFactura}`

<h3 id="obtenerpornumero-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|numeroFactura|path|string|true|none|

> Example responses

> 200 Response

<h3 id="obtenerpornumero-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FacturaDTO](#schemafacturadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorFechas

<a id="opIdlistarPorFechas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/fechas',
  params: {
  'fechaInicio' => 'string(date)',
'fechaFin' => 'string(date)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/fechas', params={
  'fechaInicio': '2019-08-24',  'fechaFin': '2019-08-24'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/fechas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/fechas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/fechas`

<h3 id="listarporfechas-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fechaInicio|query|string(date)|true|none|
|fechaFin|query|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporfechas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporfechas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[FacturaDTO](#schemafacturadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» numeroFactura|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» usuarioEmisorId|integer(int64)|false|none|none|
|» usuarioEmisorNombre|string|false|none|none|
|» fechaEmision|string(date)|false|none|none|
|» fechaVencimiento|string(date)|false|none|none|
|» estado|string|false|none|none|
|» subtotal|number(double)|false|none|none|
|» totalDescuentos|number(double)|false|none|none|
|» totalImpuestos|number(double)|false|none|none|
|» total|number(double)|false|none|none|
|» totalPagado|number(double)|false|none|none|
|» saldoPendiente|number(double)|false|none|none|
|» observaciones|string|false|none|none|
|» motivoAnulacion|string|false|none|none|
|» fechaAnulacion|string(date)|false|none|none|
|» detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» tipoServicioId|integer(int64)|false|none|none|
|»» tipoServicioNombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» cantidad|integer(int32)|false|none|none|
|»» precioUnitario|number(double)|false|none|none|
|»» subtotal|number(double)|false|none|none|
|»» descuentoPorcentaje|number(double)|false|none|none|
|»» descuentoMonto|number(double)|false|none|none|
|»» total|number(double)|false|none|none|
|»» observaciones|string|false|none|none|
|» pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» fechaPago|string(date)|false|none|none|
|»» horaPago|string(date-time)|false|none|none|
|»» metodoPago|string|false|none|none|
|»» monto|number(double)|false|none|none|
|»» comision|number(double)|false|none|none|
|»» montoNeto|number(double)|false|none|none|
|»» numeroReferencia|string|false|none|none|
|»» numeroVoucher|string|false|none|none|
|»» usuarioRegistroId|integer(int64)|false|none|none|
|»» usuarioRegistroNombre|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» verificado|boolean|false|none|none|
|»» fechaVerificacion|string(date-time)|false|none|none|
|»» usuarioVerificacionId|integer(int64)|false|none|none|
|»» usuarioVerificacionNombre|string|false|none|none|
|» descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» codigo|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» tipo|string|false|none|none|
|»» valor|number(double)|false|none|none|
|»» monto|number(double)|false|none|none|
|»» motivo|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» esPromocional|boolean|false|none|none|
|»» esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorEstado

<a id="opIdlistarPorEstado"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/estado/{estado} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/estado/{estado} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/estado/{estado}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/estado/{estado}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/estado/{estado}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/estado/{estado}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/estado/{estado}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/estado/{estado}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/estado/{estado}`

<h3 id="listarporestado-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|estado|path|string|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|

> Example responses

> 200 Response

<h3 id="listarporestado-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporestado-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[FacturaDTO](#schemafacturadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» numeroFactura|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» usuarioEmisorId|integer(int64)|false|none|none|
|» usuarioEmisorNombre|string|false|none|none|
|» fechaEmision|string(date)|false|none|none|
|» fechaVencimiento|string(date)|false|none|none|
|» estado|string|false|none|none|
|» subtotal|number(double)|false|none|none|
|» totalDescuentos|number(double)|false|none|none|
|» totalImpuestos|number(double)|false|none|none|
|» total|number(double)|false|none|none|
|» totalPagado|number(double)|false|none|none|
|» saldoPendiente|number(double)|false|none|none|
|» observaciones|string|false|none|none|
|» motivoAnulacion|string|false|none|none|
|» fechaAnulacion|string(date)|false|none|none|
|» detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» tipoServicioId|integer(int64)|false|none|none|
|»» tipoServicioNombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» cantidad|integer(int32)|false|none|none|
|»» precioUnitario|number(double)|false|none|none|
|»» subtotal|number(double)|false|none|none|
|»» descuentoPorcentaje|number(double)|false|none|none|
|»» descuentoMonto|number(double)|false|none|none|
|»» total|number(double)|false|none|none|
|»» observaciones|string|false|none|none|
|» pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» fechaPago|string(date)|false|none|none|
|»» horaPago|string(date-time)|false|none|none|
|»» metodoPago|string|false|none|none|
|»» monto|number(double)|false|none|none|
|»» comision|number(double)|false|none|none|
|»» montoNeto|number(double)|false|none|none|
|»» numeroReferencia|string|false|none|none|
|»» numeroVoucher|string|false|none|none|
|»» usuarioRegistroId|integer(int64)|false|none|none|
|»» usuarioRegistroNombre|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» verificado|boolean|false|none|none|
|»» fechaVerificacion|string(date-time)|false|none|none|
|»» usuarioVerificacionId|integer(int64)|false|none|none|
|»» usuarioVerificacionNombre|string|false|none|none|
|» descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» codigo|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» tipo|string|false|none|none|
|»» valor|number(double)|false|none|none|
|»» monto|number(double)|false|none|none|
|»» motivo|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» esPromocional|boolean|false|none|none|
|»» esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConSaldoPendiente

<a id="opIdlistarConSaldoPendiente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/con-saldo-pendiente \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/con-saldo-pendiente HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/con-saldo-pendiente',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/con-saldo-pendiente',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/con-saldo-pendiente', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/con-saldo-pendiente', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/con-saldo-pendiente");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/con-saldo-pendiente", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/con-saldo-pendiente`

> Example responses

> 200 Response

<h3 id="listarconsaldopendiente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconsaldopendiente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[FacturaDTO](#schemafacturadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» numeroFactura|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» usuarioEmisorId|integer(int64)|false|none|none|
|» usuarioEmisorNombre|string|false|none|none|
|» fechaEmision|string(date)|false|none|none|
|» fechaVencimiento|string(date)|false|none|none|
|» estado|string|false|none|none|
|» subtotal|number(double)|false|none|none|
|» totalDescuentos|number(double)|false|none|none|
|» totalImpuestos|number(double)|false|none|none|
|» total|number(double)|false|none|none|
|» totalPagado|number(double)|false|none|none|
|» saldoPendiente|number(double)|false|none|none|
|» observaciones|string|false|none|none|
|» motivoAnulacion|string|false|none|none|
|» fechaAnulacion|string(date)|false|none|none|
|» detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» tipoServicioId|integer(int64)|false|none|none|
|»» tipoServicioNombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» cantidad|integer(int32)|false|none|none|
|»» precioUnitario|number(double)|false|none|none|
|»» subtotal|number(double)|false|none|none|
|»» descuentoPorcentaje|number(double)|false|none|none|
|»» descuentoMonto|number(double)|false|none|none|
|»» total|number(double)|false|none|none|
|»» observaciones|string|false|none|none|
|» pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» fechaPago|string(date)|false|none|none|
|»» horaPago|string(date-time)|false|none|none|
|»» metodoPago|string|false|none|none|
|»» monto|number(double)|false|none|none|
|»» comision|number(double)|false|none|none|
|»» montoNeto|number(double)|false|none|none|
|»» numeroReferencia|string|false|none|none|
|»» numeroVoucher|string|false|none|none|
|»» usuarioRegistroId|integer(int64)|false|none|none|
|»» usuarioRegistroNombre|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» verificado|boolean|false|none|none|
|»» fechaVerificacion|string(date-time)|false|none|none|
|»» usuarioVerificacionId|integer(int64)|false|none|none|
|»» usuarioVerificacionNombre|string|false|none|none|
|» descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» codigo|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» tipo|string|false|none|none|
|»» valor|number(double)|false|none|none|
|»» monto|number(double)|false|none|none|
|»» motivo|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» esPromocional|boolean|false|none|none|
|»» esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorCliente

<a id="opIdlistarPorCliente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/facturas/cliente/{clienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/facturas/cliente/{clienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/cliente/{clienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/facturas/cliente/{clienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/facturas/cliente/{clienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/facturas/cliente/{clienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/cliente/{clienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/facturas/cliente/{clienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/facturas/cliente/{clienteId}`

<h3 id="listarporcliente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|clienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporcliente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporcliente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[FacturaDTO](#schemafacturadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» numeroFactura|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» usuarioEmisorId|integer(int64)|false|none|none|
|» usuarioEmisorNombre|string|false|none|none|
|» fechaEmision|string(date)|false|none|none|
|» fechaVencimiento|string(date)|false|none|none|
|» estado|string|false|none|none|
|» subtotal|number(double)|false|none|none|
|» totalDescuentos|number(double)|false|none|none|
|» totalImpuestos|number(double)|false|none|none|
|» total|number(double)|false|none|none|
|» totalPagado|number(double)|false|none|none|
|» saldoPendiente|number(double)|false|none|none|
|» observaciones|string|false|none|none|
|» motivoAnulacion|string|false|none|none|
|» fechaAnulacion|string(date)|false|none|none|
|» detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» tipoServicioId|integer(int64)|false|none|none|
|»» tipoServicioNombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» cantidad|integer(int32)|false|none|none|
|»» precioUnitario|number(double)|false|none|none|
|»» subtotal|number(double)|false|none|none|
|»» descuentoPorcentaje|number(double)|false|none|none|
|»» descuentoMonto|number(double)|false|none|none|
|»» total|number(double)|false|none|none|
|»» observaciones|string|false|none|none|
|» pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» fechaPago|string(date)|false|none|none|
|»» horaPago|string(date-time)|false|none|none|
|»» metodoPago|string|false|none|none|
|»» monto|number(double)|false|none|none|
|»» comision|number(double)|false|none|none|
|»» montoNeto|number(double)|false|none|none|
|»» numeroReferencia|string|false|none|none|
|»» numeroVoucher|string|false|none|none|
|»» usuarioRegistroId|integer(int64)|false|none|none|
|»» usuarioRegistroNombre|string|false|none|none|
|»» observaciones|string|false|none|none|
|»» verificado|boolean|false|none|none|
|»» fechaVerificacion|string(date-time)|false|none|none|
|»» usuarioVerificacionId|integer(int64)|false|none|none|
|»» usuarioVerificacionNombre|string|false|none|none|
|» descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» facturaId|integer(int64)|false|none|none|
|»» codigo|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» tipo|string|false|none|none|
|»» valor|number(double)|false|none|none|
|»» monto|number(double)|false|none|none|
|»» motivo|string|false|none|none|
|»» fechaAplicacion|string(date)|false|none|none|
|»» esPromocional|boolean|false|none|none|
|»» esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminarDetalle

<a id="opIdeliminarDetalle"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/facturas/{facturaId}/detalles/{detalleId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/facturas/{facturaId}/detalles/{detalleId}`

<h3 id="eliminardetalle-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|facturaId|path|integer(int64)|true|none|
|detalleId|path|integer(int64)|true|none|

<h3 id="eliminardetalle-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminarDescuento

<a id="opIdeliminarDescuento"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/facturas/{facturaId}/descuentos/{descuentoId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/facturas/{facturaId}/descuentos/{descuentoId}`

<h3 id="eliminardescuento-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|facturaId|path|integer(int64)|true|none|
|descuentoId|path|integer(int64)|true|none|

<h3 id="eliminardescuento-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-consulta-controller">consulta-controller</h1>

## obtenerPorId_4

<a id="opIdobtenerPorId_4"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/{id}`

<h3 id="obtenerporid_4-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_4-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ConsultaDTO](#schemaconsultadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizar_4

<a id="opIdactualizar_4"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/consultas/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/consultas/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "signosVitales": {
    "temperatura": 30,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 30,
    "frecuenciaRespiratoria": 5,
    "temperaturaRectal": 30,
    "condicionCorporal": 1
  }
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/consultas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/consultas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/consultas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/consultas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/consultas/{id}`

> Body parameter

```json
{
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "signosVitales": {
    "temperatura": 30,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 30,
    "frecuenciaRespiratoria": 5,
    "temperaturaRectal": 30,
    "condicionCorporal": 1
  }
}
```

<h3 id="actualizar_4-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateConsultaRequest](#schemaupdateconsultarequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizar_4-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ConsultaDTO](#schemaconsultadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_3

<a id="opIdeliminar_3"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/consultas/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/consultas/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/consultas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/consultas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/consultas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/consultas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/consultas/{id}`

<h3 id="eliminar_3-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_3-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crear_6

<a id="opIdcrear_6"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/consultas \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/consultas HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "historialClinicoId": 0,
  "citaId": 0,
  "veterinarioId": 0,
  "fechaConsulta": "2019-08-24",
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "signosVitales": {
    "temperatura": 30,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 30,
    "frecuenciaRespiratoria": 5,
    "temperaturaRectal": 30,
    "condicionCorporal": 1
  }
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/consultas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/consultas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/consultas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/consultas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/consultas`

> Body parameter

```json
{
  "historialClinicoId": 0,
  "citaId": 0,
  "veterinarioId": 0,
  "fechaConsulta": "2019-08-24",
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "signosVitales": {
    "temperatura": 30,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 30,
    "frecuenciaRespiratoria": 5,
    "temperaturaRectal": 30,
    "condicionCorporal": 1
  }
}
```

<h3 id="crear_6-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateConsultaRequest](#schemacreateconsultarequest)|true|none|

> Example responses

> 200 Response

<h3 id="crear_6-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ConsultaDTO](#schemaconsultadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## agregarTratamiento

<a id="opIdagregarTratamiento"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "consultaId": 0,
  "tipoTratamiento": "string",
  "nombre": "string",
  "descripcion": "string",
  "dosis": "string",
  "frecuencia": "string",
  "viaAdministracion": "string",
  "duracionDias": 1,
  "fechaInicio": "2019-08-24",
  "fechaFin": "2019-08-24",
  "indicaciones": "string",
  "efectosSecundarios": "string",
  "cantidad": 0.1,
  "unidad": "string",
  "requiereSupervision": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/consultas/{consultaId}/tratamientos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/consultas/{consultaId}/tratamientos`

> Body parameter

```json
{
  "consultaId": 0,
  "tipoTratamiento": "string",
  "nombre": "string",
  "descripcion": "string",
  "dosis": "string",
  "frecuencia": "string",
  "viaAdministracion": "string",
  "duracionDias": 1,
  "fechaInicio": "2019-08-24",
  "fechaFin": "2019-08-24",
  "indicaciones": "string",
  "efectosSecundarios": "string",
  "cantidad": 0.1,
  "unidad": "string",
  "requiereSupervision": true
}
```

<h3 id="agregartratamiento-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|consultaId|path|integer(int64)|true|none|
|body|body|[CreateTratamientoRequest](#schemacreatetratamientorequest)|true|none|

> Example responses

> 200 Response

<h3 id="agregartratamiento-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TratamientoDTO](#schematratamientodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## agregarDiagnostico

<a id="opIdagregarDiagnostico"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "consultaId": 0,
  "tipoDiagnostico": "string",
  "descripcion": "string",
  "codigoCie10": "string",
  "gravedad": "string",
  "notas": "string",
  "esPrincipal": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/consultas/{consultaId}/diagnosticos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/consultas/{consultaId}/diagnosticos`

> Body parameter

```json
{
  "consultaId": 0,
  "tipoDiagnostico": "string",
  "descripcion": "string",
  "codigoCie10": "string",
  "gravedad": "string",
  "notas": "string",
  "esPrincipal": true
}
```

<h3 id="agregardiagnostico-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|consultaId|path|integer(int64)|true|none|
|body|body|[CreateDiagnosticoRequest](#schemacreatediagnosticorequest)|true|none|

> Example responses

> 200 Response

<h3 id="agregardiagnostico-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[DiagnosticoDTO](#schemadiagnosticodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## finalizar

<a id="opIdfinalizar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/consultas/{id}/finalizar \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/consultas/{id}/finalizar HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "observacionesFinales": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/{id}/finalizar',
{
  method: 'PATCH',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/consultas/{id}/finalizar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/consultas/{id}/finalizar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/consultas/{id}/finalizar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/{id}/finalizar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/consultas/{id}/finalizar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/consultas/{id}/finalizar`

> Body parameter

```json
{
  "observacionesFinales": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24"
}
```

<h3 id="finalizar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[FinalizarConsultaRequest](#schemafinalizarconsultarequest)|true|none|

> Example responses

> 200 Response

<h3 id="finalizar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ConsultaDTO](#schemaconsultadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorVeterinario

<a id="opIdlistarPorVeterinario"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/veterinario/{veterinarioId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/veterinario/{veterinarioId}`

<h3 id="listarporveterinario-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|veterinarioId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporveterinario-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporveterinario-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaConsulta|string(date)|false|none|none|
|» horaInicio|string(date-time)|false|none|none|
|» horaFin|string(date-time)|false|none|none|
|» motivo|string|false|none|none|
|» anamnesis|string|false|none|none|
|» examenFisico|string|false|none|none|
|» observaciones|string|false|none|none|
|» planTratamiento|string|false|none|none|
|» pronostico|string|false|none|none|
|» requiereSeguimiento|boolean|false|none|none|
|» fechaSeguimiento|string(date)|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEspecialidad|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»» temperatura|number(double)|false|none|none|
|»» pesoKg|number(double)|false|none|none|
|»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»» temperaturaRectal|number(double)|false|none|none|
|»» condicionCorporal|integer(int32)|false|none|none|
|»» condicionCorporalDescripcion|string|false|none|none|
|»» tieneParametrosAnormales|boolean|false|none|none|
|»» tieneFiebre|boolean|false|none|none|
|»» tieneHipotermia|boolean|false|none|none|
|» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoDiagnostico|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» codigoCie10|string|false|none|none|
|»» gravedad|string|false|none|none|
|»» notas|string|false|none|none|
|»» esPrincipal|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esPresuntivo|boolean|false|none|none|
|»» esDefinitivo|boolean|false|none|none|
|»» esGraveOCritico|boolean|false|none|none|
|»» tieneCie10|boolean|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoTratamiento|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» dosis|string|false|none|none|
|»» frecuencia|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» duracionDias|integer(int32)|false|none|none|
|»» fechaInicio|string(date)|false|none|none|
|»» fechaFin|string(date)|false|none|none|
|»» indicaciones|string|false|none|none|
|»» efectosSecundarios|string|false|none|none|
|»» cantidad|number(double)|false|none|none|
|»» unidad|string|false|none|none|
|»» activo|boolean|false|none|none|
|»» requiereSupervision|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esMedicamento|boolean|false|none|none|
|»» haFinalizado|boolean|false|none|none|
|»» estaVigente|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» duracionMinutos|integer(int64)|false|none|none|
|» estaEnCurso|boolean|false|none|none|
|» estaFinalizada|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorPaciente_4

<a id="opIdlistarPorPaciente_4"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/paciente/{pacienteId}`

<h3 id="listarporpaciente_4-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporpaciente_4-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporpaciente_4-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaConsulta|string(date)|false|none|none|
|» horaInicio|string(date-time)|false|none|none|
|» horaFin|string(date-time)|false|none|none|
|» motivo|string|false|none|none|
|» anamnesis|string|false|none|none|
|» examenFisico|string|false|none|none|
|» observaciones|string|false|none|none|
|» planTratamiento|string|false|none|none|
|» pronostico|string|false|none|none|
|» requiereSeguimiento|boolean|false|none|none|
|» fechaSeguimiento|string(date)|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEspecialidad|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»» temperatura|number(double)|false|none|none|
|»» pesoKg|number(double)|false|none|none|
|»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»» temperaturaRectal|number(double)|false|none|none|
|»» condicionCorporal|integer(int32)|false|none|none|
|»» condicionCorporalDescripcion|string|false|none|none|
|»» tieneParametrosAnormales|boolean|false|none|none|
|»» tieneFiebre|boolean|false|none|none|
|»» tieneHipotermia|boolean|false|none|none|
|» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoDiagnostico|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» codigoCie10|string|false|none|none|
|»» gravedad|string|false|none|none|
|»» notas|string|false|none|none|
|»» esPrincipal|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esPresuntivo|boolean|false|none|none|
|»» esDefinitivo|boolean|false|none|none|
|»» esGraveOCritico|boolean|false|none|none|
|»» tieneCie10|boolean|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoTratamiento|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» dosis|string|false|none|none|
|»» frecuencia|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» duracionDias|integer(int32)|false|none|none|
|»» fechaInicio|string(date)|false|none|none|
|»» fechaFin|string(date)|false|none|none|
|»» indicaciones|string|false|none|none|
|»» efectosSecundarios|string|false|none|none|
|»» cantidad|number(double)|false|none|none|
|»» unidad|string|false|none|none|
|»» activo|boolean|false|none|none|
|»» requiereSupervision|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esMedicamento|boolean|false|none|none|
|»» haFinalizado|boolean|false|none|none|
|»» estaVigente|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» duracionMinutos|integer(int64)|false|none|none|
|» estaEnCurso|boolean|false|none|none|
|» estaFinalizada|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerUltimaConsultaPaciente

<a id="opIdobtenerUltimaConsultaPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/paciente/{pacienteId}/ultima", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/paciente/{pacienteId}/ultima`

<h3 id="obtenerultimaconsultapaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerultimaconsultapaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ConsultaDTO](#schemaconsultadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorFecha

<a id="opIdlistarPorFecha"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/fecha/{fecha} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/fecha/{fecha} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/fecha/{fecha}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/fecha/{fecha}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/fecha/{fecha}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/fecha/{fecha}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/fecha/{fecha}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/fecha/{fecha}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/fecha/{fecha}`

<h3 id="listarporfecha-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fecha|path|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporfecha-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporfecha-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaConsulta|string(date)|false|none|none|
|» horaInicio|string(date-time)|false|none|none|
|» horaFin|string(date-time)|false|none|none|
|» motivo|string|false|none|none|
|» anamnesis|string|false|none|none|
|» examenFisico|string|false|none|none|
|» observaciones|string|false|none|none|
|» planTratamiento|string|false|none|none|
|» pronostico|string|false|none|none|
|» requiereSeguimiento|boolean|false|none|none|
|» fechaSeguimiento|string(date)|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEspecialidad|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»» temperatura|number(double)|false|none|none|
|»» pesoKg|number(double)|false|none|none|
|»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»» temperaturaRectal|number(double)|false|none|none|
|»» condicionCorporal|integer(int32)|false|none|none|
|»» condicionCorporalDescripcion|string|false|none|none|
|»» tieneParametrosAnormales|boolean|false|none|none|
|»» tieneFiebre|boolean|false|none|none|
|»» tieneHipotermia|boolean|false|none|none|
|» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoDiagnostico|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» codigoCie10|string|false|none|none|
|»» gravedad|string|false|none|none|
|»» notas|string|false|none|none|
|»» esPrincipal|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esPresuntivo|boolean|false|none|none|
|»» esDefinitivo|boolean|false|none|none|
|»» esGraveOCritico|boolean|false|none|none|
|»» tieneCie10|boolean|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoTratamiento|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» dosis|string|false|none|none|
|»» frecuencia|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» duracionDias|integer(int32)|false|none|none|
|»» fechaInicio|string(date)|false|none|none|
|»» fechaFin|string(date)|false|none|none|
|»» indicaciones|string|false|none|none|
|»» efectosSecundarios|string|false|none|none|
|»» cantidad|number(double)|false|none|none|
|»» unidad|string|false|none|none|
|»» activo|boolean|false|none|none|
|»» requiereSupervision|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esMedicamento|boolean|false|none|none|
|»» haFinalizado|boolean|false|none|none|
|»» estaVigente|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» duracionMinutos|integer(int64)|false|none|none|
|» estaEnCurso|boolean|false|none|none|
|» estaFinalizada|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarEnCurso

<a id="opIdlistarEnCurso"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/en-curso \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/en-curso HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/en-curso',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/en-curso',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/en-curso', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/en-curso', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/en-curso");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/en-curso", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/en-curso`

> Example responses

> 200 Response

<h3 id="listarencurso-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarencurso-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaConsulta|string(date)|false|none|none|
|» horaInicio|string(date-time)|false|none|none|
|» horaFin|string(date-time)|false|none|none|
|» motivo|string|false|none|none|
|» anamnesis|string|false|none|none|
|» examenFisico|string|false|none|none|
|» observaciones|string|false|none|none|
|» planTratamiento|string|false|none|none|
|» pronostico|string|false|none|none|
|» requiereSeguimiento|boolean|false|none|none|
|» fechaSeguimiento|string(date)|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEspecialidad|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»» temperatura|number(double)|false|none|none|
|»» pesoKg|number(double)|false|none|none|
|»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»» temperaturaRectal|number(double)|false|none|none|
|»» condicionCorporal|integer(int32)|false|none|none|
|»» condicionCorporalDescripcion|string|false|none|none|
|»» tieneParametrosAnormales|boolean|false|none|none|
|»» tieneFiebre|boolean|false|none|none|
|»» tieneHipotermia|boolean|false|none|none|
|» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoDiagnostico|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» codigoCie10|string|false|none|none|
|»» gravedad|string|false|none|none|
|»» notas|string|false|none|none|
|»» esPrincipal|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esPresuntivo|boolean|false|none|none|
|»» esDefinitivo|boolean|false|none|none|
|»» esGraveOCritico|boolean|false|none|none|
|»» tieneCie10|boolean|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoTratamiento|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» dosis|string|false|none|none|
|»» frecuencia|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» duracionDias|integer(int32)|false|none|none|
|»» fechaInicio|string(date)|false|none|none|
|»» fechaFin|string(date)|false|none|none|
|»» indicaciones|string|false|none|none|
|»» efectosSecundarios|string|false|none|none|
|»» cantidad|number(double)|false|none|none|
|»» unidad|string|false|none|none|
|»» activo|boolean|false|none|none|
|»» requiereSupervision|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esMedicamento|boolean|false|none|none|
|»» haFinalizado|boolean|false|none|none|
|»» estaVigente|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» duracionMinutos|integer(int64)|false|none|none|
|» estaEnCurso|boolean|false|none|none|
|» estaFinalizada|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConSeguimiento

<a id="opIdlistarConSeguimiento"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/consultas/con-seguimiento \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/consultas/con-seguimiento HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/consultas/con-seguimiento',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/consultas/con-seguimiento',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/consultas/con-seguimiento', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/consultas/con-seguimiento', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/consultas/con-seguimiento");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/consultas/con-seguimiento", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/consultas/con-seguimiento`

> Example responses

> 200 Response

<h3 id="listarconseguimiento-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconseguimiento-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fechaConsulta|string(date)|false|none|none|
|» horaInicio|string(date-time)|false|none|none|
|» horaFin|string(date-time)|false|none|none|
|» motivo|string|false|none|none|
|» anamnesis|string|false|none|none|
|» examenFisico|string|false|none|none|
|» observaciones|string|false|none|none|
|» planTratamiento|string|false|none|none|
|» pronostico|string|false|none|none|
|» requiereSeguimiento|boolean|false|none|none|
|» fechaSeguimiento|string(date)|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEspecialidad|string|false|none|none|
|» citaId|integer(int64)|false|none|none|
|» signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|»» temperatura|number(double)|false|none|none|
|»» pesoKg|number(double)|false|none|none|
|»» frecuenciaCardiaca|integer(int32)|false|none|none|
|»» frecuenciaRespiratoria|integer(int32)|false|none|none|
|»» temperaturaRectal|number(double)|false|none|none|
|»» condicionCorporal|integer(int32)|false|none|none|
|»» condicionCorporalDescripcion|string|false|none|none|
|»» tieneParametrosAnormales|boolean|false|none|none|
|»» tieneFiebre|boolean|false|none|none|
|»» tieneHipotermia|boolean|false|none|none|
|» diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoDiagnostico|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» codigoCie10|string|false|none|none|
|»» gravedad|string|false|none|none|
|»» notas|string|false|none|none|
|»» esPrincipal|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esPresuntivo|boolean|false|none|none|
|»» esDefinitivo|boolean|false|none|none|
|»» esGraveOCritico|boolean|false|none|none|
|»» tieneCie10|boolean|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» tipoTratamiento|string|false|none|none|
|»» nombre|string|false|none|none|
|»» descripcion|string|false|none|none|
|»» dosis|string|false|none|none|
|»» frecuencia|string|false|none|none|
|»» viaAdministracion|string|false|none|none|
|»» duracionDias|integer(int32)|false|none|none|
|»» fechaInicio|string(date)|false|none|none|
|»» fechaFin|string(date)|false|none|none|
|»» indicaciones|string|false|none|none|
|»» efectosSecundarios|string|false|none|none|
|»» cantidad|number(double)|false|none|none|
|»» unidad|string|false|none|none|
|»» activo|boolean|false|none|none|
|»» requiereSupervision|boolean|false|none|none|
|»» consultaId|integer(int64)|false|none|none|
|»» esMedicamento|boolean|false|none|none|
|»» haFinalizado|boolean|false|none|none|
|»» estaVigente|boolean|false|none|none|
|»» resumen|string|false|none|none|
|»» createdAt|string(date-time)|false|none|none|
|»» createdBy|string|false|none|none|
|»» updatedAt|string(date-time)|false|none|none|
|»» updatedBy|string|false|none|none|
|» duracionMinutos|integer(int64)|false|none|none|
|» estaEnCurso|boolean|false|none|none|
|» estaFinalizada|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-cliente-controller">cliente-controller</h1>

## obtenerClientePorId

<a id="opIdobtenerClientePorId"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes/{id}`

<h3 id="obtenerclienteporid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerclienteporid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ClienteDTO](#schemaclientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizarCliente

<a id="opIdactualizarCliente"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/clientes/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/clientes/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/clientes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/clientes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/clientes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/clientes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/clientes/{id}`

> Body parameter

```json
{
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string"
}
```

<h3 id="actualizarcliente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateClienteRequest](#schemaupdateclienterequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizarcliente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ClienteDTO](#schemaclientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminarCliente

<a id="opIdeliminarCliente"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/clientes/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/clientes/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/clientes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/clientes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/clientes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/clientes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/clientes/{id}`

<h3 id="eliminarcliente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminarcliente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerTodosLosClientes

<a id="opIdobtenerTodosLosClientes"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes`

> Example responses

> 200 Response

<h3 id="obtenertodoslosclientes-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenertodoslosclientes-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ClienteDTO](#schemaclientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» apellido|string|false|none|none|
|» nombreCompleto|string|false|none|none|
|» dni|string|false|none|none|
|» email|string|false|none|none|
|» telefono|string|false|none|none|
|» direccion|string|false|none|none|
|» ciudad|string|false|none|none|
|» departamento|string|false|none|none|
|» codigoPostal|string|false|none|none|
|» observaciones|string|false|none|none|
|» pacientes|[[PacienteResumenDTO](#schemapacienteresumendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» especie|string|false|none|none|
|»» edadAnios|integer(int32)|false|none|none|
|»» estado|string|false|none|none|
|» cantidadPacientesActivos|integer(int64)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crearCliente

<a id="opIdcrearCliente"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/clientes \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/clientes HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nombre": "string",
  "apellido": "string",
  "dni": "strings",
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/clientes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/clientes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/clientes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/clientes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/clientes`

> Body parameter

```json
{
  "nombre": "string",
  "apellido": "string",
  "dni": "strings",
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string"
}
```

<h3 id="crearcliente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateClienteRequest](#schemacreateclienterequest)|true|none|

> Example responses

> 200 Response

<h3 id="crearcliente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ClienteDTO](#schemaclientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorEmail

<a id="opIdbuscarPorEmail"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes/email?email=string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes/email?email=string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/email?email=string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes/email',
  params: {
  'email' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes/email', params={
  'email': 'string'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes/email', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/email?email=string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes/email", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes/email`

<h3 id="buscarporemail-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|email|query|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarporemail-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ClienteDTO](#schemaclientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorDni

<a id="opIdbuscarPorDni"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes/dni/{dni} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes/dni/{dni} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/dni/{dni}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes/dni/{dni}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes/dni/{dni}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes/dni/{dni}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/dni/{dni}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes/dni/{dni}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes/dni/{dni}`

<h3 id="buscarpordni-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|dni|path|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarpordni-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ClienteDTO](#schemaclientedto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerClientesConPacientesActivos

<a id="opIdobtenerClientesConPacientesActivos"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes/con-pacientes-activos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes/con-pacientes-activos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/con-pacientes-activos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes/con-pacientes-activos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes/con-pacientes-activos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes/con-pacientes-activos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/con-pacientes-activos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes/con-pacientes-activos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes/con-pacientes-activos`

> Example responses

> 200 Response

<h3 id="obtenerclientesconpacientesactivos-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerclientesconpacientesactivos-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ClienteDTO](#schemaclientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» apellido|string|false|none|none|
|» nombreCompleto|string|false|none|none|
|» dni|string|false|none|none|
|» email|string|false|none|none|
|» telefono|string|false|none|none|
|» direccion|string|false|none|none|
|» ciudad|string|false|none|none|
|» departamento|string|false|none|none|
|» codigoPostal|string|false|none|none|
|» observaciones|string|false|none|none|
|» pacientes|[[PacienteResumenDTO](#schemapacienteresumendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» especie|string|false|none|none|
|»» edadAnios|integer(int32)|false|none|none|
|»» estado|string|false|none|none|
|» cantidadPacientesActivos|integer(int64)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorCiudad

<a id="opIdbuscarPorCiudad"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes/ciudad/{ciudad} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes/ciudad/{ciudad} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/ciudad/{ciudad}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes/ciudad/{ciudad}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes/ciudad/{ciudad}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes/ciudad/{ciudad}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/ciudad/{ciudad}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes/ciudad/{ciudad}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes/ciudad/{ciudad}`

<h3 id="buscarporciudad-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|ciudad|path|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarporciudad-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarporciudad-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ClienteDTO](#schemaclientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» apellido|string|false|none|none|
|» nombreCompleto|string|false|none|none|
|» dni|string|false|none|none|
|» email|string|false|none|none|
|» telefono|string|false|none|none|
|» direccion|string|false|none|none|
|» ciudad|string|false|none|none|
|» departamento|string|false|none|none|
|» codigoPostal|string|false|none|none|
|» observaciones|string|false|none|none|
|» pacientes|[[PacienteResumenDTO](#schemapacienteresumendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» especie|string|false|none|none|
|»» edadAnios|integer(int32)|false|none|none|
|»» estado|string|false|none|none|
|» cantidadPacientesActivos|integer(int64)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorNombreOApellido

<a id="opIdbuscarPorNombreOApellido"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/clientes/buscar?termino=string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/clientes/buscar?termino=string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/clientes/buscar?termino=string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/clientes/buscar',
  params: {
  'termino' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/clientes/buscar', params={
  'termino': 'string'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/clientes/buscar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/clientes/buscar?termino=string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/clientes/buscar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/clientes/buscar`

<h3 id="buscarpornombreoapellido-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|termino|query|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarpornombreoapellido-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarpornombreoapellido-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ClienteDTO](#schemaclientedto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» apellido|string|false|none|none|
|» nombreCompleto|string|false|none|none|
|» dni|string|false|none|none|
|» email|string|false|none|none|
|» telefono|string|false|none|none|
|» direccion|string|false|none|none|
|» ciudad|string|false|none|none|
|» departamento|string|false|none|none|
|» codigoPostal|string|false|none|none|
|» observaciones|string|false|none|none|
|» pacientes|[[PacienteResumenDTO](#schemapacienteresumendto)]|false|none|none|
|»» id|integer(int64)|false|none|none|
|»» nombre|string|false|none|none|
|»» especie|string|false|none|none|
|»» edadAnios|integer(int32)|false|none|none|
|»» estado|string|false|none|none|
|» cantidadPacientesActivos|integer(int64)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» updatedAt|string(date-time)|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-cita-controller">cita-controller</h1>

## obtenerPorId_5

<a id="opIdobtenerPorId_5"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/{id}`

<h3 id="obtenerporid_5-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_5-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## actualizar_5

<a id="opIdactualizar_5"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/api/v1/citas/{id} \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PUT http://localhost:8080/api/v1/citas/{id} HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "motivo": "string",
  "notas": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}',
{
  method: 'PUT',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.put 'http://localhost:8080/api/v1/citas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.put('http://localhost:8080/api/v1/citas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PUT','http://localhost:8080/api/v1/citas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PUT");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PUT", "http://localhost:8080/api/v1/citas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PUT /api/v1/citas/{id}`

> Body parameter

```json
{
  "motivo": "string",
  "notas": "string"
}
```

<h3 id="actualizar_5-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UpdateCitaRequest](#schemaupdatecitarequest)|true|none|

> Example responses

> 200 Response

<h3 id="actualizar_5-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_4

<a id="opIdeliminar_4"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/citas/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/citas/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/citas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/citas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/citas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/citas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/citas/{id}`

<h3 id="eliminar_4-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_4-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarTodas

<a id="opIdlistarTodas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas`

> Example responses

> 200 Response

<h3 id="listartodas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listartodas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[CitaDTO](#schemacitadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fecha|string(date)|false|none|none|
|» hora|[LocalTime](#schemalocaltime)|false|none|none|
|»» hour|integer(int32)|false|none|none|
|»» minute|integer(int32)|false|none|none|
|»» second|integer(int32)|false|none|none|
|»» nano|integer(int32)|false|none|none|
|» motivo|string|false|none|none|
|» estado|string|false|none|none|
|» notas|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» motivoCancelacion|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEmail|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» clienteEmail|string|false|none|none|
|» clienteTelefono|string|false|none|none|
|» tipoServicioId|integer(int64)|false|none|none|
|» tipoServicioNombre|string|false|none|none|
|» tipoServicioCategoria|string|false|none|none|
|» tipoServicioPrecio|number(double)|false|none|none|
|» fechaConfirmacion|string(date-time)|false|none|none|
|» fechaInicioAtencion|string(date-time)|false|none|none|
|» fechaFinAtencion|string(date-time)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|
|» horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|» resumen|string|false|none|none|
|» esModificable|boolean|false|none|none|
|» estaActiva|boolean|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## crear_7

<a id="opIdcrear_7"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/citas \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/citas HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "fecha": "2019-08-24",
  "hora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivo": "string",
  "veterinarioId": 0,
  "pacienteId": 0,
  "clienteId": 0,
  "tipoServicioId": 0,
  "notas": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/citas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/citas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/citas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/citas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/citas`

> Body parameter

```json
{
  "fecha": "2019-08-24",
  "hora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivo": "string",
  "veterinarioId": 0,
  "pacienteId": 0,
  "clienteId": 0,
  "tipoServicioId": 0,
  "notas": "string"
}
```

<h3 id="crear_7-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateCitaRequest](#schemacreatecitarequest)|true|none|

> Example responses

> 200 Response

<h3 id="crear_7-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## reagendar

<a id="opIdreagendar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/citas/{id}/reagendar \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/citas/{id}/reagendar HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "nuevaFecha": "2019-08-24",
  "nuevaHora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivoReagendamiento": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}/reagendar',
{
  method: 'PATCH',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/citas/{id}/reagendar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/citas/{id}/reagendar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/citas/{id}/reagendar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}/reagendar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/citas/{id}/reagendar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/citas/{id}/reagendar`

> Body parameter

```json
{
  "nuevaFecha": "2019-08-24",
  "nuevaHora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivoReagendamiento": "string"
}
```

<h3 id="reagendar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[ReagendarCitaRequest](#schemareagendarcitarequest)|true|none|

> Example responses

> 200 Response

<h3 id="reagendar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## iniciar

<a id="opIdiniciar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/citas/{id}/iniciar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/citas/{id}/iniciar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}/iniciar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/citas/{id}/iniciar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/citas/{id}/iniciar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/citas/{id}/iniciar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}/iniciar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/citas/{id}/iniciar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/citas/{id}/iniciar`

<h3 id="iniciar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="iniciar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## confirmar

<a id="opIdconfirmar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/citas/{id}/confirmar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/citas/{id}/confirmar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}/confirmar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/citas/{id}/confirmar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/citas/{id}/confirmar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/citas/{id}/confirmar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}/confirmar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/citas/{id}/confirmar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/citas/{id}/confirmar`

<h3 id="confirmar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="confirmar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## completar

<a id="opIdcompletar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/citas/{id}/completar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/citas/{id}/completar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}/completar',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/citas/{id}/completar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/citas/{id}/completar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/citas/{id}/completar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}/completar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/citas/{id}/completar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/citas/{id}/completar`

<h3 id="completar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="completar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cancelar_1

<a id="opIdcancelar_1"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/citas/{id}/cancelar \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/citas/{id}/cancelar HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "motivoCancelacion": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/{id}/cancelar',
{
  method: 'PATCH',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/citas/{id}/cancelar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/citas/{id}/cancelar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/citas/{id}/cancelar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/{id}/cancelar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/citas/{id}/cancelar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/citas/{id}/cancelar`

> Body parameter

```json
{
  "motivoCancelacion": "string"
}
```

<h3 id="cancelar_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[CancelarCitaRequest](#schemacancelarcitarequest)|true|none|

> Example responses

> 200 Response

<h3 id="cancelar_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[CitaDTO](#schemacitadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorVeterinarioYFecha

<a id="opIdlistarPorVeterinarioYFecha"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}`

<h3 id="listarporveterinarioyfecha-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|veterinarioId|path|integer(int64)|true|none|
|fecha|path|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporveterinarioyfecha-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporveterinarioyfecha-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[CitaDTO](#schemacitadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fecha|string(date)|false|none|none|
|» hora|[LocalTime](#schemalocaltime)|false|none|none|
|»» hour|integer(int32)|false|none|none|
|»» minute|integer(int32)|false|none|none|
|»» second|integer(int32)|false|none|none|
|»» nano|integer(int32)|false|none|none|
|» motivo|string|false|none|none|
|» estado|string|false|none|none|
|» notas|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» motivoCancelacion|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEmail|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» clienteEmail|string|false|none|none|
|» clienteTelefono|string|false|none|none|
|» tipoServicioId|integer(int64)|false|none|none|
|» tipoServicioNombre|string|false|none|none|
|» tipoServicioCategoria|string|false|none|none|
|» tipoServicioPrecio|number(double)|false|none|none|
|» fechaConfirmacion|string(date-time)|false|none|none|
|» fechaInicioAtencion|string(date-time)|false|none|none|
|» fechaFinAtencion|string(date-time)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|
|» horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|» resumen|string|false|none|none|
|» esModificable|boolean|false|none|none|
|» estaActiva|boolean|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorPaciente_5

<a id="opIdlistarPorPaciente_5"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/paciente/{pacienteId}`

<h3 id="listarporpaciente_5-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporpaciente_5-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporpaciente_5-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[CitaDTO](#schemacitadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fecha|string(date)|false|none|none|
|» hora|[LocalTime](#schemalocaltime)|false|none|none|
|»» hour|integer(int32)|false|none|none|
|»» minute|integer(int32)|false|none|none|
|»» second|integer(int32)|false|none|none|
|»» nano|integer(int32)|false|none|none|
|» motivo|string|false|none|none|
|» estado|string|false|none|none|
|» notas|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» motivoCancelacion|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEmail|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» clienteEmail|string|false|none|none|
|» clienteTelefono|string|false|none|none|
|» tipoServicioId|integer(int64)|false|none|none|
|» tipoServicioNombre|string|false|none|none|
|» tipoServicioCategoria|string|false|none|none|
|» tipoServicioPrecio|number(double)|false|none|none|
|» fechaConfirmacion|string(date-time)|false|none|none|
|» fechaInicioAtencion|string(date-time)|false|none|none|
|» fechaFinAtencion|string(date-time)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|
|» horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|» resumen|string|false|none|none|
|» esModificable|boolean|false|none|none|
|» estaActiva|boolean|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarProximasCitasPaciente

<a id="opIdlistarProximasCitasPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/paciente/{pacienteId}/proximas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/paciente/{pacienteId}/proximas`

<h3 id="listarproximascitaspaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarproximascitaspaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarproximascitaspaciente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[CitaDTO](#schemacitadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fecha|string(date)|false|none|none|
|» hora|[LocalTime](#schemalocaltime)|false|none|none|
|»» hour|integer(int32)|false|none|none|
|»» minute|integer(int32)|false|none|none|
|»» second|integer(int32)|false|none|none|
|»» nano|integer(int32)|false|none|none|
|» motivo|string|false|none|none|
|» estado|string|false|none|none|
|» notas|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» motivoCancelacion|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEmail|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» clienteEmail|string|false|none|none|
|» clienteTelefono|string|false|none|none|
|» tipoServicioId|integer(int64)|false|none|none|
|» tipoServicioNombre|string|false|none|none|
|» tipoServicioCategoria|string|false|none|none|
|» tipoServicioPrecio|number(double)|false|none|none|
|» fechaConfirmacion|string(date-time)|false|none|none|
|» fechaInicioAtencion|string(date-time)|false|none|none|
|» fechaFinAtencion|string(date-time)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|
|» horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|» resumen|string|false|none|none|
|» esModificable|boolean|false|none|none|
|» estaActiva|boolean|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorFecha_1

<a id="opIdlistarPorFecha_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/fecha/{fecha} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/fecha/{fecha} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/fecha/{fecha}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/fecha/{fecha}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/fecha/{fecha}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/fecha/{fecha}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/fecha/{fecha}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/fecha/{fecha}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/fecha/{fecha}`

<h3 id="listarporfecha_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fecha|path|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporfecha_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporfecha_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[CitaDTO](#schemacitadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fecha|string(date)|false|none|none|
|» hora|[LocalTime](#schemalocaltime)|false|none|none|
|»» hour|integer(int32)|false|none|none|
|»» minute|integer(int32)|false|none|none|
|»» second|integer(int32)|false|none|none|
|»» nano|integer(int32)|false|none|none|
|» motivo|string|false|none|none|
|» estado|string|false|none|none|
|» notas|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» motivoCancelacion|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEmail|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» clienteEmail|string|false|none|none|
|» clienteTelefono|string|false|none|none|
|» tipoServicioId|integer(int64)|false|none|none|
|» tipoServicioNombre|string|false|none|none|
|» tipoServicioCategoria|string|false|none|none|
|» tipoServicioPrecio|number(double)|false|none|none|
|» fechaConfirmacion|string(date-time)|false|none|none|
|» fechaInicioAtencion|string(date-time)|false|none|none|
|» fechaFinAtencion|string(date-time)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|
|» horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|» resumen|string|false|none|none|
|» esModificable|boolean|false|none|none|
|» estaActiva|boolean|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerHorariosDisponibles

<a id="opIdobtenerHorariosDisponibles"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}?fecha=2019-08-24 \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}?fecha=2019-08-24 HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}?fecha=2019-08-24',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}',
  params: {
  'fecha' => 'string(date)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}', params={
  'fecha': '2019-08-24'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}?fecha=2019-08-24");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/disponibilidad/veterinario/{veterinarioId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/disponibilidad/veterinario/{veterinarioId}`

<h3 id="obtenerhorariosdisponibles-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|veterinarioId|path|integer(int64)|true|none|
|fecha|query|string(date)|true|none|
|duracion|query|integer(int32)|false|none|

> Example responses

> 200 Response

<h3 id="obtenerhorariosdisponibles-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerhorariosdisponibles-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorCliente_1

<a id="opIdlistarPorCliente_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/citas/cliente/{clienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/citas/cliente/{clienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/citas/cliente/{clienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/citas/cliente/{clienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/citas/cliente/{clienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/citas/cliente/{clienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/citas/cliente/{clienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/citas/cliente/{clienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/citas/cliente/{clienteId}`

<h3 id="listarporcliente_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|clienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporcliente_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporcliente_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[CitaDTO](#schemacitadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» fecha|string(date)|false|none|none|
|» hora|[LocalTime](#schemalocaltime)|false|none|none|
|»» hour|integer(int32)|false|none|none|
|»» minute|integer(int32)|false|none|none|
|»» second|integer(int32)|false|none|none|
|»» nano|integer(int32)|false|none|none|
|» motivo|string|false|none|none|
|» estado|string|false|none|none|
|» notas|string|false|none|none|
|» duracionEstimada|integer(int32)|false|none|none|
|» motivoCancelacion|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» veterinarioEmail|string|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» pacienteEspecie|string|false|none|none|
|» clienteId|integer(int64)|false|none|none|
|» clienteNombre|string|false|none|none|
|» clienteEmail|string|false|none|none|
|» clienteTelefono|string|false|none|none|
|» tipoServicioId|integer(int64)|false|none|none|
|» tipoServicioNombre|string|false|none|none|
|» tipoServicioCategoria|string|false|none|none|
|» tipoServicioPrecio|number(double)|false|none|none|
|» fechaConfirmacion|string(date-time)|false|none|none|
|» fechaInicioAtencion|string(date-time)|false|none|none|
|» fechaFinAtencion|string(date-time)|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|
|» horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|» resumen|string|false|none|none|
|» esModificable|boolean|false|none|none|
|» estaActiva|boolean|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-vacuna-controller">vacuna-controller</h1>

## registrar

<a id="opIdregistrar"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/vacunas \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/vacunas HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "historialClinicoId": 0,
  "veterinarioId": 0,
  "nombre": "string",
  "tipoVacuna": "string",
  "fechaAplicacion": "2019-08-24",
  "fechaProximaDosis": "2019-08-24",
  "lote": "string",
  "laboratorio": "string",
  "dosis": "string",
  "viaAdministracion": "string",
  "numeroDosis": 1,
  "intervaloDias": 0,
  "esRefuerzo": true,
  "observaciones": "string",
  "reaccionesAdversas": "string",
  "pesoAplicacion": 0.1,
  "serieCompleta": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/vacunas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/vacunas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/vacunas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/vacunas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/vacunas`

> Body parameter

```json
{
  "historialClinicoId": 0,
  "veterinarioId": 0,
  "nombre": "string",
  "tipoVacuna": "string",
  "fechaAplicacion": "2019-08-24",
  "fechaProximaDosis": "2019-08-24",
  "lote": "string",
  "laboratorio": "string",
  "dosis": "string",
  "viaAdministracion": "string",
  "numeroDosis": 1,
  "intervaloDias": 0,
  "esRefuerzo": true,
  "observaciones": "string",
  "reaccionesAdversas": "string",
  "pesoAplicacion": 0.1,
  "serieCompleta": true
}
```

<h3 id="registrar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateVacunaRequest](#schemacreatevacunarequest)|true|none|

> Example responses

> 200 Response

<h3 id="registrar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[VacunaDTO](#schemavacunadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## completarSerie

<a id="opIdcompletarSerie"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/vacunas/{id}/completar-serie \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/vacunas/{id}/completar-serie HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/{id}/completar-serie',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/vacunas/{id}/completar-serie',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/vacunas/{id}/completar-serie', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/vacunas/{id}/completar-serie', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/{id}/completar-serie");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/vacunas/{id}/completar-serie", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/vacunas/{id}/completar-serie`

<h3 id="completarserie-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="completarserie-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[VacunaDTO](#schemavacunadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## calcularProximaDosis

<a id="opIdcalcularProximaDosis"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/vacunas/{id}/calcular-proxima-dosis", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/vacunas/{id}/calcular-proxima-dosis`

<h3 id="calcularproximadosis-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="calcularproximadosis-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[VacunaDTO](#schemavacunadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorId_6

<a id="opIdobtenerPorId_6"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/{id}`

<h3 id="obtenerporid_6-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_6-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[VacunaDTO](#schemavacunadto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_5

<a id="opIdeliminar_5"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/vacunas/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/vacunas/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/vacunas/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/vacunas/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/vacunas/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/vacunas/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/vacunas/{id}`

<h3 id="eliminar_5-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_5-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorTipo

<a id="opIdlistarPorTipo"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/tipo/{tipoVacuna}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/tipo/{tipoVacuna}`

<h3 id="listarportipo-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|tipoVacuna|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarportipo-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarportipo-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConSerieIncompleta

<a id="opIdlistarConSerieIncompleta"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/serie-incompleta \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/serie-incompleta HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/serie-incompleta',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/serie-incompleta',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/serie-incompleta', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/serie-incompleta', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/serie-incompleta");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/serie-incompleta", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/serie-incompleta`

> Example responses

> 200 Response

<h3 id="listarconserieincompleta-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconserieincompleta-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConRefuerzoVencido

<a id="opIdlistarConRefuerzoVencido"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/refuerzo-vencido \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/refuerzo-vencido HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/refuerzo-vencido',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/refuerzo-vencido',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/refuerzo-vencido', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/refuerzo-vencido', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/refuerzo-vencido");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/refuerzo-vencido", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/refuerzo-vencido`

> Example responses

> 200 Response

<h3 id="listarconrefuerzovencido-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconrefuerzovencido-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConRefuerzoProximo

<a id="opIdlistarConRefuerzoProximo"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/refuerzo-proximo \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/refuerzo-proximo HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/refuerzo-proximo',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/refuerzo-proximo',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/refuerzo-proximo', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/refuerzo-proximo', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/refuerzo-proximo");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/refuerzo-proximo", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/refuerzo-proximo`

<h3 id="listarconrefuerzoproximo-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|dias|query|integer(int32)|false|none|

> Example responses

> 200 Response

<h3 id="listarconrefuerzoproximo-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconrefuerzoproximo-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorRangoFechas

<a id="opIdlistarPorRangoFechas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/rango-fechas',
  params: {
  'fechaInicio' => 'string(date)',
'fechaFin' => 'string(date)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/rango-fechas', params={
  'fechaInicio': '2019-08-24',  'fechaFin': '2019-08-24'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/rango-fechas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/rango-fechas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/rango-fechas`

<h3 id="listarporrangofechas-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fechaInicio|query|string(date)|true|none|
|fechaFin|query|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporrangofechas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporrangofechas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorPaciente

<a id="opIdlistarPorPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/paciente/{pacienteId}`

<h3 id="listarporpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporpaciente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConSerieIncompletaPorPaciente

<a id="opIdlistarConSerieIncompletaPorPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/paciente/{pacienteId}/serie-incompleta", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/paciente/{pacienteId}/serie-incompleta`

<h3 id="listarconserieincompletaporpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarconserieincompletaporpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconserieincompletaporpaciente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConReaccionesAdversas

<a id="opIdlistarConReaccionesAdversas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/con-reacciones-adversas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/con-reacciones-adversas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/con-reacciones-adversas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/con-reacciones-adversas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/con-reacciones-adversas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/con-reacciones-adversas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/con-reacciones-adversas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/con-reacciones-adversas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/con-reacciones-adversas`

> Example responses

> 200 Response

<h3 id="listarconreaccionesadversas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconreaccionesadversas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarAntirrábicas

<a id="opIdlistarAntirrábicas"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/vacunas/antirrabicas \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/vacunas/antirrabicas HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/vacunas/antirrabicas',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/vacunas/antirrabicas',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/vacunas/antirrabicas', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/vacunas/antirrabicas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/vacunas/antirrabicas");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/vacunas/antirrabicas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/vacunas/antirrabicas`

> Example responses

> 200 Response

<h3 id="listarantirrábicas-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarantirrábicas-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» nombre|string|false|none|none|
|» tipoVacuna|string|false|none|none|
|» fechaAplicacion|string(date)|false|none|none|
|» fechaProximaDosis|string(date)|false|none|none|
|» lote|string|false|none|none|
|» laboratorio|string|false|none|none|
|» dosis|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» numeroDosis|integer(int32)|false|none|none|
|» intervaloDias|integer(int32)|false|none|none|
|» esRefuerzo|boolean|false|none|none|
|» observaciones|string|false|none|none|
|» reaccionesAdversas|string|false|none|none|
|» pesoAplicacion|number(double)|false|none|none|
|» serieCompleta|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioId|integer(int64)|false|none|none|
|» veterinarioNombre|string|false|none|none|
|» esObligatoria|boolean|false|none|none|
|» esAntirrábica|boolean|false|none|none|
|» refuerzoVencido|boolean|false|none|none|
|» refuerzoProximo|boolean|false|none|none|
|» tuvoReacciones|boolean|false|none|none|
|» estado|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-tratamiento-controller">tratamiento-controller</h1>

## crear_1

<a id="opIdcrear_1"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/tratamientos \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/tratamientos HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "consultaId": 0,
  "tipoTratamiento": "string",
  "nombre": "string",
  "descripcion": "string",
  "dosis": "string",
  "frecuencia": "string",
  "viaAdministracion": "string",
  "duracionDias": 1,
  "fechaInicio": "2019-08-24",
  "fechaFin": "2019-08-24",
  "indicaciones": "string",
  "efectosSecundarios": "string",
  "cantidad": 0.1,
  "unidad": "string",
  "requiereSupervision": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/tratamientos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/tratamientos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/tratamientos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/tratamientos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/tratamientos`

> Body parameter

```json
{
  "consultaId": 0,
  "tipoTratamiento": "string",
  "nombre": "string",
  "descripcion": "string",
  "dosis": "string",
  "frecuencia": "string",
  "viaAdministracion": "string",
  "duracionDias": 1,
  "fechaInicio": "2019-08-24",
  "fechaFin": "2019-08-24",
  "indicaciones": "string",
  "efectosSecundarios": "string",
  "cantidad": 0.1,
  "unidad": "string",
  "requiereSupervision": true
}
```

<h3 id="crear_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateTratamientoRequest](#schemacreatetratamientorequest)|true|none|

> Example responses

> 200 Response

<h3 id="crear_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TratamientoDTO](#schematratamientodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## suspender

<a id="opIdsuspender"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/tratamientos/{id}/suspender \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/tratamientos/{id}/suspender HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/{id}/suspender',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/tratamientos/{id}/suspender',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/tratamientos/{id}/suspender', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/tratamientos/{id}/suspender', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/{id}/suspender");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/tratamientos/{id}/suspender", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/tratamientos/{id}/suspender`

<h3 id="suspender-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="suspender-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TratamientoDTO](#schematratamientodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## calcularFechaFin

<a id="opIdcalcularFechaFin"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/tratamientos/{id}/calcular-fecha-fin", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/tratamientos/{id}/calcular-fecha-fin`

<h3 id="calcularfechafin-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="calcularfechafin-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TratamientoDTO](#schematratamientodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorId_7

<a id="opIdobtenerPorId_7"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/{id}`

<h3 id="obtenerporid_7-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_7-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[TratamientoDTO](#schematratamientodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_6

<a id="opIdeliminar_6"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/tratamientos/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/tratamientos/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/tratamientos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/tratamientos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/tratamientos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/tratamientos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/tratamientos/{id}`

<h3 id="eliminar_6-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_6-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorTipo_1

<a id="opIdlistarPorTipo_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/tipo/{tipoTratamiento}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/tipo/{tipoTratamiento}`

<h3 id="listarportipo_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|tipoTratamiento|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarportipo_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarportipo_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarSuspendidos

<a id="opIdlistarSuspendidos"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/suspendidos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/suspendidos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/suspendidos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/suspendidos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/suspendidos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/suspendidos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/suspendidos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/suspendidos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/suspendidos`

> Example responses

> 200 Response

<h3 id="listarsuspendidos-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarsuspendidos-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorRangoFechas_1

<a id="opIdlistarPorRangoFechas_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/rango-fechas',
  params: {
  'fechaInicio' => 'string(date)',
'fechaFin' => 'string(date)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/rango-fechas', params={
  'fechaInicio': '2019-08-24',  'fechaFin': '2019-08-24'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/rango-fechas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/rango-fechas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/rango-fechas`

<h3 id="listarporrangofechas_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fechaInicio|query|string(date)|true|none|
|fechaFin|query|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporrangofechas_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporrangofechas_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarProximosAFinalizar

<a id="opIdlistarProximosAFinalizar"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/proximos-a-finalizar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/proximos-a-finalizar`

<h3 id="listarproximosafinalizar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|dias|query|integer(int32)|false|none|

> Example responses

> 200 Response

<h3 id="listarproximosafinalizar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarproximosafinalizar-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorPaciente_1

<a id="opIdlistarPorPaciente_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/paciente/{pacienteId}`

<h3 id="listarporpaciente_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporpaciente_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporpaciente_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarVigentesPorPaciente

<a id="opIdlistarVigentesPorPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/vigentes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/paciente/{pacienteId}/vigentes`

<h3 id="listarvigentesporpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarvigentesporpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarvigentesporpaciente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarActivosPorPaciente

<a id="opIdlistarActivosPorPaciente"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/paciente/{pacienteId}/activos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/paciente/{pacienteId}/activos`

<h3 id="listaractivosporpaciente-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listaractivosporpaciente-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listaractivosporpaciente-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarMedicamentosActivos

<a id="opIdlistarMedicamentosActivos"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/medicamentos-activos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/medicamentos-activos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/medicamentos-activos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/medicamentos-activos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/medicamentos-activos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/medicamentos-activos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/medicamentos-activos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/medicamentos-activos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/medicamentos-activos`

> Example responses

> 200 Response

<h3 id="listarmedicamentosactivos-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarmedicamentosactivos-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConSupervision

<a id="opIdlistarConSupervision"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/tratamientos/con-supervision \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/tratamientos/con-supervision HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/tratamientos/con-supervision',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/tratamientos/con-supervision',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/tratamientos/con-supervision', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/tratamientos/con-supervision', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/tratamientos/con-supervision");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/tratamientos/con-supervision", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/tratamientos/con-supervision`

> Example responses

> 200 Response

<h3 id="listarconsupervision-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconsupervision-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoTratamiento|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» dosis|string|false|none|none|
|» frecuencia|string|false|none|none|
|» viaAdministracion|string|false|none|none|
|» duracionDias|integer(int32)|false|none|none|
|» fechaInicio|string(date)|false|none|none|
|» fechaFin|string(date)|false|none|none|
|» indicaciones|string|false|none|none|
|» efectosSecundarios|string|false|none|none|
|» cantidad|number(double)|false|none|none|
|» unidad|string|false|none|none|
|» activo|boolean|false|none|none|
|» requiereSupervision|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esMedicamento|boolean|false|none|none|
|» haFinalizado|boolean|false|none|none|
|» estaVigente|boolean|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-examen-controller">examen-controller</h1>

## solicitar

<a id="opIdsolicitar"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/examenes \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/examenes HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "historialClinicoId": 0,
  "veterinarioSolicitanteId": 0,
  "consultaId": 0,
  "tipoExamen": "string",
  "nombre": "string",
  "descripcion": "string",
  "fechaSolicitud": "2019-08-24",
  "fechaRealizacion": "2019-08-24",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "laboratorioExterno": "string",
  "referenciaExterna": "string",
  "costo": 0.1,
  "notas": "string",
  "requiereAyuno": true,
  "esUrgente": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/examenes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/examenes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/examenes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/examenes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/examenes`

> Body parameter

```json
{
  "historialClinicoId": 0,
  "veterinarioSolicitanteId": 0,
  "consultaId": 0,
  "tipoExamen": "string",
  "nombre": "string",
  "descripcion": "string",
  "fechaSolicitud": "2019-08-24",
  "fechaRealizacion": "2019-08-24",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "laboratorioExterno": "string",
  "referenciaExterna": "string",
  "costo": 0.1,
  "notas": "string",
  "requiereAyuno": true,
  "esUrgente": true
}
```

<h3 id="solicitar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateExamenRequest](#schemacreateexamenrequest)|true|none|

> Example responses

> 200 Response

<h3 id="solicitar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ExamenDTO](#schemaexamendto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## registrarResultados

<a id="opIdregistrarResultados"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/examenes/{id}/resultados \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/examenes/{id}/resultados HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "fechaRealizacion": "2019-08-24",
  "resultados": "string",
  "valoresReferencia": "string",
  "interpretacion": "string",
  "hallazgos": "string",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "resultadoAnormal": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/{id}/resultados',
{
  method: 'PATCH',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/examenes/{id}/resultados',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/examenes/{id}/resultados', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/examenes/{id}/resultados', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/{id}/resultados");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/examenes/{id}/resultados", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/examenes/{id}/resultados`

> Body parameter

```json
{
  "fechaRealizacion": "2019-08-24",
  "resultados": "string",
  "valoresReferencia": "string",
  "interpretacion": "string",
  "hallazgos": "string",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "resultadoAnormal": true
}
```

<h3 id="registrarresultados-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[RegistrarResultadosExamenRequest](#schemaregistrarresultadosexamenrequest)|true|none|

> Example responses

> 200 Response

<h3 id="registrarresultados-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ExamenDTO](#schemaexamendto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## marcarRealizado

<a id="opIdmarcarRealizado"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/examenes/{id}/marcar-realizado \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/examenes/{id}/marcar-realizado HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/{id}/marcar-realizado',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/examenes/{id}/marcar-realizado',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/examenes/{id}/marcar-realizado', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/examenes/{id}/marcar-realizado', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/{id}/marcar-realizado");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/examenes/{id}/marcar-realizado", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/examenes/{id}/marcar-realizado`

<h3 id="marcarrealizado-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="marcarrealizado-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ExamenDTO](#schemaexamendto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cancelar

<a id="opIdcancelar"></a>

> Code samples

```shell
# You can also use wget
curl -X PATCH http://localhost:8080/api/v1/examenes/{id}/cancelar?motivo=string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
PATCH http://localhost:8080/api/v1/examenes/{id}/cancelar?motivo=string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/{id}/cancelar?motivo=string',
{
  method: 'PATCH',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.patch 'http://localhost:8080/api/v1/examenes/{id}/cancelar',
  params: {
  'motivo' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.patch('http://localhost:8080/api/v1/examenes/{id}/cancelar', params={
  'motivo': 'string'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('PATCH','http://localhost:8080/api/v1/examenes/{id}/cancelar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/{id}/cancelar?motivo=string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("PATCH");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("PATCH", "http://localhost:8080/api/v1/examenes/{id}/cancelar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`PATCH /api/v1/examenes/{id}/cancelar`

<h3 id="cancelar-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|motivo|query|string|true|none|

> Example responses

> 200 Response

<h3 id="cancelar-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ExamenDTO](#schemaexamendto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorId_8

<a id="opIdobtenerPorId_8"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/{id}`

<h3 id="obtenerporid_8-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_8-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ExamenDTO](#schemaexamendto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_7

<a id="opIdeliminar_7"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/examenes/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/examenes/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/examenes/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/examenes/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/examenes/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/examenes/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/examenes/{id}`

<h3 id="eliminar_7-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_7-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarUrgentes

<a id="opIdlistarUrgentes"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/urgentes \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/urgentes HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/urgentes',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/urgentes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/urgentes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/urgentes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/urgentes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/urgentes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/urgentes`

> Example responses

> 200 Response

<h3 id="listarurgentes-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarurgentes-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorTipo_2

<a id="opIdlistarPorTipo_2"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/tipo/{tipoExamen} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/tipo/{tipoExamen} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/tipo/{tipoExamen}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/tipo/{tipoExamen}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/tipo/{tipoExamen}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/tipo/{tipoExamen}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/tipo/{tipoExamen}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/tipo/{tipoExamen}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/tipo/{tipoExamen}`

<h3 id="listarportipo_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|tipoExamen|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarportipo_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarportipo_2-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarConResultadosAnormales

<a id="opIdlistarConResultadosAnormales"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/resultados-anormales \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/resultados-anormales HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/resultados-anormales',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/resultados-anormales',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/resultados-anormales', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/resultados-anormales', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/resultados-anormales");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/resultados-anormales", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/resultados-anormales`

> Example responses

> 200 Response

<h3 id="listarconresultadosanormales-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarconresultadosanormales-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorRangoFechasSolicitud

<a id="opIdlistarPorRangoFechasSolicitud"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/rango-fechas',
  params: {
  'fechaInicio' => 'string(date)',
'fechaFin' => 'string(date)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/rango-fechas', params={
  'fechaInicio': '2019-08-24',  'fechaFin': '2019-08-24'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/rango-fechas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/rango-fechas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/rango-fechas`

<h3 id="listarporrangofechassolicitud-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fechaInicio|query|string(date)|true|none|
|fechaFin|query|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporrangofechassolicitud-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporrangofechassolicitud-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPendientes_1

<a id="opIdlistarPendientes_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/pendientes \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/pendientes HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/pendientes',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/pendientes',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/pendientes', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/pendientes', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/pendientes");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/pendientes", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/pendientes`

> Example responses

> 200 Response

<h3 id="listarpendientes_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarpendientes_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorPaciente_2

<a id="opIdlistarPorPaciente_2"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/paciente/{pacienteId}`

<h3 id="listarporpaciente_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporpaciente_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporpaciente_2-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarLaboratorio

<a id="opIdlistarLaboratorio"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/laboratorio \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/laboratorio HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/laboratorio',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/laboratorio',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/laboratorio', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/laboratorio', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/laboratorio");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/laboratorio", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/laboratorio`

> Example responses

> 200 Response

<h3 id="listarlaboratorio-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarlaboratorio-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarImagenologia

<a id="opIdlistarImagenologia"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/imagenologia \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/imagenologia HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/imagenologia',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/imagenologia',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/imagenologia', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/imagenologia', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/imagenologia");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/imagenologia", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/imagenologia`

> Example responses

> 200 Response

<h3 id="listarimagenologia-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarimagenologia-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorEstado_1

<a id="opIdlistarPorEstado_1"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/estado/{estado} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/estado/{estado} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/estado/{estado}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/estado/{estado}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/estado/{estado}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/estado/{estado}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/estado/{estado}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/estado/{estado}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/estado/{estado}`

<h3 id="listarporestado_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|estado|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarporestado_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporestado_1-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarDemorados

<a id="opIdlistarDemorados"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/demorados \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/demorados HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/demorados',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/demorados',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/demorados', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/demorados', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/demorados");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/demorados", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/demorados`

<h3 id="listardemorados-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|dias|query|integer(int32)|false|none|

> Example responses

> 200 Response

<h3 id="listardemorados-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listardemorados-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorConsulta

<a id="opIdlistarPorConsulta"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/consulta/{consultaId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/consulta/{consultaId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/consulta/{consultaId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/consulta/{consultaId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/consulta/{consultaId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/consulta/{consultaId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/consulta/{consultaId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/consulta/{consultaId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/consulta/{consultaId}`

<h3 id="listarporconsulta-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|consultaId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporconsulta-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporconsulta-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarCompletados

<a id="opIdlistarCompletados"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/examenes/completados \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/examenes/completados HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/examenes/completados',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/examenes/completados',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/examenes/completados', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/examenes/completados', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/examenes/completados");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/examenes/completados", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/examenes/completados`

> Example responses

> 200 Response

<h3 id="listarcompletados-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarcompletados-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoExamen|string|false|none|none|
|» nombre|string|false|none|none|
|» descripcion|string|false|none|none|
|» fechaSolicitud|string(date)|false|none|none|
|» fechaRealizacion|string(date)|false|none|none|
|» fechaResultados|string(date-time)|false|none|none|
|» estado|string|false|none|none|
|» resultados|string|false|none|none|
|» valoresReferencia|string|false|none|none|
|» interpretacion|string|false|none|none|
|» hallazgos|string|false|none|none|
|» archivoRuta|string|false|none|none|
|» archivoTipo|string|false|none|none|
|» laboratorioExterno|string|false|none|none|
|» referenciaExterna|string|false|none|none|
|» costo|number(double)|false|none|none|
|» notas|string|false|none|none|
|» requiereAyuno|boolean|false|none|none|
|» esUrgente|boolean|false|none|none|
|» resultadoAnormal|boolean|false|none|none|
|» historialClinicoId|integer(int64)|false|none|none|
|» pacienteId|integer(int64)|false|none|none|
|» pacienteNombre|string|false|none|none|
|» veterinarioSolicitanteId|integer(int64)|false|none|none|
|» veterinarioSolicitanteNombre|string|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» estaPendiente|boolean|false|none|none|
|» estaCompletado|boolean|false|none|none|
|» esLaboratorio|boolean|false|none|none|
|» esImagenologia|boolean|false|none|none|
|» tieneArchivo|boolean|false|none|none|
|» esExterno|boolean|false|none|none|
|» diasDesdeSolicitud|integer(int64)|false|none|none|
|» estadoDescriptivo|string|false|none|none|
|» resumen|string|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-diagnostico-controller">diagnostico-controller</h1>

## crear_5

<a id="opIdcrear_5"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/v1/diagnosticos \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/v1/diagnosticos HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "consultaId": 0,
  "tipoDiagnostico": "string",
  "descripcion": "string",
  "codigoCie10": "string",
  "gravedad": "string",
  "notas": "string",
  "esPrincipal": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/v1/diagnosticos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/v1/diagnosticos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/v1/diagnosticos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/v1/diagnosticos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/v1/diagnosticos`

> Body parameter

```json
{
  "consultaId": 0,
  "tipoDiagnostico": "string",
  "descripcion": "string",
  "codigoCie10": "string",
  "gravedad": "string",
  "notas": "string",
  "esPrincipal": true
}
```

<h3 id="crear_5-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CreateDiagnosticoRequest](#schemacreatediagnosticorequest)|true|none|

> Example responses

> 200 Response

<h3 id="crear_5-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[DiagnosticoDTO](#schemadiagnosticodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerPorId_9

<a id="opIdobtenerPorId_9"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/{id} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/{id} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/{id}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/{id}`

<h3 id="obtenerporid_9-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="obtenerporid_9-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[DiagnosticoDTO](#schemadiagnosticodto)|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## eliminar_8

<a id="opIdeliminar_8"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/api/v1/diagnosticos/{id} \
  -H 'Authorization: Bearer {access-token}'

```

```http
DELETE http://localhost:8080/api/v1/diagnosticos/{id} HTTP/1.1
Host: localhost:8080

```

```javascript

const headers = {
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/{id}',
{
  method: 'DELETE',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.delete 'http://localhost:8080/api/v1/diagnosticos/{id}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Authorization': 'Bearer {access-token}'
}

r = requests.delete('http://localhost:8080/api/v1/diagnosticos/{id}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('DELETE','http://localhost:8080/api/v1/diagnosticos/{id}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/{id}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("DELETE");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("DELETE", "http://localhost:8080/api/v1/diagnosticos/{id}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`DELETE /api/v1/diagnosticos/{id}`

<h3 id="eliminar_8-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

<h3 id="eliminar_8-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorTipo_3

<a id="opIdlistarPorTipo_3"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/tipo/{tipoDiagnostico}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/tipo/{tipoDiagnostico}`

<h3 id="listarportipo_3-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|tipoDiagnostico|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarportipo_3-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarportipo_3-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorRangoFechas_2

<a id="opIdlistarPorRangoFechas_2"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24 HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/rango-fechas',
  params: {
  'fechaInicio' => 'string(date)',
'fechaFin' => 'string(date)'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/rango-fechas', params={
  'fechaInicio': '2019-08-24',  'fechaFin': '2019-08-24'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/rango-fechas', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/rango-fechas?fechaInicio=2019-08-24&fechaFin=2019-08-24");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/rango-fechas", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/rango-fechas`

<h3 id="listarporrangofechas_2-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fechaInicio|query|string(date)|true|none|
|fechaFin|query|string(date)|true|none|

> Example responses

> 200 Response

<h3 id="listarporrangofechas_2-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporrangofechas_2-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPrincipales

<a id="opIdlistarPrincipales"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/principales \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/principales HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/principales',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/principales',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/principales', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/principales', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/principales");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/principales", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/principales`

> Example responses

> 200 Response

<h3 id="listarprincipales-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarprincipales-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorPaciente_3

<a id="opIdlistarPorPaciente_3"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/paciente/{pacienteId}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/paciente/{pacienteId}`

<h3 id="listarporpaciente_3-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|pacienteId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="listarporpaciente_3-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporpaciente_3-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarGravesOCriticos

<a id="opIdlistarGravesOCriticos"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/graves-criticos \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/graves-criticos HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/graves-criticos',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/graves-criticos',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/graves-criticos', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/graves-criticos', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/graves-criticos");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/graves-criticos", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/graves-criticos`

> Example responses

> 200 Response

<h3 id="listargravesocriticos-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listargravesocriticos-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorGravedad

<a id="opIdlistarPorGravedad"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/gravedad/{gravedad}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/gravedad/{gravedad}`

<h3 id="listarporgravedad-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|gravedad|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarporgravedad-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporgravedad-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## listarPorCodigoCie10

<a id="opIdlistarPorCodigoCie10"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10} \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10} HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10}',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10}',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10}', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10}', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10}");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/cie10/{codigoCie10}", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/cie10/{codigoCie10}`

<h3 id="listarporcodigocie10-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|codigoCie10|path|string|true|none|

> Example responses

> 200 Response

<h3 id="listarporcodigocie10-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="listarporcodigocie10-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## buscarPorDescripcion

<a id="opIdbuscarPorDescripcion"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/v1/diagnosticos/buscar?descripcion=string \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/v1/diagnosticos/buscar?descripcion=string HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/v1/diagnosticos/buscar?descripcion=string',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/v1/diagnosticos/buscar',
  params: {
  'descripcion' => 'string'
}, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/v1/diagnosticos/buscar', params={
  'descripcion': 'string'
}, headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/v1/diagnosticos/buscar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/v1/diagnosticos/buscar?descripcion=string");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/v1/diagnosticos/buscar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/v1/diagnosticos/buscar`

<h3 id="buscarpordescripcion-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|descripcion|query|string|true|none|

> Example responses

> 200 Response

<h3 id="buscarpordescripcion-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="buscarpordescripcion-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|» id|integer(int64)|false|none|none|
|» tipoDiagnostico|string|false|none|none|
|» descripcion|string|false|none|none|
|» codigoCie10|string|false|none|none|
|» gravedad|string|false|none|none|
|» notas|string|false|none|none|
|» esPrincipal|boolean|false|none|none|
|» consultaId|integer(int64)|false|none|none|
|» esPresuntivo|boolean|false|none|none|
|» esDefinitivo|boolean|false|none|none|
|» esGraveOCritico|boolean|false|none|none|
|» tieneCie10|boolean|false|none|none|
|» createdAt|string(date-time)|false|none|none|
|» createdBy|string|false|none|none|
|» updatedAt|string(date-time)|false|none|none|
|» updatedBy|string|false|none|none|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

<h1 id="sistema-gestion-veterinaria-api-rest-auth-controller">auth-controller</h1>

## recuperarPassword

<a id="opIdrecuperarPassword"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/auth/recuperar-password \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/auth/recuperar-password HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "email": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/recuperar-password',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/auth/recuperar-password',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/auth/recuperar-password', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/auth/recuperar-password', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/recuperar-password");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/auth/recuperar-password", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/auth/recuperar-password`

> Body parameter

```json
{
  "email": "string"
}
```

<h3 id="recuperarpassword-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[RecuperarPasswordRequest](#schemarecuperarpasswordrequest)|true|none|

> Example responses

> 200 Response

<h3 id="recuperarpassword-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="recuperarpassword-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## logout

<a id="opIdlogout"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/auth/logout \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/auth/logout HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "cerrarTodasLasSesiones": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/logout',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/auth/logout',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/auth/logout', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/auth/logout', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/logout");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/auth/logout", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/auth/logout`

> Body parameter

```json
{
  "cerrarTodasLasSesiones": true
}
```

<h3 id="logout-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[LogoutRequest](#schemalogoutrequest)|false|none|

> Example responses

> 200 Response

<h3 id="logout-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="logout-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## login

<a id="opIdlogin"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "email": "string",
  "password": "string",
  "huellaDispositivo": "string",
  "nombreDispositivo": "string",
  "recordarDispositivo": true
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/login',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/auth/login',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/auth/login', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/auth/login', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/login");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/auth/login", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/auth/login`

> Body parameter

```json
{
  "email": "string",
  "password": "string",
  "huellaDispositivo": "string",
  "nombreDispositivo": "string",
  "recordarDispositivo": true
}
```

<h3 id="login-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[LoginRequest](#schemaloginrequest)|true|none|

> Example responses

> 200 Response

<h3 id="login-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="login-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## cambiarPassword_1

<a id="opIdcambiarPassword_1"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/api/auth/cambiar-password \
  -H 'Content-Type: application/json' \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
POST http://localhost:8080/api/auth/cambiar-password HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*

```

```javascript
const inputBody = '{
  "passwordActual": "string",
  "passwordNueva": "string",
  "passwordConfirmacion": "string"
}';
const headers = {
  'Content-Type':'application/json',
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/cambiar-password',
{
  method: 'POST',
  body: inputBody,
  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Content-Type' => 'application/json',
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.post 'http://localhost:8080/api/auth/cambiar-password',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Content-Type': 'application/json',
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.post('http://localhost:8080/api/auth/cambiar-password', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Content-Type' => 'application/json',
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('POST','http://localhost:8080/api/auth/cambiar-password', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/cambiar-password");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("POST");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Content-Type": []string{"application/json"},
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("POST", "http://localhost:8080/api/auth/cambiar-password", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`POST /api/auth/cambiar-password`

> Body parameter

```json
{
  "passwordActual": "string",
  "passwordNueva": "string",
  "passwordConfirmacion": "string"
}
```

<h3 id="cambiarpassword_1-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CambiarPasswordRequest](#schemacambiarpasswordrequest)|true|none|

> Example responses

> 200 Response

<h3 id="cambiarpassword_1-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="cambiarpassword_1-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## validarToken

<a id="opIdvalidarToken"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/auth/validar \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/auth/validar HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/validar',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/auth/validar',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/auth/validar', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/auth/validar', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/validar");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/auth/validar", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/auth/validar`

> Example responses

> 200 Response

<h3 id="validartoken-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="validartoken-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## ping

<a id="opIdping"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/auth/ping \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/auth/ping HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/ping',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/auth/ping',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/auth/ping', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/auth/ping', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/ping");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/auth/ping", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/auth/ping`

> Example responses

> 200 Response

<h3 id="ping-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="ping-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

## obtenerUsuarioActual

<a id="opIdobtenerUsuarioActual"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/api/auth/me \
  -H 'Accept: */*' \
  -H 'Authorization: Bearer {access-token}'

```

```http
GET http://localhost:8080/api/auth/me HTTP/1.1
Host: localhost:8080
Accept: */*

```

```javascript

const headers = {
  'Accept':'*/*',
  'Authorization':'Bearer {access-token}'
};

fetch('http://localhost:8080/api/auth/me',
{
  method: 'GET',

  headers: headers
})
.then(function(res) {
    return res.json();
}).then(function(body) {
    console.log(body);
});

```

```ruby
require 'rest-client'
require 'json'

headers = {
  'Accept' => '*/*',
  'Authorization' => 'Bearer {access-token}'
}

result = RestClient.get 'http://localhost:8080/api/auth/me',
  params: {
  }, headers: headers

p JSON.parse(result)

```

```python
import requests
headers = {
  'Accept': '*/*',
  'Authorization': 'Bearer {access-token}'
}

r = requests.get('http://localhost:8080/api/auth/me', headers = headers)

print(r.json())

```

```php
<?php

require 'vendor/autoload.php';

$headers = array(
    'Accept' => '*/*',
    'Authorization' => 'Bearer {access-token}',
);

$client = new \GuzzleHttp\Client();

// Define array of request body.
$request_body = array();

try {
    $response = $client->request('GET','http://localhost:8080/api/auth/me', array(
        'headers' => $headers,
        'json' => $request_body,
       )
    );
    print_r($response->getBody()->getContents());
 }
 catch (\GuzzleHttp\Exception\BadResponseException $e) {
    // handle exception or api errors.
    print_r($e->getMessage());
 }

 // ...

```

```java
URL obj = new URL("http://localhost:8080/api/auth/me");
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
con.setRequestMethod("GET");
int responseCode = con.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(con.getInputStream()));
String inputLine;
StringBuffer response = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
System.out.println(response.toString());

```

```go
package main

import (
       "bytes"
       "net/http"
)

func main() {

    headers := map[string][]string{
        "Accept": []string{"*/*"},
        "Authorization": []string{"Bearer {access-token}"},
    }

    data := bytes.NewBuffer([]byte{jsonReq})
    req, err := http.NewRequest("GET", "http://localhost:8080/api/auth/me", data)
    req.Header = headers

    client := &http.Client{}
    resp, err := client.Do(req)
    // ...
}

```

`GET /api/auth/me`

> Example responses

> 200 Response

<h3 id="obtenerusuarioactual-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="obtenerusuarioactual-responseschema">Response Schema</h3>

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
Bearer Authentication
</aside>

# Schemas

<h2 id="tocS_UpdateUsuarioRequest">UpdateUsuarioRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdateusuariorequest"></a>
<a id="schema_UpdateUsuarioRequest"></a>
<a id="tocSupdateusuariorequest"></a>
<a id="tocsupdateusuariorequest"></a>

```json
{
  "email": "string",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "roleIds": [
    0
  ],
  "fotoperfilUrl": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|email|string|false|none|none|
|nombre|string|false|none|none|
|apellido|string|false|none|none|
|dni|string|false|none|none|
|telefono|string|false|none|none|
|direccion|string|false|none|none|
|roleIds|[integer]|false|none|none|
|fotoperfilUrl|string|false|none|none|

<h2 id="tocS_UsuarioDTO">UsuarioDTO</h2>
<!-- backwards compatibility -->
<a id="schemausuariodto"></a>
<a id="schema_UsuarioDTO"></a>
<a id="tocSusuariodto"></a>
<a id="tocsusuariodto"></a>

```json
{
  "id": 0,
  "username": "string",
  "email": "string",
  "nombre": "string",
  "apellido": "string",
  "nombreCompleto": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "tipoUsuario": "ADMINISTRADOR",
  "roles": [
    "string"
  ],
  "cuentaBloqueada": true,
  "cuentaExpirada": true,
  "intentosFallidos": 0,
  "ultimoAcceso": "2019-08-24T14:15:22Z",
  "fechaCambioPassword": "2019-08-24T14:15:22Z",
  "requiereCambioPassword": true,
  "fotoperfilUrl": "string",
  "isActive": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|username|string|false|none|none|
|email|string|false|none|none|
|nombre|string|false|none|none|
|apellido|string|false|none|none|
|nombreCompleto|string|false|none|none|
|dni|string|false|none|none|
|telefono|string|false|none|none|
|direccion|string|false|none|none|
|tipoUsuario|string|false|none|none|
|roles|[string]|false|none|none|
|cuentaBloqueada|boolean|false|none|none|
|cuentaExpirada|boolean|false|none|none|
|intentosFallidos|integer(int32)|false|none|none|
|ultimoAcceso|string(date-time)|false|none|none|
|fechaCambioPassword|string(date-time)|false|none|none|
|requiereCambioPassword|boolean|false|none|none|
|fotoperfilUrl|string|false|none|none|
|isActive|boolean|false|none|none|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|tipoUsuario|ADMINISTRADOR|
|tipoUsuario|VETERINARIO|
|tipoUsuario|ASISTENTE|
|tipoUsuario|RECEPCIONISTA|
|tipoUsuario|PROPIETARIO|

<h2 id="tocS_UpdateTipoServicioRequest">UpdateTipoServicioRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdatetiposerviciorequest"></a>
<a id="schema_UpdateTipoServicioRequest"></a>
<a id="tocSupdatetiposerviciorequest"></a>
<a id="tocsupdatetiposerviciorequest"></a>

```json
{
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 5,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nombre|string|false|none|none|
|descripcion|string|false|none|none|
|duracionEstimada|integer(int32)|false|none|none|
|precioBase|number(double)|false|none|none|
|categoria|string|false|none|none|
|requiereConfirmacion|boolean|false|none|none|

<h2 id="tocS_TipoServicioDTO">TipoServicioDTO</h2>
<!-- backwards compatibility -->
<a id="schematiposerviciodto"></a>
<a id="schema_TipoServicioDTO"></a>
<a id="tocStiposerviciodto"></a>
<a id="tocstiposerviciodto"></a>

```json
{
  "id": 0,
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 0,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true,
  "estaDisponible": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|descripcion|string|false|none|none|
|duracionEstimada|integer(int32)|false|none|none|
|precioBase|number(double)|false|none|none|
|categoria|string|false|none|none|
|requiereConfirmacion|boolean|false|none|none|
|estaDisponible|boolean|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_UpdateRazaRequest">UpdateRazaRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdaterazarequest"></a>
<a id="schema_UpdateRazaRequest"></a>
<a id="tocSupdaterazarequest"></a>
<a id="tocsupdaterazarequest"></a>

```json
{
  "nombre": "string",
  "descripcion": "string",
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1,
  "isActive": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nombre|string|false|none|none|
|descripcion|string|false|none|none|
|esMestizo|boolean|false|none|none|
|tamanioTipico|string|false|none|none|
|pesoPromedioKg|number(double)|false|none|none|
|isActive|boolean|false|none|none|

<h2 id="tocS_RazaDTO">RazaDTO</h2>
<!-- backwards compatibility -->
<a id="schemarazadto"></a>
<a id="schema_RazaDTO"></a>
<a id="tocSrazadto"></a>
<a id="tocsrazadto"></a>

```json
{
  "id": 0,
  "nombre": "string",
  "especie": "PERRO",
  "descripcion": "string",
  "esPredefinida": true,
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1,
  "isActive": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|especie|string|false|none|none|
|descripcion|string|false|none|none|
|esPredefinida|boolean|false|none|none|
|esMestizo|boolean|false|none|none|
|tamanioTipico|string|false|none|none|
|pesoPromedioKg|number(double)|false|none|none|
|isActive|boolean|false|none|none|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedBy|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<h2 id="tocS_UpdatePacienteRequest">UpdatePacienteRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdatepacienterequest"></a>
<a id="schema_UpdatePacienteRequest"></a>
<a id="tocSupdatepacienterequest"></a>
<a id="tocsupdatepacienterequest"></a>

```json
{
  "raza": "string",
  "color": "string",
  "pesoKg": 0.1,
  "estado": "ACTIVO",
  "fotoUrl": "string",
  "observaciones": "string",
  "clienteId": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|raza|string|false|none|none|
|color|string|false|none|none|
|pesoKg|number(double)|false|none|none|
|estado|string|false|none|none|
|fotoUrl|string|false|none|none|
|observaciones|string|false|none|none|
|clienteId|integer(int64)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<h2 id="tocS_ClienteResumenDTO">ClienteResumenDTO</h2>
<!-- backwards compatibility -->
<a id="schemaclienteresumendto"></a>
<a id="schema_ClienteResumenDTO"></a>
<a id="tocSclienteresumendto"></a>
<a id="tocsclienteresumendto"></a>

```json
{
  "id": 0,
  "nombreCompleto": "string",
  "telefono": "string",
  "email": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombreCompleto|string|false|none|none|
|telefono|string|false|none|none|
|email|string|false|none|none|

<h2 id="tocS_PacienteDTO">PacienteDTO</h2>
<!-- backwards compatibility -->
<a id="schemapacientedto"></a>
<a id="schema_PacienteDTO"></a>
<a id="tocSpacientedto"></a>
<a id="tocspacientedto"></a>

```json
{
  "id": 0,
  "nombre": "string",
  "especie": "PERRO",
  "raza": "string",
  "fechaNacimiento": "2019-08-24",
  "edadAnios": 0,
  "sexo": "MACHO",
  "color": "string",
  "pesoKg": 0.1,
  "estado": "ACTIVO",
  "fotoUrl": "string",
  "observaciones": "string",
  "microchip": "string",
  "cliente": {
    "id": 0,
    "nombreCompleto": "string",
    "telefono": "string",
    "email": "string"
  },
  "cuidadosEspecificos": "string",
  "dietaRecomendada": "string",
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|especie|string|false|none|none|
|raza|string|false|none|none|
|fechaNacimiento|string(date)|false|none|none|
|edadAnios|integer(int32)|false|none|none|
|sexo|string|false|none|none|
|color|string|false|none|none|
|pesoKg|number(double)|false|none|none|
|estado|string|false|none|none|
|fotoUrl|string|false|none|none|
|observaciones|string|false|none|none|
|microchip|string|false|none|none|
|cliente|[ClienteResumenDTO](#schemaclienteresumendto)|false|none|none|
|cuidadosEspecificos|string|false|none|none|
|dietaRecomendada|string|false|none|none|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|
|estado|ACTIVO|
|estado|INACTIVO|
|estado|FALLECIDO|

<h2 id="tocS_UpdateHistorialClinicoRequest">UpdateHistorialClinicoRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdatehistorialclinicorequest"></a>
<a id="schema_UpdateHistorialClinicoRequest"></a>
<a id="tocSupdatehistorialclinicorequest"></a>
<a id="tocsupdatehistorialclinicorequest"></a>

```json
{
  "grupoSanguineo": "string",
  "alergias": "string",
  "condicionesCronicas": "string",
  "notasImportantes": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|grupoSanguineo|string|false|none|none|
|alergias|string|false|none|none|
|condicionesCronicas|string|false|none|none|
|notasImportantes|string|false|none|none|

<h2 id="tocS_ConsultaDTO">ConsultaDTO</h2>
<!-- backwards compatibility -->
<a id="schemaconsultadto"></a>
<a id="schema_ConsultaDTO"></a>
<a id="tocSconsultadto"></a>
<a id="tocsconsultadto"></a>

```json
{
  "id": 0,
  "fechaConsulta": "2019-08-24",
  "horaInicio": "2019-08-24T14:15:22Z",
  "horaFin": "2019-08-24T14:15:22Z",
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "historialClinicoId": 0,
  "pacienteId": 0,
  "pacienteNombre": "string",
  "pacienteEspecie": "string",
  "veterinarioId": 0,
  "veterinarioNombre": "string",
  "veterinarioEspecialidad": "string",
  "citaId": 0,
  "signosVitales": {
    "temperatura": 0.1,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 0,
    "frecuenciaRespiratoria": 0,
    "temperaturaRectal": 0.1,
    "condicionCorporal": 0,
    "condicionCorporalDescripcion": "string",
    "tieneParametrosAnormales": true,
    "tieneFiebre": true,
    "tieneHipotermia": true
  },
  "diagnosticos": [
    {
      "id": 0,
      "tipoDiagnostico": "string",
      "descripcion": "string",
      "codigoCie10": "string",
      "gravedad": "string",
      "notas": "string",
      "esPrincipal": true,
      "consultaId": 0,
      "esPresuntivo": true,
      "esDefinitivo": true,
      "esGraveOCritico": true,
      "tieneCie10": true,
      "createdAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedAt": "2019-08-24T14:15:22Z",
      "updatedBy": "string"
    }
  ],
  "tratamientos": [
    {
      "id": 0,
      "tipoTratamiento": "string",
      "nombre": "string",
      "descripcion": "string",
      "dosis": "string",
      "frecuencia": "string",
      "viaAdministracion": "string",
      "duracionDias": 0,
      "fechaInicio": "2019-08-24",
      "fechaFin": "2019-08-24",
      "indicaciones": "string",
      "efectosSecundarios": "string",
      "cantidad": 0.1,
      "unidad": "string",
      "activo": true,
      "requiereSupervision": true,
      "consultaId": 0,
      "esMedicamento": true,
      "haFinalizado": true,
      "estaVigente": true,
      "resumen": "string",
      "createdAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedAt": "2019-08-24T14:15:22Z",
      "updatedBy": "string"
    }
  ],
  "duracionMinutos": 0,
  "estaEnCurso": true,
  "estaFinalizada": true,
  "resumen": "string",
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|fechaConsulta|string(date)|false|none|none|
|horaInicio|string(date-time)|false|none|none|
|horaFin|string(date-time)|false|none|none|
|motivo|string|false|none|none|
|anamnesis|string|false|none|none|
|examenFisico|string|false|none|none|
|observaciones|string|false|none|none|
|planTratamiento|string|false|none|none|
|pronostico|string|false|none|none|
|requiereSeguimiento|boolean|false|none|none|
|fechaSeguimiento|string(date)|false|none|none|
|historialClinicoId|integer(int64)|false|none|none|
|pacienteId|integer(int64)|false|none|none|
|pacienteNombre|string|false|none|none|
|pacienteEspecie|string|false|none|none|
|veterinarioId|integer(int64)|false|none|none|
|veterinarioNombre|string|false|none|none|
|veterinarioEspecialidad|string|false|none|none|
|citaId|integer(int64)|false|none|none|
|signosVitales|[SignosVitalesDTO](#schemasignosvitalesdto)|false|none|none|
|diagnosticos|[[DiagnosticoDTO](#schemadiagnosticodto)]|false|none|none|
|tratamientos|[[TratamientoDTO](#schematratamientodto)]|false|none|none|
|duracionMinutos|integer(int64)|false|none|none|
|estaEnCurso|boolean|false|none|none|
|estaFinalizada|boolean|false|none|none|
|resumen|string|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_DiagnosticoDTO">DiagnosticoDTO</h2>
<!-- backwards compatibility -->
<a id="schemadiagnosticodto"></a>
<a id="schema_DiagnosticoDTO"></a>
<a id="tocSdiagnosticodto"></a>
<a id="tocsdiagnosticodto"></a>

```json
{
  "id": 0,
  "tipoDiagnostico": "string",
  "descripcion": "string",
  "codigoCie10": "string",
  "gravedad": "string",
  "notas": "string",
  "esPrincipal": true,
  "consultaId": 0,
  "esPresuntivo": true,
  "esDefinitivo": true,
  "esGraveOCritico": true,
  "tieneCie10": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|tipoDiagnostico|string|false|none|none|
|descripcion|string|false|none|none|
|codigoCie10|string|false|none|none|
|gravedad|string|false|none|none|
|notas|string|false|none|none|
|esPrincipal|boolean|false|none|none|
|consultaId|integer(int64)|false|none|none|
|esPresuntivo|boolean|false|none|none|
|esDefinitivo|boolean|false|none|none|
|esGraveOCritico|boolean|false|none|none|
|tieneCie10|boolean|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_ExamenDTO">ExamenDTO</h2>
<!-- backwards compatibility -->
<a id="schemaexamendto"></a>
<a id="schema_ExamenDTO"></a>
<a id="tocSexamendto"></a>
<a id="tocsexamendto"></a>

```json
{
  "id": 0,
  "tipoExamen": "string",
  "nombre": "string",
  "descripcion": "string",
  "fechaSolicitud": "2019-08-24",
  "fechaRealizacion": "2019-08-24",
  "fechaResultados": "2019-08-24T14:15:22Z",
  "estado": "string",
  "resultados": "string",
  "valoresReferencia": "string",
  "interpretacion": "string",
  "hallazgos": "string",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "laboratorioExterno": "string",
  "referenciaExterna": "string",
  "costo": 0.1,
  "notas": "string",
  "requiereAyuno": true,
  "esUrgente": true,
  "resultadoAnormal": true,
  "historialClinicoId": 0,
  "pacienteId": 0,
  "pacienteNombre": "string",
  "veterinarioSolicitanteId": 0,
  "veterinarioSolicitanteNombre": "string",
  "consultaId": 0,
  "estaPendiente": true,
  "estaCompletado": true,
  "esLaboratorio": true,
  "esImagenologia": true,
  "tieneArchivo": true,
  "esExterno": true,
  "diasDesdeSolicitud": 0,
  "estadoDescriptivo": "string",
  "resumen": "string",
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|tipoExamen|string|false|none|none|
|nombre|string|false|none|none|
|descripcion|string|false|none|none|
|fechaSolicitud|string(date)|false|none|none|
|fechaRealizacion|string(date)|false|none|none|
|fechaResultados|string(date-time)|false|none|none|
|estado|string|false|none|none|
|resultados|string|false|none|none|
|valoresReferencia|string|false|none|none|
|interpretacion|string|false|none|none|
|hallazgos|string|false|none|none|
|archivoRuta|string|false|none|none|
|archivoTipo|string|false|none|none|
|laboratorioExterno|string|false|none|none|
|referenciaExterna|string|false|none|none|
|costo|number(double)|false|none|none|
|notas|string|false|none|none|
|requiereAyuno|boolean|false|none|none|
|esUrgente|boolean|false|none|none|
|resultadoAnormal|boolean|false|none|none|
|historialClinicoId|integer(int64)|false|none|none|
|pacienteId|integer(int64)|false|none|none|
|pacienteNombre|string|false|none|none|
|veterinarioSolicitanteId|integer(int64)|false|none|none|
|veterinarioSolicitanteNombre|string|false|none|none|
|consultaId|integer(int64)|false|none|none|
|estaPendiente|boolean|false|none|none|
|estaCompletado|boolean|false|none|none|
|esLaboratorio|boolean|false|none|none|
|esImagenologia|boolean|false|none|none|
|tieneArchivo|boolean|false|none|none|
|esExterno|boolean|false|none|none|
|diasDesdeSolicitud|integer(int64)|false|none|none|
|estadoDescriptivo|string|false|none|none|
|resumen|string|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_HistorialClinicoDTO">HistorialClinicoDTO</h2>
<!-- backwards compatibility -->
<a id="schemahistorialclinicodto"></a>
<a id="schema_HistorialClinicoDTO"></a>
<a id="tocShistorialclinicodto"></a>
<a id="tocshistorialclinicodto"></a>

```json
{
  "id": 0,
  "fechaApertura": "2019-08-24",
  "grupoSanguineo": "string",
  "alergias": "string",
  "condicionesCronicas": "string",
  "notasImportantes": "string",
  "pacienteId": 0,
  "pacienteNombre": "string",
  "pacienteEspecie": "string",
  "pacienteRaza": "string",
  "consultas": [
    {
      "id": 0,
      "fechaConsulta": "2019-08-24",
      "horaInicio": "2019-08-24T14:15:22Z",
      "horaFin": "2019-08-24T14:15:22Z",
      "motivo": "string",
      "anamnesis": "string",
      "examenFisico": "string",
      "observaciones": "string",
      "planTratamiento": "string",
      "pronostico": "string",
      "requiereSeguimiento": true,
      "fechaSeguimiento": "2019-08-24",
      "historialClinicoId": 0,
      "pacienteId": 0,
      "pacienteNombre": "string",
      "pacienteEspecie": "string",
      "veterinarioId": 0,
      "veterinarioNombre": "string",
      "veterinarioEspecialidad": "string",
      "citaId": 0,
      "signosVitales": {
        "temperatura": 0.1,
        "pesoKg": 0.1,
        "frecuenciaCardiaca": 0,
        "frecuenciaRespiratoria": 0,
        "temperaturaRectal": 0.1,
        "condicionCorporal": 0,
        "condicionCorporalDescripcion": "string",
        "tieneParametrosAnormales": true,
        "tieneFiebre": true,
        "tieneHipotermia": true
      },
      "diagnosticos": [
        {
          "id": 0,
          "tipoDiagnostico": "string",
          "descripcion": "string",
          "codigoCie10": "string",
          "gravedad": "string",
          "notas": "string",
          "esPrincipal": true,
          "consultaId": 0,
          "esPresuntivo": true,
          "esDefinitivo": true,
          "esGraveOCritico": true,
          "tieneCie10": true,
          "createdAt": "2019-08-24T14:15:22Z",
          "createdBy": "string",
          "updatedAt": "2019-08-24T14:15:22Z",
          "updatedBy": "string"
        }
      ],
      "tratamientos": [
        {
          "id": 0,
          "tipoTratamiento": "string",
          "nombre": "string",
          "descripcion": "string",
          "dosis": "string",
          "frecuencia": "string",
          "viaAdministracion": "string",
          "duracionDias": 0,
          "fechaInicio": "2019-08-24",
          "fechaFin": "2019-08-24",
          "indicaciones": "string",
          "efectosSecundarios": "string",
          "cantidad": 0.1,
          "unidad": "string",
          "activo": true,
          "requiereSupervision": true,
          "consultaId": 0,
          "esMedicamento": true,
          "haFinalizado": true,
          "estaVigente": true,
          "resumen": "string",
          "createdAt": "2019-08-24T14:15:22Z",
          "createdBy": "string",
          "updatedAt": "2019-08-24T14:15:22Z",
          "updatedBy": "string"
        }
      ],
      "duracionMinutos": 0,
      "estaEnCurso": true,
      "estaFinalizada": true,
      "resumen": "string",
      "createdAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedAt": "2019-08-24T14:15:22Z",
      "updatedBy": "string"
    }
  ],
  "vacunas": [
    {
      "id": 0,
      "nombre": "string",
      "tipoVacuna": "string",
      "fechaAplicacion": "2019-08-24",
      "fechaProximaDosis": "2019-08-24",
      "lote": "string",
      "laboratorio": "string",
      "dosis": "string",
      "viaAdministracion": "string",
      "numeroDosis": 0,
      "intervaloDias": 0,
      "esRefuerzo": true,
      "observaciones": "string",
      "reaccionesAdversas": "string",
      "pesoAplicacion": 0.1,
      "serieCompleta": true,
      "historialClinicoId": 0,
      "pacienteId": 0,
      "pacienteNombre": "string",
      "veterinarioId": 0,
      "veterinarioNombre": "string",
      "esObligatoria": true,
      "esAntirrábica": true,
      "refuerzoVencido": true,
      "refuerzoProximo": true,
      "tuvoReacciones": true,
      "estado": "string",
      "resumen": "string",
      "createdAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedAt": "2019-08-24T14:15:22Z",
      "updatedBy": "string"
    }
  ],
  "examenes": [
    {
      "id": 0,
      "tipoExamen": "string",
      "nombre": "string",
      "descripcion": "string",
      "fechaSolicitud": "2019-08-24",
      "fechaRealizacion": "2019-08-24",
      "fechaResultados": "2019-08-24T14:15:22Z",
      "estado": "string",
      "resultados": "string",
      "valoresReferencia": "string",
      "interpretacion": "string",
      "hallazgos": "string",
      "archivoRuta": "string",
      "archivoTipo": "string",
      "laboratorioExterno": "string",
      "referenciaExterna": "string",
      "costo": 0.1,
      "notas": "string",
      "requiereAyuno": true,
      "esUrgente": true,
      "resultadoAnormal": true,
      "historialClinicoId": 0,
      "pacienteId": 0,
      "pacienteNombre": "string",
      "veterinarioSolicitanteId": 0,
      "veterinarioSolicitanteNombre": "string",
      "consultaId": 0,
      "estaPendiente": true,
      "estaCompletado": true,
      "esLaboratorio": true,
      "esImagenologia": true,
      "tieneArchivo": true,
      "esExterno": true,
      "diasDesdeSolicitud": 0,
      "estadoDescriptivo": "string",
      "resumen": "string",
      "createdAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedAt": "2019-08-24T14:15:22Z",
      "updatedBy": "string"
    }
  ],
  "ultimaConsulta": {
    "id": 0,
    "fechaConsulta": "2019-08-24",
    "horaInicio": "2019-08-24T14:15:22Z",
    "horaFin": "2019-08-24T14:15:22Z",
    "motivo": "string",
    "anamnesis": "string",
    "examenFisico": "string",
    "observaciones": "string",
    "planTratamiento": "string",
    "pronostico": "string",
    "requiereSeguimiento": true,
    "fechaSeguimiento": "2019-08-24",
    "historialClinicoId": 0,
    "pacienteId": 0,
    "pacienteNombre": "string",
    "pacienteEspecie": "string",
    "veterinarioId": 0,
    "veterinarioNombre": "string",
    "veterinarioEspecialidad": "string",
    "citaId": 0,
    "signosVitales": {
      "temperatura": 0.1,
      "pesoKg": 0.1,
      "frecuenciaCardiaca": 0,
      "frecuenciaRespiratoria": 0,
      "temperaturaRectal": 0.1,
      "condicionCorporal": 0,
      "condicionCorporalDescripcion": "string",
      "tieneParametrosAnormales": true,
      "tieneFiebre": true,
      "tieneHipotermia": true
    },
    "diagnosticos": [
      {
        "id": 0,
        "tipoDiagnostico": "string",
        "descripcion": "string",
        "codigoCie10": "string",
        "gravedad": "string",
        "notas": "string",
        "esPrincipal": true,
        "consultaId": 0,
        "esPresuntivo": true,
        "esDefinitivo": true,
        "esGraveOCritico": true,
        "tieneCie10": true,
        "createdAt": "2019-08-24T14:15:22Z",
        "createdBy": "string",
        "updatedAt": "2019-08-24T14:15:22Z",
        "updatedBy": "string"
      }
    ],
    "tratamientos": [
      {
        "id": 0,
        "tipoTratamiento": "string",
        "nombre": "string",
        "descripcion": "string",
        "dosis": "string",
        "frecuencia": "string",
        "viaAdministracion": "string",
        "duracionDias": 0,
        "fechaInicio": "2019-08-24",
        "fechaFin": "2019-08-24",
        "indicaciones": "string",
        "efectosSecundarios": "string",
        "cantidad": 0.1,
        "unidad": "string",
        "activo": true,
        "requiereSupervision": true,
        "consultaId": 0,
        "esMedicamento": true,
        "haFinalizado": true,
        "estaVigente": true,
        "resumen": "string",
        "createdAt": "2019-08-24T14:15:22Z",
        "createdBy": "string",
        "updatedAt": "2019-08-24T14:15:22Z",
        "updatedBy": "string"
      }
    ],
    "duracionMinutos": 0,
    "estaEnCurso": true,
    "estaFinalizada": true,
    "resumen": "string",
    "createdAt": "2019-08-24T14:15:22Z",
    "createdBy": "string",
    "updatedAt": "2019-08-24T14:15:22Z",
    "updatedBy": "string"
  },
  "totalConsultas": 0,
  "totalVacunas": 0,
  "totalExamenes": 0,
  "tieneAlergias": true,
  "tieneCondicionesCronicas": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|fechaApertura|string(date)|false|none|none|
|grupoSanguineo|string|false|none|none|
|alergias|string|false|none|none|
|condicionesCronicas|string|false|none|none|
|notasImportantes|string|false|none|none|
|pacienteId|integer(int64)|false|none|none|
|pacienteNombre|string|false|none|none|
|pacienteEspecie|string|false|none|none|
|pacienteRaza|string|false|none|none|
|consultas|[[ConsultaDTO](#schemaconsultadto)]|false|none|none|
|vacunas|[[VacunaDTO](#schemavacunadto)]|false|none|none|
|examenes|[[ExamenDTO](#schemaexamendto)]|false|none|none|
|ultimaConsulta|[ConsultaDTO](#schemaconsultadto)|false|none|none|
|totalConsultas|integer(int32)|false|none|none|
|totalVacunas|integer(int32)|false|none|none|
|totalExamenes|integer(int32)|false|none|none|
|tieneAlergias|boolean|false|none|none|
|tieneCondicionesCronicas|boolean|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_SignosVitalesDTO">SignosVitalesDTO</h2>
<!-- backwards compatibility -->
<a id="schemasignosvitalesdto"></a>
<a id="schema_SignosVitalesDTO"></a>
<a id="tocSsignosvitalesdto"></a>
<a id="tocssignosvitalesdto"></a>

```json
{
  "temperatura": 0.1,
  "pesoKg": 0.1,
  "frecuenciaCardiaca": 0,
  "frecuenciaRespiratoria": 0,
  "temperaturaRectal": 0.1,
  "condicionCorporal": 0,
  "condicionCorporalDescripcion": "string",
  "tieneParametrosAnormales": true,
  "tieneFiebre": true,
  "tieneHipotermia": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|temperatura|number(double)|false|none|none|
|pesoKg|number(double)|false|none|none|
|frecuenciaCardiaca|integer(int32)|false|none|none|
|frecuenciaRespiratoria|integer(int32)|false|none|none|
|temperaturaRectal|number(double)|false|none|none|
|condicionCorporal|integer(int32)|false|none|none|
|condicionCorporalDescripcion|string|false|none|none|
|tieneParametrosAnormales|boolean|false|none|none|
|tieneFiebre|boolean|false|none|none|
|tieneHipotermia|boolean|false|none|none|

<h2 id="tocS_TratamientoDTO">TratamientoDTO</h2>
<!-- backwards compatibility -->
<a id="schematratamientodto"></a>
<a id="schema_TratamientoDTO"></a>
<a id="tocStratamientodto"></a>
<a id="tocstratamientodto"></a>

```json
{
  "id": 0,
  "tipoTratamiento": "string",
  "nombre": "string",
  "descripcion": "string",
  "dosis": "string",
  "frecuencia": "string",
  "viaAdministracion": "string",
  "duracionDias": 0,
  "fechaInicio": "2019-08-24",
  "fechaFin": "2019-08-24",
  "indicaciones": "string",
  "efectosSecundarios": "string",
  "cantidad": 0.1,
  "unidad": "string",
  "activo": true,
  "requiereSupervision": true,
  "consultaId": 0,
  "esMedicamento": true,
  "haFinalizado": true,
  "estaVigente": true,
  "resumen": "string",
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|tipoTratamiento|string|false|none|none|
|nombre|string|false|none|none|
|descripcion|string|false|none|none|
|dosis|string|false|none|none|
|frecuencia|string|false|none|none|
|viaAdministracion|string|false|none|none|
|duracionDias|integer(int32)|false|none|none|
|fechaInicio|string(date)|false|none|none|
|fechaFin|string(date)|false|none|none|
|indicaciones|string|false|none|none|
|efectosSecundarios|string|false|none|none|
|cantidad|number(double)|false|none|none|
|unidad|string|false|none|none|
|activo|boolean|false|none|none|
|requiereSupervision|boolean|false|none|none|
|consultaId|integer(int64)|false|none|none|
|esMedicamento|boolean|false|none|none|
|haFinalizado|boolean|false|none|none|
|estaVigente|boolean|false|none|none|
|resumen|string|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_VacunaDTO">VacunaDTO</h2>
<!-- backwards compatibility -->
<a id="schemavacunadto"></a>
<a id="schema_VacunaDTO"></a>
<a id="tocSvacunadto"></a>
<a id="tocsvacunadto"></a>

```json
{
  "id": 0,
  "nombre": "string",
  "tipoVacuna": "string",
  "fechaAplicacion": "2019-08-24",
  "fechaProximaDosis": "2019-08-24",
  "lote": "string",
  "laboratorio": "string",
  "dosis": "string",
  "viaAdministracion": "string",
  "numeroDosis": 0,
  "intervaloDias": 0,
  "esRefuerzo": true,
  "observaciones": "string",
  "reaccionesAdversas": "string",
  "pesoAplicacion": 0.1,
  "serieCompleta": true,
  "historialClinicoId": 0,
  "pacienteId": 0,
  "pacienteNombre": "string",
  "veterinarioId": 0,
  "veterinarioNombre": "string",
  "esObligatoria": true,
  "esAntirrábica": true,
  "refuerzoVencido": true,
  "refuerzoProximo": true,
  "tuvoReacciones": true,
  "estado": "string",
  "resumen": "string",
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|tipoVacuna|string|false|none|none|
|fechaAplicacion|string(date)|false|none|none|
|fechaProximaDosis|string(date)|false|none|none|
|lote|string|false|none|none|
|laboratorio|string|false|none|none|
|dosis|string|false|none|none|
|viaAdministracion|string|false|none|none|
|numeroDosis|integer(int32)|false|none|none|
|intervaloDias|integer(int32)|false|none|none|
|esRefuerzo|boolean|false|none|none|
|observaciones|string|false|none|none|
|reaccionesAdversas|string|false|none|none|
|pesoAplicacion|number(double)|false|none|none|
|serieCompleta|boolean|false|none|none|
|historialClinicoId|integer(int64)|false|none|none|
|pacienteId|integer(int64)|false|none|none|
|pacienteNombre|string|false|none|none|
|veterinarioId|integer(int64)|false|none|none|
|veterinarioNombre|string|false|none|none|
|esObligatoria|boolean|false|none|none|
|esAntirrábica|boolean|false|none|none|
|refuerzoVencido|boolean|false|none|none|
|refuerzoProximo|boolean|false|none|none|
|tuvoReacciones|boolean|false|none|none|
|estado|string|false|none|none|
|resumen|string|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|

<h2 id="tocS_UpdateFacturaRequest">UpdateFacturaRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdatefacturarequest"></a>
<a id="schema_UpdateFacturaRequest"></a>
<a id="tocSupdatefacturarequest"></a>
<a id="tocsupdatefacturarequest"></a>

```json
{
  "fechaVencimiento": "2019-08-24",
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|fechaVencimiento|string(date)|false|none|none|
|observaciones|string|false|none|none|

<h2 id="tocS_DescuentoDTO">DescuentoDTO</h2>
<!-- backwards compatibility -->
<a id="schemadescuentodto"></a>
<a id="schema_DescuentoDTO"></a>
<a id="tocSdescuentodto"></a>
<a id="tocsdescuentodto"></a>

```json
{
  "id": 0,
  "facturaId": 0,
  "codigo": "string",
  "descripcion": "string",
  "tipo": "PORCENTAJE",
  "valor": 0.1,
  "monto": 0.1,
  "motivo": "string",
  "fechaAplicacion": "2019-08-24",
  "esPromocional": true,
  "esValido": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|facturaId|integer(int64)|false|none|none|
|codigo|string|false|none|none|
|descripcion|string|false|none|none|
|tipo|string|false|none|none|
|valor|number(double)|false|none|none|
|monto|number(double)|false|none|none|
|motivo|string|false|none|none|
|fechaAplicacion|string(date)|false|none|none|
|esPromocional|boolean|false|none|none|
|esValido|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<h2 id="tocS_DetalleFacturaDTO">DetalleFacturaDTO</h2>
<!-- backwards compatibility -->
<a id="schemadetallefacturadto"></a>
<a id="schema_DetalleFacturaDTO"></a>
<a id="tocSdetallefacturadto"></a>
<a id="tocsdetallefacturadto"></a>

```json
{
  "id": 0,
  "facturaId": 0,
  "tipoServicioId": 0,
  "tipoServicioNombre": "string",
  "descripcion": "string",
  "cantidad": 0,
  "precioUnitario": 0.1,
  "subtotal": 0.1,
  "descuentoPorcentaje": 0.1,
  "descuentoMonto": 0.1,
  "total": 0.1,
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|facturaId|integer(int64)|false|none|none|
|tipoServicioId|integer(int64)|false|none|none|
|tipoServicioNombre|string|false|none|none|
|descripcion|string|false|none|none|
|cantidad|integer(int32)|false|none|none|
|precioUnitario|number(double)|false|none|none|
|subtotal|number(double)|false|none|none|
|descuentoPorcentaje|number(double)|false|none|none|
|descuentoMonto|number(double)|false|none|none|
|total|number(double)|false|none|none|
|observaciones|string|false|none|none|

<h2 id="tocS_FacturaDTO">FacturaDTO</h2>
<!-- backwards compatibility -->
<a id="schemafacturadto"></a>
<a id="schema_FacturaDTO"></a>
<a id="tocSfacturadto"></a>
<a id="tocsfacturadto"></a>

```json
{
  "id": 0,
  "numeroFactura": "string",
  "clienteId": 0,
  "clienteNombre": "string",
  "citaId": 0,
  "usuarioEmisorId": 0,
  "usuarioEmisorNombre": "string",
  "fechaEmision": "2019-08-24",
  "fechaVencimiento": "2019-08-24",
  "estado": "PENDIENTE",
  "subtotal": 0.1,
  "totalDescuentos": 0.1,
  "totalImpuestos": 0.1,
  "total": 0.1,
  "totalPagado": 0.1,
  "saldoPendiente": 0.1,
  "observaciones": "string",
  "motivoAnulacion": "string",
  "fechaAnulacion": "2019-08-24",
  "detalles": [
    {
      "id": 0,
      "facturaId": 0,
      "tipoServicioId": 0,
      "tipoServicioNombre": "string",
      "descripcion": "string",
      "cantidad": 0,
      "precioUnitario": 0.1,
      "subtotal": 0.1,
      "descuentoPorcentaje": 0.1,
      "descuentoMonto": 0.1,
      "total": 0.1,
      "observaciones": "string"
    }
  ],
  "pagos": [
    {
      "id": 0,
      "facturaId": 0,
      "fechaPago": "2019-08-24",
      "horaPago": "2019-08-24T14:15:22Z",
      "metodoPago": "EFECTIVO",
      "monto": 0.1,
      "comision": 0.1,
      "montoNeto": 0.1,
      "numeroReferencia": "string",
      "numeroVoucher": "string",
      "usuarioRegistroId": 0,
      "usuarioRegistroNombre": "string",
      "observaciones": "string",
      "verificado": true,
      "fechaVerificacion": "2019-08-24T14:15:22Z",
      "usuarioVerificacionId": 0,
      "usuarioVerificacionNombre": "string"
    }
  ],
  "descuentos": [
    {
      "id": 0,
      "facturaId": 0,
      "codigo": "string",
      "descripcion": "string",
      "tipo": "PORCENTAJE",
      "valor": 0.1,
      "monto": 0.1,
      "motivo": "string",
      "fechaAplicacion": "2019-08-24",
      "esPromocional": true,
      "esValido": true
    }
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|numeroFactura|string|false|none|none|
|clienteId|integer(int64)|false|none|none|
|clienteNombre|string|false|none|none|
|citaId|integer(int64)|false|none|none|
|usuarioEmisorId|integer(int64)|false|none|none|
|usuarioEmisorNombre|string|false|none|none|
|fechaEmision|string(date)|false|none|none|
|fechaVencimiento|string(date)|false|none|none|
|estado|string|false|none|none|
|subtotal|number(double)|false|none|none|
|totalDescuentos|number(double)|false|none|none|
|totalImpuestos|number(double)|false|none|none|
|total|number(double)|false|none|none|
|totalPagado|number(double)|false|none|none|
|saldoPendiente|number(double)|false|none|none|
|observaciones|string|false|none|none|
|motivoAnulacion|string|false|none|none|
|fechaAnulacion|string(date)|false|none|none|
|detalles|[[DetalleFacturaDTO](#schemadetallefacturadto)]|false|none|none|
|pagos|[[PagoDTO](#schemapagodto)]|false|none|none|
|descuentos|[[DescuentoDTO](#schemadescuentodto)]|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|PENDIENTE|
|estado|PARCIAL|
|estado|PAGADA|
|estado|ANULADA|
|estado|VENCIDA|

<h2 id="tocS_PagoDTO">PagoDTO</h2>
<!-- backwards compatibility -->
<a id="schemapagodto"></a>
<a id="schema_PagoDTO"></a>
<a id="tocSpagodto"></a>
<a id="tocspagodto"></a>

```json
{
  "id": 0,
  "facturaId": 0,
  "fechaPago": "2019-08-24",
  "horaPago": "2019-08-24T14:15:22Z",
  "metodoPago": "EFECTIVO",
  "monto": 0.1,
  "comision": 0.1,
  "montoNeto": 0.1,
  "numeroReferencia": "string",
  "numeroVoucher": "string",
  "usuarioRegistroId": 0,
  "usuarioRegistroNombre": "string",
  "observaciones": "string",
  "verificado": true,
  "fechaVerificacion": "2019-08-24T14:15:22Z",
  "usuarioVerificacionId": 0,
  "usuarioVerificacionNombre": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|facturaId|integer(int64)|false|none|none|
|fechaPago|string(date)|false|none|none|
|horaPago|string(date-time)|false|none|none|
|metodoPago|string|false|none|none|
|monto|number(double)|false|none|none|
|comision|number(double)|false|none|none|
|montoNeto|number(double)|false|none|none|
|numeroReferencia|string|false|none|none|
|numeroVoucher|string|false|none|none|
|usuarioRegistroId|integer(int64)|false|none|none|
|usuarioRegistroNombre|string|false|none|none|
|observaciones|string|false|none|none|
|verificado|boolean|false|none|none|
|fechaVerificacion|string(date-time)|false|none|none|
|usuarioVerificacionId|integer(int64)|false|none|none|
|usuarioVerificacionNombre|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|

<h2 id="tocS_SignosVitalesRequest">SignosVitalesRequest</h2>
<!-- backwards compatibility -->
<a id="schemasignosvitalesrequest"></a>
<a id="schema_SignosVitalesRequest"></a>
<a id="tocSsignosvitalesrequest"></a>
<a id="tocssignosvitalesrequest"></a>

```json
{
  "temperatura": 30,
  "pesoKg": 0.1,
  "frecuenciaCardiaca": 30,
  "frecuenciaRespiratoria": 5,
  "temperaturaRectal": 30,
  "condicionCorporal": 1
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|temperatura|number(double)|false|none|none|
|pesoKg|number(double)|false|none|none|
|frecuenciaCardiaca|integer(int32)|false|none|none|
|frecuenciaRespiratoria|integer(int32)|false|none|none|
|temperaturaRectal|number(double)|false|none|none|
|condicionCorporal|integer(int32)|false|none|none|

<h2 id="tocS_UpdateConsultaRequest">UpdateConsultaRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdateconsultarequest"></a>
<a id="schema_UpdateConsultaRequest"></a>
<a id="tocSupdateconsultarequest"></a>
<a id="tocsupdateconsultarequest"></a>

```json
{
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "signosVitales": {
    "temperatura": 30,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 30,
    "frecuenciaRespiratoria": 5,
    "temperaturaRectal": 30,
    "condicionCorporal": 1
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|motivo|string|false|none|none|
|anamnesis|string|false|none|none|
|examenFisico|string|false|none|none|
|observaciones|string|false|none|none|
|planTratamiento|string|false|none|none|
|pronostico|string|false|none|none|
|requiereSeguimiento|boolean|false|none|none|
|fechaSeguimiento|string(date)|false|none|none|
|signosVitales|[SignosVitalesRequest](#schemasignosvitalesrequest)|false|none|none|

<h2 id="tocS_UpdateClienteRequest">UpdateClienteRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdateclienterequest"></a>
<a id="schema_UpdateClienteRequest"></a>
<a id="tocSupdateclienterequest"></a>
<a id="tocsupdateclienterequest"></a>

```json
{
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|email|string|false|none|none|
|telefono|string|false|none|none|
|direccion|string|false|none|none|
|ciudad|string|false|none|none|
|departamento|string|false|none|none|
|codigoPostal|string|false|none|none|
|observaciones|string|false|none|none|

<h2 id="tocS_ClienteDTO">ClienteDTO</h2>
<!-- backwards compatibility -->
<a id="schemaclientedto"></a>
<a id="schema_ClienteDTO"></a>
<a id="tocSclientedto"></a>
<a id="tocsclientedto"></a>

```json
{
  "id": 0,
  "nombre": "string",
  "apellido": "string",
  "nombreCompleto": "string",
  "dni": "string",
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string",
  "pacientes": [
    {
      "id": 0,
      "nombre": "string",
      "especie": "string",
      "edadAnios": 0,
      "estado": "string"
    }
  ],
  "cantidadPacientesActivos": 0,
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|apellido|string|false|none|none|
|nombreCompleto|string|false|none|none|
|dni|string|false|none|none|
|email|string|false|none|none|
|telefono|string|false|none|none|
|direccion|string|false|none|none|
|ciudad|string|false|none|none|
|departamento|string|false|none|none|
|codigoPostal|string|false|none|none|
|observaciones|string|false|none|none|
|pacientes|[[PacienteResumenDTO](#schemapacienteresumendto)]|false|none|none|
|cantidadPacientesActivos|integer(int64)|false|none|none|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|

<h2 id="tocS_PacienteResumenDTO">PacienteResumenDTO</h2>
<!-- backwards compatibility -->
<a id="schemapacienteresumendto"></a>
<a id="schema_PacienteResumenDTO"></a>
<a id="tocSpacienteresumendto"></a>
<a id="tocspacienteresumendto"></a>

```json
{
  "id": 0,
  "nombre": "string",
  "especie": "string",
  "edadAnios": 0,
  "estado": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|especie|string|false|none|none|
|edadAnios|integer(int32)|false|none|none|
|estado|string|false|none|none|

<h2 id="tocS_UpdateCitaRequest">UpdateCitaRequest</h2>
<!-- backwards compatibility -->
<a id="schemaupdatecitarequest"></a>
<a id="schema_UpdateCitaRequest"></a>
<a id="tocSupdatecitarequest"></a>
<a id="tocsupdatecitarequest"></a>

```json
{
  "motivo": "string",
  "notas": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|motivo|string|false|none|none|
|notas|string|false|none|none|

<h2 id="tocS_CitaDTO">CitaDTO</h2>
<!-- backwards compatibility -->
<a id="schemacitadto"></a>
<a id="schema_CitaDTO"></a>
<a id="tocScitadto"></a>
<a id="tocscitadto"></a>

```json
{
  "id": 0,
  "fecha": "2019-08-24",
  "hora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivo": "string",
  "estado": "string",
  "notas": "string",
  "duracionEstimada": 0,
  "motivoCancelacion": "string",
  "veterinarioId": 0,
  "veterinarioNombre": "string",
  "veterinarioEmail": "string",
  "pacienteId": 0,
  "pacienteNombre": "string",
  "pacienteEspecie": "string",
  "clienteId": 0,
  "clienteNombre": "string",
  "clienteEmail": "string",
  "clienteTelefono": "string",
  "tipoServicioId": 0,
  "tipoServicioNombre": "string",
  "tipoServicioCategoria": "string",
  "tipoServicioPrecio": 0.1,
  "fechaConfirmacion": "2019-08-24T14:15:22Z",
  "fechaInicioAtencion": "2019-08-24T14:15:22Z",
  "fechaFinAtencion": "2019-08-24T14:15:22Z",
  "createdAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedAt": "2019-08-24T14:15:22Z",
  "updatedBy": "string",
  "horaFinEstimada": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "resumen": "string",
  "esModificable": true,
  "estaActiva": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|none|
|fecha|string(date)|false|none|none|
|hora|[LocalTime](#schemalocaltime)|false|none|none|
|motivo|string|false|none|none|
|estado|string|false|none|none|
|notas|string|false|none|none|
|duracionEstimada|integer(int32)|false|none|none|
|motivoCancelacion|string|false|none|none|
|veterinarioId|integer(int64)|false|none|none|
|veterinarioNombre|string|false|none|none|
|veterinarioEmail|string|false|none|none|
|pacienteId|integer(int64)|false|none|none|
|pacienteNombre|string|false|none|none|
|pacienteEspecie|string|false|none|none|
|clienteId|integer(int64)|false|none|none|
|clienteNombre|string|false|none|none|
|clienteEmail|string|false|none|none|
|clienteTelefono|string|false|none|none|
|tipoServicioId|integer(int64)|false|none|none|
|tipoServicioNombre|string|false|none|none|
|tipoServicioCategoria|string|false|none|none|
|tipoServicioPrecio|number(double)|false|none|none|
|fechaConfirmacion|string(date-time)|false|none|none|
|fechaInicioAtencion|string(date-time)|false|none|none|
|fechaFinAtencion|string(date-time)|false|none|none|
|createdAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|updatedBy|string|false|none|none|
|horaFinEstimada|[LocalTime](#schemalocaltime)|false|none|none|
|resumen|string|false|none|none|
|esModificable|boolean|false|none|none|
|estaActiva|boolean|false|none|none|

<h2 id="tocS_LocalTime">LocalTime</h2>
<!-- backwards compatibility -->
<a id="schemalocaltime"></a>
<a id="schema_LocalTime"></a>
<a id="tocSlocaltime"></a>
<a id="tocslocaltime"></a>

```json
{
  "hour": 0,
  "minute": 0,
  "second": 0,
  "nano": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|hour|integer(int32)|false|none|none|
|minute|integer(int32)|false|none|none|
|second|integer(int32)|false|none|none|
|nano|integer(int32)|false|none|none|

<h2 id="tocS_CreateUsuarioRequest">CreateUsuarioRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreateusuariorequest"></a>
<a id="schema_CreateUsuarioRequest"></a>
<a id="tocScreateusuariorequest"></a>
<a id="tocscreateusuariorequest"></a>

```json
{
  "username": "string",
  "email": "string",
  "password": "stringst",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "tipoUsuario": "ADMINISTRADOR",
  "roleIds": [
    0
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|username|string|true|none|none|
|email|string|true|none|none|
|password|string|true|none|none|
|nombre|string|true|none|none|
|apellido|string|true|none|none|
|dni|string|false|none|none|
|telefono|string|false|none|none|
|direccion|string|false|none|none|
|tipoUsuario|string|true|none|none|
|roleIds|[integer]|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|tipoUsuario|ADMINISTRADOR|
|tipoUsuario|VETERINARIO|
|tipoUsuario|ASISTENTE|
|tipoUsuario|RECEPCIONISTA|
|tipoUsuario|PROPIETARIO|

<h2 id="tocS_MessageResponse">MessageResponse</h2>
<!-- backwards compatibility -->
<a id="schemamessageresponse"></a>
<a id="schema_MessageResponse"></a>
<a id="tocSmessageresponse"></a>
<a id="tocsmessageresponse"></a>

```json
{
  "message": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|message|string|false|none|none|

<h2 id="tocS_ChangePasswordRequest">ChangePasswordRequest</h2>
<!-- backwards compatibility -->
<a id="schemachangepasswordrequest"></a>
<a id="schema_ChangePasswordRequest"></a>
<a id="tocSchangepasswordrequest"></a>
<a id="tocschangepasswordrequest"></a>

```json
{
  "currentPassword": "string",
  "newPassword": "stringst",
  "confirmPassword": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|currentPassword|string|true|none|none|
|newPassword|string|true|none|none|
|confirmPassword|string|true|none|none|

<h2 id="tocS_CreateVacunaRequest">CreateVacunaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatevacunarequest"></a>
<a id="schema_CreateVacunaRequest"></a>
<a id="tocScreatevacunarequest"></a>
<a id="tocscreatevacunarequest"></a>

```json
{
  "historialClinicoId": 0,
  "veterinarioId": 0,
  "nombre": "string",
  "tipoVacuna": "string",
  "fechaAplicacion": "2019-08-24",
  "fechaProximaDosis": "2019-08-24",
  "lote": "string",
  "laboratorio": "string",
  "dosis": "string",
  "viaAdministracion": "string",
  "numeroDosis": 1,
  "intervaloDias": 0,
  "esRefuerzo": true,
  "observaciones": "string",
  "reaccionesAdversas": "string",
  "pesoAplicacion": 0.1,
  "serieCompleta": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|historialClinicoId|integer(int64)|true|none|none|
|veterinarioId|integer(int64)|true|none|none|
|nombre|string|true|none|none|
|tipoVacuna|string|true|none|none|
|fechaAplicacion|string(date)|true|none|none|
|fechaProximaDosis|string(date)|false|none|none|
|lote|string|false|none|none|
|laboratorio|string|false|none|none|
|dosis|string|false|none|none|
|viaAdministracion|string|false|none|none|
|numeroDosis|integer(int32)|false|none|none|
|intervaloDias|integer(int32)|false|none|none|
|esRefuerzo|boolean|false|none|none|
|observaciones|string|false|none|none|
|reaccionesAdversas|string|false|none|none|
|pesoAplicacion|number(double)|false|none|none|
|serieCompleta|boolean|false|none|none|

<h2 id="tocS_CreateTratamientoRequest">CreateTratamientoRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatetratamientorequest"></a>
<a id="schema_CreateTratamientoRequest"></a>
<a id="tocScreatetratamientorequest"></a>
<a id="tocscreatetratamientorequest"></a>

```json
{
  "consultaId": 0,
  "tipoTratamiento": "string",
  "nombre": "string",
  "descripcion": "string",
  "dosis": "string",
  "frecuencia": "string",
  "viaAdministracion": "string",
  "duracionDias": 1,
  "fechaInicio": "2019-08-24",
  "fechaFin": "2019-08-24",
  "indicaciones": "string",
  "efectosSecundarios": "string",
  "cantidad": 0.1,
  "unidad": "string",
  "requiereSupervision": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|consultaId|integer(int64)|true|none|none|
|tipoTratamiento|string|true|none|none|
|nombre|string|true|none|none|
|descripcion|string|false|none|none|
|dosis|string|false|none|none|
|frecuencia|string|false|none|none|
|viaAdministracion|string|false|none|none|
|duracionDias|integer(int32)|false|none|none|
|fechaInicio|string(date)|true|none|none|
|fechaFin|string(date)|false|none|none|
|indicaciones|string|false|none|none|
|efectosSecundarios|string|false|none|none|
|cantidad|number(double)|false|none|none|
|unidad|string|false|none|none|
|requiereSupervision|boolean|false|none|none|

<h2 id="tocS_CreateTipoServicioRequest">CreateTipoServicioRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatetiposerviciorequest"></a>
<a id="schema_CreateTipoServicioRequest"></a>
<a id="tocScreatetiposerviciorequest"></a>
<a id="tocscreatetiposerviciorequest"></a>

```json
{
  "nombre": "string",
  "descripcion": "string",
  "duracionEstimada": 5,
  "precioBase": 0.1,
  "categoria": "string",
  "requiereConfirmacion": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nombre|string|true|none|none|
|descripcion|string|false|none|none|
|duracionEstimada|integer(int32)|true|none|none|
|precioBase|number(double)|true|none|none|
|categoria|string|true|none|none|
|requiereConfirmacion|boolean|false|none|none|

<h2 id="tocS_CreateRazaRequest">CreateRazaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreaterazarequest"></a>
<a id="schema_CreateRazaRequest"></a>
<a id="tocScreaterazarequest"></a>
<a id="tocscreaterazarequest"></a>

```json
{
  "nombre": "string",
  "especie": "PERRO",
  "descripcion": "string",
  "esMestizo": true,
  "tamanioTipico": "string",
  "pesoPromedioKg": 0.1
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nombre|string|true|none|none|
|especie|string|true|none|none|
|descripcion|string|false|none|none|
|esMestizo|boolean|false|none|none|
|tamanioTipico|string|false|none|none|
|pesoPromedioKg|number(double)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|

<h2 id="tocS_CreatePacienteRequest">CreatePacienteRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatepacienterequest"></a>
<a id="schema_CreatePacienteRequest"></a>
<a id="tocScreatepacienterequest"></a>
<a id="tocscreatepacienterequest"></a>

```json
{
  "nombre": "string",
  "especie": "PERRO",
  "raza": "string",
  "fechaNacimiento": "2019-08-24",
  "sexo": "MACHO",
  "color": "string",
  "pesoKg": 0.1,
  "fotoUrl": "string",
  "observaciones": "string",
  "microchip": "string",
  "clienteId": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nombre|string|true|none|none|
|especie|string|true|none|none|
|raza|string|false|none|none|
|fechaNacimiento|string(date)|true|none|none|
|sexo|string|true|none|none|
|color|string|false|none|none|
|pesoKg|number(double)|false|none|none|
|fotoUrl|string|false|none|none|
|observaciones|string|false|none|none|
|microchip|string|false|none|none|
|clienteId|integer(int64)|true|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|especie|PERRO|
|especie|GATO|
|especie|AVE|
|especie|REPTIL|
|especie|ROEDOR|
|especie|OTRO|
|sexo|MACHO|
|sexo|HEMBRA|

<h2 id="tocS_CreateFacturaRequest">CreateFacturaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatefacturarequest"></a>
<a id="schema_CreateFacturaRequest"></a>
<a id="tocScreatefacturarequest"></a>
<a id="tocscreatefacturarequest"></a>

```json
{
  "clienteId": 0,
  "citaId": 0,
  "usuarioEmisorId": 0,
  "fechaEmision": "2019-08-24",
  "fechaVencimiento": "2019-08-24",
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|clienteId|integer(int64)|true|none|none|
|citaId|integer(int64)|false|none|none|
|usuarioEmisorId|integer(int64)|true|none|none|
|fechaEmision|string(date)|true|none|none|
|fechaVencimiento|string(date)|false|none|none|
|observaciones|string|false|none|none|

<h2 id="tocS_CreatePagoRequest">CreatePagoRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatepagorequest"></a>
<a id="schema_CreatePagoRequest"></a>
<a id="tocScreatepagorequest"></a>
<a id="tocscreatepagorequest"></a>

```json
{
  "fechaPago": "2019-08-24",
  "metodoPago": "EFECTIVO",
  "monto": 0.1,
  "numeroReferencia": "string",
  "numeroVoucher": "string",
  "usuarioRegistroId": 0,
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|fechaPago|string(date)|true|none|none|
|metodoPago|string|true|none|none|
|monto|number(double)|true|none|none|
|numeroReferencia|string|false|none|none|
|numeroVoucher|string|false|none|none|
|usuarioRegistroId|integer(int64)|true|none|none|
|observaciones|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|metodoPago|EFECTIVO|
|metodoPago|TARJETA_CREDITO|
|metodoPago|TARJETA_DEBITO|
|metodoPago|TRANSFERENCIA|
|metodoPago|YAPE|
|metodoPago|PLIN|

<h2 id="tocS_CreateDetalleFacturaRequest">CreateDetalleFacturaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatedetallefacturarequest"></a>
<a id="schema_CreateDetalleFacturaRequest"></a>
<a id="tocScreatedetallefacturarequest"></a>
<a id="tocscreatedetallefacturarequest"></a>

```json
{
  "tipoServicioId": 0,
  "descripcion": "string",
  "cantidad": 0,
  "precioUnitario": 0.1,
  "descuentoPorcentaje": 0.1,
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|tipoServicioId|integer(int64)|false|none|none|
|descripcion|string|true|none|none|
|cantidad|integer(int32)|true|none|none|
|precioUnitario|number(double)|true|none|none|
|descuentoPorcentaje|number(double)|false|none|none|
|observaciones|string|false|none|none|

<h2 id="tocS_CreateDescuentoRequest">CreateDescuentoRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatedescuentorequest"></a>
<a id="schema_CreateDescuentoRequest"></a>
<a id="tocScreatedescuentorequest"></a>
<a id="tocscreatedescuentorequest"></a>

```json
{
  "codigo": "string",
  "descripcion": "string",
  "tipo": "PORCENTAJE",
  "valor": 0.1,
  "motivo": "string",
  "esPromocional": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|codigo|string|false|none|none|
|descripcion|string|true|none|none|
|tipo|string|true|none|none|
|valor|number(double)|true|none|none|
|motivo|string|false|none|none|
|esPromocional|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|tipo|PORCENTAJE|
|tipo|MONTO_FIJO|

<h2 id="tocS_CreateExamenRequest">CreateExamenRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreateexamenrequest"></a>
<a id="schema_CreateExamenRequest"></a>
<a id="tocScreateexamenrequest"></a>
<a id="tocscreateexamenrequest"></a>

```json
{
  "historialClinicoId": 0,
  "veterinarioSolicitanteId": 0,
  "consultaId": 0,
  "tipoExamen": "string",
  "nombre": "string",
  "descripcion": "string",
  "fechaSolicitud": "2019-08-24",
  "fechaRealizacion": "2019-08-24",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "laboratorioExterno": "string",
  "referenciaExterna": "string",
  "costo": 0.1,
  "notas": "string",
  "requiereAyuno": true,
  "esUrgente": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|historialClinicoId|integer(int64)|true|none|none|
|veterinarioSolicitanteId|integer(int64)|true|none|none|
|consultaId|integer(int64)|false|none|none|
|tipoExamen|string|true|none|none|
|nombre|string|true|none|none|
|descripcion|string|false|none|none|
|fechaSolicitud|string(date)|true|none|none|
|fechaRealizacion|string(date)|false|none|none|
|archivoRuta|string|false|none|none|
|archivoTipo|string|false|none|none|
|laboratorioExterno|string|false|none|none|
|referenciaExterna|string|false|none|none|
|costo|number(double)|false|none|none|
|notas|string|false|none|none|
|requiereAyuno|boolean|false|none|none|
|esUrgente|boolean|false|none|none|

<h2 id="tocS_CreateDiagnosticoRequest">CreateDiagnosticoRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatediagnosticorequest"></a>
<a id="schema_CreateDiagnosticoRequest"></a>
<a id="tocScreatediagnosticorequest"></a>
<a id="tocscreatediagnosticorequest"></a>

```json
{
  "consultaId": 0,
  "tipoDiagnostico": "string",
  "descripcion": "string",
  "codigoCie10": "string",
  "gravedad": "string",
  "notas": "string",
  "esPrincipal": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|consultaId|integer(int64)|true|none|none|
|tipoDiagnostico|string|true|none|none|
|descripcion|string|true|none|none|
|codigoCie10|string|false|none|none|
|gravedad|string|false|none|none|
|notas|string|false|none|none|
|esPrincipal|boolean|false|none|none|

<h2 id="tocS_CreateConsultaRequest">CreateConsultaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreateconsultarequest"></a>
<a id="schema_CreateConsultaRequest"></a>
<a id="tocScreateconsultarequest"></a>
<a id="tocscreateconsultarequest"></a>

```json
{
  "historialClinicoId": 0,
  "citaId": 0,
  "veterinarioId": 0,
  "fechaConsulta": "2019-08-24",
  "motivo": "string",
  "anamnesis": "string",
  "examenFisico": "string",
  "observaciones": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24",
  "signosVitales": {
    "temperatura": 30,
    "pesoKg": 0.1,
    "frecuenciaCardiaca": 30,
    "frecuenciaRespiratoria": 5,
    "temperaturaRectal": 30,
    "condicionCorporal": 1
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|historialClinicoId|integer(int64)|true|none|none|
|citaId|integer(int64)|false|none|none|
|veterinarioId|integer(int64)|true|none|none|
|fechaConsulta|string(date)|true|none|none|
|motivo|string|true|none|none|
|anamnesis|string|false|none|none|
|examenFisico|string|false|none|none|
|observaciones|string|false|none|none|
|planTratamiento|string|false|none|none|
|pronostico|string|false|none|none|
|requiereSeguimiento|boolean|false|none|none|
|fechaSeguimiento|string(date)|false|none|none|
|signosVitales|[SignosVitalesRequest](#schemasignosvitalesrequest)|false|none|none|

<h2 id="tocS_CreateClienteRequest">CreateClienteRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreateclienterequest"></a>
<a id="schema_CreateClienteRequest"></a>
<a id="tocScreateclienterequest"></a>
<a id="tocscreateclienterequest"></a>

```json
{
  "nombre": "string",
  "apellido": "string",
  "dni": "strings",
  "email": "string",
  "telefono": "string",
  "direccion": "string",
  "ciudad": "string",
  "departamento": "string",
  "codigoPostal": "string",
  "observaciones": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nombre|string|true|none|none|
|apellido|string|true|none|none|
|dni|string|true|none|none|
|email|string|true|none|none|
|telefono|string|true|none|none|
|direccion|string|true|none|none|
|ciudad|string|false|none|none|
|departamento|string|false|none|none|
|codigoPostal|string|false|none|none|
|observaciones|string|false|none|none|

<h2 id="tocS_CreateCitaRequest">CreateCitaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacreatecitarequest"></a>
<a id="schema_CreateCitaRequest"></a>
<a id="tocScreatecitarequest"></a>
<a id="tocscreatecitarequest"></a>

```json
{
  "fecha": "2019-08-24",
  "hora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivo": "string",
  "veterinarioId": 0,
  "pacienteId": 0,
  "clienteId": 0,
  "tipoServicioId": 0,
  "notas": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|fecha|string(date)|true|none|none|
|hora|[LocalTime](#schemalocaltime)|true|none|none|
|motivo|string|true|none|none|
|veterinarioId|integer(int64)|true|none|none|
|pacienteId|integer(int64)|true|none|none|
|clienteId|integer(int64)|true|none|none|
|tipoServicioId|integer(int64)|true|none|none|
|notas|string|false|none|none|

<h2 id="tocS_RecuperarPasswordRequest">RecuperarPasswordRequest</h2>
<!-- backwards compatibility -->
<a id="schemarecuperarpasswordrequest"></a>
<a id="schema_RecuperarPasswordRequest"></a>
<a id="tocSrecuperarpasswordrequest"></a>
<a id="tocsrecuperarpasswordrequest"></a>

```json
{
  "email": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|email|string|true|none|none|

<h2 id="tocS_LogoutRequest">LogoutRequest</h2>
<!-- backwards compatibility -->
<a id="schemalogoutrequest"></a>
<a id="schema_LogoutRequest"></a>
<a id="tocSlogoutrequest"></a>
<a id="tocslogoutrequest"></a>

```json
{
  "cerrarTodasLasSesiones": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|cerrarTodasLasSesiones|boolean|false|none|none|

<h2 id="tocS_LoginRequest">LoginRequest</h2>
<!-- backwards compatibility -->
<a id="schemaloginrequest"></a>
<a id="schema_LoginRequest"></a>
<a id="tocSloginrequest"></a>
<a id="tocsloginrequest"></a>

```json
{
  "email": "string",
  "password": "string",
  "huellaDispositivo": "string",
  "nombreDispositivo": "string",
  "recordarDispositivo": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|email|string|true|none|none|
|password|string|true|none|none|
|huellaDispositivo|string|false|none|none|
|nombreDispositivo|string|false|none|none|
|recordarDispositivo|boolean|false|none|none|

<h2 id="tocS_CambiarPasswordRequest">CambiarPasswordRequest</h2>
<!-- backwards compatibility -->
<a id="schemacambiarpasswordrequest"></a>
<a id="schema_CambiarPasswordRequest"></a>
<a id="tocScambiarpasswordrequest"></a>
<a id="tocscambiarpasswordrequest"></a>

```json
{
  "passwordActual": "string",
  "passwordNueva": "string",
  "passwordConfirmacion": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|passwordActual|string|true|none|none|
|passwordNueva|string|true|none|none|
|passwordConfirmacion|string|true|none|none|

<h2 id="tocS_CambiarEstadoPacienteRequest">CambiarEstadoPacienteRequest</h2>
<!-- backwards compatibility -->
<a id="schemacambiarestadopacienterequest"></a>
<a id="schema_CambiarEstadoPacienteRequest"></a>
<a id="tocScambiarestadopacienterequest"></a>
<a id="tocscambiarestadopacienterequest"></a>

```json
{
  "nuevoEstado": "ACTIVO",
  "motivo": "string",
  "fechaCambio": "2019-08-24"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nuevoEstado|string|true|none|none|
|motivo|string|true|none|none|
|fechaCambio|string(date)|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|nuevoEstado|ACTIVO|
|nuevoEstado|INACTIVO|
|nuevoEstado|FALLECIDO|

<h2 id="tocS_AnularFacturaRequest">AnularFacturaRequest</h2>
<!-- backwards compatibility -->
<a id="schemaanularfacturarequest"></a>
<a id="schema_AnularFacturaRequest"></a>
<a id="tocSanularfacturarequest"></a>
<a id="tocsanularfacturarequest"></a>

```json
{
  "motivoAnulacion": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|motivoAnulacion|string|true|none|none|

<h2 id="tocS_RegistrarResultadosExamenRequest">RegistrarResultadosExamenRequest</h2>
<!-- backwards compatibility -->
<a id="schemaregistrarresultadosexamenrequest"></a>
<a id="schema_RegistrarResultadosExamenRequest"></a>
<a id="tocSregistrarresultadosexamenrequest"></a>
<a id="tocsregistrarresultadosexamenrequest"></a>

```json
{
  "fechaRealizacion": "2019-08-24",
  "resultados": "string",
  "valoresReferencia": "string",
  "interpretacion": "string",
  "hallazgos": "string",
  "archivoRuta": "string",
  "archivoTipo": "string",
  "resultadoAnormal": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|fechaRealizacion|string(date)|false|none|none|
|resultados|string|true|none|none|
|valoresReferencia|string|false|none|none|
|interpretacion|string|false|none|none|
|hallazgos|string|false|none|none|
|archivoRuta|string|false|none|none|
|archivoTipo|string|false|none|none|
|resultadoAnormal|boolean|false|none|none|

<h2 id="tocS_FinalizarConsultaRequest">FinalizarConsultaRequest</h2>
<!-- backwards compatibility -->
<a id="schemafinalizarconsultarequest"></a>
<a id="schema_FinalizarConsultaRequest"></a>
<a id="tocSfinalizarconsultarequest"></a>
<a id="tocsfinalizarconsultarequest"></a>

```json
{
  "observacionesFinales": "string",
  "planTratamiento": "string",
  "pronostico": "string",
  "requiereSeguimiento": true,
  "fechaSeguimiento": "2019-08-24"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|observacionesFinales|string|false|none|none|
|planTratamiento|string|false|none|none|
|pronostico|string|false|none|none|
|requiereSeguimiento|boolean|false|none|none|
|fechaSeguimiento|string(date)|false|none|none|

<h2 id="tocS_ReagendarCitaRequest">ReagendarCitaRequest</h2>
<!-- backwards compatibility -->
<a id="schemareagendarcitarequest"></a>
<a id="schema_ReagendarCitaRequest"></a>
<a id="tocSreagendarcitarequest"></a>
<a id="tocsreagendarcitarequest"></a>

```json
{
  "nuevaFecha": "2019-08-24",
  "nuevaHora": {
    "hour": 0,
    "minute": 0,
    "second": 0,
    "nano": 0
  },
  "motivoReagendamiento": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|nuevaFecha|string(date)|true|none|none|
|nuevaHora|[LocalTime](#schemalocaltime)|true|none|none|
|motivoReagendamiento|string|true|none|none|

<h2 id="tocS_CancelarCitaRequest">CancelarCitaRequest</h2>
<!-- backwards compatibility -->
<a id="schemacancelarcitarequest"></a>
<a id="schema_CancelarCitaRequest"></a>
<a id="tocScancelarcitarequest"></a>
<a id="tocscancelarcitarequest"></a>

```json
{
  "motivoCancelacion": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|motivoCancelacion|string|true|none|none|

<h2 id="tocS_Pageable">Pageable</h2>
<!-- backwards compatibility -->
<a id="schemapageable"></a>
<a id="schema_Pageable"></a>
<a id="tocSpageable"></a>
<a id="tocspageable"></a>

```json
{
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|page|integer(int32)|false|none|none|
|size|integer(int32)|false|none|none|
|sort|[string]|false|none|none|

<h2 id="tocS_PageUsuarioDTO">PageUsuarioDTO</h2>
<!-- backwards compatibility -->
<a id="schemapageusuariodto"></a>
<a id="schema_PageUsuarioDTO"></a>
<a id="tocSpageusuariodto"></a>
<a id="tocspageusuariodto"></a>

```json
{
  "totalElements": 0,
  "totalPages": 0,
  "pageable": {
    "paged": true,
    "pageNumber": 0,
    "pageSize": 0,
    "unpaged": true,
    "offset": 0,
    "sort": {
      "sorted": true,
      "unsorted": true,
      "empty": true
    }
  },
  "first": true,
  "last": true,
  "size": 0,
  "content": [
    {
      "id": 0,
      "username": "string",
      "email": "string",
      "nombre": "string",
      "apellido": "string",
      "nombreCompleto": "string",
      "dni": "string",
      "telefono": "string",
      "direccion": "string",
      "tipoUsuario": "ADMINISTRADOR",
      "roles": [
        "string"
      ],
      "cuentaBloqueada": true,
      "cuentaExpirada": true,
      "intentosFallidos": 0,
      "ultimoAcceso": "2019-08-24T14:15:22Z",
      "fechaCambioPassword": "2019-08-24T14:15:22Z",
      "requiereCambioPassword": true,
      "fotoperfilUrl": "string",
      "isActive": true,
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z"
    }
  ],
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 0,
  "empty": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|totalElements|integer(int64)|false|none|none|
|totalPages|integer(int32)|false|none|none|
|pageable|[PageableObject](#schemapageableobject)|false|none|none|
|first|boolean|false|none|none|
|last|boolean|false|none|none|
|size|integer(int32)|false|none|none|
|content|[[UsuarioDTO](#schemausuariodto)]|false|none|none|
|number|integer(int32)|false|none|none|
|sort|[SortObject](#schemasortobject)|false|none|none|
|numberOfElements|integer(int32)|false|none|none|
|empty|boolean|false|none|none|

<h2 id="tocS_PageableObject">PageableObject</h2>
<!-- backwards compatibility -->
<a id="schemapageableobject"></a>
<a id="schema_PageableObject"></a>
<a id="tocSpageableobject"></a>
<a id="tocspageableobject"></a>

```json
{
  "paged": true,
  "pageNumber": 0,
  "pageSize": 0,
  "unpaged": true,
  "offset": 0,
  "sort": {
    "sorted": true,
    "unsorted": true,
    "empty": true
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|paged|boolean|false|none|none|
|pageNumber|integer(int32)|false|none|none|
|pageSize|integer(int32)|false|none|none|
|unpaged|boolean|false|none|none|
|offset|integer(int64)|false|none|none|
|sort|[SortObject](#schemasortobject)|false|none|none|

<h2 id="tocS_SortObject">SortObject</h2>
<!-- backwards compatibility -->
<a id="schemasortobject"></a>
<a id="schema_SortObject"></a>
<a id="tocSsortobject"></a>
<a id="tocssortobject"></a>

```json
{
  "sorted": true,
  "unsorted": true,
  "empty": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|sorted|boolean|false|none|none|
|unsorted|boolean|false|none|none|
|empty|boolean|false|none|none|

<h2 id="tocS_Permiso">Permiso</h2>
<!-- backwards compatibility -->
<a id="schemapermiso"></a>
<a id="schema_Permiso"></a>
<a id="tocSpermiso"></a>
<a id="tocspermiso"></a>

```json
{
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedBy": "string",
  "isActive": true,
  "version": 0,
  "id": 0,
  "codigo": "string",
  "nombre": "string",
  "modulo": "string",
  "descripcion": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedBy|string|false|none|none|
|isActive|boolean|false|none|none|
|version|integer(int64)|false|none|none|
|id|integer(int64)|false|none|none|
|codigo|string|false|none|none|
|nombre|string|false|none|none|
|modulo|string|false|none|none|
|descripcion|string|false|none|none|

<h2 id="tocS_Rol">Rol</h2>
<!-- backwards compatibility -->
<a id="schemarol"></a>
<a id="schema_Rol"></a>
<a id="tocSrol"></a>
<a id="tocsrol"></a>

```json
{
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedBy": "string",
  "isActive": true,
  "version": 0,
  "id": 0,
  "nombre": "string",
  "descripcion": "string",
  "permisos": [
    {
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedBy": "string",
      "isActive": true,
      "version": 0,
      "id": 0,
      "codigo": "string",
      "nombre": "string",
      "modulo": "string",
      "descripcion": "string"
    }
  ],
  "usuarios": [
    {
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedBy": "string",
      "isActive": true,
      "version": 0,
      "id": 0,
      "username": "string",
      "email": "string",
      "password": "string",
      "nombre": "string",
      "apellido": "string",
      "dni": "string",
      "telefono": "string",
      "direccion": "string",
      "tipoUsuario": "ADMINISTRADOR",
      "roles": [
        {
          "createdAt": "2019-08-24T14:15:22Z",
          "updatedAt": "2019-08-24T14:15:22Z",
          "createdBy": "string",
          "updatedBy": "string",
          "isActive": true,
          "version": 0,
          "id": 0,
          "nombre": "string",
          "descripcion": "string",
          "permisos": [
            {
              "createdAt": "2019-08-24T14:15:22Z",
              "updatedAt": "2019-08-24T14:15:22Z",
              "createdBy": "string",
              "updatedBy": "string",
              "isActive": true,
              "version": 0,
              "id": 0,
              "codigo": "string",
              "nombre": "string",
              "modulo": "string",
              "descripcion": "string"
            }
          ],
          "usuarios": []
        }
      ],
      "cuentaBloqueada": true,
      "cuentaExpirada": true,
      "credencialesExpiradas": true,
      "intentosFallidos": 0,
      "ultimoAcceso": "2019-08-24T14:15:22Z",
      "fechaCambioPassword": "2019-08-24T14:15:22Z",
      "requiereCambioPassword": true,
      "fotoperfilUrl": "string",
      "nombreCompleto": "string",
      "cuentaHabilitada": true
    }
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedBy|string|false|none|none|
|isActive|boolean|false|none|none|
|version|integer(int64)|false|none|none|
|id|integer(int64)|false|none|none|
|nombre|string|false|none|none|
|descripcion|string|false|none|none|
|permisos|[[Permiso](#schemapermiso)]|false|none|none|
|usuarios|[[Usuario](#schemausuario)]|false|none|none|

<h2 id="tocS_Sesion">Sesion</h2>
<!-- backwards compatibility -->
<a id="schemasesion"></a>
<a id="schema_Sesion"></a>
<a id="tocSsesion"></a>
<a id="tocssesion"></a>

```json
{
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedBy": "string",
  "isActive": true,
  "version": 0,
  "id": 0,
  "usuario": {
    "createdAt": "2019-08-24T14:15:22Z",
    "updatedAt": "2019-08-24T14:15:22Z",
    "createdBy": "string",
    "updatedBy": "string",
    "isActive": true,
    "version": 0,
    "id": 0,
    "username": "string",
    "email": "string",
    "password": "string",
    "nombre": "string",
    "apellido": "string",
    "dni": "string",
    "telefono": "string",
    "direccion": "string",
    "tipoUsuario": "ADMINISTRADOR",
    "roles": [
      {
        "createdAt": "2019-08-24T14:15:22Z",
        "updatedAt": "2019-08-24T14:15:22Z",
        "createdBy": "string",
        "updatedBy": "string",
        "isActive": true,
        "version": 0,
        "id": 0,
        "nombre": "string",
        "descripcion": "string",
        "permisos": [
          {
            "createdAt": "2019-08-24T14:15:22Z",
            "updatedAt": "2019-08-24T14:15:22Z",
            "createdBy": "string",
            "updatedBy": "string",
            "isActive": true,
            "version": 0,
            "id": 0,
            "codigo": "string",
            "nombre": "string",
            "modulo": "string",
            "descripcion": "string"
          }
        ],
        "usuarios": [
          {}
        ]
      }
    ],
    "cuentaBloqueada": true,
    "cuentaExpirada": true,
    "credencialesExpiradas": true,
    "intentosFallidos": 0,
    "ultimoAcceso": "2019-08-24T14:15:22Z",
    "fechaCambioPassword": "2019-08-24T14:15:22Z",
    "requiereCambioPassword": true,
    "fotoperfilUrl": "string",
    "nombreCompleto": "string",
    "cuentaHabilitada": true
  },
  "token": "string",
  "refreshToken": "string",
  "fechaInicio": "2019-08-24T14:15:22Z",
  "fechaExpiracion": "2019-08-24T14:15:22Z",
  "fechaFin": "2019-08-24T14:15:22Z",
  "estado": "ACTIVA",
  "ipAddress": "string",
  "userAgent": "string",
  "ultimaActividad": "2019-08-24T14:15:22Z",
  "valid": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedBy|string|false|none|none|
|isActive|boolean|false|none|none|
|version|integer(int64)|false|none|none|
|id|integer(int64)|false|none|none|
|usuario|[Usuario](#schemausuario)|true|none|none|
|token|string|true|none|none|
|refreshToken|string|false|none|none|
|fechaInicio|string(date-time)|true|none|none|
|fechaExpiracion|string(date-time)|true|none|none|
|fechaFin|string(date-time)|false|none|none|
|estado|string|false|none|none|
|ipAddress|string|false|none|none|
|userAgent|string|false|none|none|
|ultimaActividad|string(date-time)|false|none|none|
|valid|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|estado|ACTIVA|
|estado|EXPIRADA|
|estado|CERRADA_MANUAL|
|estado|CERRADA_POR_INACTIVIDAD|
|estado|REVOCADA|

<h2 id="tocS_Usuario">Usuario</h2>
<!-- backwards compatibility -->
<a id="schemausuario"></a>
<a id="schema_Usuario"></a>
<a id="tocSusuario"></a>
<a id="tocsusuario"></a>

```json
{
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "createdBy": "string",
  "updatedBy": "string",
  "isActive": true,
  "version": 0,
  "id": 0,
  "username": "string",
  "email": "string",
  "password": "string",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "direccion": "string",
  "tipoUsuario": "ADMINISTRADOR",
  "roles": [
    {
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z",
      "createdBy": "string",
      "updatedBy": "string",
      "isActive": true,
      "version": 0,
      "id": 0,
      "nombre": "string",
      "descripcion": "string",
      "permisos": [
        {
          "createdAt": "2019-08-24T14:15:22Z",
          "updatedAt": "2019-08-24T14:15:22Z",
          "createdBy": "string",
          "updatedBy": "string",
          "isActive": true,
          "version": 0,
          "id": 0,
          "codigo": "string",
          "nombre": "string",
          "modulo": "string",
          "descripcion": "string"
        }
      ],
      "usuarios": [
        {
          "createdAt": "2019-08-24T14:15:22Z",
          "updatedAt": "2019-08-24T14:15:22Z",
          "createdBy": "string",
          "updatedBy": "string",
          "isActive": true,
          "version": 0,
          "id": 0,
          "username": "string",
          "email": "string",
          "password": "string",
          "nombre": "string",
          "apellido": "string",
          "dni": "string",
          "telefono": "string",
          "direccion": "string",
          "tipoUsuario": "ADMINISTRADOR",
          "roles": [],
          "cuentaBloqueada": true,
          "cuentaExpirada": true,
          "credencialesExpiradas": true,
          "intentosFallidos": 0,
          "ultimoAcceso": "2019-08-24T14:15:22Z",
          "fechaCambioPassword": "2019-08-24T14:15:22Z",
          "requiereCambioPassword": true,
          "fotoperfilUrl": "string",
          "nombreCompleto": "string",
          "cuentaHabilitada": true
        }
      ]
    }
  ],
  "cuentaBloqueada": true,
  "cuentaExpirada": true,
  "credencialesExpiradas": true,
  "intentosFallidos": 0,
  "ultimoAcceso": "2019-08-24T14:15:22Z",
  "fechaCambioPassword": "2019-08-24T14:15:22Z",
  "requiereCambioPassword": true,
  "fotoperfilUrl": "string",
  "nombreCompleto": "string",
  "cuentaHabilitada": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|createdAt|string(date-time)|false|none|none|
|updatedAt|string(date-time)|false|none|none|
|createdBy|string|false|none|none|
|updatedBy|string|false|none|none|
|isActive|boolean|false|none|none|
|version|integer(int64)|false|none|none|
|id|integer(int64)|false|none|none|
|username|string|true|none|none|
|email|string|true|none|none|
|password|string|true|none|none|
|nombre|string|true|none|none|
|apellido|string|true|none|none|
|dni|string|false|none|none|
|telefono|string|false|none|none|
|direccion|string|false|none|none|
|tipoUsuario|string|false|none|none|
|roles|[[Rol](#schemarol)]|false|none|none|
|cuentaBloqueada|boolean|false|none|none|
|cuentaExpirada|boolean|false|none|none|
|credencialesExpiradas|boolean|false|none|none|
|intentosFallidos|integer(int32)|false|none|none|
|ultimoAcceso|string(date-time)|false|none|none|
|fechaCambioPassword|string(date-time)|false|none|none|
|requiereCambioPassword|boolean|false|none|none|
|fotoperfilUrl|string|false|none|none|
|nombreCompleto|string|false|none|none|
|cuentaHabilitada|boolean|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|tipoUsuario|ADMINISTRADOR|
|tipoUsuario|VETERINARIO|
|tipoUsuario|ASISTENTE|
|tipoUsuario|RECEPCIONISTA|
|tipoUsuario|PROPIETARIO|

