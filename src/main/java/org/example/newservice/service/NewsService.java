package org.example.newservice.service;

import org.example.newservice.mapping.NewsMapping;
import org.example.newservice.model.entity.News;
import org.example.newservice.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private final NewsRepository newsRepository;
    private final NewsMapping newsMapping;

    public NewsService(NewsRepository newsRepository, NewsMapping newsMapping) {
        this.newsRepository = newsRepository;
        this.newsMapping = newsMapping;
    }

    /**
     * Сохраняет новости из DTO в базу данных.
     */
    public void saveFromMapping() {
        try {
            logger.info("Попытка сохранить новости из DTO.");
            newsMapping.saveNewsFromDto();
            logger.info("Новости успешно сохранены.");
        } catch (IOException e) {
            logger.error("Ошибка при сохранении новостей из DTO: {}", e.getMessage());
            throw new RuntimeException("Не удалось сохранить новости из DTO", e);
        }
    }

    /**
     * Получает следующую новость для отправки.
     */
    public Optional<News> getNextNewsToSend() {
        try {
            // Получаем все новости, которые еще не были отправлены
            List<News> allNews = newsRepository.findAll();

            // Находим первую новость, которая еще не была отправлена
            for (News news : allNews) {
                if (news != null && !news.isSend()) {
                    logger.info("Найдена новость для отправки: {}", news.getHead());
                    return Optional.of(news);  // Возвращаем первую найденную новость
                }
            }

            // Если не нашли новостей для отправки
            logger.info("Нет новостей для отправки.");
            return Optional.empty();  // Возвращаем пустой Optional, если новостей нет

        } catch (Exception e) {
            logger.error("Ошибка при получении новости для отправки.", e);
            return Optional.empty();  // Возвращаем пустой Optional в случае ошибки
        }
    }


    /**
     * Генерирует сообщение для отправки.
     */
    public String getMessageToPost(News news) {
        return String.format("%s\n%s", news.getHead(), news.getUrl());
    }

    /**
     * Удаляет старые новости, которые были отправлены и прошло более 5 минут.
     */
    public void deleteOldSentNews() {
        try {
            // Получаем все новости из базы данных
            List<News> allNews = newsRepository.findAll();
            LocalDateTime currentTime = LocalDateTime.now();  // Текущее время

            // Ищем новости, которые были отправлены более 5 минут назад
            for (News news : allNews) {
                if (news.isSend() && news.getPostTime() != null) {
                    // Если новость была отправлена и прошло больше 5 минут
                    LocalDateTime postTimePlusFiveMinutes = news.getPostTime().plusMinutes(5);

                    if (currentTime.isAfter(postTimePlusFiveMinutes)) {
                        logger.info("Удаление новости спустя 5 минут после отправки: {}", news.getHead());
                        newsRepository.delete(news);  // Удаляем новость из базы данных
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при удалении старых новостей.", e);
        }
    }


    /**
     * Помечает новость как отправленную и сохраняет время отправки.
     */
    public void postTimeNews(News news) {
        news.setSend(true);
        news.setPostTime(LocalDateTime.now());
        newsRepository.save(news);
        logger.info("Новость успешно помечена как отправленная: {}", news.getHead());
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("Новость не найдена"));
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public void deleteAllNews(){
        newsRepository.deleteAll();
    }
}