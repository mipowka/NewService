package org.example.newservice.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "habrs")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String head;
    private String url;

    private LocalDateTime postTime;
    private Boolean isSend = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

    public Boolean isSend() {
        return isSend;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", head='" + head + '\'' +
                ", url='" + url + '\'' +
                ", postTime=" + postTime +
                ", isSend=" + isSend +
                '}';
    }
}
