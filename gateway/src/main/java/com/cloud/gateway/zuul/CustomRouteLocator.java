package com.cloud.gateway.zuul;

import com.cloud.gateway.dao.model.ZuulRouteConfig;
import com.cloud.gateway.dao.ZuulRouteConfigMapper;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 7le
 */
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);

    @Autowired
    private ZuulRouteConfigMapper zuulRouteConfigMapper;

    private ZuulProperties properties;

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath:{}", servletPath);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        List<ZuulRouteConfig> results = zuulRouteConfigMapper.selectByEnabled(1);
        for (ZuulRouteConfig result : results) {
            if (Strings.isBlank(result.getPath())){
                continue;
            }
            if (Strings.isBlank(result.getServiceId()) && Strings.isBlank(result.getUrl())) {
                continue;
            }
            ZuulRoute zuulRoute = new ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                logger.error("=============load zuul route info from db with error==============", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
