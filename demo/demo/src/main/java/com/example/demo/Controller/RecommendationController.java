package com.example.demo.Controller;

import com.example.demo.Service.RecommendationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<String> genres = recommendationService.getAllGenres();
        model.addAttribute("genres", genres);
        return "index";
    }

    @GetMapping("/recommendations/random")
    public String randomRecommendation(Model model) {
        var randomShow = recommendationService.getRandomShow();
        model.addAttribute("show", randomShow);
        return "random";
    }

    @GetMapping("/recommendations/genre")
    public String recommendationByGenre(@RequestParam("genre") String genre, Model model) {
        var showsByGenre = recommendationService.getShowsByGenre(genre);
        model.addAttribute("shows", showsByGenre);
        model.addAttribute("genre", genre);
        return "genre";
    }
}

