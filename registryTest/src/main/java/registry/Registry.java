package registry;

import common.URL;

public interface Registry {

    void register(URL url);

    void unregister(URL url);
}
