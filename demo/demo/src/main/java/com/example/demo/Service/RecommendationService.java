package com.example.demo.Service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String TVMAZE_BASE_URL = "https://api.tvmaze.com";

    public Map<String, Object> getRandomShow() {
        int randomPage = (int) (Math.random() * 10);
        String url = TVMAZE_BASE_URL + "/shows?page=" + randomPage;

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                }
        );

        List<Map<String, Object>> shows = response.getBody();

        if (shows != null && !shows.isEmpty()) {
            int randomIndex = (int) (Math.random() * shows.size());
            return shows.get(randomIndex);
        }
        return null;
    }

    public List<Map<String, Object>> getShowsByGenre(String genre) {
        String urlTemplate = TVMAZE_BASE_URL + "/shows?page=";
        List<Map<String, Object>> filteredShows = new ArrayList<>();
        int page = 0;
    
        while (page < 20) { // Limitar a 5 páginas
            String url = urlTemplate + page;
    
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {
                    }
            );
    
            List<Map<String, Object>> shows = response.getBody();
            if (shows == null || shows.isEmpty()) {
                break; // No hay más páginas
            }
    
            for (Map<String, Object> show : shows) {
                List<String> genres = (List<String>) show.get("genres");
                if (genres != null && genres.contains(genre)) {
                    filteredShows.add(show);
                }
            }
    
            page++; // Pasar a la siguiente página
        }
    
        return filteredShows;
    }
    

    public List<String> getAllGenres() {
        return List.of("Action", "Adult", "Adventure", "Anime", "Children", "Comedy", "Crime", "DIY",
                "Drama", "Espionage", "Family", "Fantasy", "Food", "History", "Horror", "Legal",
                "Medical", "Music", "Mystery", "Nature", "Romance", "Science-Fiction", "Sports",
                "Supernatural", "Thriller", "Travel", "War", "Western");
    }
}

