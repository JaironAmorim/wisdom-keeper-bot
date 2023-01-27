package com.jaironamorim.wisdomkeeperbot;

import com.jaironamorim.wisdomkeeperbot.service.Gpt3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WisdomKeeperBotApplication{

    public static void main(String[] args) {SpringApplication.run(WisdomKeeperBotApplication.class, args);}

}
