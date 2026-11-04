import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class  SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    static class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean alive = true;
        boolean used = false;
        Block (int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;

        }


    }
    int tileSize = 32;
    int rows = 16;
    int columns = 16;
    int boardWidth = tileSize * columns;
    int boardHeight = tileSize * rows;

        //Basic Aliens
        Image shipImage;
        Image Alien_1_Image;
        Image Alien_2_Image;
        Image Alien_3_Image;
        Image Alien_4_Image;
        Image Alien_5_Image;
        Image Alien_6_Image;
        ArrayList<Image> alienImgArray;
        //For the Final boss
        Image Alien_Boss_1_Image;
        Image Alien_Boss_2_Image;
        Image Alien_Boss_3_Image;
        ArrayList<Image> alienBossImgArray;

        //For the bullets
        Image Bullet_1; 
        Image Bullet_2;
        Image Bullet_3;
        ArrayList<Image> bulletImgArray;


        Block ship;



        ArrayList<Block> alienArray;
        int alienwidth = tileSize;
        int alienheight = tileSize;
        int alienX = tileSize;
        int alienY = tileSize;



        int alienRows = 3;
        int alienColumns = 4;
        int Aliendefeated = 0;
        int AlienvelocityX = 3;

         
        int shipHeight = tileSize * 2;
        int shipWidth = tileSize * 2;
        int shipX = tileSize * columns/2 - tileSize;
        int shipY = boardHeight - tileSize * 2;
        int shipVelocityX = tileSize;
        int shipVelocityY = tileSize*2;

        ArrayList<Block> bulletArray;
        int bulletWidth = tileSize/2;
        int bulletHeight = tileSize/2;
        int bulletVelocityY = -7;
        
    SpaceInvaders() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);

        shipImage = new ImageIcon(
                getClass().getResource("/Spaceship_end.png")
        ).getImage();

        Alien_1_Image = new ImageIcon(
                getClass().getResource("/Alien_1.png")
        ).getImage();

        Alien_2_Image = new ImageIcon(
                getClass().getResource("/Alien_2.png")
        ).getImage();

        Alien_3_Image = new ImageIcon(
                getClass().getResource("/Alien_3.png")
        ).getImage();

        Alien_5_Image = new ImageIcon(
                getClass().getResource("/Alien_5.png")
        ).getImage();

        Alien_6_Image = new ImageIcon(
                getClass().getResource("/Alien_6.png")
        ).getImage();
        Alien_Boss_1_Image = new ImageIcon(
                getClass().getResource("/First_Boss.png")
        ).getImage();
        Alien_Boss_2_Image = new ImageIcon(
                getClass().getResource("/Alien_Boss2.png")
        ).getImage();
         Alien_Boss_3_Image = new ImageIcon(
                getClass().getResource("/A_B_4.png")
        ).getImage();
        Bullet_1 = new ImageIcon(
                getClass().getResource("/Shoot_1.png")
        ).getImage();
         Bullet_2 = new ImageIcon(
                getClass().getResource("/Shoot_8.png")
        ).getImage();
         Bullet_3 = new ImageIcon(
                getClass().getResource("/Shoot_3.png")
        ).getImage();





        alienBossImgArray = new ArrayList<>();
        alienBossImgArray.add(Alien_Boss_1_Image);
        alienBossImgArray.add(Alien_Boss_2_Image);
        alienBossImgArray.add(Alien_Boss_3_Image);

        alienImgArray = new ArrayList<>();
        alienImgArray.add(Alien_1_Image);
        alienImgArray.add(Alien_2_Image);
        alienImgArray.add(Alien_3_Image);
        alienImgArray.add(Alien_4_Image);
        alienImgArray.add(Alien_5_Image);
        alienImgArray.add(Alien_6_Image);

        bulletImgArray = new ArrayList<>();
        bulletImgArray.add(Bullet_1);
        bulletImgArray.add(Bullet_2);
        bulletImgArray.add(Bullet_3);

        ship = new Block(shipX, shipY, shipWidth, shipHeight, shipImage);
        alienArray = new ArrayList<>();
        createAliens();

        bulletArray = new ArrayList<>();

        createBullets();

     gameloop = new Timer(1000/60, this);
     gameloop.start();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);
        //aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }
    for (Block bullet : bulletArray) {
    if (bullet.used) {
        g.drawImage(bullet.img, bullet.x, bullet.y, bullet.width, bullet.height, null);
    }
    }
    }
    public void move() {
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                alien.x += AlienvelocityX;
                if (alien.x + alien.width >= boardWidth || alien.x <= 0) {
                    AlienvelocityX *= -1;
                    for (int j = 0; j < alienArray.size(); j++) {
                        Block a = alienArray.get(j);
                        a.y += tileSize;
                    }
                    break;
                }
            }
          

        }
        for (Block bullet : bulletArray) {
    if (!bullet.used) continue;

    bullet.y += bulletVelocityY;

    // off-screen: make it available again
    if (bullet.y + bullet.height < 0) {
        bullet.used = false;
        continue;
    }
int score = 0;
    // hit an alien?
    for (Block alien : alienArray) {
        if (alien.alive && bullet.x < alien.x + alien.width
                        && bullet.x + bullet.width > alien.x
                        && bullet.y < alien.y + alien.height
                        && bullet.y + bullet.height > alien.y) {
            alien.alive = false;
 bullet.used = false;
            Aliendefeated--;
            break;
        }
         //next level
      
    }

}
}
   public void actionPerformed(java.awt.event.ActionEvent e) {
        move();
        repaint();
    }
     @Override
  public void keyTyped(KeyEvent e) {
    
  }
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.isActionKey())
    {
      
    }

  }
  public void createBullets() {
        for (int i = 0; i < bulletImgArray.size(); i++) {
            Block bullet = new Block(ship.x + shipWidth*15/32, ship.y, bulletWidth, bulletHeight, null);
            bullet.used = false;
            bulletArray.add(bullet);
        }
    }
    public void shootBullet(Image img) {
    for (Block bullet : bulletArray) {
        if (!bullet.used) {
            bullet.img = img;
            bullet.x = ship.x + ship.width / 2 - bullet.width / 2;
            bullet.y = ship.y - bullet.height;
            bullet.used = true;
            break;
        }
    }
}
  public void createAliens() {
        Random random = new Random();
        for (int r = 0; r < alienRows; r++) {
            for (int c = 0; c < alienColumns; c++) {
                int randomIndex = random.nextInt(alienImgArray.size());
                Block alien = new Block(
                alienX + c * alienwidth,
                alienY + r * alienheight,
                alienwidth,
                alienheight,
                alienImgArray.get(randomIndex)
                );
                alienArray.add(alien);
            }
           
        }
        Aliendefeated = alienArray.size();
    }
    
  public void keyReleased(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_LEFT && ship.x - shipVelocityX >= 0) {
        ship.x -= shipVelocityX;
    }
    else if(e.getKeyCode() == KeyEvent.VK_RIGHT && ship.x + shipVelocityX + ship.width <= boardWidth) {
        ship.x += shipVelocityX;
    }
    else if(e.getKeyCode() == KeyEvent.VK_UP && ship.y - shipVelocityY >= 0) {
        ship.y -= shipVelocityY;
    }
    else if(e.getKeyCode() == KeyEvent.VK_DOWN && ship.y + shipVelocityY + ship.height <= boardHeight) {
        ship.y += shipVelocityY;
    }
    else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        shootBullet(Bullet_1);
    }
    else if(e.getKeyCode() == KeyEvent.VK_A) {
        shootBullet(Bullet_2);
    }
    else if(e.getKeyCode() == KeyEvent.VK_W) {
        shootBullet(Bullet_3);
    }

  }
 
    Timer gameloop;

}



      



