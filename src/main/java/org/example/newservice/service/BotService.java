package org.example.newservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class BotService {

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);
    private static final Long CHAT_ID = -1002484868151L;  // Идентификатор чата для отправки сообщений

    /**
     * Отправляет новость в Telegram-чат.
     *
     * @param news Текст новости, который нужно отправить
     * @return Объект SendMessage для отправки через Telegram API
     */
    public SendMessage sendNews(String news) {
        logger.info("Начинаем отправку новости в Telegram: {}", news);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(CHAT_ID);
        sendMessage.setText(news);

        try {
            // Логируем успешную отправку
            logger.info("Сообщение отправлено в чат с ID {}: {}", CHAT_ID, news);
        } catch (Exception e) {
            // Логируем ошибку, если что-то пошло не так
            logger.error("Ошибка при отправке сообщения в Telegram. Текст сообщения: {}", news, e);
            throw new RuntimeException("Ошибка при отправке сообщения в Telegram", e);
        }

        return sendMessage;
    }
}
