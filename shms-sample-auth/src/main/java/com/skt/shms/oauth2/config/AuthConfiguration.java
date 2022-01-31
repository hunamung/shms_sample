package com.skt.shms.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAuthorizationServer
@SpringBootApplication
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ClientDetailsService clientDetailsService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResourceServerProperties resourceServerProperties;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /*
    @Value("${security.oauth2.client.client-id}")
    String client_id;
    */
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 인증 과정 endpoint에 대한 설정을 해줍니다.
        super.configure(endpoints);
        
        //refresh token 발급을 위해서는 UserDetailsService(AuthenticationManager authenticate()에서 사용)필요
        endpoints
        .userDetailsService(userDetailsService)
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtAccessTokenConverter()).authenticationManager(authenticationManager);
        
        //endpoints.accessTokenConverter(jwtAccessTokenConverter()).authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // oauth_client_details 테이블에 등록된 사용자로 조회합니다.
        // schema.sql 참고 -> insert oauth_client_details
        clients.withClientDetails(clientDetailsService);        
    }

    @Bean
    @Primary
    public JdbcClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
        // Jdbc(H2 데이터베이스)를 이용한 Oauth client 정보등록을 위한 설정입니다.
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // JWT key-value 방식을 사용하기 위한 설정입니다.
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());

        return accessTokenConverter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
