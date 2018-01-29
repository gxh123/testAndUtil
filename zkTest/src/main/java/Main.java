import org.I0Itec.zkclient.ZkClient;

public class Main {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181",300);
        zkClient.createPersistent("/myNode2","123");
        System.out.println((String) zkClient.readData("/myNode2"));
    }
}
