package dev.buddly.flow_sync.service;

import dev.buddly.flow_sync.model.Token;

public interface TokenService {
    Token findByToken(String token);
}
