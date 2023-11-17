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

    String hash(String val){
        return Hashing.murmur3_32().hashString(val, StandardCharsets.UTF_8).toString();
    }
    public String shortenUrl(UrlRedirect redirect) {
        // Implement URL shortening logic (generate a unique short code)
        String alias = hash(redirect.getUrl());
        String userAlias =redirect.getAlias();
        if(redirect.getAlias().isEmpty())//if alias is not set will give you one (hash value)
              redirect.setAlias(alias);

        if(repository.existsByAlias(redirect.getAlias()))//has to be unique
        {
            while(repository.existsByAlias(redirect.getAlias())){
                // if it is already present make it as the hash value of it till it is unique
                //the more there are links with the same alias the more this loop will be working,
                // so it will be slow by the time
                redirect.setAlias(hash(redirect.getAlias()));
            }
            redirect.setAlias(userAlias+redirect.getAlias());
            // Save the mapping in the database
            repository.save(redirect);
        }
        else
            repository.save(redirect);

        // Return the short URL
        return redirect.getAlias();
    }

    public Optional<UrlRedirect> getUrlRedirectByAlias(String alias) {
        // Retrieve the original URL from the database based on the short URL
        // Return the original URL
        return repository.findByAlias(alias);
    }
}
