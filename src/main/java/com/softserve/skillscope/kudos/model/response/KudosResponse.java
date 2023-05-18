package com.softserve.skillscope.kudos.model.response;

import lombok.Builder;

@Builder
public record KudosResponse(
        Long proofId,
        boolean isClicked,
        Integer amountOfKudos,
        Integer amountOfKudosCurrentUser
) {
}
