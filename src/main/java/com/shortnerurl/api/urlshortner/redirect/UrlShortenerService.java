package com.shortnerurl.api.urlshortner.redirect;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UrlShortenerService {
    private final UrlRedirectRepository repository;
    public UrlShortenerService(UrlRedirectRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(UrlRedirect redirect) {
        String alias = Hashing.murmur3_32().hashString(redirect.getUrl(), StandardCharsets.UTF_8).toString();
        // Implement URL shortening logic (generate a unique short code)
        // Save the mapping in the database
        // Return the short URL
        if(redirect.getAlias().isEmpty())
              redirect.setAlias(alias);
        if(repository.existsByAlias(redirect.getAlias()))//has to be unique
            return redirect.getAlias();
        else
            repository.save(redirect);
        return redirect.getAlias();
    }

    public Optional<UrlRedirect> getOriginalUrl(String alias) {
        // Retrieve the original URL from the database based on the short URL
        // Return the original URL
        return repository.findByAlias(alias);
    }
}
