package com.flagship.backend.DTO;

import com.flagship.backend.Entities.User;
import lombok.Data;

@Data
public class UserInfo {
    public String username;
    public String apiKey;
    public UserInfo(User user) {
        this.username = user.getUsername();
        this.apiKey = user.getApiKey();
    }
}
