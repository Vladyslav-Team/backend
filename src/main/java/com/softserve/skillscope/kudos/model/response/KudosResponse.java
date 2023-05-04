package com.softserve.skillscope.kudos.model.response;

public record KudosResponse(
        Long proofId,
        boolean isClicked,
        Integer amountOfKudos,
        Integer amountOfKudosCurrentUser
) {
}
