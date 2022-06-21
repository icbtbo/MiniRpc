package com.mini.rpc.serialization;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 序列化类型 枚举类
 * @date 2022/6/18 9:39 下午
 */
public enum SerializationTypeEnum {

    /** hessian 序列化类 */
    HESSIAN(1),
    /** json 序列化类 */
    JSON(2);

    private final int type;


    SerializationTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static SerializationTypeEnum findByType(int type){
        for (SerializationTypeEnum serializationType : SerializationTypeEnum.values()) {
            if(serializationType.getType() == type){
                return serializationType;
            }
        }
        // 找不到则默认用 Hessian
        return HESSIAN;
    }
}
