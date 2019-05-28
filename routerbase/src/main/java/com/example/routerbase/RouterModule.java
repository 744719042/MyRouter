package com.example.routerbase;

import java.util.Map;

public interface RouterModule {
    void loadConfigs(Map<String, RouterConfig> map);
}
