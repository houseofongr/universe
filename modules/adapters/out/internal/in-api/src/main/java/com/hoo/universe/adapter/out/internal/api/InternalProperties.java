package com.hoo.universe.adapter.out.internal.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "internal")
public class InternalProperties {

    private User user;
    private File file;

    @Data
    public static class User {
        private String baseUrl;
        private String getUserInfoUrl;
    }

    @Data
    public static class File {
        private String baseUrl;
        private String uploadFileUrl;
        private String getFileInfoUrl;
        private String getFileInfoListUrl;
    }

}
