// Recuperación del objeto div del HTML
const container = document.getElementById("div-characters-container")

// Recuperación del objeto button del HTML
const btn = document.getElementById("btn-load")

const fetchCharacters = async () => {
    const url = 'https://rickandmortyapi.com/api/character';

    try {

	// Consulta 
	const response = await fetch(url)

	// Respuesta correcta
	if(response.status === 200) {

	    // Consulta 
	    const data = await response.json()
	    // get all 

	    if(data.results === undefined){
		alert("El recurso es indefinido ")
	    }
	    console.log(data.results)
	    renderCharacters(data.results)

	}

	// Errores del lado del cliente
	else if(response.status >= 400){
	    throw new Error(`Error del lado del servidor con el status: ${response.status}`)
	}
	
	// Errores del lado del servidor
	else if(response.status >= 500){
	    throw new Error(`Error del lado del servidor con el status: ${response.status}`)
	}
    	
    } catch (error) {
    	
    }

}

const renderCharacters = (characters) => {
    // Modificar el contenido HTML de un objeto del DOM
    container.innerHTML = ""

    
    characters.forEach(character => {

	const div = document.createElement("div")
	const h3 = document.createElement("h3")
	const p = document.createElement("p")
	const img = document.createElement("img")

	h3.textContent = character.name
	img.src = character.image
	img.alt = character.name
	img.width = 150

	div.appendChild(h3)
	div.appendChild(img)

	container.appendChild(div)

	// <div>
	//     <div>
	// 	<h3> character.name </h3>
	// 	<img src=character.img alt=character.name></img>
	//     </div>
	// </div>


    });


}

btn.addEventListener('click', fetchCharacters)

// Ejemplo objeto JSON
// {
// 
//     "name": "name1", 
//     "age": 10, 
//     "pet": {
// 	"name": "pet1", 
// 	"age": 3
//     },
//     "toys": [ {"type": "type1"}, {"type": "type2"}]
// 
// }
