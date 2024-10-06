package dev.buddly.flow_sync.service.impl;

import dev.buddly.flow_sync.exception.NotFoundException;
import dev.buddly.flow_sync.model.Token;
import dev.buddly.flow_sync.repository.TokenRepository;
import dev.buddly.flow_sync.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(()->new NotFoundException("Token bulunamadı"));
    }
}
