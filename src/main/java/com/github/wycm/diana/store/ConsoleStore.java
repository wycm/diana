package com.github.wycm.diana.store;

import com.github.wycm.diana.model.DianaPage;

/**
 * Output extraction results to the console
 */
public class ConsoleStore implements Store<DianaPage>{
    @Override
    public void store(DianaPage page) {
        System.out.println("Store result:" + page.getExtractResult().toString());
    }
}
