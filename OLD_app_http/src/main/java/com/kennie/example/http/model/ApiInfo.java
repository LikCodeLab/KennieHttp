

package com.kennie.example.http.model;

/**
 * <p>描述：post  body提交演示</p>
 */
public class ApiInfo {
    private ApiInfoBean apiInfo;
    public ApiInfoBean getApiInfo() {
        return apiInfo;
    }
    public void setApiInfo(ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }
    public class ApiInfoBean {
        private String apiName;
        private String apiKey;
        //省略get和set方法

        public String getApiName() {
            return apiName;
        }

        public ApiInfoBean setApiName(String apiName) {
            this.apiName = apiName;
            return this;
        }

        public String getApiKey() {
            return apiKey;
        }

        public ApiInfoBean setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }
    }
}
