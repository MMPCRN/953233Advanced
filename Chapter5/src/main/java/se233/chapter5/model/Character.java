package se233.chapter5.model;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter5.Launcher;
import se233.chapter5.view.Platform;

import java.util.concurrent.TimeUnit;

public class Character extends Pane {
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image characterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;

    private int startX;

    private int startY;

    private int offsetX;

    private int offsetY;

    private  int score =0;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity = 0;
    int yVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;



    public Character(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.offsetX = offsetX;
        this.offsetY =offsetY;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.characterImg = new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
        this.imageView = new AnimatedSprite(characterImg,4,4,1,offsetX,offsetY,16,32);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }
    public void collided(Character c){
        if(isMoveLeft){
           x = c.getX()+CHARACTER_WIDTH+1;
           stop();
        }else if(isMoveRight){
            x = c.getX()-CHARACTER_WIDTH-1;
            stop();
        }
        if( y < Platform.GROUND - CHARACTER_HEIGHT){
            if(isFalling && y < c.getY() && Math.abs(y-c.getY())<= CHARACTER_HEIGHT + 1){
                score++;
                y = Platform.GROUND - CHARACTER_HEIGHT-5;
                repaint();
                c.collapsed();
                c.respawn();
            }
        }
    }
    public void collapsed(){
        imageView.setFitHeight(5);
        y = Platform.GROUND-5;
        repaint();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        }catch (InterruptedException e){
             throw new RuntimeException(e);
        }
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void respawn(){
        x = startX;
        y = startY;
        imageView.setFitHeight(CHARACTER_HEIGHT);
        imageView.setFitWidth(CHARACTER_WIDTH);
        isFalling = true;
        isMoveRight=false;
        isMoveLeft=false;
        isJumping=false;
        canJump=false;
    }
    public void moveLeft() {
        isMoveLeft = true;
        isMoveRight = false;
    }
    public void moveRight() {
        isMoveLeft = false;
        isMoveRight = true;
    }
    public void stop() {
        isMoveLeft = false;
        isMoveRight = false;
    }
    public void moveX() {
        setTranslateX(x);
        if(isMoveLeft) {
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x - xVelocity;
        }
        if(isMoveRight) {
            xVelocity = xVelocity>=xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x + xVelocity;
        }
    }
    public void moveY() {
        setTranslateY(y);
        if(isFalling) {
            yVelocity = yVelocity >= yMaxVelocity? yMaxVelocity : yVelocity+yAcceleration;
            y = y + yVelocity;
        } else if(isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity-yAcceleration;
            y = y - yVelocity;
        }
    }
    public void checkReachGameWall() {
        if(x <= 0) {
            x = 0;
        } else if( x+getWidth() >= Platform.WIDTH) {
            x = Platform.WIDTH-(int)getWidth();
        }
    }
    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }
    public void checkReachHighest () {
        if(isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachFloor() {
        if(isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }
    public void repaint() {
        moveX();
        moveY();
    }
    public KeyCode getLeftKey() {
        return leftKey;
    }
    public KeyCode getRightKey() {
        return rightKey;
    }
    public KeyCode getUpKey() {
        return upKey;
    }
    public AnimatedSprite getImageView() {
        return imageView;
    }

    public int getScore() {
        return score;
    }

    public double getoffsetX() {
        return offsetX;
    }

    public double getoffsetY() {
        return offsetY;
    }
}