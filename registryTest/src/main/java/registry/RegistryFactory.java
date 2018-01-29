package registry;

import common.URL;

public interface RegistryFactory {

    Registry getRegistry(URL url);

}
