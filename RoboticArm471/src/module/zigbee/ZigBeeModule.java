/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module.zigbee;
import javax.comm.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class ZigBeeModule {

	private final static int baudRate = 9600;
        
        //Packet information
        private final static byte startByte=(byte)0x7E;
        private static byte motor1Byte=0;
        private static byte motor2Byte=0;
        private static byte motor3Byte=0;
        private static byte motor4Byte=0;
        private static byte motor5Byte=0;
        private final static byte endByte=(byte)0x9A;
        private byte packet[];
        //End of Packer Information
        
        
	static SerialPort serialPort;
	static CommPortIdentifier commport;
	OutputStream outputStream;
	InputStream inputStream;
	Thread readThread;
	public static int lock=0;
	public void OpenPort(String port) {
		try {
			commport = CommPortIdentifier.getPortIdentifier(port);
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(null,"Please connect your ZigBee and retry");
                        e.printStackTrace();
                        System.exit(0);
			
		}
		if (commport.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			if (commport.getName() == port) {
				System.out.println("Opening " + port);
			}
			try {
				serialPort = (SerialPort) commport
						.open("GCCarController", 20000);
			} catch (PortInUseException e) {
				System.out.println("Port is in use");
				e.printStackTrace();
			}
			try {
				outputStream = serialPort.getOutputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.FLOWCONTROL_NONE);
			} catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
			}
		}

	}

        public void setMotor1Byte(int motor1byte){
            motor1Byte=(byte)(motor1byte);
        }
        public void setMotor2Byte(int motor2byte){
            motor2Byte=(byte)motor2byte;
        }
        public void setMotor3Byte(int motor3byte){
            motor3Byte=((byte)motor3byte);
        }
        public void setMotor4Byte(int motor4byte){
            motor4Byte=((byte)motor4byte);
        }
        public void setMotor5Byte(int motor5byte){
            motor5Byte=((byte)motor5byte);
        }
        public void preparePacket(){
            packet=new byte[7];
           
            packet[0]=startByte;
            packet[1]=motor1Byte;
            packet[2]=motor2Byte;
            packet[3]=motor4Byte;
            packet[4]=motor3Byte;
            packet[5]=motor5Byte;
            packet[6]=endByte;
            
            /*
            packet[0]=startByte;
            packet[1]=0;
            packet[2]=1;
            packet[3]=1;
            packet[4]=2;
            packet[5]=3;
            packet[6]=endByte;
            */
            
            
        }
        public void sendPacket(){
            try {         
                outputStream.write(packet);
                   
            } catch (IOException ex) {
                Logger.getLogger(ZigBeeModule.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
}