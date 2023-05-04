package com.softserve.skillscope.sponsor.model.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softserve.skillscope.sponsor.model.dto.GeneralSponsor;
import lombok.Builder;

import java.util.List;

@Builder
public record GeneralSponsorResponse(
        @JsonProperty("totalItems")
        long totalItems,
        @JsonProperty("totalPages")
        int totalPage,
        @JsonProperty("currentPage")
        int currentPage,
        List<GeneralSponsor> sponsors
) {
}
