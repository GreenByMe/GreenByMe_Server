package org.greenbyme.angelhack.service.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.greenbyme.angelhack.domain.Category.Category;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnumWithSingleValueResDto {

    private TreeMap<?, Integer> enums;

    public EnumWithSingleValueResDto(TreeMap<?, Integer> enums){
        this.enums = enums;
    }
}
