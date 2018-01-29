package registry;

import common.Constants;
import common.URL;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

public class ZookeeperRegistry implements Registry {

    private URL url;
    private ZkClient zkClient;

    public ZookeeperRegistry(URL url) {
        this.url = url;
        this.zkClient = new ZkClient(url.getAddress());
    }

    @Override
    public void register(URL url) {
        create(toZKPath(url), true);
    }

    @Override
    public void unregister(URL url) {

    }

    private String toCategoryPath(URL url) {
        return Constants.PATH_SEPARATOR + url.getPath() + Constants.PATH_SEPARATOR + "providers";
    }

    private String toZKPath(URL url) {
        return toCategoryPath(url) + Constants.PATH_SEPARATOR + URL.encode(url.toFullString());
    }

    private void create(String path, boolean ephemeral) {
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path);
        } else {
            createPersistent(path);
        }
    }

    private void createPersistent(String path) {
        try {
            zkClient.createPersistent(path, true);
        } catch (ZkNodeExistsException e) {
        }
    }

    private void createEphemeral(String path) {
        try {
            zkClient.createEphemeral(path);
        } catch (ZkNodeExistsException e) {
        }
    }
}
