package com.mini.rpc.serialization;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 序列化工厂，用以根据类型参数产生具体的序列化类
 * @date 2022/6/18 9:37 下午
 */
public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(byte serializationType){

        SerializationTypeEnum serializationTypeEnum = SerializationTypeEnum.findByType(serializationType);
        switch (serializationTypeEnum){
            case HESSIAN:
                return new HessianSerialization();
            case JSON:
                return new JsonSerialization();
            default:
                throw new IllegalArgumentException("serialization type [" + serializationType + "] is illegal!");
        }
    }

}
