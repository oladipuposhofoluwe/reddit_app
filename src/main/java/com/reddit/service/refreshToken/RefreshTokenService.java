package com.reddit.service.refreshToken;

import com.reddit.exception.SpringRedditException;
import com.reddit.model.refreshToken.RefreshToken;
import com.reddit.repository.refreshToken.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token){
      Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
      refreshToken.orElseThrow(()-> new SpringRedditException("Invalid refresh token"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
