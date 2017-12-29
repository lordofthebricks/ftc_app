package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.AutonomousJewelGripperTurnBackToCryptoBoxWithVuforiaRed123readjewels2wait.COUNTS_PER_INCH;

/**
 * Created by jgp22 on 10/25/2017.
 */
@Autonomous(name = "BlueJewels", group = "John")
public class Red1JewelSafe extends LinearOpMode {

    Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    static final double     EXTEND_ARM = .2;
    static final double     RETRACTED_ARM = .90;
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.4;
    static final double     FORWARD_SPEED = 0.4;
    static final double     TURN_SPEED    = 0.5;
    static final double     JEWEL_SPEED   = .2;
    static final double     Kill_Speed    = 0;
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
                encoderDrive(FORWARD_SPEED, -12.3, 12.3, 4.0);
                encoderDrive(FORWARD_SPEED, 12.3, -12.3, 4.0);
               // robot.RightMotor.setPower(.6);
                //robot.LeftMotor.setPower(-.6);

                //while (opModeIsActive() && (runtime.seconds() <0.5));
                robot.Jewel.setPosition(RETRACTED_ARM);
                encoderDrive(FORWARD_SPEED, 32, 32, 4.0);
                while (opModeIsActive()){
                    encoderDrive(Kill_Speed, 0, 0, 4.0);
                }

                //{

                   // while (opModeIsActive()&& (runtime.seconds()<0.5));
               // }

            }
            //if blue knock off blue
            else if (hsvValues[0] >100){
                encoderDrive(FORWARD_SPEED, 12.3, -12.3, 4.0);
                encoderDrive(FORWARD_SPEED, -12.3, 12.3, 4.0);
                //robot.RightMotor.setPower(-.6);
               //robot.LeftMotor.setPower(.6);
               // runtime.reset();
                //while (opModeIsActive() && (runtime.seconds()<0.2));
                robot.Jewel.setPosition(RETRACTED_ARM);
                encoderDrive(FORWARD_SPEED, 32, 32, 4.0);
                while (opModeIsActive()){
                    encoderDrive(Kill_Speed, 0, 0, 4.0);
                }
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

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.LeftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.RightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.LeftMotor.setTargetPosition(newLeftTarget);
            robot.RightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.LeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.RightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.LeftMotor.setPower(Math.abs(leftInches));
            robot.RightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.LeftMotor.isBusy() && robot.RightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.LeftMotor.getCurrentPosition(),
                        robot.RightMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.LeftMotor.setPower(0);
            robot.RightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.LeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.RightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }

}
























