package com.softserve.skillscope.talent.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
@Builder
public record TalentStatsResponse(
        @JsonProperty("mostKudosed")
        List<Long> mostKudosedList
) {
}