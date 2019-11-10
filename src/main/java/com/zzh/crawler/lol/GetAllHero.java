package com.zzh.crawler.lol;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SuppressWarnings("static-access")
public  class GetAllHero {
    private static PoolingHttpClientConnectionManager cm;
	public GetAllHero() {
        this.cm = new PoolingHttpClientConnectionManager();
//        设置最大连接数
        this.cm.setMaxTotal(100);
//        设置每个主机最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    public static void main(String[] args) throws Exception {
    	//      开启爬虫
      getAllHeros();

    }
    public static HashSet<String> getAllHeros() throws Exception {
        //解析地址
        String url = "https://game.gtimg.cn/images/lol/act/img/js/heroList/hero_list.js";   //首页
        String html = doGetHtml(url);
        HashSet<String> allHeroSet = parse(html);
		return allHeroSet;
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return 页面数据
     */
    public static String doGetHtml(String url) {
//    获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
//     创建httpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 UBrowser/4.0.3214.0 Safari/537.36");
//        设置请求信息
        httpGet.setConfig(getConfig());
//        使用httpClient发起请求,获取响应
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
//            解析响应，返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
//                判断响应体Entity是否不为空,如果不为空，就可以使用Entityutils
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        解析响应，返回结果
        return "";
    }

    @SuppressWarnings("unchecked")
    private static HashSet<String> parse(String json) throws Exception {
    	HashSet<String> allHeroSet = new HashSet<String>();
    	HashMap<String, Object> herosMap = new HashMap<String, Object>();
    	ObjectMapper mapper = new ObjectMapper();
    	herosMap = (HashMap<String, Object>) mapper.readValue(json, Map.class);
    	List<Map<String, Object>> herosList = (List<Map<String, Object>>) herosMap.get("hero");
    	for (Map<String, Object> map : herosList) {
    		allHeroSet.add((map.get("name").toString().trim()+map.get("title").toString().trim()));
		}
		return allHeroSet;
    }

    //    设置请求信息
    private static RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)  //数据传输的最长时间
                .build();
        return config;
    }
}
