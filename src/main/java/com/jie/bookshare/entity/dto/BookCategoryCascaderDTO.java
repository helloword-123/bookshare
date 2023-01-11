package com.jie.bookshare.entity.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BookCategoryCascaderDTO {

    private Integer id;

    private String name;

    private List<Map<String, Object>> children;
}
