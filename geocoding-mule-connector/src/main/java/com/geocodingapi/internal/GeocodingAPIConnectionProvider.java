package com.geocodingapi.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class GeocodingAPIConnectionProvider implements PoolingConnectionProvider<GeocodingAPIConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(GeocodingAPIConnectionProvider.class);

 /**
  * A parameter that is always required to be configured.
  */


 /**
  * A parameter that is not required to be configured by the user.
  */
     @Parameter
     @DisplayName("Base Path")
     private String baseUri;

     @Parameter
     @DisplayName("Path")
     private String path;

     @Parameter
     @Placement(tab = "Advanced")
     @Optional(defaultValue = "5000")
     int connectionTimeout;

     @Inject
     private HttpService httpService;

     private HttpClient httpClient;

    @RefName
    private String configName;

  @Override
  public GeocodingAPIConnection connect() throws ConnectionException {
      httpClient = httpService.getClientFactory().create(new HttpClientConfiguration.Builder()
              .setName(configName)
              .build());
      httpClient.start();
      return new GeocodingAPIConnection(httpClient, baseUri, path);
  }

  @Override
  public void disconnect(GeocodingAPIConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(GeocodingAPIConnection connection) {
    return ConnectionValidationResult.success();
  }
}
