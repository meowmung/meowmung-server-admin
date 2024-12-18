package com.example.admin.dto;

import java.util.HashMap;
import java.util.Map;

public record TermsRequest(
        int page,
        String term_name

) {
    // Map 형태로 변환하는 메서드
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", this.page);
        map.put("term_name", this.term_name);
        return map;
    }
}
