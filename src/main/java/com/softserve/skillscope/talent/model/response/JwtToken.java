package com.softserve.skillscope.talent.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record JwtToken(
    @JsonProperty("jwt-token")
    String token
) {
}
