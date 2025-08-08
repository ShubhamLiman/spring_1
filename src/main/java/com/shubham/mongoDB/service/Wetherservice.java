package com.shubham.mongoDB.service;


import com.shubham.mongoDB.Entities.Apiresponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Wetherservice {

    @Autowired
    private RedisService redisService;

    @Value("${weather.api.key}")
    private String key;
//    private static final String api = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public Apiresponce getApi(String city) {
        Apiresponce redisresponce = redisService.get(city,Apiresponce.class);
        if(redisresponce != null){
            return redisresponce;
        }else{
            String urlTemplate = appCache.getAppcache().get("weather_api");
            if (urlTemplate == null) {
                throw new IllegalStateException("Missing 'weather_api' in AppCache. Check MongoDB data.");
            }
            String finalApi = urlTemplate.replace("<city>",city).replace("<apikey>",key);
            ResponseEntity<Apiresponce> responce = restTemplate.exchange(finalApi, HttpMethod.GET,null, Apiresponce.class);
            Apiresponce body = responce.getBody();
            if(body != null){
                redisService.set(city,body,60);
            }
            return body;
        }

    }

    public void checkDb(){
        appCache.checkDb();
    }
}
