package com.geocodingapi.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import static org.mule.runtime.http.api.HttpHeaders.Names.CONTENT_TYPE;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class GeocodingAPIOperations {



  @DisplayName("Geocoding")
  @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
  public InputStream getGeoCoding(@Config GeocodingAPIConfiguration configuration, @Connection GeocodingAPIConnection connection, String address, String key) throws Exception {
    String uri= connection.getBaseUri().concat(connection.getPath());
    HttpClient client = connection.getHttpClient();
    HttpRequestBuilder builder = HttpRequest.builder();
    builder.uri(uri)
            .addQueryParam("address", address)
            .addQueryParam("key", key)
            .method(HttpConstants.Method.GET)
            .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    CompletableFuture<HttpResponse> response =  client.sendAsync(builder.build(),300, false, null);
    InputStream inputStream = response.get().getEntity().getContent();
    return inputStream;
  }

  @DisplayName("Reverse Geocoding")
  @MediaType(value = MediaType.APPLICATION_JSON, strict = false)
  public InputStream getReverseGeoCoding(@Config GeocodingAPIConfiguration configuration, @Connection GeocodingAPIConnection connection, String latLang, String key) throws Exception {
    String uri= connection.getBaseUri().concat(connection.getPath());
    HttpClient client = connection.getHttpClient();
    HttpRequestBuilder builder = HttpRequest.builder();
    builder.uri(uri)
            .addQueryParam("latlng", latLang)
            .addQueryParam("key", key)
            .method(HttpConstants.Method.GET)
            .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    CompletableFuture<HttpResponse> response =  client.sendAsync(builder.build(),300, false, null);
    InputStream inputStream = response.get().getEntity().getContent();
    return inputStream;
  }


}
