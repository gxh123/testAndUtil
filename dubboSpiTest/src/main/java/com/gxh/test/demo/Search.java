package com.gxh.test.demo;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Search {

    void doSearch();

}
