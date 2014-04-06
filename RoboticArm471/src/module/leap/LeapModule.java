/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.leap;
import arm.robot.gui.RobotArmGUI;
import com.leapmotion.leap.*;
import static com.leapmotion.leap.Gesture.Type.TYPE_KEY_TAP;
import module.zigbee.*;

import module.zigbee.*;

public class LeapModule extends Listener{
    private final static int FINGERS=5;

    private static int pitch;
    private static int yaw;
    private static int roll;
    
    private int motor1;
    private int motor2;
    private int motor3;
    private int motor4;
    private int motor5;
    
    private RobotArmGUI localgui;
    private ZigBeeModule zigbee;
    public Controller controller;
    public LeapModule(RobotArmGUI gui) {
       controller=new Controller();
       localgui=new RobotArmGUI();
       localgui=gui;
       motor1=motor2=motor3=motor4=motor5=0;
       
       
       zigbee=new ZigBeeModule();
       
       zigbee.OpenPort("/dev/ttyUSB0");
       zigbee.setMotor1Byte(motor1);
       zigbee.setMotor2Byte(motor2);
       zigbee.setMotor3Byte(motor3);
       zigbee.setMotor4Byte(motor4);
       zigbee.setMotor5Byte(motor5);
       zigbee.preparePacket();
       zigbee.sendPacket(localgui);
       
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

        if (!frame.hands().isEmpty() && frame.fingers().count()>=2) {
                // Get the first hand
                Hand hand = frame.hands().get(0);

                // Check if the hand has any fingers
                FingerList fingers = hand.fingers();
                
                Pointable finger0=fingers.get(0);
                Pointable finger1=fingers.get(1);
                
                int finger0pos=(int)finger0.tipPosition().getX();
                int finger1pos=(int)finger1.tipPosition().getX();
                
                
                // Get the hand's normal vector and direction
                Vector normal = hand.palmNormal();
                Vector direction = hand.direction();
                Vector position=hand.palmPosition();
                pitch=(int)(Math.toDegrees(direction.pitch()));
                yaw=(int)(Math.toDegrees(direction.yaw()));
                roll=(int)(Math.toDegrees(normal.roll()));
                
                
                
                int x=(int)position.getX();
                int y=(int)position.getY();
                int z=(int)position.getZ();
                
                localgui.XAxisValue.setText(Integer.toString(x));
                localgui.YAxisValue.setText(Integer.toString(y));
                localgui.ZAxisValue.setText(Integer.toString(z));
                localgui.FingerDistanceValue.setText(Integer.toString(finger0pos-finger1pos));
                
                
                zigbee.preparePacket();
                zigbee.sendPacket(localgui);
        }                            

    }
}