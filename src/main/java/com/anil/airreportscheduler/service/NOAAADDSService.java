package com.anil.airreportscheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Service
public class NOAAADDSService {
    private final RestTemplate restTemplate;
    private final String url;

    public NOAAADDSService(RestTemplate restTemplate, @Value("${aviation_url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public <T> ResponseEntity<T> getAircraftReportFromAddsServer(Class<T> responseType, final String reportType, final MultiValueMap<String, String> requestParam) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI uri = buildUri(requestParam);

        System.out.println(uri);

        ResponseEntity<T> response = null;
        try {
            response = restTemplate
                    .exchange(
                            uri,
                            HttpMethod.GET,
                            entity,
                            responseType
                    );

            log.info(Objects.requireNonNull(response.getBody()).toString());

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Successfully get the report type of {} with status code {}", reportType, response.getStatusCode());
            } else {
                log.error("failed get the report type of {} with status code {}", reportType, response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return response;
    }

    private URI buildUri(final MultiValueMap<String, String> requestParam) {
        return UriComponentsBuilder.fromUri(URI.create(url))
                .queryParams(requestParam)
                .build().toUri();
    }

}
