package xit.gateway.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${torch.gateway.deacon.url}")
    private String domain;

    public Object get(String url, Map<String, String> headerMap, Class<?> resp) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<?> result = restTemplate.exchange(domain + url, HttpMethod.GET, httpEntity, resp);
        return result.getBody();
    }

    public ResultInfo post(String url, HttpHeaders headerMap, Map<String, Object> map) {
        HttpEntity httpEntity = new HttpEntity(map, headerMap);
        ResultInfo result = restTemplate.postForObject(domain + url, httpEntity, ResultInfo.class);
        return result;
    }

    public ResultInfo put(String url, HttpHeaders headerMap, Map<String, Object> map) {

        HttpEntity httpEntity = new HttpEntity(map, headerMap);
        ResponseEntity<ResultInfo> result = restTemplate.exchange(domain + url,HttpMethod.PUT, httpEntity, ResultInfo.class);
        return result.getBody();
    }

}
