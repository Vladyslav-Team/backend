package com.softserve.skillscope.talent.model.dto.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record JwtToken(
    @JsonProperty("jwt-token")
    String token
) {
}
