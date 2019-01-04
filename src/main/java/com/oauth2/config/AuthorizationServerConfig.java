package com.oauth2.config;

import com.oauth2.common.Authorities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置OAuth2验证服务器
 * Created by fuhongxing on 2019/1/4.
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties({Oauth2Properties.class})
public  class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    private static final String ENV_OAUTH = "authentication.oauth.";

    @Autowired
    private Oauth2Properties oauth2Properties;

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnection;

    @Bean
    public TokenStore tokenStore() {
        //这个是基于JDBC的实现，令牌（Access Token）会保存到数据库
//        return new JdbcTokenStore(dataSource);
        return new RedisTokenStore(redisConnection);
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
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory() // 使用in-memory存储
                .withClient(oauth2Properties.getClientid()) //client_id用来标识客户的Id
                .scopes("read", "write") //允许授权范围
                .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name()) //客户端可以使用的权限
                .authorizedGrantTypes("password", "refresh_token", "authorization_code") //允许授权类型
                .secret(oauth2Properties.getSecret()) //secret客户端安全码
                .accessTokenValiditySeconds(oauth2Properties.getExpiration());
    }

//    @Override
//    public void setEnvironment(Environment environment) {
//        //获取到前缀是"authentication.oauth." 的属性列表值
//        oauth2Properties = Binder.get(environment).bind(ENV_OAUTH, Bindable.of(Oauth2Properties.class)).orElse(null);
//        log.info("加载环境");
//    }

}
