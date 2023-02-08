package frc.robot;

import java.io.*;
import java.util.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SingletonConstants {
    private static SingletonConstants instance;

    private static double clawSpeed;

    private SingletonConstants(){}

    public static SingletonConstants getInstance() {
        if (instance == null) {
            instance = new SingletonConstants();
        }
        return instance;
    }

    public static Properties readPropertiesFile(String fileName) throws IOException{
        FileInputStream fis = null;
        Properties prop = null;
        try{
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
            //clawSpeed = Double.parseDouble(prop.getProperty("clawspeed"));
        } catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }

    public void blabla() throws IOException{
    Properties prop = readPropertiesFile("../config.properties");
    //System.out.println("clawSpeed: " + prop.getProperty("clawSpeed"));
    }
} 
