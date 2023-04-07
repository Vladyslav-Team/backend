package com.softserve.skillscope.mapper.proof;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.entity.Proof;

public interface ProofMapper {
    FullProof toFullProof(Proof proof);
}
