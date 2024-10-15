import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean timeSort, distanceSort, rightHandSort;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_T){ // time
            System.out.println("T pressed");
            timeSort = true;
        }
        if(code == KeyEvent.VK_D){ // distance
            System.out.println("D pressed");
            distanceSort = true;
        }
        if(code == KeyEvent.VK_R){ // right hand
            System.out.println("R pressed");
            rightHandSort = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_T){ // time
            timeSort = false;
        }
        if(code == KeyEvent.VK_D){ // distance
            distanceSort = false;
        }
        if(code == KeyEvent.VK_R){ // right hand
            rightHandSort = false;
        }
    }
}
