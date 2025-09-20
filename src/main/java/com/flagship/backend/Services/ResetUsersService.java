package com.flagship.backend.Services;

import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Exceptions.InvalidFlagIdException;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetUsersService {

    private final FeatureFlagRepository featureFlagRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public ResetUsersService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    public void resetUsers(UUID id) {
        Optional<FeatureFlag> optionalFlag = featureFlagRepository.findFeatureFlagById(id);

        if (optionalFlag.isEmpty()) {
            throw new InvalidFlagIdException();
        }

        FeatureFlag featureFlag = optionalFlag.get();

        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        String seed = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        featureFlag.setSeed(seed);

        featureFlagRepository.save(featureFlag);
    }
}
