package org.example.newservice.service;

import org.example.newservice.model.dto.NewsDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsParserService {

    private static final Logger logger = LoggerFactory.getLogger(NewsParserService.class);
    private final String urlNews = "https://habr.com/ru/news/";

    /**
     * Получает список новостей с сайта Habr.
     *
     * @return Список новостей, собранных с сайта
     * @throws IOException Если произошла ошибка при подключении или парсинге страницы
     */
    public List<NewsDto> getNewsList() throws IOException {
        logger.info("Начинаем парсинг новостей с сайта: {}", urlNews);
        List<NewsDto> newsList = new ArrayList<>();

        try {
            // Подключаемся к странице и получаем HTML-документ
            Document document = Jsoup.connect(urlNews).get();
            Elements elements = document.select("article.tm-articles-list__item h2 a");

            if (elements.isEmpty()) {
                logger.warn("На странице не найдено новостей.");
            }

            // Обрабатываем каждый элемент новости
            for (Element element : elements) {
                String title = element.text();
                String url = element.absUrl("href");

                // Логируем каждую новость
                logger.debug("Найдена новость: {} - {}", title, url);

                NewsDto news = new NewsDto(title, url);
                newsList.add(news);
            }

            logger.info("Парсинг завершён. Найдено новостей: {}", newsList.size());
        } catch (IOException e) {
            logger.error("Ошибка при парсинге страницы: {}", e.getMessage(), e);
            throw e;  // Перебрасываем исключение выше
        }

        return newsList;
    }
}