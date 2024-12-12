package org.example.newservice.scheduler;

import org.example.newservice.bot.BotNews;
import org.example.newservice.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class NewsScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NewsScheduler.class);

    private final BotNews botNews;
    private final NewsService newsService;

    public NewsScheduler(BotNews botNews, NewsService newsService) {
        this.botNews = botNews;
        this.newsService = newsService;
    }


    /**
     * Отправка следующей новости каждый день в 10:00 15:00 20:00
     */
    @Scheduled(cron = "0 0 10,15,20 * * *", zone = "GMT+3")
    public void sendScheduledMessage() {
        logger.info("Запуск задачи отправки следующей новости.");

        try {
            // Получаем следующую новость для отправки
            newsService.getNextNewsToSend()
                    .ifPresentOrElse(
                            news -> {
                                String message = newsService.getMessageToPost(news);
                                botNews.sendNews(message);  // Отправляем новость
                                newsService.postTimeNews(news);  // Помечаем новость как отправленную
                                logger.info("Новость успешно отправлена: {}", news.getHead());
                            },
                            () -> logger.info("Нет новостей для отправки.")
                    );
        } catch (Exception e) {
            logger.error("Ошибка при отправке новости.", e);
        }
    }

    /**
     * Очистка старых новостей раз в день в 21:00.
     */
    @Scheduled(cron = "0 0 21 * * *", zone = "GMT+3")
    public void cleanOldNews() {
        logger.info("Запуск задачи очистки старых новостей.");

        try {
            // Получаем список старых новостей для удаления
            newsService.deleteAllNews();
            logger.info("Очистка старых новостей завершена.");
        } catch (Exception e) {
            logger.error("Ошибка при очистке старых новостей.", e);
        }
    }


    /**
     * Синхронизация новостей с внешним источником в 9:00 и 18:00 по времени GMT+3.
     */
    @Scheduled(cron = "0 0 9,18 * * *", zone = "GMT+3")
    public void syncNews() {
        logger.info("Запуск задачи синхронизации новостей.");

        try {
            newsService.saveFromMapping();  // Получаем и сохраняем новости
            logger.info("Синхронизация новостей завершена.");
        } catch (Exception e) {
            logger.error("Ошибка при синхронизации новостей.", e);
        }
    }
}