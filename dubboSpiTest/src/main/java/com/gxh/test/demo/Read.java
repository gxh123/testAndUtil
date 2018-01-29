package com.gxh.test.demo;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Read {

    void doRead();

}
