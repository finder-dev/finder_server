package com.cmc.finder.global.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JasyptConfigTest {

//    @Value("${jasypt.encryptor.password}")
//    private String encryptKey;

    @Test
    public void jasyt_test() {
        String plainText = "JfGNK9gtkH1P0bAOUWtxh1TCwfKVDNnIj4rmAFRt";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword("dudwls143"); // 암호화할 때 사용하는 키
        config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘
        config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
        config.setPoolSize("1"); // 인스턴스 pool
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
        config.setStringOutputType("base64"); //인코딩 방식
        encryptor.setConfig(config);

        String encyrptText = encryptor.encrypt(plainText);
        System.out.println(encyrptText);
        String decyrptText = encryptor.decrypt(encyrptText);
        System.out.println(decyrptText);
        assertEquals(plainText, decyrptText);

    }






}