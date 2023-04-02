package com.softserve.skillscope.talent.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import lombok.Builder;

import java.util.List;

@Builder
public record GeneralTalentResponse(
        @JsonProperty("totalItems")
        long totalItems,
        @JsonProperty("totalPage")
        int totalPage,
        @JsonProperty("currentPage")
        int currentPage,
        List<GeneralTalent> talents) {
}
