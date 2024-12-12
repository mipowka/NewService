package org.example.newservice.bot;

import org.example.newservice.service.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@PropertySource("classpath:bot.properties")
@Component
public class BotNews extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotNews.class);

    @Value("${bot.name}")
    private String botName;

    private final BotService botService;

    public BotNews(@Value("${bot.token}") String botToken, BotService botService) {
        super(botToken);
        this.botService = botService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Здесь можно логировать обновления, если необходимо, например:
        logger.info("Получено обновление: {}", update.toString());
    }

    public void sendNews(String news) {
        SendMessage sendMessage = botService.sendNews(news);

        // Логируем отправку сообщения
        logger.info("Отправка сообщения: {}", news);

        try {
            execute(sendMessage);
            // Логируем успешную отправку
            logger.info("Сообщение успешно отправлено.");
        } catch (TelegramApiException e) {
            // Логируем ошибку при отправке сообщения
            logger.error("Ошибка при отправке сообщения.", e);
            throw new RuntimeException(e);  // Перебрасываем исключение
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}