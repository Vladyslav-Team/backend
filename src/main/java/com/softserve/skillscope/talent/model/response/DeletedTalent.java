package com.softserve.skillscope.talent.model.response;

public record DeletedTalent (Long talentId, String status){
    public DeletedTalent(Long talentId){
        this(talentId, "Deleted successfully!");
    }
}
