# Explicación del Async / Await en JavaScript

```mermaid 
graph TD
    A[Inicio: Llamada a función async] --> B{¿Hay un await?}
    B -- No --> C[Ejecuta código síncrono]
    C --> D[Retorna Promise.resolve]
    
    B -- Sí --> E[Se ejecuta la expresión del await]
    E --> F[La función se PAUSA]
    F --> G[El control vuelve al hilo principal]
    G --> H[El navegador sigue procesando: Clics, Animaciones, Otros scripts]
    
    H --> I{¿Promesa resuelta?}
    I -- No --> H
    I -- Sí --> J[La función se REANUDA]
    J --> K[El valor obtenido se asigna a la variable]
    K --> L[Continúa con la siguiente línea de la función]
    L --> M[Fin de la función]
```

## Diagrama de secuencia 
```mermaid
sequenceDiagram
    participant U as Usuario
    participant JS as index.js (Función Async)
    participant HW as Web API / Navegador
    participant API as Rick & Morty API

    U->>JS: Clic en "Cargar Personajes"
    Note over JS: Inicia fetchCharacters()
    
    JS->>HW: fetch('https://rickandmortyapi.com/api/character')
    Note right of JS: Palabra clave: await
    
    JS-->>HW: Se pausa la función y libera el hilo
    Note over HW: El navegador sigue libre para<br/>animaciones o interacción
    
    HW->>API: Petición HTTP GET
    API-->>HW: Respuesta HTTP (JSON)
    
    HW-->>JS: Promesa resuelta (Response)
    Note over JS: La función se REANUDA
    
    JS->>HW: response.json()
    Note right of JS: Segundo await
    JS-->>HW: Se vuelve a pausar
    HW-->>JS: Datos convertidos a Objeto
    
    Note over JS: renderCharacters(data)
    JS->>U: Actualiza el DOM (Muestra personajes)
```

# Ejemplo Consumo de APIs con fetch 

Definición del `index.html`: 

```html
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consumo API Fetch</title>
</head>
<body>

    <h1>Personajes de Rick and Morty</h1>
    <button id="load-characters">Cargar Personajes</button>
    
    <div id="characters-container">
        <!-- Los datos se cargarán aquí -->
    </div>

    <script src="index.js"></script>
</body>
</html>
```

Definición del `index.js`: 

```javascript 
// Referencias a los elementos del DOM
const container = document.getElementById('characters-container');
const btn = document.getElementById('load-characters');

// Función para obtener los datos de la API
const fetchCharacters = async () => {
    const url = 'https://rickandmortyapi.com/api/character';

    try {
        const response = await fetch(url);
        
        // Verificamos si la respuesta es exitosa
        if (!response.ok) {
            throw new Error(`Error en la petición: ${response.status}`);
        }

        const data = await response.json();
        renderCharacters(data.results);
    } catch (error) {
        console.error('Hubo un error:', error);
        container.innerHTML = `<p>Error al cargar los datos.</p>`;
    }
};

// Función para modificar el DOM con los resultados
const renderCharacters = (characters) => {
    // Limpiamos el contenedor antes de renderizar
    container.innerHTML = '';

    characters.forEach(character => {
        // Creamos los elementos
        const div = document.createElement('div');
        const h3 = document.createElement('h3');
        const p = document.createElement('p');
        const img = document.createElement('img');

        // Asignamos contenido
        h3.textContent = character.name;
        p.textContent = `Estado: ${character.status} - Especie: ${character.species}`;
        img.src = character.image;
        img.alt = character.name;
        img.width = 150;

        // Construimos la jerarquía
        div.appendChild(h3);
        div.appendChild(p);
        div.appendChild(img);
        
        // Inyectamos el div en el contenedor principal
        container.appendChild(div);
    });
};

// Evento de escucha para el botón
btn.addEventListener('click', fetchCharacters);
```
