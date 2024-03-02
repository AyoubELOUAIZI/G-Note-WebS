package estm.dsic.jee.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import estm.dsic.jee.util.CorsFilter;
import jakarta.annotation.Priority;

@ApplicationPath("/api")
public class RestApplication extends Application {
    @Provider
    @Priority(value = 1)
    public static class CorsFilterFeature implements DynamicFeature {
        @Override
        public void configure(ResourceInfo resourceInfo, FeatureContext context) {
            context.register(CorsFilter.class);
        }
    }
}
