package com.softserve.skillscope.talent.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TalentStatsResponse(
        @JsonProperty("mostKudosed")
        List<Long> mostKudosedList
) {
}
