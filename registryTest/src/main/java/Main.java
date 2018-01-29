import common.URL;
import registry.Registry;
import registry.RegistryFactory;
import registry.ZookeeperRegistryFactory;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String registryUrlStr = "127.0.0.1:2181";
        URL registryUrl = URL.valueOf(registryUrlStr);
        Registry registry = new ZookeeperRegistryFactory().getRegistry(registryUrl);
        String serviceUrlStr = "127.0.0.1:22880/myService?a=1&b=2";
        URL serviceUrl = URL.valueOf(serviceUrlStr);
        registry.register(serviceUrl);
        while(true){
            System.out.println("111");
            Thread.sleep(2000);
        }
    }


}
