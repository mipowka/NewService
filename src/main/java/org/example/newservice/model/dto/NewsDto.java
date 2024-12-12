package org.example.newservice.model.dto;


import lombok.Data;

@Data
public class NewsDto {

    private String head;
    private String url;

    public NewsDto(String head, String url) {
        this.head = head;
        this.url = url;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "head='" + head + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
