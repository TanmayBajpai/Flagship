package com.flagship.backend.Services;

import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Entities.User;
import com.flagship.backend.Exceptions.InvalidApiKeyException;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import com.flagship.backend.Respositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuccessHandlerService {

    private final FeatureFlagRepository featureFlagRepository;
    private final UserRepository userRepository;
    private final EvaluateUserService evaluateUserService;

    public SuccessHandlerService(FeatureFlagRepository featureFlagRepository, UserRepository userRepository, EvaluateUserService evaluateUserService) {
        this.featureFlagRepository = featureFlagRepository;
        this.userRepository = userRepository;
        this.evaluateUserService = evaluateUserService;
    }

    public void handleSuccess(String apiKey, String userId) {

        Optional<User> userOptional = userRepository.findUserByApiKey(apiKey);

        if (userOptional.isEmpty()) throw new InvalidApiKeyException();

        List<FeatureFlag> featureFlagList = featureFlagRepository.findFeatureFlagsByOwner(userOptional.get().getUsername());

        for (FeatureFlag featureFlag : featureFlagList) {
            if (evaluateUserService.evaluate(featureFlag.getId(), userId, null)) {
                featureFlag.setWithFlagSuccess(featureFlag.getWithFlagSuccess() + 1);
            } else {
                featureFlag.setWithoutFlagSuccess(featureFlag.getWithoutFlagSuccess() + 1);
            }
            featureFlagRepository.save(featureFlag);
        }
    }
}
