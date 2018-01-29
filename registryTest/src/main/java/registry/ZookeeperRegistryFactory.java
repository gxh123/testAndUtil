package registry;

import common.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZookeeperRegistryFactory implements RegistryFactory {
    //<address, registry>
    private Map<String,Registry> registries = new ConcurrentHashMap<>();
    private Lock lock = new ReentrantLock();

    @Override
    public Registry getRegistry(URL url) {
        String key = url.toServiceString();
        lock.lock();
        try{
            Registry registry = registries.get(key);
            if(registry == null){
                registry = createRegistry(url);
            }
            registries.put(key, registry);
            return registry;
        }finally {
            lock.unlock();
        }
    }

    private Registry createRegistry(URL url) {
        return new ZookeeperRegistry(url);
    }
}
