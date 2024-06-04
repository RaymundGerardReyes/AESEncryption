import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Apptest extends Applet implements MouseListener, MouseMotionListener {

    int x1, y1;   // Coords of top-left corner of the red square.
    int x2, y2;   // Coords of top-left corner of the blue square.

    /* Some variables used during dragging */
    boolean dragging;      // Set to true when a drag is in progress.
    boolean dragRedSquare; // True if red square is being dragged, false if blue square is being dragged.
    int offsetX, offsetY;  // Offset of mouse-click coordinates from top-left corner of the square that was clicked.

    public void init() {
        x1 = 10;
        y1 = 10;
        x2 = 50;
        y2 = 10;

        setBackground(Color.lightGray);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x1, y1, 30, 30);
        g.setColor(Color.blue);
        g.fillRect(x2, y2, 30, 30);
        g.setColor(Color.black);
        g.fillRect(35, 10, 30, 30);
    }

    public void mousePressed(MouseEvent evt) {
        if (dragging)  // Exit if a drag is already in progress.
            return;

        int x = evt.getX();  // Location where user clicked.
        int y = evt.getY();

        if (x >= x2 && x < x2+30 && y >= y2 && y < y2+30) {
            dragging = true;
            dragRedSquare = false;
            offsetX = x - x2;  // Distance from corner of square to click point.
            offsetY = y - y2;
        }
        else if (x >= x1 && x < x1+30 && y >= y1 && y < y1+30) {
            dragging = true;
            dragRedSquare = true;
            offsetX = x - x1;  // Distance from corner of square to click point.
            offsetY = y - y1;
        }
    }

    public void mouseReleased(MouseEvent evt) {
        dragging = false;
    }

    public void mouseDragged(MouseEvent evt) {
        if (dragging) {
            int x = evt.getX();
            int y = evt.getY();

            if (dragRedSquare) {
                x1 = x - offsetX;
                y1 = y - offsetY;
            } else {
                x2 = x - offsetX;
                y2 = y - offsetY;
            }

            repaint();
        }
    }

    public void mouseMoved(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
}