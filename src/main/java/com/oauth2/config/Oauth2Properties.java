package com.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wxpay pay properties
 *
 * @author Alan
 */
@Data
@ConfigurationProperties(prefix = "authentication.oauth")
public class Oauth2Properties {
  /**
   * 客户端id
   */
  private String clientid;

  /**
   * secret
   */
  private String secret;

  /**
   * 有效时间
   */
  private int expiration;

}
