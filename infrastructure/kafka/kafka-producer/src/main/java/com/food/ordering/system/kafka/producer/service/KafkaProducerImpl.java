package com.food.ordering.system.kafka.producer.service;

import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K,V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V value, ListenableFutureCallback<SendResult<K, V>> callback) {
        try {
            ListenableFuture<SendResult<K, V>> send = kafkaTemplate.send(topicName, key, value);
            send.addCallback(callback);
        }catch (KafkaException e){
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void close(){
        if(kafkaTemplate != null){
            kafkaTemplate.destroy();
        }
    }
}
