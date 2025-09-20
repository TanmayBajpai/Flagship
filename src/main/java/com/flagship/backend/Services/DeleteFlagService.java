package com.flagship.backend.Services;

import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Exceptions.InvalidFlagIdException;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteFlagService {

    private final FeatureFlagRepository featureFlagRepository;

    public DeleteFlagService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    @Transactional
    public void deleteFlag(UUID id) {
        Optional<FeatureFlag> optionalFeatureFlag = featureFlagRepository.findFeatureFlagById(id);

        if (optionalFeatureFlag.isEmpty()) throw new InvalidFlagIdException();

        FeatureFlag featureFlag = optionalFeatureFlag.get();

        featureFlagRepository.delete(featureFlag);
    }
}
