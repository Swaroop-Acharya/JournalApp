package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class BatchProcessingService {

    // A thread-safe queue to hold the products
    Queue<Product> queue = new ConcurrentLinkedQueue<>();
    List<Product> batch = new ArrayList<>();

    @Async
    public void insertIntoQueue(Product product) {
        // Simulate adding products asynchronously
        queue.add(product);
        System.out.println("Inserted product: " + product.getId() + ", Queue size: " + queue.size());
    }

    // Save to the database (you can implement actual DB save logic here)
    public void saveToDatabase(List<Product> batch) {
        System.out.println("Saving batch of size " + batch.size() + " to DB: " + batch);
    }

    // Scheduled task to process the queue every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void bulkInsertScheduler() {

        // Log the initial size of the queue
        System.out.println("Queue size at start: " + queue.size());

        // Poll items from the queue until batch size reaches 10
        while (!queue.isEmpty() && batch.size() < 5) {
            Product product = queue.poll();
            batch.add(product);
            System.out.println("Polled product: " + product.getId() + ", Queue size: " + queue.size());

        }

        // If the batch size reaches 10, save to the database
        if (batch.size() == 5) {
            saveToDatabase(batch);
            batch.clear();
        } else {
            System.out.println("Batch size is less than 10, not inserting to DB. Current batch size: " + batch.size());
        }
    }
}


