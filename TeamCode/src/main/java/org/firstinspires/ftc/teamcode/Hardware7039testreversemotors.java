package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by jgp22 on 10/20/2017.
 */

public class Hardware7039
{

    public DcMotor RightMotor;
    public DcMotor LeftMotor;
    public Servo Jewel;
   public Servo GlyphGripper;
   public DcMotor GlyphLifter;

    public final static double ARM_MIN_RANGE = .10;
    public final static double ARM_MAX_RANGE = .90;
    public final static double ARM_HOME = .10;
    public final static double TELEOP_ARM = .9;
   public final static double Gripper_Home = .40;
   public final static double Gripper_Min_Range = .20;
   public final static double Gripper_Max_Range = .90;


    HardwareMap HwMap = null;
    private ElapsedTime period = new ElapsedTime();
    public Hardware7039()
    {

    }
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        HwMap = ahwMap;
        RightMotor = HwMap.get(DcMotor.class, "RightMotor");
        LeftMotor = HwMap.get(DcMotor.class, "LeftMotor");
        Jewel = HwMap.get(Servo.class, "Jewel");
        GlyphGripper = HwMap.get(Servo.class, "GlyphGripper");
        GlyphLifter = HwMap.get(DcMotor.class, "GlyphLifter");
        GlyphLifter.setDirection(DcMotor.Direction.REVERSE);
        LeftMotor.setDirection(DcMotor.Direction.REVERSE);
        RightMotor.setPower(0);
        LeftMotor.setPower(0);
        RightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        GlyphLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
