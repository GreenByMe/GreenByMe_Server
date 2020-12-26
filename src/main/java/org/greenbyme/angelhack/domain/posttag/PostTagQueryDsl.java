package org.greenbyme.angelhack.domain.posttag;

import java.util.List;

public interface PostTagQueryDsl {

    void deleteByPostIds(List<Long> postIds);
}
