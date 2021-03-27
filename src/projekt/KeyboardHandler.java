package projekt;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

/** Quelle:
 * https://www.youtube.com/watch?v=RvvkfJytRIU, TutorialEdge, "Tutorial 03 - Creating Mice and Keyboard Input Handlers - Programming 2D Pong with LWJGL 3"
 *
 */
public class KeyboardHandler extends GLFWKeyCallback{

    public static boolean[] keys = new boolean[65536];

    // The GLFWKeyCallback class is an abstract method that
    // can't be instantiated by itself and must instead be extended
    //
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    // boolean method that returns true if a given key
    // is pressed.
    static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

}
