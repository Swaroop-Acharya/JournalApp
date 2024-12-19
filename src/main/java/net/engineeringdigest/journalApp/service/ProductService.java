package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ProductService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private BatchProcessingService batchProcessingService;

    Queue<Product> queue = new ConcurrentLinkedQueue<>();
    public void addProduct(Product product){
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set("product#"+product.getId(),product.toString());
        batchProcessingService.insertIntoQueue(product);
    }





}
