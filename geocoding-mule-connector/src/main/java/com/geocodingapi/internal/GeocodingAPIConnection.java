package com.geocodingapi.internal;


import org.mule.runtime.http.api.client.HttpClient;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class GeocodingAPIConnection {

  private String baseUri;
  private HttpClient httpClient;
  private  String path;


  public GeocodingAPIConnection(HttpClient httpClient, String baseUri, String path) {
    this.baseUri = baseUri;
    this.httpClient = httpClient;
    this.path = path;
  }

  public String getBaseUri() {
    return baseUri;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public String getPath() {
    return path;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
