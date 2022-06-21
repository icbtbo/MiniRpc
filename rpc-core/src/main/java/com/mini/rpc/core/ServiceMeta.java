package com.mini.rpc.core;

import lombok.Data;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc服务元数据
 * @date 2022/6/17 3:52 下午
 */
@Data
public class ServiceMeta {

    private String serviceName;
    private String serviceVersion;
    private String serviceAddr;
    private int servicePort;

}
