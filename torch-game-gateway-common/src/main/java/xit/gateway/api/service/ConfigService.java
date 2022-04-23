package xit.gateway.api.service;

import reactor.core.publisher.Mono;

public interface ConfigService {
    Mono<String> get(String kee);
}
