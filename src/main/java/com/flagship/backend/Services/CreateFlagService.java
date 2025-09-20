package com.flagship.backend.Services;

import com.flagship.backend.DTO.CreateFlagRequest;
import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Entities.User;
import com.flagship.backend.Exceptions.InvalidApiKeyException;
import com.flagship.backend.Exceptions.InvalidFeatureFlag;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import com.flagship.backend.Respositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CreateFlagService {

    private final UserRepository userRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final FeatureFlagRepository featureFlagRepository;

    public CreateFlagService(UserRepository userRepository, FeatureFlagRepository featureFlagRepository) {
        this.userRepository = userRepository;
        this.featureFlagRepository = featureFlagRepository;
    }

    public void createFlag(CreateFlagRequest createFlagRequest, String username) {
        String name = createFlagRequest.getFlagName();
        if (name == null) throw new InvalidFeatureFlag();

        String description = createFlagRequest.getDescription();

        boolean enabled = createFlagRequest.isEnabled();

        List<String> allowedCountries = createFlagRequest.getAllowedCountries();
        if(allowedCountries == null) {
            allowedCountries = new ArrayList<>();
        }

        int rolloutPercent = createFlagRequest.getRolloutPercent();
        if (rolloutPercent > 100 || rolloutPercent < 0) throw new InvalidFeatureFlag();

        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        String seed = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        String user;

        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new InvalidApiKeyException();
        }

        user = optionalUser.get().getUsername();

        FeatureFlag featureFlag = new FeatureFlag();
        featureFlag.setFlagName(name);
        featureFlag.setDescription(description);
        featureFlag.setEnabled(enabled);
        featureFlag.setRolloutPercent(rolloutPercent);
        featureFlag.setAllowedCountries(allowedCountries);
        featureFlag.setOwner(user);
        featureFlag.setSeed(seed);

        featureFlagRepository.save(featureFlag);
    }
}
