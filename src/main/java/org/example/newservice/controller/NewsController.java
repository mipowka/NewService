package org.example.newservice.controller;

import org.example.newservice.model.entity.News;
import org.example.newservice.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        logger.info("Получение новости с ID: {}", id);

        try {
            // Получение новости
            News newsById = newsService.getNewsById(id);

            if (newsById == null) {
                logger.warn("Новость с ID {} не найдена.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Новость не найдена");
            }

            logger.info("Новость с ID {} успешно найдена: {}", id, newsById.getHead());
            return ResponseEntity.ok(newsById);
        } catch (Exception e) {
            logger.error("Ошибка при получении новости с ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllNews() {
        logger.info("Получение всех новостей.");

        try {
            // Получение всех новостей
            List<News> allNews = newsService.getAllNews();

            if (allNews.isEmpty()) {
                logger.warn("Список новостей пуст.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Список новостей пуст");
            }

            logger.info("Успешно получены все новости. Количество: {}", allNews.size());
            return ResponseEntity.ok(allNews);
        } catch (Exception e) {
            logger.error("Ошибка при получении всех новостей: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

}
