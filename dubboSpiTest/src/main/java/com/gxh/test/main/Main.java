package com.gxh.test.main;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.gxh.test.demo.Search;

public class Main {

    public static void main(String[] args) {
        Search search = ExtensionLoader.getExtensionLoader(Search.class).getExtension("databaseSearch");
        search.doSearch();
        Search search2 = ExtensionLoader.getExtensionLoader(Search.class).getExtension("fileSearch");
        search2.doSearch();
        //Search search2 = ExtensionLoader.getExtensionLoader(Search.class).getAdaptiveExtension();
        //search2.doSearch();
    }


}
