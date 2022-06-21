package com.mini.rpc.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 利用 json 进行序列化和反序列化的序列化类
 * @date 2022/6/18 9:32 下午
 */
public class JsonSerialization implements RpcSerialization {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = generateMapper(JsonInclude.Include.ALWAYS);
    }

    /** 初始化 objectMapper */
    private static ObjectMapper generateMapper(JsonInclude.Include include) {
        ObjectMapper customMapper = new ObjectMapper();

        //通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        //Include.Include.ALWAYS 默认
        //Include.NON_DEFAULT 属性为默认值不序列化
        //Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
        //Include.NON_NULL 属性为NULL 不序列化
        customMapper.setSerializationInclusion(include);
        // 常用,json中含pojo不存在属性时是否失败报错,默认true
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 用整数反序列化为枚举是否报错
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        // 全局设置@JsonFormat的格式pattern
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return customMapper;
    }

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return obj instanceof String ? ((String) obj).getBytes() : MAPPER.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return MAPPER.readValue(Arrays.toString(data), clz);
    }
}
