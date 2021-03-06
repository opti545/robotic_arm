/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roboticarm471;
import java.io.IOException;
import module.leap.*;
import arm.robot.gui.*;
/**
 *
 * @author root
 */
public class RoboticArm471 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing Robotic Arm Interface");
              
                RobotArmGUI gui=new RobotArmGUI();
                
		LeapModule leapmodule = new LeapModule(gui);
                gui.setVisible(true);
                //start acquiring the gestures
		leapmodule.controller.addListener(leapmodule);
                
		System.out.println("Press any key to quit...");

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		leapmodule.controller.removeListener(leapmodule);
    }
    
}
