package org.example.newservice.mapping;

import org.example.newservice.model.dto.NewsDto;
import org.example.newservice.model.entity.News;
import org.example.newservice.repository.NewsRepository;
import org.example.newservice.service.NewsParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NewsMapping {

    private static final Logger logger = LoggerFactory.getLogger(NewsMapping.class);

    private final NewsRepository newsRepository;
    private final NewsParserService newsParserService;

    public NewsMapping(NewsRepository newsRepository, NewsParserService newsParserService) {
        this.newsRepository = newsRepository;
        this.newsParserService = newsParserService;
    }

    /**
     * Сохраняет новости, полученные через парсер, в базу данных.
     *
     * @throws IOException если произошла ошибка при получении новостей
     */
    public void saveNewsFromDto() throws IOException {
        logger.info("Начинаем процесс сохранения новостей из парсера.");

        try {
            List<NewsDto> newsList = newsParserService.getNewsList();
            logger.info("Получено {} новостей.", newsList.size());

            newsList.forEach(newsDto -> {
                News news = new News();
                news.setHead(newsDto.getHead());
                news.setUrl(newsDto.getUrl());

                // Сохраняем новость в базу данных
                try {
                    newsRepository.save(news);
                    logger.debug("Новость успешно сохранена: {}", news.getHead());
                } catch (Exception e) {
                    logger.error("Ошибка при сохранении новости: {}", news.getHead(), e);
                }
            });

            logger.info("Процесс сохранения новостей завершен.");
        } catch (IOException e) {
            logger.error("Ошибка при парсинге новостей: ", e);
            throw e; // Перебрасываем исключение выше для дальнейшей обработки
        }
    }
}