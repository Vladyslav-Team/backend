package com.softserve.skillscope.kudos.model.request;

import lombok.Builder;

@Builder
public record KudosAmountRequest(
        Integer amount
) {
}
