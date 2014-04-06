/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.leap;
import com.leapmotion.leap.*;
import static com.leapmotion.leap.Gesture.Type.TYPE_KEY_TAP;

import module.zigbee.*;

public class LeapModule extends Listener{
    private final static int FINGERS=5;

    private static int pitch;
    private static int yaw;
    private static int roll;
    
    public Controller controller;
    public LeapModule() {
       controller=new Controller();
       

    }

    public void onInit(Controller controller) {
            System.out.println("LeapMotion Initialized");

    }

    public void onConnect(Controller controller) {
            System.out.println("LeapMotion Connected");

            controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
            controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
            controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
            if(controller.config().setFloat("Gesture.ScreenTap.MinForwardVelocity", 60)&&
                    controller.config().setFloat("Gesture.ScreenTap.HistorySeconds", 1)&&
                    controller.config().setFloat("Gesture.ScreenTap.MinDistance", 6)){
                controller.config().save();
            }

    }

    public void onDisconnect(Controller controller) {
            // Note: not dispatched when running in a debugger.

            System.out.println("LeapMotion Disconnected");



    }

    public void onExit(Controller controller) {
        System.out.println("We Exited");

    }


    public void onFrame(Controller controller) {

        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        GestureList gesture=frame.gestures();

        if (!frame.hands().isEmpty() && frame.fingers().count() == FINGERS) {
                // Get the first hand
                Hand hand = frame.hands().get(0);

                // Check if the hand has any fingers
                FingerList fingers = hand.fingers();

                // Get the hand's normal vector and direction
                Vector normal = hand.palmNormal();
                Vector direction = hand.direction();
                pitch=(int)(Math.toDegrees(direction.pitch()));
                yaw=(int)(Math.toDegrees(direction.yaw()));
                roll=(int)(Math.toDegrees(normal.roll()));
                
                
                System.out.println("Pitch: "+pitch);
                System.out.println("Yaw: "+ yaw);
                System.out.println("Roll: "+ roll);
        }                            

    }
}