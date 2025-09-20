package com.flagship.backend.Services;

import com.flagship.backend.DTO.UpdateFlagRequest;
import com.flagship.backend.Entities.FeatureFlag;
import com.flagship.backend.Exceptions.InvalidFlagIdException;
import com.flagship.backend.Respositories.FeatureFlagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateFlagService {

    private final FeatureFlagRepository featureFlagRepository;

    public UpdateFlagService(FeatureFlagRepository featureFlagRepository) {
        this.featureFlagRepository = featureFlagRepository;
    }

    @Transactional
    public void updateFlag(UpdateFlagRequest updateFlagRequest, UUID id){
        Optional<FeatureFlag> optionalFeatureFlag = featureFlagRepository.findFeatureFlagById(id);

        if (optionalFeatureFlag.isEmpty()) throw new InvalidFlagIdException();
        FeatureFlag featureFlag = optionalFeatureFlag.get();

        if (updateFlagRequest.getFlagName() != null) {
            featureFlag.setFlagName(updateFlagRequest.getFlagName());
        }

        if (updateFlagRequest.getDescription() != null) {
            featureFlag.setDescription(updateFlagRequest.getDescription());
        }

        if (updateFlagRequest.getEnabled() != null) {
            featureFlag.setEnabled(updateFlagRequest.getEnabled());
        }

        if (updateFlagRequest.getRolloutPercent() != null) {
            if (updateFlagRequest.getRolloutPercent() < 0 || updateFlagRequest.getRolloutPercent() > 100) throw new RuntimeException("Invalid Rollout Percent");
            featureFlag.setRolloutPercent(updateFlagRequest.getRolloutPercent());
        }

        if (updateFlagRequest.getAllowedCountries() != null) {
            featureFlag.setAllowedCountries(updateFlagRequest.getAllowedCountries());
        }

        featureFlagRepository.save(featureFlag);
    }
}
