package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by jgp22 on 10/25/2017.
 */
@Autonomous(name = "Red1Jewel", group = "John")
public class Red1Jewel extends LinearOpMode {

    Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    static final double EXTEND_ARM = .25;
    static final double RETRACTED_ARM = .90;
    VuforiaLocalizer Vuforia;
    NormalizedColorSensor colorSensor;
    View relativeLayout;
    @Override
    public void runOpMode() throws InterruptedException {
       robot.init(hardwareMap);

        waitForStart();
        //ReadPictoGraphs();
        while (opModeIsActive()) {
        robot.Jewel.setPosition(EXTEND_ARM);
         //   robot.Jewel.setPosition(RETRACTED_ARM);
          telemetry.addData("Arm", EXTEND_ARM);
        telemetry.update();
        ReadJewels();
            //Move Arm in upright position
          //  robot.Jewel.setPosition(RETRACTED_ARM);



       }
    }

    // function to read the pictographs for the vuforia
    public void ReadPictoGraphs() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);




        parameters.vuforiaLicenseKey = "AS+QHXr/////AAAAGVyp6kN2Dkf5mToAr7hhqvAbPwQkQrWVlv7Ish2458X+fqOCT95EqEN0d2doBzzhB5U2omktsSEjVuuEshc3eFPgnfatgK7/jf9o89JgSEadKAMwjMTcE0q6nqmLXWQnzwxs/XHLNkU4uxwnjwtFFr5Lb2zU1VGl9DqxOme3jV9xVrbKJSOVfB1qXlxIMAs5Ggu0EdOeJb7XZcbA9Q0jzKdbIxPrD4CO7sxbaOBL0mUnzURGH2RWBF26zkcqKN4/HZuMu3ud/aQja1etyLAn9VOkk4e56t0Wm1xss9i+V4E49bJyCA+Fyu+nSlovTSqEkBUvybuhiCvuEAWeWsWOMO/sRniL5kWG61r6sGVSmPqK";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        robot.init(hardwareMap);
        this.Vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.Vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        waitForStart();

        relicTrackables.activate();


            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
            } else {
                telemetry.addData("VuMark", "not visible");


                telemetry.update();

               /* runtime.reset();
                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    robot.RightMotor.setPower(0.6);
                    robot.LeftMotor.setPower(0.6);
                    // runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 2)) ;

                    runtime.reset();
                    robot.RightMotor.setPower(-0.5);
                    robot.LeftMotor.setPower(0.5);
                    while (opModeIsActive() && (runtime.seconds() < .3)) ;

                    runtime.reset();
                    robot.LeftMotor.setPower(.6);
                    robot.RightMotor.setPower(.6);
                    while (opModeIsActive() && (runtime.seconds() < .2)) ;
                } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                    robot.LeftMotor.setPower(0.6);
                    robot.RightMotor.setPower(0.6);
                    // runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 3.5)) ;

                    runtime.reset();
                    robot.RightMotor.setPower(-0.5);
                    robot.LeftMotor.setPower(0.5);
                    while (opModeIsActive() && (runtime.seconds() < .3)) ;

                    runtime.reset();
                    robot.LeftMotor.setPower(0.6);
                    robot.RightMotor.setPower(0.6);
                    while (opModeIsActive() && (runtime.seconds() < .2)) ;
                }
                else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    robot.RightMotor.setPower(0.6);
                    robot.LeftMotor.setPower(0.6);
                    //runtime.reset();
                    while (opModeIsActive() && (runtime.seconds() < 3)) ;

                    runtime.reset();
                    robot.LeftMotor.setPower(0.5);
                    robot.RightMotor.setPower(-0.5);
                    while (opModeIsActive() && (runtime.seconds() < .3)) ;

                    runtime.reset();
                    robot.RightMotor.setPower(0.6);
                    robot.LeftMotor.setPower(0.6);
                    while (opModeIsActive() && (runtime.seconds() < .2)) ;
                }*/

            }
    }
public void ReadJewels(){
   // int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
  //  relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
    try {
        runSample();
    } finally {

       // relativeLayout.post(new Runnable() {
       //     public void run() {
        //        relativeLayout.setBackgroundColor(Color.WHITE);
       //     }
       // });
    }
}



    public void runSample(){
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        boolean bPrevState = false;
        boolean bCurrState = false;
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "JewelReader");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }
       // waitForStart();
       // while (opModeIsActive()) {
          /*  bCurrState = gamepad1.x;
            if (bCurrState != bPrevState) {
                if (bCurrState) {
                    if (colorSensor instanceof SwitchableLight) {
                        SwitchableLight light = (SwitchableLight)colorSensor;
                        light.enableLight(!light.isLightOn());
                    }
                }
            }
            bPrevState = bCurrState;*/
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            telemetry.addLine()
                    .addData("H", "%.3f", hsvValues[0])
                    .addData("S", "%.3f", hsvValues[1])
                    .addData("V", "%.3f", hsvValues[2]);
            telemetry.addLine()
                    .addData("a", "%.3f", colors.alpha)
                    .addData("r", "%.3f", colors.red)
                    .addData("g", "%.3f", colors.green)
                    .addData("b", "%.3f", colors.blue);
            int color = colors.toColor();
            telemetry.addLine("raw Android color: ")
                    .addData("a", "%02x", Color.alpha(color))
                    .addData("r", "%02x", Color.red(color))
                    .addData("g", "%02x", Color.green(color))
                    .addData("b", "%02x", Color.blue(color));
            float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
            colors.red   /= max;
            colors.green /= max;
            colors.blue  /= max;
            color = colors.toColor();
            telemetry.addLine("normalized color:  ")
                    .addData("a", "%02x", Color.alpha(color))
                    .addData("r", "%02x", Color.red(color))
                    .addData("g", "%02x", Color.green(color))
                    .addData("b", "%02x", Color.blue(color));
            telemetry.update();
            Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsvValues);
//if red knock off blue

            runtime.reset();
            if (hsvValues[0] <10){
                robot.RightMotor.setPower(.6);
              robot.LeftMotor.setPower(-.6);

                while (opModeIsActive() && (runtime.seconds() <0.5));
                robot.Jewel.setPosition(RETRACTED_ARM);
                //{

                   // while (opModeIsActive()&& (runtime.seconds()<0.5));
               // }

            }
            //if blue knock off blue
            else if (hsvValues[0] >100){
                robot.RightMotor.setPower(-.6);
               robot.LeftMotor.setPower(.6);
               // runtime.reset();
                while (opModeIsActive() && (runtime.seconds()<0.2));
                robot.Jewel.setPosition(RETRACTED_ARM);
            }
            //if it is not blue or red don't go anywhere
            else {
                robot.RightMotor.setPower(0);
                robot.LeftMotor.setPower(0);
            }
          //  relativeLayout.post(new Runnable() {
              //  public void run() {
               //     relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
               // }
           // });
            //seting to start position


    }
}
























