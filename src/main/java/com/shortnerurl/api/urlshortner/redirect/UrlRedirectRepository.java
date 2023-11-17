package com.shortnerurl.api.urlshortner.redirect;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRedirectRepository extends MongoRepository<UrlRedirect, String> {
    Optional<UrlRedirect> findByAlias(String alias);
    Boolean existsByAlias(String alias);
}
