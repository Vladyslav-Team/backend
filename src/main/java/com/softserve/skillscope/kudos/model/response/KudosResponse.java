package com.softserve.skillscope.kudos.model.response;

import jakarta.validation.constraints.Min;

public record KudosResponse(
        Long proofId,
        boolean isClicked,
        @Min(1)
        Integer amountOfKudos
) {
}
