package org.greenbyme.angelhack.service.dto.page;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class PageDto<T> {

    private int totalPage;
    private int pageNumber;
    private List<T> contents = new ArrayList<>();
}
