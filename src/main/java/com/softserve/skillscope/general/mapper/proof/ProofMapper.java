package com.softserve.skillscope.general.mapper.proof;

import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.entity.Proof;

public interface ProofMapper {
    FullProof toFullProof(Proof proof);
    GeneralProof toGeneralProof(Proof proof);
}
