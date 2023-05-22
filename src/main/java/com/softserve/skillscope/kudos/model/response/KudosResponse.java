package com.softserve.skillscope.kudos.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record KudosResponse(
        Long proofId,
        boolean isClicked,
        Integer amountOfKudos,
        Integer amountOfKudosCurrentUser
) {
}
