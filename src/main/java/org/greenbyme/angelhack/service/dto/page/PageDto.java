package org.greenbyme.angelhack.service.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageDto<T> {

    private int totalPage;
    private int pageNumber;
    private List<T> contents = new ArrayList<>();

    public PageDto(Page<T> dto) {
        this.totalPage = dto.getTotalPages();
        this.pageNumber = dto.getNumber();
        this.contents = dto.getContent();
    }
}
