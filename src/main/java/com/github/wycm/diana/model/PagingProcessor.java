package com.github.wycm.diana.model;

import java.util.List;

/**
 * Created by wycm on 2019-11-15.
 * 分页处理器
 */
public interface PagingProcessor {

    List<DianaRequest> process(DianaPage dianaPage);

}
