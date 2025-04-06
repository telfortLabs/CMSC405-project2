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
 * @summary For this project
 * I Used the template from JoglStarter class
 * to create the frame with all the necessary methods.
 * then created 6 different shapes, then added an
 * animation that will rotate the objects around the
 * x-axis & y-axis.
 */

public class ThreeDimensional extends JPanel implements
        GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {

    private final GLJPanel display;
    private Timer animationTimer;
    private final JCheckBox rotateX;
    private final JCheckBox rotateY;

    private int frameNumber = 0;                                                        /* The current frame number for an animation. */

    private final GLUT glut = new GLUT();                                                     /* For drawing GLUT objects. */
    private float yRotation = 0.0f;
    private float xRotation = 35.0f;
    // ---------------  Variables below to create camera object-----------
    private final Camera camera;

    /**
     * Created a constructor below
     * that will display the drawing
     * area and start the animation.
     */

    public ThreeDimensional() {
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(600,600) );                 /* set display size. */
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display,BorderLayout.CENTER);

        rotateX = new JCheckBox("Rotate X-axis", false);                   /* checkbox for x-axis. */
        rotateY = new JCheckBox("Rotate Y-axis", false);                   /* checkbox for y-axis. */

        JPanel bottom = new JPanel();                                                   /* Panel to add items at bottom of frame. */
        bottom.setLayout(new GridLayout(1,1));                               /* Setting layout of panel. */
        JPanel row1 = new JPanel();                                                     /* Panel to add items in row. */
        row1.add(rotateX);                                                              /* Added checkbox to bottom row. */
        row1.add(rotateY);                                                              /* Added checkbox to bottom row. */
        bottom.add(row1);                                                               /* Added row to bottom panel. */
        add(bottom,BorderLayout.SOUTH);                                                 /* Added panel to bottom of frame. */

        // ---------------  Added camera component below-----------
        camera = new Camera();
        camera.lookAt(0,5,10, 0,0,0, 0,1,0);
        camera.setScale(5);
        camera.installTrackball(display);
        // ---------------  start the animation-----------
        startAnimation();

    } // end of constructor

    /**
     * Method below will display
     * the GUI (Graphical User Interface) to the user.
     */

    public void GUI() {
        JFrame window = new JFrame("3D app");
        ThreeDimensional panel = new ThreeDimensional();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(350, 250);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    } // end of GUI method

    // ---------------  Methods of the GLEventListener interface -----------
    /**
     * This is called when the GLJPanel is first created.  It can be used to initialize
     * the OpenGL drawing context.
     */

    @Override
    public void init(GLAutoDrawable drawable) {
        // called when the panel is created
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.3F, 0.3F, 0.3F, 1.0F);                  /* Set background color. */

        gl.glEnable(GL2.GL_DEPTH_TEST);
         gl.glEnable(GL2.GL_LIGHTING);                                                  /* Enable lighting. */
         gl.glEnable(GL2.GL_LIGHT0);                                                    /* Turn on a light.  By default, shines from direction of viewer. */
         gl.glEnable(GL2.GL_NORMALIZE);                                                 /* OpenGL will make all normal vectors into unit normals. */
         gl.glEnable(GL2.GL_COLOR_MATERIAL);                                            /* Material ambient and diffuse colors can be set by glColor. */

    } // end of init method

    @Override
    public void display(GLAutoDrawable drawable) {

        // called when the panel needs to be drawn
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0,0,0,0);
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );            /* Omit depth buffer for 2D. */

        gl.glMatrixMode(GL2.GL_PROJECTION);                                             /* Set up a better projection. */
        gl.glLoadIdentity();
        gl.glOrtho(-1,1,-1,1,-2,2);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        camera.apply(gl);                                                               /* call method to project view. */
        float yTranslation = 0;
        float xTranslation = 0;
        gl.glTranslatef(xTranslation, yTranslation, 0);

        /* Rectangle shape */
        gl.glPushMatrix();
        gl.glRotatef(yRotation, 1.0f, 0.0f, 0);
        gl.glRotatef(xRotation, 0.0f, 1.0f, 0);
        gl.glColor3d(0.450, 0.000, 0.630);
        // ---------------  Variables below to create the shapes -----------
        float scaleRatio = 6f;
        gl.glScalef(scaleRatio, scaleRatio / 10, scaleRatio);
        glut.glutSolidCube(.5f);
        gl.glPopMatrix();

        /* Sphere shape */
        gl.glPushMatrix();
        gl.glScalef(scaleRatio / 10, scaleRatio / 10, scaleRatio / 10);
        gl.glRotatef(yRotation, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(xRotation, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(-1.75f, 1, -1.5f);
        gl.glColor3d(0.500, 0.500, 0.500);
        glut.glutSolidSphere(0.5, 100, 100);
        gl.glPopMatrix();

        /* Torus Shape */
        gl.glPushMatrix();
        gl.glScalef(scaleRatio / 10, scaleRatio / 10, scaleRatio / 10);
        gl.glRotatef(yRotation, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(xRotation, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(-1.75f, 1, 1.75f);
        gl.glColor3d(0.999, 0.999, 0.000);
        glut.glutSolidTorus(.2, .5, 100, 100);
        gl.glPopMatrix();

        /* Cylinder shape */
        gl.glPushMatrix();
        gl.glScalef(scaleRatio / 10, scaleRatio / 10, scaleRatio / 10);
        gl.glRotatef(yRotation, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(xRotation, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(1.75f, 0.5f, -2);
        gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
        gl.glColor3d(0.999, 0.999, 0.999);
        glut.glutSolidCylinder(.5, 1, 100, 100);
        gl.glPopMatrix();

        /* Cone shape */
        gl.glPushMatrix();
        gl.glScalef(scaleRatio / 10, scaleRatio / 10, scaleRatio / 10);
        gl.glRotatef(yRotation, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(xRotation, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0, .75f, 0);
        gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
        gl.glColor3d(0.800, 0.000, 0.000);
        glut.glutSolidCone(.5, 1, 100, 100);
        gl.glPopMatrix();

        /* Tetrahedron shape */
        gl.glPushMatrix();
        gl.glScalef(scaleRatio / 10, scaleRatio / 10, scaleRatio / 10);
        gl.glRotatef(yRotation, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(xRotation, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(2, 1.15f, 1.25f);
        gl.glColor3d(0.250, 0.75, 0.150);
        glut.glutSolidTetrahedron();
        gl.glPopMatrix();

        if(rotateX.isSelected()) {
            xRotation += 1;                                                             /* If checkbox is selected, rotate shapes around x-axis. */
            System.out.println("rotating x-axis");
        }
        if(rotateY.isSelected()) {
            yRotation -= 1;                                                             /* If checkbox is selected, rotate shapes around y-axis. */
            System.out.println("rotating y-axis");
        }

        gl.glFlush();
        gl.glLoadIdentity();                                                            /* Set up model view transform. */
    } // end of display method

    /**
     * Called when the size of the GLJPanel changes.  Note:  glViewport(x,y,width,height)
     * has already been called before this method is called!
     */

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }

    /**
     * This is called before the GLJPanel is destroyed.  It can be used to release OpenGL resources.
     */

    @Override
    public void dispose(GLAutoDrawable drawable) { }

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
        int key = e.getKeyCode();                                                       /* Tells which key was pressed. */
        display.repaint();                                                              /* Causes the display() function to be called. */
    }

    /**
     * Called when the user types a character.  This function is called in
     * addition to one or more calls to keyPressed and keyTyped. Note that ch is an
     * actual character such as 'A' or '@'.
     */

    @Override
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();                                                       /* Which character was typed. */
        display.repaint();                                                              /* Causes the display() function to be called. */
    }

    /**
     * Called when the user releases any key.
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    // --------------------------- animation support ---------------------------
    /* You can call startAnimation() to run an animation.  A frame will be drawn every
     * 30 milliseconds (can be changed in the call to glutTimerFunc).  The global frameNumber
     * variable will be incremented for each frame.  Call pauseAnimation() to stop animating.
     */

    private boolean animating;                                                          /* True if animation is running.  Do not set directly. */

    // This is set by startAnimation() and pauseAnimation().
    private void updateFrame() {
        frameNumber++;
    }

    public void startAnimation() {
        if ( ! animating ) {
            if (animationTimer == null) {
                animationTimer = new Timer(60, this);
            }
            animationTimer.start();
            animating = true;
        }
        System.out.println("Animation started");
    }

    public void pauseAnimation() {
        if (animating) {
            animationTimer.stop();
            animating = false;
        }
        System.out.println("Animation paused");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateFrame();
        display.repaint();
    }
    // ---------------------- support for mouse events ----------------------
    private boolean dragging;                                                           /* is a drag operation in progress? */

    private int startX, startY;                                                         /* starting location of mouse during drag. */
    private int prevX, prevY;                                                           /* previous location of mouse during drag. */

    /**
     * Called when the user presses a mouse button on the display.
     */

    @Override
    public void mousePressed(MouseEvent e) {
        if (dragging) {
            return;                                                                     /* don't start a new drag while one is already in progress. */
        }
        int x = e.getX();                                                               /* mouse location in pixel coordinates. */
        int y = e.getY();
        // respond to mouse click at (x,y)
        dragging = true;                                                                /* might not always be correct! */
        prevX = startX = x;
        prevY = startY = y;
        display.repaint();                                                              /* only needed if display should change. */
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
    }

    /**
     * Called during a drag operation when the user drags the mouse on the display/
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (! dragging) {
            return;
        }
        int x = e.getX();                                                               /* mouse location in pixel coordinates. */
        int y = e.getY();
        // respond to mouse drag to new point (x,y)
        prevX = x;
        prevY = y;
        display.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) { }                                            /* Other methods required for MouseListener, MouseMotionListener. */

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }
} // end of class three-dimensional
