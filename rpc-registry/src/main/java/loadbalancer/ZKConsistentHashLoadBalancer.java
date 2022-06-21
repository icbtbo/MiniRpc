package loadbalancer;

import com.mini.rpc.core.ServiceMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 使用 Zookeeper 作为注册中心时，选择服务节点的一致性hash负载均衡算法
 * @date 2022/6/20 10:00 下午
 */
@Slf4j
public class ZKConsistentHashLoadBalancer implements ServiceLoadBalancer<ServiceInstance<ServiceMeta>> {
    /** 一致性hash环中每个节点的虚拟节点数 */
    private final static int VIRTUAL_NODE_SIZE = 10;
    /** 用于创建虚拟节点名的分隔符 */
    private final static String VIRTUAL_NODE_SPLIT = "#";

    @Override
    public ServiceInstance<ServiceMeta> select(List<ServiceInstance<ServiceMeta>> servers, int hashCode) {
        if(null == servers || servers.size() == 0){
            log.error("There is no services registered in zookeeper!");
        }
        TreeMap<Integer, ServiceInstance<ServiceMeta>> ring = makeConsistentHashRing(servers);
        return allocateNode(ring, hashCode);
    }

    /** 在一致性hash环中分配服务节点 */
    private ServiceInstance<ServiceMeta> allocateNode(TreeMap<Integer, ServiceInstance<ServiceMeta>> ring, int hashCode) {
        // 从 ring 中找到第一个key值大于等于hashcode的entry
        Map.Entry<Integer, ServiceInstance<ServiceMeta>> ceilingEntry = ring.ceilingEntry(hashCode);
        if(null != ceilingEntry){
            return ceilingEntry.getValue();
        }
        // 若找不到，则返回第一个服务节点
        return ring.firstEntry().getValue();
    }

    /** 构造一致性hash环 */
    private TreeMap<Integer, ServiceInstance<ServiceMeta>> makeConsistentHashRing(List<ServiceInstance<ServiceMeta>> servers) {
        TreeMap<Integer, ServiceInstance<ServiceMeta>> ring = new TreeMap<>();
        for (ServiceInstance<ServiceMeta> server : servers) {
            for(int i=0; i<VIRTUAL_NODE_SIZE; i++){
                // TODO 使用 MurmurHash hash算法进行优化
                ring.put(buildServiceInstanceKey(server).hashCode(), server);
            }
        }
        return ring;
    }

    private String buildServiceInstanceKey(ServiceInstance<ServiceMeta> server) {
        ServiceMeta serviceMeta = server.getPayload();
        return String.join(":", serviceMeta.getServiceAddr(), String.valueOf(serviceMeta.getServicePort()));
    }
}
