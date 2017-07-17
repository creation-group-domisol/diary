package com.group.creation.domisol.diary;

import android.support.v4.app.Fragment;

/**
 * Created by cob on 2017. 7. 12..
 */

public abstract class Section extends Fragment {
    private final int maxPage;

    protected Section(int maxPage) {
        this.maxPage = maxPage;
    }

    public abstract boolean loadPage(int idx);

    public boolean goToFirstPage() {
        return loadPage(0);

    }

    public boolean goToLastPage() {
        return loadPage(maxPage);
    }
}
