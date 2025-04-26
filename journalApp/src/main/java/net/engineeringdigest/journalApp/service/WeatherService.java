package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")//if you stored your api key in application.properties
    private  String apiKey ;
   

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache ;

    @Autowired
    private RestTemplate restTemplate ;//processes https requests & give response

    @Autowired
    private  RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather:" + city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<CITY>", city).replace("<api_key>", apiKey);
            System.out.println(AppCache.keys.WEATHER_API.toString());
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);//using WeatherResponse we are desialising whether data from json to corresponding java objects(pojo)
            WeatherResponse body = response.getBody();
            
            if(body != null){
                redisService.set("weather:"+city,body,300l);
            }
            return body;
        }

    }


    /* this method is just to show how can we do a post request
   public WeatherResponse getWeather(String city){
       String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
       HttpHeaders httpHeaders = new HttpHeaders() ;
       httpHeaders.set("key","value");
       User user = User.builder().username("vipul").password("vipul").build();
       HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeaders );
       ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST,httpEntity, WeatherResponse.class);//using WeatherResponse we are desialising whether data from json to corresponding java objects(pojo)
       WeatherResponse body = response.getBody();

       return body;
   }
   */

}
