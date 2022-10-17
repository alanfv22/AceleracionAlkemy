package com.alkemy.ong.domain.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
@Getter
@Setter
public class CategoryResponsePage {
    Page<CategoryResponse> response;
    String nextPage;
    String previousPage;
}
