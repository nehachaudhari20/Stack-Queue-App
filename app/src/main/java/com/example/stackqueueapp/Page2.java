package com.example.stackqueueapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import java.util.Stack;

public class Page2 extends AppCompatActivity {

    private static final int MAZE_SIZE = 5;
    private int playerRow = 0;
    private int playerCol = 0;
    private View[][] mazeBlocks = new View[MAZE_SIZE][MAZE_SIZE];
    private boolean[][] blockedPaths = new boolean[MAZE_SIZE][MAZE_SIZE];

    private Stack<int[]> previousPositions = new Stack<>();

    // Stack to track blocks in the GridLayout
    private Stack<View> stackBlocks = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        initializeMaze();
        setupMovementButtons();
        updatePlayerPosition();
    }

    private void initializeMaze() {
        GridLayout mazeGrid = findViewById(R.id.mazeGrid);

        // ensure the last and first block is always unblocked
        blockedPaths[0][0] = false;
        blockedPaths[MAZE_SIZE - 1][MAZE_SIZE - 1] = false;


        int blockedCount = 0;
        while (blockedCount < 5) {
            int randomRow = (int) (Math.random() * MAZE_SIZE);
            int randomCol = (int) (Math.random() * MAZE_SIZE);

            if (!blockedPaths[randomRow][randomCol] && (randomRow != 0 || randomCol != 0) && (randomRow != MAZE_SIZE - 1 || randomCol != MAZE_SIZE - 1)) {
                blockedPaths[randomRow][randomCol] = true;
                blockedCount++;
            }
        }

        blockedPaths[0][4] = false;
        blockedPaths[1][3] = false;
        blockedPaths[2][2] = false;
        blockedPaths[3][1] = false;
        blockedPaths[4][0] = false;



        //initialize the maze blocks in the grid layout
        for (int row = 0; row < MAZE_SIZE; row++) {
            for (int col = 0; col < MAZE_SIZE; col++) {
                int blockId = getResources().getIdentifier("block_" + row + col, "id", getPackageName());
                mazeBlocks[row][col] = findViewById(blockId);
                mazeBlocks[row][col].setBackgroundResource(R.drawable.grid_border); // Set the grid block background

                //set colour
                if (blockedPaths[row][col]) {
                    mazeBlocks[row][col].setBackgroundColor(Color.BLACK);
                } else {
                    mazeBlocks[row][col].setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }

        // Set colors for starting and ending positions
        mazeBlocks[0][0].setBackgroundColor(Color.BLUE); // Starting position
        mazeBlocks[MAZE_SIZE - 1][MAZE_SIZE - 1].setBackgroundColor(Color.BLUE); // Ending position
    }

    private void movePlayer(int rowChange, int colChange) {
        int newRow = playerRow + rowChange;
        int newCol = playerCol + colChange;

        //Check boundaries
        if (newRow >= 0 && newRow < MAZE_SIZE && newCol >= 0 && newCol < MAZE_SIZE) {
            //Check if the path is blocked
            if (!blockedPaths[newRow][newCol]) {
                //Save the current position before moving
                previousPositions.push(new int[]{playerRow, playerCol});

                //Add a block to the stack
                View blockView = new View(this);
                blockView.setLayoutParams(new GridLayout.LayoutParams());
                blockView.setBackgroundColor(Color.RED); // Make the block red for the stack
                stackBlocks.push(blockView);

                // Move the player
                mazeBlocks[playerRow][playerCol].setBackgroundColor(Color.TRANSPARENT); // Clear previous position
                playerRow = newRow;
                playerCol = newCol;
                updatePlayerPosition(); // Update the player's position on the maze

                //TOAST MSG
                if (playerRow == 0 && playerCol == 0) {
                    Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
                }

                //TOAST MSG
                if (playerRow == MAZE_SIZE - 1 && playerCol == MAZE_SIZE - 1) {
                    Toast.makeText(this, "Congratulations, you reached the destination!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Path blocked!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Can't move outside the maze!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePlayerPosition() {
        // Reset all blocks to their initial state
        for (int row = 0; row < MAZE_SIZE; row++) {
            for (int col = 0; col < MAZE_SIZE; col++) {
                if (blockedPaths[row][col]) {
                    mazeBlocks[row][col].setBackgroundColor(Color.BLACK); // Blocked paths remain black
                } else {
                    mazeBlocks[row][col].setBackgroundResource(R.drawable.grid_border); // Clear paths reset to grid block
                }
            }
        }
        //Set the player's current position
        mazeBlocks[playerRow][playerCol].setBackgroundColor(Color.RED); // Highlight the player's position
    }

    private void resetPlayer() {
        playerRow = 0; // Reset to starting position
        playerCol = 0; // Reset to starting position
        updatePlayerPosition(); // Update maze to reflect the player's reset position
    }

    private void setupMovementButtons() {
        ImageButton btnUp = findViewById(R.id.btn_up);
        ImageButton btnDown = findViewById(R.id.btn_down);
        ImageButton btnLeft = findViewById(R.id.btn_left);
        ImageButton btnRight = findViewById(R.id.btn_right);
        ImageButton btnCenter = findViewById(R.id.btn_center);
        ImageButton btnBack = findViewById(R.id.btn_center);
        //Reference to the new Stack button
        btnUp.setOnClickListener(v -> movePlayer(-1, 0)); // Move up
        btnDown.setOnClickListener(v -> movePlayer(1, 0)); // Move down
        btnLeft.setOnClickListener(v -> movePlayer(0, -1)); // Move left
        btnRight.setOnClickListener(v -> movePlayer(0, 1)); // Move right
        btnCenter.setOnClickListener(v -> resetPlayer()); // Reset position

        //back button logic: move to the last position
        btnBack.setOnClickListener(v -> {
            if (!previousPositions.isEmpty()) {
                int[] lastPosition = previousPositions.pop(); //get the last position
                mazeBlocks[playerRow][playerCol].setBackgroundColor(Color.TRANSPARENT); // Clear current position
                playerRow = lastPosition[0];
                playerCol = lastPosition[1];
                updatePlayerPosition(); // Update to the last position

                //remove the top block from thex     stack
                if (!stackBlocks.isEmpty()) {
                    stackBlocks.pop(); //remove the last block from the stack
                }
            } else {
                Toast.makeText(this, "No previous position to move back to", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
