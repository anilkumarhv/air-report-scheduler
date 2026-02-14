package com.anil.airreportscheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class NOAAADDSService {
    private final RestTemplate restTemplate;
    private final String url;

    public NOAAADDSService(RestTemplate restTemplate, @Value("${aviation_url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Retryable(
        retryFor = {ResourceAccessException.class, HttpServerErrorException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    public <T> ResponseEntity<T> getAircraftReportFromAddsServer(Class<T> responseType, final String reportType, final MultiValueMap<String, String> requestParam) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI uri = buildUri(requestParam);
        log.debug("Requesting NOAA API: {}", uri);

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                responseType
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Successfully retrieved {} report with status {}", reportType, response.getStatusCode());
            } else {
                log.warn("Unexpected status code {} for {} report", response.getStatusCode(), reportType);
            }

            return response;

        } catch (HttpClientErrorException e) {
            log.error("Client error retrieving {} report: {} - {}", reportType, e.getStatusCode(), e.getMessage());
            throw new RuntimeException("Failed to retrieve " + reportType + " report: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            log.error("Server error retrieving {} report: {} - {}", reportType, e.getStatusCode(), e.getMessage());
            throw new RuntimeException("NOAA server error for " + reportType + " report", e);
        } catch (ResourceAccessException e) {
            log.error("Network error retrieving {} report: {}", reportType, e.getMessage());
            throw new RuntimeException("Network error accessing NOAA API", e);
        } catch (RestClientException e) {
            log.error("REST client error retrieving {} report: {}", reportType, e.getMessage());
            throw new RuntimeException("Error communicating with NOAA API", e);
        }
    }

    private URI buildUri(final MultiValueMap<String, String> requestParam) {
        return UriComponentsBuilder.fromUri(URI.create(url))
                .queryParams(requestParam)
                .build().toUri();
    }

}
