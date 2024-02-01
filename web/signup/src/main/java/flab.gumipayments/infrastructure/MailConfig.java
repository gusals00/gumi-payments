package flab.gumipayments.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String id;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.properties.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.stmp.auth}")
    private String auth;

    @Value("${spring.mail.properties.stmp.starttls.enable}")
    private String starttlsEnable;

    @Value("${spring.mail.properties.debug}")
    private String debug;

    @Value("${spring.mail.properties.stmp.ssl.trust}")
    private String sslTrust;

    @Value("${spring.mail.properties.stmp.ssl.enable}")
    private String sslEnable;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.starttls.enable", starttlsEnable);
        properties.setProperty("mail.debug", debug);
        properties.setProperty("mail.smtp.ssl.trust",sslTrust);
        properties.setProperty("mail.smtp.ssl.enable",sslEnable);
        return properties;
    }
}