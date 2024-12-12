package org.example.newservice.service;

import org.example.newservice.model.dto.NewsDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class NewsParserServiceTest {


    private NewsParserService newsParserService;

    @BeforeEach
    void setUp() {
        newsParserService = new NewsParserService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUrlNews() {


    }

    @Test
    void getNewsList() {
        try {
            List<NewsDto> newsList = newsParserService.getNewsList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}