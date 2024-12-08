package com.TechTitans.RecomendacionOpenLibrary.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenLibraryService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene una lista de libros por género desde la API de Open Library.
     * 
     * @param genre El género para buscar libros.
     * @return Lista de mapas con la información de los libros.
     */
    public List<Map<String, Object>> getBooksByGenre(String genre) {
        String apiUrl = "https://openlibrary.org/subjects/" + genre.toLowerCase() + ".json";
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        List<Map<String, Object>> books = new ArrayList<>();
        if (response != null && response.containsKey("works")) {
            List<Map<String, Object>> works = (List<Map<String, Object>>) response.get("works");
            for (Map<String, Object> work : works) {
                Map<String, Object> book = new HashMap<>();
                book.put("title", work.get("title"));
                book.put("author", work.containsKey("authors") ? 
                    ((List<Map<String, String>>) work.get("authors")).get(0).get("name") : "Unknown Author");
                
                // Agregar portada si está disponible
                if (work.containsKey("cover_id")) {
                    String coverUrl = "https://covers.openlibrary.org/b/id/" + work.get("cover_id") + "-M.jpg";
                    book.put("coverUrl", coverUrl);
                } else {
                    book.put("coverUrl", "https://via.placeholder.com/150?text=No+Image");
                }

                books.add(book);
            }
        }
        return books;
    }

    /**
     * Obtiene una recomendación de libro aleatoria desde la API de Open Library.
     * 
     * @return Un mapa con la información de un libro aleatorio.
     */
    public Map<String, Object> getRandomBook() {
        // URL para obtener una lista de libros (esto se puede cambiar por un endpoint que entregue varios libros)
        String apiUrl = "https://openlibrary.org/subjects/love.json"; // Aquí puedes usar cualquier tema o género que prefieras
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        // Verificar que la respuesta contiene la lista de libros
        if (response != null && response.containsKey("works")) {
            List<Map<String, Object>> works = (List<Map<String, Object>>) response.get("works");

            // Seleccionar un libro aleatorio de la lista
            Random rand = new Random();
            Map<String, Object> randomBook = works.get(rand.nextInt(works.size()));  // Selección aleatoria

            // Crear un mapa para devolver la información relevante del libro
            Map<String, Object> book = new HashMap<>();
            book.put("title", randomBook.get("title"));
            book.put("author", randomBook.containsKey("authors") ? 
                ((List<Map<String, String>>) randomBook.get("authors")).get(0).get("name") : "Unknown Author");
            
            // Agregar portada si está disponible
            if (randomBook.containsKey("cover_id")) {
                String coverUrl = "https://covers.openlibrary.org/b/id/" + randomBook.get("cover_id") + "-M.jpg";
                book.put("coverUrl", coverUrl);
            } else {
                book.put("coverUrl", "https://via.placeholder.com/150?text=No+Image");
            }

            return book;
        }
        return null;  // En caso de que no se encuentren libros
    }

}

