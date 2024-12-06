package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
@Slf4j
@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String API_KEY;

    private final String API_QUERY="https://api.weatherstack.com/current?access_key=API_KEY&query=LOCATION";

    @Autowired
    private RestTemplate restTemplate;

    public String getWeather(String city){
        final String FINAL_API=API_QUERY.replace("API_KEY",API_KEY).replace("LOCATION",city);

        HttpHeaders headers = new HttpHeaders();
        headers.set("key","value");
        log.warn("Hellow");
        User user =  User.builder().userName("Swaroop").password("1234").build();
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> response = restTemplate.exchange(FINAL_API, HttpMethod.GET, entity, String.class);
        return restTemplate.getForObject(FINAL_API, String.class);
    }
}
