package com.softserve.skillscope.talent.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Set;

@Builder
public record TalentStatsResponse(
        @JsonProperty("mostKudosed")
        Set<Long> mostKudosedList
) {
}
