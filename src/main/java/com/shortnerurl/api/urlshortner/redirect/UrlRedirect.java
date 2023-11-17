package com.shortnerurl.api.urlshortner.redirect;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UrlRedirect {
    @Id
    private String id;
    private String url;
    private String alias;

    public UrlRedirect(String originalUrl, String shortUrl) {
        this.url = originalUrl;
        this.alias = shortUrl;
    }

    public UrlRedirect() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }
}
