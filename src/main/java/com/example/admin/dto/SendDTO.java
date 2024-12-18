package com.example.admin.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record SendDTO(
        List<Map<String, Object>> terms,
        String file_path
) {

}
