package loadbalancer;

import java.util.List;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc服务负载均衡接口
 * @date 2022/6/20 10:06 下午
 */
public interface ServiceLoadBalancer<T> {

    /**
     * 根据给定的 哈希值 从服务集合中选择一个返回
     * @param services 服务集合
     * @param hashcode 哈希值
     * @return: T
    */
    T select(List<T> services, int hashcode);

}
