package com.TechTitans.RecomendacionOpenLibrary.Controller;

import com.TechTitans.RecomendacionOpenLibrary.Service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class RecommendationController {

    @Autowired
    private OpenLibraryService openLibraryService;

    @GetMapping
    public String home() {
        return "index"; // Renderiza la página principal
    }

    @GetMapping("/recommendations")
    public String getRecommendations(@RequestParam(required = false) String genre, Model model) {
        if (genre != null && !genre.isBlank()) {
            List<Map<String, Object>> books = openLibraryService.getBooksByGenre(genre);
            model.addAttribute("books", books);
        }
        return "recommendations"; // Renderiza la página de recomendaciones por género
    }

    // Método para la recomendación aleatoria
    @GetMapping("/random")
    public String getRandomRecommendation(Model model) {
        // Obtener un libro aleatorio
        Map<String, Object> book = openLibraryService.getRandomBook();

        // Agregar el libro aleatorio al modelo
        if (book != null) {
            model.addAttribute("book", book);
        }
        return "random";  // Página para mostrar la recomendación aleatoria
    }


}