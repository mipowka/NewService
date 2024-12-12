package org.example.newservice.config;

import org.example.newservice.bot.BotNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);

    @Bean
    public TelegramBotsApi createBotNews(BotNews botNews) throws TelegramApiException {
        // Логируем начало процесса создания и регистрации бота
        logger.info("Инициализация и регистрация бота в Telegram API.");

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(botNews);
            logger.info("Бот успешно зарегистрирован в Telegram API.");
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            // Логируем ошибку, если бот не может быть зарегистрирован
            logger.error("Ошибка при регистрации бота в Telegram API.", e);
            throw e;  // Перебрасываем исключение выше
        }
    }
}
