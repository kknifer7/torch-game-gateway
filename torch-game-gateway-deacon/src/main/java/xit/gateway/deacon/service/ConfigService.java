package xit.gateway.deacon.service;

import reactor.core.publisher.Mono;

public interface ConfigService {
    Mono<String> get(String kee);
}
