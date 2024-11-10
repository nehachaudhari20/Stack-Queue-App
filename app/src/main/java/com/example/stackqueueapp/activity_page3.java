package com.example.stackqueueapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class activity_page3 extends AppCompatActivity {

    private TextView tvQueueStatus, queue1Display, queue2Display, queue3Display;
    private Button btnAddVehicle, btnNextCar1, btnNextCar2, btnNextCar3;
    private LinearLayout queue1ImageContainer, queue2ImageContainer, queue3ImageContainer;
    private Queue<String> queue1 = new LinkedList<>();
    private Queue<String> queue2 = new LinkedList<>();
    private Queue<String> queue3 = new LinkedList<>();
    private Queue<ImageView> queue1Images = new LinkedList<>();
    private Queue<ImageView> queue2Images = new LinkedList<>();
    private Queue<ImageView> queue3Images = new LinkedList<>();
    private Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        // Initialize UI components
        tvQueueStatus = findViewById(R.id.tvQueueStatus);
        queue1Display = findViewById(R.id.Queue1Display);
        queue2Display = findViewById(R.id.Queue2Display);
        queue3Display = findViewById(R.id.Queue3Display);
        queue1ImageContainer = findViewById(R.id.queue1ImageContainer);
        queue2ImageContainer = findViewById(R.id.queue2ImageContainer);
        queue3ImageContainer = findViewById(R.id.queue3ImageContainer);

        btnAddVehicle = findViewById(R.id.btnAddVehicle);
        btnNextCar1 = findViewById(R.id.btnNextCar1);
        btnNextCar2 = findViewById(R.id.btnNextCar2);
        btnNextCar3 = findViewById(R.id.btnNextCar3);

        random = new Random();
        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVehicleToQueue();
            }
        });
        btnNextCar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNextCar(queue1, queue1Display, queue1Images, queue1ImageContainer);
            }
        });
        btnNextCar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNextCar(queue2, queue2Display, queue2Images, queue2ImageContainer);
            }
        });
        btnNextCar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNextCar(queue3, queue3Display, queue3Images, queue3ImageContainer);
            }
        });
    }

    private void addVehicleToQueue() {
        //generate vehicle number
        String vehicleNumber = "Car " + (random.nextInt(100) + 1);

        //to determine the least traffic queue
        Queue<String> leastQueue = getLeastTrafficQueue();
        Queue<ImageView> imageQueue = getImageQueueFor(leastQueue);
        TextView displayTextView = getDisplayForQueue(leastQueue);
        LinearLayout imageContainer = getImageContainerForQueue(leastQueue);

        //add car details to the queue and display
        leastQueue.add(vehicleNumber);
        displayTextView.append(vehicleNumber + "\n");

        //add the car image for the respective queue
        ImageView carImage = createImageView(R.drawable.vehicle);
        imageContainer.addView(carImage);
        imageQueue.add(carImage);

        updateQueueStatus();
        Toast.makeText(this, "Car added to queue: " + vehicleNumber, Toast.LENGTH_SHORT).show();
    }

    private void processNextCar(Queue<String> carQueue, TextView displayView, Queue<ImageView> imageQueue, LinearLayout imageContainer) {
        if (!carQueue.isEmpty()) {
            String nextCar = carQueue.poll();
            displayView.setText(displayView.getText().toString().replace(nextCar + "\n", ""));

            if (!imageQueue.isEmpty()) {
                ImageView carImage = imageQueue.poll();
                imageContainer.removeView(carImage);
            }

            updateQueueStatus();
            Toast.makeText(this, "Next Car processed: " + nextCar, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No cars in queue!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateQueueStatus() {
        if (queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty()) {
            tvQueueStatus.setText("Queue is empty.");
        } else {
            tvQueueStatus.setText("Queue is not empty.");
        }
    }

    private Queue<String> getLeastTrafficQueue() {
        if (queue1.size() <= queue2.size() && queue1.size() <= queue3.size()) {
            return queue1;
        } else if (queue2.size() <= queue1.size() && queue2.size() <= queue3.size()) {
            return queue2;
        } else {
            return queue3;
        }
    }

    private Queue<ImageView> getImageQueueFor(Queue<String> carQueue) {
        if (carQueue == queue1) {
            return queue1Images;
        } else if (carQueue == queue2) {
            return queue2Images;
        } else {
            return queue3Images;
        }
    }

    private TextView getDisplayForQueue(Queue<String> carQueue) {
        if (carQueue == queue1) {
            return queue1Display;
        } else if (carQueue == queue2) {
            return queue2Display;
        } else {
            return queue3Display;
        }
    }

    private LinearLayout getImageContainerForQueue(Queue<String> carQueue) {
        if (carQueue == queue1) {
            return queue1ImageContainer;
        } else if (carQueue == queue2) {
            return queue2ImageContainer;
        } else {
            return queue3ImageContainer;
        }
    }

    private ImageView createImageView(int imageResource) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageResource);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        return imageView;
    }
}
