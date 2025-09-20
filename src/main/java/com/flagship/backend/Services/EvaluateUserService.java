package com.flagship.backend.Services;

import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EvaluateUserService {

    private final FeatureFlagRepository featureFlagRepository;

    public EvaluateUserService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    public boolean evaluate(UUID id, String userID, String userCountry) {
        Optional<FeatureFlag> optionalFeatureFlag = featureFlagRepository.findFeatureFlagById(id);

        if (optionalFeatureFlag.isEmpty()) {
            return false;
        }

        FeatureFlag featureFlag = optionalFeatureFlag.get();

        if (!featureFlag.isEnabled()) return false;

        List<String> allowedCountries = featureFlag.getAllowedCountries();

        if (userCountry != null && !allowedCountries.isEmpty() && !allowedCountries.contains(userCountry)) return false;

        String input = userID + "|" + featureFlag.getFlagName() + "|" + featureFlag.getSeed();

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

        long value = 0;

        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (bytes[i] & 0xffL);
        }

        int bucket = (int) (Long.remainderUnsigned(value, 100));

        int rollout = featureFlag.getRolloutPercent();

        return bucket < rollout;
    }
}
