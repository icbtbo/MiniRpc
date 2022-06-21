package com.mini.rpc.serialization;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 利用 hessian 进行序列化和反序列化的序列化类
 * @date 2022/6/18 8:46 下午
 */
public class HessianSerialization implements RpcSerialization {

    @Override
    public <T> byte[] serialize(T obj) {
        if(null == obj){
            throw new RuntimeException("can not serialize null object");
        }

        byte[] results;

        // 使用 hessian 进行序列化操作
        HessianSerializerOutput output;
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){

            output = new HessianSerializerOutput(os);
            output.writeObject(obj);
            output.flush();
            results = os.toByteArray();

        }catch (IOException e) {
            throw new SerializationException("Error occurred when serialize [ " + obj + "]");
        }

        return results;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        if(null == data){
            throw new RuntimeException("can not deserialize null object");
        }

        T result;

        // 使用 hessian 进行反序列化操作
        try(ByteArrayInputStream is = new ByteArrayInputStream(data)){

            HessianSerializerInput input = new HessianSerializerInput(is);
            result = (T) input.readObject(clazz);

        } catch (Exception e) {
            throw new SerializationException("Error occurred when deserialize byte array to [ " + clazz + "]");
        }

        return result;
    }
}
