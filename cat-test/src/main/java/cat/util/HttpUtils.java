package cat.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Slf4j
public class HttpUtils {
    private static ThreadLocal<Map<String,String>> httpHeader = new ThreadLocal<Map<String,String>>();
    //连接超时时间
    public static final String CONNECT_TIMEOUT= "connect_timeout";
    //socket超时时间
    public static final String SOCKET_TIMEOUT= "socket_timeout";
    public static final Integer DEFAULT_CONNECT_TIMEOUT = 600000;
    public static final Integer DEFAULT_SOCKET_TIMEOUT = 600000;
    private static ThreadLocal<Map<String,Object>> httpClientConfig = new ThreadLocal<Map<String,Object>>();
    private static void handlerHeader(HttpRequestBase requestBase) {
        if(httpHeader!=null&&httpHeader.get()!=null){
            Map<String,String> map = httpHeader.get();
            for(String key:map.keySet()){
                requestBase.addHeader(key,map.get(key));
            }
        }
    }

    /**
     *  HTTP Post 获取内容
     * @param url  请求的url地址
     * @param params 请求的参数
     * @return
     */
    public static String doPost(String url,String params,String contentType,String encode) throws Exception {
        if(StringUtils.isBlank(url)){
            return null;
        }
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            handlerHeader(httpPost);
            if(StringUtils.isNotBlank(params)){
                StringEntity entity = new StringEntity(params,encode);
                entity.setContentEncoding(encode);

                if(StringUtils.isNotBlank(contentType)){
                    entity.setContentType(contentType);
                }
                httpPost.setEntity(entity);
            }
            httpClient = buildHttpClient();
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            response.getEntity().getContentLength();
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, encode);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            log.error("http error",e);
            throw e;
        }finally{
            if(response!=null){
                response.close();
            }
            if(httpClient!=null){
                httpClient.close();
            }
        }
    }
    public static void setHeader(Map<String,String> header){
        if(header!=null) {
            httpHeader.set(header);
        }
    }

    public static void setConfig(Map<String,Object> config){
        if(config!=null) {
            httpClientConfig.set(config);
        }
    }

    public static CloseableHttpClient buildHttpClient(){
        Map<String,Object> configSetting = new HashMap<String,Object>();
        if(httpClientConfig!=null && null!=httpClientConfig.get()){
            configSetting = httpClientConfig.get();
        }
        RequestConfig.Builder builder = RequestConfig.custom();
        Integer connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        if(configSetting.get(CONNECT_TIMEOUT)!=null){
            try{
                connectTimeout = Integer.valueOf(configSetting.get(CONNECT_TIMEOUT).toString());
            }catch(Exception e){
                log.error("class covert error!",e);
                connectTimeout = DEFAULT_CONNECT_TIMEOUT;
            }
        }
        builder.setConnectTimeout(connectTimeout);

        Integer socketTimeout = DEFAULT_SOCKET_TIMEOUT;
        if(configSetting.get(SOCKET_TIMEOUT)!=null){
            try{
                socketTimeout = Integer.valueOf(configSetting.get(SOCKET_TIMEOUT).toString());
            }catch(Exception e){
                log.error("class covert error!",e);
                socketTimeout = DEFAULT_SOCKET_TIMEOUT;
            }
        }
        builder.setSocketTimeout(socketTimeout);
        RequestConfig config = builder.build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
}
