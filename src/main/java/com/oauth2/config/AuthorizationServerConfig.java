package com.oauth2.config;

import com.oauth2.common.Authorities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * 配置OAuth2验证服务器
 * Created by fuhongxing on 2019/1/4.
 */
@Slf4j
@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
@EnableConfigurationProperties({Oauth2Properties.class})
public  class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    private static final String ENV_OAUTH = "authentication.oauth.";

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnection;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        //这个是基于JDBC的实现，令牌（Access Token）会保存到数据库
//        return new JdbcTokenStore(dataSource);
        return new RedisTokenStore(redisConnection);
    }

    /**
     *  声明 ClientDetails实现
     * @return
     */
//    @Bean
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()");
//        oauthServer.checkTokenAccess("permitAll()");
//        oauthServer.allowFormAuthenticationForClients();
    }

    /**
     * 认证方式
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

        // 配置TokenServices参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(false);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        // 30天
//        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30));
//        endpoints.tokenServices(tokenServices);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetails());
        clients
                .inMemory() // 使用in-memory存储
                .withClient(oauth2Properties.getClientid()) //client_id用来标识客户的Id
                .scopes("read", "write") //允许授权范围
                .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name()) //客户端可以使用的权限
                .authorizedGrantTypes("password", "refresh_token", "authorization_code") //允许授权类型
                .secret(passwordEncoder.encode(oauth2Properties.getSecret())) //secret客户端安全码(加密)
                .accessTokenValiditySeconds(oauth2Properties.getExpiration());
    }

//    @Override
//    public void setEnvironment(Environment environment) {
//        //获取到前缀是"authentication.oauth." 的属性列表值
//        oauth2Properties = Binder.get(environment).bind(ENV_OAUTH, Bindable.of(Oauth2Properties.class)).orElse(null);
//        log.info("加载环境");
//    }

}
