package xit.gateway.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xit.gateway.pojo.ResultInfo;

import java.util.Map;

@Service
public class HTTPService {
    @Autowired
    private RestTemplate restTemplate;

    public Object get(String url, Map<String, String> headerMap, Class<?> resp) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, resp);
        return result.getBody();
    }

    public ResultInfo post(String url, HttpHeaders headerMap, Map<String, Object> map) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
//            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
//        }
        HttpEntity httpEntity = new HttpEntity(map, headerMap);
        ResultInfo result = restTemplate.postForObject(url, httpEntity, ResultInfo.class);
        return result;
    }

}
