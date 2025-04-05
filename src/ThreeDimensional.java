import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project 2
 *
 * @author Telfort, Pierre
 *
 * @created 04/3/2025
 *
 * @summary This is project 2.
 *
 * Used the template from JoglStarter class
 * to create the frame with all the necessary methods.
 */

public class ThreeDimensional extends JPanel implements
        GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {

    final private GLJPanel display;
    private Timer animationTimer;
    private int frameNumber = 0;  // The current frame number for an animation.

    private GLUT glut = new GLUT();  // TODO: For drawing GLUT objects, otherwise, not needed.

    // ---------------------- support for mouse events ----------------------

    private boolean dragging;  // is a drag operation in progress?

    private int startX, startY;  // starting location of mouse during drag
    private int prevX, prevY;    // previous location of mouse during drag

    public ThreeDimensional() {
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(600,600) );  // TODO: set display size here
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display,BorderLayout.CENTER);
        // TODO:  Other components could be added to the main panel.

        // TODO:  Uncomment the next two lines to enable keyboard event handling
        //requestFocusInWindow();
        //addKeyListener(this);

        // TODO:  Uncomment the next one or two lines to enable mouse event handling
        //display.addMouseListener(this);
        //display.addMouseMotionListener(this);

        //TODO:  Uncomment the following line to start the animation
        //startAnimation();
    } // end of constructor


    /**
     * The method below will create
     * a Gui and display it
     * to the user.
     */

    public void GUI() {
        JFrame window = new JFrame("3D app");
        ThreeDimensional panel = new ThreeDimensional();
        window.setContentPane(panel);
        /* TODO: If you want to have a menu, comment out the following line. */
        //window.setJMenuBar(panel.createMenuBar());
        window.pack();
        window.setLocation(350, 250);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    } // end of GUI method

    // ------------ Support for a menu -----------------------

    public JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        MenuHandler menuHandler = new MenuHandler(); // An object to respond to menu commands.

        JMenu menu = new JMenu("Menu"); // Create a menu and add it to the menu bar
        menubar.add(menu);

        JMenuItem item = new JMenuItem("Quit");  // Create a menu command.
        item.addActionListener(menuHandler);  // Set up handling for this command.
        menu.add(item);  // Add the command to the menu.

        // TODO:  Add additional menu commands and menus.

        return menubar;
    } // end of menu bar method

    /**
     * A class to define the ActionListener object that will respond to menu commands.
     */
    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String command = evt.getActionCommand();  // The text of the command.
            if (command.equals("Quit")) {
                System.exit(0);
            }
            // TODO: Implement any additional menu commands.
        }
    } // end of menu action listener


    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }


    // ------------ Support for keyboard handling  ------------

    /**
     * Called when the user presses any key on the keyboard, including
     * special keys like the arrow keys, the function keys, and the shift key.
     * Note that the value of key will be one of the constants from
     * the KeyEvent class that identify keys such as KeyEvent.VK_LEFT,
     * KeyEvent.VK_RIGHT, KeyEvent.VK_UP, and KeyEvent.VK_DOWN for the arrow
     * keys, KeyEvent.VK_SHIFT for the shift key, and KeyEvent.VK_F1 for a
     * function key.
     */

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        // TODO:  Add code to respond to key presses.
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user types a character.  This function is called in
     * addition to one or more calls to keyPressed and keyTyped. Note that ch is an
     * actual character such as 'A' or '@'.
     */

    @Override
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();  // Which character was typed.
        // TODO:  Add code to respond to the character being typed.
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user releases any key.
     */

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // --------------------------- animation support ---------------------------

    /* You can call startAnimation() to run an animation.  A frame will be drawn every
     * 30 milliseconds (can be changed in the call to glutTimerFunc). The global frameNumber
     * variable will be incremented for each frame.  Call pauseAnimation() to stop animating.
     */

    private boolean animating;  // True if animation is running.  Do not set directly.
    // This is set by startAnimation() and pauseAnimation().

    private void updateFrame() {
        frameNumber++;
        // TODO:  add any other updating required for the next frame.
    }

    public void startAnimation() {
        if ( ! animating ) {
            if (animationTimer == null) {
                animationTimer = new Timer(30, this);
            }
            animationTimer.start();
            animating = true;
        }
    }

    public void pauseAnimation() {
        if (animating) {
            animationTimer.stop();
            animating = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    // ---------------------- support for mouse events ----------------------

    /**
     * Called when the user presses a mouse button on the display.
     */

    @Override
    public void mousePressed(MouseEvent e) {
        if (dragging) {
            return;  // don't start a new drag while one is already in progress
        }
        int x = e.getX();  // mouse location in pixel coordinates.
        int y = e.getY();
        // TODO: respond to mouse click at (x,y)
        dragging = true;  // might not always be correct!
        prevX = startX = x;
        prevY = startY = y;
        display.repaint();    //  only needed if display should change
    }

    /**
     * Called when the user releases a mouse button after pressing it on the display.
     */

    @Override
    public void mouseReleased(MouseEvent e) {
        if (! dragging) {
            return;
        }
        dragging = false;
        // TODO:  finish drag (generally nothing to do here)
    }

    /**
     * Called during a drag operation when the user drags the mouse on the display/
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (! dragging) {
            return;
        }
        int x = e.getX();  // mouse location in pixel coordinates.
        int y = e.getY();
        // TODO:  respond to mouse drag to new point (x,y)
        prevX = x;
        prevY = y;
        display.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

} // end of class three-dimensional
