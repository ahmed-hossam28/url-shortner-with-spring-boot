package com.shortnerurl.api.urlshortner.redirect;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/urls")
public class UrlRedirectController {

    private final UrlShortenerService urlShortenerService;

    public UrlRedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRedirect redirect) {
        return new ResponseEntity<>(urlShortenerService.shortenUrl(redirect), HttpStatus.CREATED);
    }
     @GetMapping("/{alias}")
    public ResponseEntity<?> handleRedirect(@PathVariable String alias) {
       Optional<UrlRedirect> redirect = urlShortenerService.getUrlRedirectByAlias(alias);
      return redirect.map(urlRedirect->{
           URI uri = null;
           try {
               uri = new URI(urlRedirect.getUrl());
           } catch (URISyntaxException e) {
               throw new RuntimeException(e);
           }
           HttpHeaders httpHeaders = new HttpHeaders();
           httpHeaders.setLocation(uri);
           return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
       }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
