# TMDB Movie API Integration

Este proyecto consiste en la creación de una API que consume la API de TMDB para proveer información detallada sobre películas, series, actores y géneros. El objetivo es que esta API funcione como una fuente de datos para otra aplicación que voy a desarrollar. La API permitirá realizar búsquedas de películas, series, actores y géneros, y almacenar la información obtenida en la base de datos de la futura aplicación. Esta integración facilitará la gestión de contenido multimedia y proporcionará datos actualizados provenientes de TMDB para la app.

---

## Instalación

Sigue estos pasos para clonar y ejecutar el proyecto localmente:

1. **Clona el repositorio:**

   ```bash
   git clone https://github.com/Michael-Chacon/Cine.git
   ```

2. **Navega al directorio del proyecto:**

   ```bash
   cd Cine
   ```

3. **Configura el archivo `application.properties`:**

   ara que el proyecto funcione correctamente, es necesario configurar el archivo `application.properties` con los parámetros adecuados de la base de datos y la API de TMDB. A continuación se detalla la configuración necesaria, donde cada usuario deberá completar los valores según su entorno y credenciales.

   #### Archivo `application.properties`

   ```properties
   spring.datasource.url=jdbc:TU_BASE_DE_DATOS_URL
   spring.datasource.username=USUARIO
   spring.datasource.password=CONTRASEÑA
   spring.datasource.driver-class-name=DRIVER  # Driver de la base de datos
   # Configuración de JPA
   spring.jpa.database-platform=DIALECT
   #TMDB
   tmdb.api.key=TU_TMDB_API_KEY # Reemplazar con tu token de TMDB
   ```

   Obtener el token de TMDB:

   1. **Crea una cuenta** en [TMDB](https://www.themoviedb.org/).

   2. **Inicia sesión** y ve a tu perfil.

   3. En el perfil, ve a **Settings** y luego a la sección **API**.

   4. Solicita una **clave API** completando el formulario.

   5. Obtendrás un **Bearer Token (v4 auth)**.

   6. Copia el token y colócalo en tu `application.properties`:

      ```properties
      tmdb.api.token=TU_BEARER_TOKEN
      ```

   ¡Y listo! Con eso ya puedes usar la API de TMDB.

4. **Instala las dependencias:**

   Asegúrate de tener Maven instalado en tu sistema. Luego, ejecuta el siguiente comando para instalar las dependencias del proyecto:

   ```bash
   mvn install
   ```

5. **Ejecuta el proyecto:**

   Inicia el servidor con el siguiente comando:

   ```bash
   mvn spring-boot:run
   ```

6. **Accede a la API:**

   Una vez que el servidor esté en funcionamiento, puedes acceder a la API en `http://localhost:8080`.

---

## Endpoints de la API

### Genres

#### 1. Obtener todos los géneros de la API de TMDB

- **Método**: `GET`
- **Endpoint**: `/genres/tmdb`
- **Descripción**: Este endpoint obtiene todos los géneros disponibles de la API de TMDB.
- **Respuesta**:
  - **Código 200**: Retorna un objeto `GenreResponse` que contiene la lista de géneros.
  

**Ejemplo de respuesta:**
```json
{
  "genres": [
    {
      "id": 28,
      "name": "Action"
    },
    {
      "id": 12,
      "name": "Adventure"
    }
    // más géneros...
  ]
}
```

---

#### 2. Obtener un género desde la DB, por ID

- **Método**: `GET`
- **Endpoint**: `/genres/{id}`
- **Descripción**: Este endpoint devuelve un género específico basado en su ID.
- **Parámetros**:
  - `id` (Path Variable): ID del género que se desea obtener.
- **Respuesta**:
  - **Código 200**: Retorna un objeto `Genre` que representa el género solicitado.

**Ejemplo de respuesta:**
```json
{
  "id": 28,
  "name": "Action"
}
```

---

#### 3. Obtener todos los géneros almacenados

- **Método**: `GET`
- **Endpoint**: `/genres/`
- **Descripción**: Este endpoint devuelve una lista de todos los géneros almacenados en la base de datos.
- **Respuesta**:
  - **Código 200**: Retorna una lista de objetos `Genre`.

**Ejemplo de respuesta:**
```json
[
  {
    "id": 28,
    "name": "Action"
  },
  {
    "id": 12,
    "name": "Adventure"
  }
  // más géneros...
]
```

---

#### 4. Guardar géneros

- **Método**: `POST`
- **Endpoint**: `/genres/save`
- **Descripción**: Este endpoint permite guardar un conjunto de géneros en la base de datos.
- **Cuerpo de la solicitud**:
  - `genres` (Request Body): Conjunto de objetos `Genre` que se desean guardar.
- **Respuesta**:
  - **Código 200**: Retorna la lista de géneros guardados.

**Ejemplo de solicitud:**
```json
{
  "genres": [
    {
      "id": 28,
      "name": "Action"
    },
    {
      "id": 12,
      "name": "Adventure"
    }
  ]
}
```

**Ejemplo de respuesta:**
```json
[
  {
    "id": 28,
    "name": "Action"
  },
  {
    "id": 12,
    "name": "Adventure"
  }
]
```

---

#### 5. Eliminar un género por ID

- **Método**: `DELETE`
- **Endpoint**: `/genres/{id}`
- **Descripción**: Este endpoint elimina un género específico basado en su ID.
- **Parámetros**:
  - `id` (Path Variable): ID del género que se desea eliminar.
- **Respuesta**:
  - **Código 200**: Retorna un mensaje confirmando que el género fue eliminado con éxito.

**Ejemplo de respuesta:**
```json
{
  "message": "Genero eliminado con éxito"
}
```

---

Aquí tienes la documentación para los endpoints del `ActorController`, incluyendo descripciones, parámetros y ejemplos de respuestas.

---

### Actores

#### 1. Buscar un actor por nombre

- **Método**: `GET`
- **Endpoint**: `/actors/tmdb/search/{name}`
- **Descripción**: Este endpoint busca actores en la API de TMDB por nombre.
- **Parámetros**:
  - `name` (Path Variable): Nombre del actor que se desea buscar.
- **Respuesta**:
  - **Código 200**: Retorna una lista de objetos `SearchActor` que coinciden con la búsqueda.

**Ejemplo de respuesta:**
```json
[
  {
    "id": 1,
    "name": "Actor Name",
    "profile_path": "/path/to/profile.jpg"
  },
  {
    "id": 2,
    "name": "Another Actor",
    "profile_path": "/path/to/another-profile.jpg"
  }
  // más actores...
]
```

---

#### 2. Obtener el detalle de un actor por ID

- **Método**: `GET`
- **Endpoint**: `/actors/tmdb/detail/{id}`
- **Descripción**: Este endpoint devuelve el detalle de un actor específico basado en su ID. Si el actor no está en la base de datos, se guarda automáticamente.
- **Parámetros**:
  - `id` (Path Variable): ID del actor que se desea obtener.
- **Respuesta**:
  - **Código 200**: Retorna un objeto `DetailActor` que contiene la información detallada del actor.

**Ejemplo de respuesta:**

```json
{
	"id": 3894,
	"name": "Christian Bale",
	"birthday": "1974-01-30",
	"profile_path": "/7Pxez9J8fuPd2Mn9kex13YALrCQ.jpg",
	"gender": 2,
	"place_of_birth": "Haverfordwest, Pembrokeshire, Wales, UK",
	"popularity": 37.731,
	"biography": "Christian Charles Philip Bale (born 30 January 1974) is an English actor. Known for his versatility and physical transformations for his roles, he has been a leading man in films of several genres..."
}
```

---

#### 3. Obtener todos los actores almacenados

- **Método**: `GET`
- **Endpoint**: `/actors/`
- **Descripción**: Este endpoint devuelve una lista de todos los actores almacenados en la base de datos.
- **Respuesta**:
  - **Código 200**: Retorna una lista de objetos `Actor`.

**Ejemplo de respuesta:**
```json
[
  {
    "id": 1,
    "name": "Actor Name",
    "profile_path": "/path/to/profile.jpg"
  },
  {
    "id": 2,
    "name": "Another Actor",
    "profile_path": "/path/to/another-profile.jpg"
  }
  // más actores...
]
```

---

#### 4. Obtener un actor por ID

- **Método**: `GET`
- **Endpoint**: `/actors/{id}`
- **Descripción**: Este endpoint devuelve un actor específico basado en su ID.
- **Parámetros**:
  - `id` (Path Variable): ID del actor que se desea obtener.
- **Respuesta**:
  - **Código 200**: Retorna un objeto `Actor` que representa el actor solicitado.

**Ejemplo de respuesta:**
```json
{
	"id": 3894,
	"name": "Christian Bale",
	"birthday": "1974-01-30T05:00:00.000+00:00",
	"profilePath": "https://image.tmdb.org/t/p/w500/7Pxez9J8fuPd2Mn9kex13YALrCQ.jpg",
	"placeOfBirth": "Haverfordwest, Pembrokeshire, Wales, UK",
	"popularity": 37.731,
	"biography": "Christian Charles Philip Bale (born 30 January 1974) is an English actor. Known for his versatility and physical transformations for his roles, he has been a leading man in films of several genres...",
	"gender": {
		"genderId": 2,
		"name": "masculino"
	}
}
```

---

#### 5. Eliminar un actor por ID

- **Método**: `DELETE`
- **Endpoint**: `/actors/{id}`
- **Descripción**: Este endpoint elimina un actor específico basado en su ID.
- **Parámetros**:
  - `id` (Path Variable): ID del actor que se desea eliminar.
- **Respuesta**:
  - **Código 200**: Retorna un mensaje confirmando que el actor fue eliminado con éxito.

**Ejemplo de respuesta:**
```json
{
  "message": "Recurso eliminado con éxito"
}
```

---

Aquí tienes la documentación para los endpoints del `MovieController`, incluyendo descripciones, parámetros y ejemplos de respuestas.

---

### Documentación de Endpoints para Gestión de Películas

#### 1. Buscar una película por nombre

- **Método**: `GET`
- **Endpoint**: `/movies/tmdb/search/{name}`
- **Descripción**: Este endpoint busca películas en la API de TMDB por nombre.
- **Parámetros**:
  - `name` (Path Variable): Nombre de la película que se desea buscar.
- **Respuesta**:
  - **Código 200**: Retorna una lista de objetos `SearchMovie` que coinciden con la búsqueda.

**Ejemplo de respuesta:**
```json
[
	{
		"id": 123,
		"title": "The Lord of the Rings",
		"vote_average": 6.6,
		"release_date": "1978-11-15T00:00:00.000+00:00",
		"poster_path": "https://image.tmdb.org/t/p/w500/liW0mjvTyLs7UCumaHhx3PpU4VT.jpg"
	},
	{
		"id": 1362,
		"title": "The Hobbit",
		"vote_average": 6.5,
		"release_date": "1977-11-27T00:00:00.000+00:00",
		"poster_path": "https://image.tmdb.org/t/p/w500/2ohvyMPhvjftLrM6S6Ljr6QrL0u.jpg"
	},
	{
		"id": 120,
		"title": "The Lord of the Rings: The Fellowship of the Ring",
		"vote_average": 8.416,
		"release_date": "2001-12-18T00:00:00.000+00:00",
		"poster_path": "https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg"
	},
    ...
]
```

---

#### 2. Obtener detalles de una película por ID

- **Método**: `GET`
- **Endpoint**: `/tmdb/details/{id}`
- **Descripción**: Este endpoint devuelve el detalle de una película específica basada en su ID. Si la película no está en la base de datos, se guarda automáticamente.
- **Parámetros**:
  - `id` (Path Variable): ID de la película que se desea obtener.
- **Respuesta**:
  - **Código 200**: Retorna un objeto `DetailsMovie` que contiene la información detallada de la película.

**Ejemplo de respuesta:**
```json
{
	"id": 123,
	"title": "The Lord of the Rings",
	"vote_average": 6.6,
	"release_date": "1978-11-15T00:00:00.000+00:00",
	"poster_path": "/liW0mjvTyLs7UCumaHhx3PpU4VT.jpg",
	"backdrop_path": "/jOuCWdh0BE6XPu2Vpjl08wDAeFz.jpg",
	"popularity": 38.904,
	"vote_count": 883,
	"genres": [
		{
			"id": 12
		},
		{
			"id": 16
		},
		{
			"id": 14
		}
	],
	"overview": "The Fellowship of the Ring embark on a journey to destroy the One Ring and end Sauron's reign over Middle-earth.",
	"runtime": 132
}
```

---

#### 3. Obtener todas las películas almacenadas

- **Método**: `GET`
- **Endpoint**: `/`
- **Descripción**: Este endpoint devuelve una lista de todas las películas almacenadas en la base de datos.
- **Respuesta**:
  - **Código 200**: Retorna una lista de objetos `Movie`.

**Ejemplo de respuesta:**
```json
[
	{
		"id": 123,
		"title": "The Lord of the Rings",
		"vote_average": 6.6,
		"release_date": "1978-11-15T00:00:00.000+00:00",
		"poster_path": "/liW0mjvTyLs7UCumaHhx3PpU4VT.jpg",
		"backdrop_path": "/jOuCWdh0BE6XPu2Vpjl08wDAeFz.jpg",
		"popularity": 38.904,
		"vote_count": 883,
		"runtime": 132,
		"overview": "The Fellowship of the Ring embark on a journey to destroy the One Ring and end Sauron's reign over Middle-earth.",
		"genres": []
	},
	{
		"id": 782,
		"title": "Gattaca",
		"vote_average": 7.6,
		"release_date": "1997-09-07T00:00:00.000+00:00",
		"poster_path": "/mi8ow4MIoPvgBnWB1OKe0ph0woa.jpg",
		"backdrop_path": "/hPsCR1ny6GnctJkWqeJwihTDD7T.jpg",
		"popularity": 41.066,
		"vote_count": 6251,
		"runtime": 106,
		"overview": "In a future society in the era of indefinite eugenics, humans are set on a life course depending on their DNA. Young Vincent Freeman is born with a condition that would prevent him from space travel, yet is determined to infiltrate the GATTACA space program.",
		"genres": [
			{
				"id": 53,
				"name": "Suspense"
			},
			{
				"id": 878,
				"name": "Ciencia ficción"
			},
			{
				"id": 9648,
				"name": "Misterio"
			},
			{
				"id": 10749,
				"name": "Romance"
			}
		]
	},
	{
		"id": 475557,
		"title": "Joker",
		"vote_average": 8.2,
		"release_date": "2019-10-01T00:00:00.000+00:00",
		"poster_path": "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
		"backdrop_path": "/gZWl93sf8AxavYpVT1Un6EF3oCj.jpg",
		"popularity": 315.944,
		"vote_count": 25452,
		"runtime": 122,
		"overview": "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.",
		"genres": [
			{
				"id": 53,
				"name": "Suspense"
			}
		]
	}
]
```

---

#### 4. Obtener una película por ID

- **Método**: `GET`
- **Endpoint**: `/{id}`
- **Descripción**: Este endpoint devuelve una película específica basada en su ID.
- **Parámetros**:
  - `id` (Path Variable): ID de la película que se desea obtener.
- **Respuesta**:
  - **Código 200**: Retorna un objeto `Movie` que representa la película solicitada.

**Ejemplo de respuesta:**
```json
{
	"id": 782,
	"title": "Gattaca",
	"vote_average": 7.6,
	"release_date": "1997-09-07T00:00:00.000+00:00",
	"poster_path": "/mi8ow4MIoPvgBnWB1OKe0ph0woa.jpg",
	"backdrop_path": "/hPsCR1ny6GnctJkWqeJwihTDD7T.jpg",
	"popularity": 41.066,
	"vote_count": 6251,
	"runtime": 106,
	"overview": "In a future society in the era of indefinite eugenics, humans are set on a life course depending on their DNA. Young Vincent Freeman is born with a condition that would prevent him from space travel, yet is determined to infiltrate the GATTACA space program.",
	"genres": [
		{
			"id": 53,
			"name": "Suspense"
		},
		{
			"id": 878,
			"name": "Ciencia ficción"
		},
		{
			"id": 9648,
			"name": "Misterio"
		},
		{
			"id": 10749,
			"name": "Romance"
		}
	]
}
```

---

#### 5. Eliminar una película por ID

- **Método**: `DELETE`
- **Endpoint**: `/{id}`
- **Descripción**: Este endpoint elimina una película específica basada en su ID.
- **Parámetros**:
  - `id` (Path Variable): ID de la película que se desea eliminar.
- **Respuesta**:
  - **Código 200**: Retorna un mensaje confirmando que la película fue eliminada con éxito.

**Ejemplo de respuesta:**
```json
{
  "Message": "Recurso eliminado con éxito"
}
```

------

## Autores

- **Alexis Chacón**

## Referencias

Este proyecto utiliza la API de [TMDB (The Movie Database)](https://www.themoviedb.org/) para obtener información sobre películas, series, actores y géneros. TMDB es una base de datos de medios de comunicación que proporciona acceso a información detallada sobre una amplia variedad de contenido audiovisual.