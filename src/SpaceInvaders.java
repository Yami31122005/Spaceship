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
        int hp = 1;
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

        //Bosses fight one after another; each next boss has double the HP
        ArrayList<Block> bossArray;
        int bossIndex = 0;

        //Boss lasers: 3 sprites, 5 shots per second
        Image Laser_1_Image;
        Image Laser_2_Image;
        Image Laser_3_Image;
        ArrayList<Image> laserImgArray;
        ArrayList<Block> bossLaserArray;
        int bossShotCounter = 0;
        int laserShotCount = 0;
        int laserWidth = tileSize / 2;
        int laserHeight = tileSize;
        int laserVelocityY = 5;

        //Player
        int shipLives = 3;
        boolean gameOver = false;


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

        Alien_4_Image = new ImageIcon(
                getClass().getResource("/Alien_4.png")
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

        Laser_1_Image = new ImageIcon(getClass().getResource("/Laser_1.png")).getImage();
        Laser_2_Image = new ImageIcon(getClass().getResource("/Laser_2.png")).getImage();
        Laser_3_Image = new ImageIcon(getClass().getResource("/Laser_3.png")).getImage();
        laserImgArray = new ArrayList<>();
        laserImgArray.add(Laser_1_Image);
        laserImgArray.add(Laser_2_Image);
        laserImgArray.add(Laser_3_Image);
        bossLaserArray = new ArrayList<>();

        //Heart sprite (src/Heart.png); the game still starts without it
        if (getClass().getResource("/Heart.png") != null) {
            heartImg = new ImageIcon(getClass().getResource("/Heart.png")).getImage();
        }

        createBosses();

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
        //boss
        Block boss = currentBoss();
        if (boss != null && boss.alive) {
            g.drawImage(boss.img, boss.x, boss.y, boss.width, boss.height, null);
        }
    for (Block bullet : bulletArray) {
    if (bullet.used) {
        g.drawImage(bullet.img, bullet.x, bullet.y, bullet.width, bullet.height, null);
    }
    }
        //boss lasers
        for (Block laser : bossLaserArray) {
            g.drawImage(laser.img, laser.x, laser.y, laser.width, laser.height, null);
        }
        //HUD
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Vies: " + shipLives, 10, 20);
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", boardWidth / 2 - 100, boardHeight / 2);
        }
    }
    public void move() {
        if (gameOver) return;
        //boss: spawn when regular aliens are gone, move, go to next boss when one is beaten
        Block boss = currentBoss();
        if (boss != null && !boss.alive && boss.hp <= 0) {
            bossIndex++;
            boss = currentBoss();
        }
        if (boss != null && !boss.alive && boss.hp > 0 && allAliensDead()) {
            boss.alive = true;
        }
        if (boss != null && boss.alive) {
            boss.x += AlienvelocityX;
            if (boss.x + boss.width >= boardWidth || boss.x <= 0) {
                AlienvelocityX *= -1;
            }
        }

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
        //boss fires 5 lasers per second (60 frames / 12 = 5), cycling through the 3 laser sprites
        if (boss != null && boss.alive) {
            bossShotCounter++;
            if (bossShotCounter >= 12) {
                bossShotCounter = 0;
                Image img = laserImgArray.get(laserShotCount % laserImgArray.size());
                laserShotCount++;
                bossLaserArray.add(new Block(boss.x + boss.width / 2 - laserWidth / 2,
                                             boss.y + boss.height, laserWidth, laserHeight, img));
            }
        }
        //lasers fall; touching the ship costs one life
        for (int i = bossLaserArray.size() - 1; i >= 0; i--) {
            Block laser = bossLaserArray.get(i);
            laser.y += laserVelocityY;
            if (laser.y > boardHeight) {
                bossLaserArray.remove(i);
                continue;
            }
            if (laser.x < ship.x + ship.width && laser.x + laser.width > ship.x
                    && laser.y < ship.y + ship.height && laser.y + laser.height > ship.y) {
                bossLaserArray.remove(i);
                shipLives--;
                if (shipLives <= 0) {
                    shipLives = 0;
                    gameOver = true;
                    return;
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
    }

    // hit the boss?
    Block target = currentBoss();
    if (bullet.used && target != null && target.alive
                    && bullet.x < target.x + target.width
                    && bullet.x + bullet.width > target.x
                    && bullet.y < target.y + target.height
                    && bullet.y + bullet.height > target.y) {
        bullet.used = false;
        target.hp--;
        if (target.hp <= 0) target.alive = false;
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
  public void createBosses() {
        bossArray = new ArrayList<>();
        for (int i = 0; i < alienBossImgArray.size(); i++) {
            Block b = new Block(boardWidth / 2 - tileSize * 2, tileSize,
                                tileSize * 4, tileSize * 4, alienBossImgArray.get(i));
            b.hp = 10 << i;   // boss 1: 10 HP, boss 2: 20, boss 3: 40
            b.alive = false;  // hidden until its turn
            bossArray.add(b);
        }
    }
    Block currentBoss() {
        return bossIndex < bossArray.size() ? bossArray.get(bossIndex) : null;
    }
    boolean allAliensDead() {
        for (Block a : alienArray) {
            if (a.alive) return false;
        }
        return true;
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
