//package com.megane.usermanager;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaTemplate;
//
//@Configuration
//public class KafkaConfig {
//
//    @Bean
//    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        // Cấu hình KafkaTemplate ở đây
//    }
//
//    // Các bean KafkaProducer và KafkaConsumer khác cũng có thể thêm điều kiện tương tự.
//}