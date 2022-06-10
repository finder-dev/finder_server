package com.cmc.finder.infra.email;

public interface EmailService {
    String sendSimpleMessage(String to) throws Exception;
}
