package com.softserve.skillscope.sercurity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record JwtToken(
    @JsonProperty("jwt-token")
    String token
) {
}
