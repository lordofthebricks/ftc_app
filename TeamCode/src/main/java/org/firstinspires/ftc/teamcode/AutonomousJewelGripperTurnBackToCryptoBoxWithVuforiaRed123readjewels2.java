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
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by jgp22 on 10/25/2017.
 */
@Autonomous(name = "Autonomous Red1 (((((John))))))")
public class AutonomousJewelGripperTurnBackToCryptoBoxWithVuforiaRed123readjewels extends LinearOpMode {

    Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    static final double EXTEND_ARM = .25;
    static final double RETRACTED_ARM = .90;
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.4;
    static final double     FORWARD_SPEED = 0.4;
    static final double     TURN_SPEED    = 0.5;
    VuforiaLocalizer vuforia;
    NormalizedColorSensor colorSensor;
    View relativeLayout;
    OpenGLMatrix lastLocation = null;
    public static final String TAG = "Vuforia Navigation Sample2";

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        robot.GlyphGripper.setPosition(.3);
       robot.Jewel.setPosition(EXTEND_ARM);
        while (opModeIsActive()) {


            ReadJewels();

            ReadPictoGraphs();

            // robot.Jewel.setPosition(EXTEND_ARM);
            //   robot.Jewel.setPosition(RETRACTED_ARM);

            telemetry.addData("Arm", EXTEND_ARM);
            telemetry.update();

            //Move Arm in upright position
            //  robot.Jewel.setPosition(RETRACTED_ARM);


        }
    }

    // function to read the pictographs for the vuforia
    public void ReadPictoGraphs() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AS34pyX/////AAAAGaIrZJw2gU9xsxqfbnnb+NRMmLab5C2kQ5nc5YQr0V2hS3svZx7pBKzTz+ivN1giF42Wv8bBcm9gKE69/IPfrHT/nmBsKSyBmg5x0lkmlzYZ16vcd8R8hR6+q97ki1Sn/tjGlKalYvYSL+326CcR1EiJ3C7dWYujBqTJwsqySEXcqrn4ieiQJ4lY8/+U6dBTx/OkBvXxAMgJHl+Qjz5o6TUtQX4WolbO9mOD0bZFdTwSwyzycdKDNXLUjABOcdnx2foEvJqcVPOCfHEh8FEZRHpDB5RLgIqF1kwxCfFXx7MVflrtoLet/e6l9PdmC8nIk5Oo9cC9C6hF8L79A52YouscEKTWVx9pmqPgRYDhXUux\n";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            relicTrackables.activate();


            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
            } else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();


            if (vuMark == RelicRecoveryVuMark.LEFT) {
                runtime.reset();
                encoderDrive(DRIVE_SPEED, 38, 38, 4.0);
                encoderDrive(TURN_SPEED, -12, 12, 4.0);
                encoderDrive(DRIVE_SPEED, 5, 5, 4.0);
                robot.GlyphGripper.setPosition(.9);
                encoderDrive(DRIVE_SPEED, 2, 2, 4.0);
                encoderDrive(DRIVE_SPEED, -1, -1, 4.0);
            }

            if (vuMark == RelicRecoveryVuMark.CENTER){
                runtime.reset();
                encoderDrive(DRIVE_SPEED, 31, 31, 4.0);
                encoderDrive(TURN_SPEED, -12, 12, 4.0);
                encoderDrive(DRIVE_SPEED, 5, 5, 4.0);
                robot.GlyphGripper.setPosition(.9);
                encoderDrive(DRIVE_SPEED, 2, 2, 4.0);
                encoderDrive(DRIVE_SPEED, -1, -1, 4.0);
            }

            if (vuMark == RelicRecoveryVuMark.RIGHT){
                runtime.reset();
                encoderDrive(DRIVE_SPEED, 23, 23, 4.0);
                encoderDrive(TURN_SPEED, -12, 12, 4.0);
                encoderDrive(DRIVE_SPEED, 5, 5, 4.0);
                robot.GlyphGripper.setPosition(.9);
                encoderDrive(DRIVE_SPEED, 2, 2, 4.0);
                encoderDrive(DRIVE_SPEED, -1, -1, 4.0);
            }
        }
    }
        String format(OpenGLMatrix transformationMatrix){
            return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
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



    public void runSample() {
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        boolean bPrevState = false;
        boolean bCurrState = false;
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "JewelReader");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
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
        colors.red /= max;
        colors.green /= max;
        colors.blue /= max;
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
        if (hsvValues[0] < 10) {
            robot.RightMotor.setPower(-.20);
            robot.LeftMotor.setPower(.20);

            while (opModeIsActive() && (runtime.seconds() < 0.5)) ;
            robot.Jewel.setPosition(RETRACTED_ARM);

            runtime.reset();
            robot.LeftMotor.setPower(-0.20);  //Turn back
            robot.RightMotor.setPower(0.20);
            while (opModeIsActive() && runtime.seconds() < 0.5) ;

            //runtime.reset();
            //robot.GlyphLifter.setPower(.3);   //Lift Glyph Lifter
            //while (opModeIsActive() && runtime.seconds()<0.1);

            //robot.GlyphLifter.setPower(0);   //Stop Glyph Lifter

                /*runtime.reset();
                robot.LeftMotor.setPower(-0.4);  //Move forward
                robot.RightMotor.setPower(-0.4);
                while (opModeIsActive() && runtime.seconds()<1);

                runtime.reset();
                robot.LeftMotor.setPower(0.4);  //Turn
                robot.RightMotor.setPower(-0.4);
                while (opModeIsActive() && runtime.seconds()<1.2);

                runtime.reset();
                robot.LeftMotor.setPower(-0.4);  //Move forward
                robot.RightMotor.setPower(-0.4);
                while (opModeIsActive() && runtime.seconds()<0.4);

                robot.GlyphGripper.setPosition(.9);  //Open Gripper

                runtime.reset();
                robot.RightMotor.setPower(-0.50);
                robot.LeftMotor.setPower(-0.50);
                while (opModeIsActive()&&(runtime.seconds()<1));
                //{

                   // while (opModeIsActive()&& (runtime.seconds()<0.5));
               // }
*/
        }
        //if blue knock off blue
        else if (hsvValues[0] > 100) {
            robot.RightMotor.setPower(.20);
            robot.LeftMotor.setPower(-.20);
            // runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 0.5)) ;
            robot.Jewel.setPosition(RETRACTED_ARM);

            runtime.reset();
            robot.LeftMotor.setPower(0.20);  //Turn back
            robot.RightMotor.setPower(-0.20);
            while (opModeIsActive() && runtime.seconds() < 0.5) ;

            // runtime.reset();
            //robot.GlyphLifter.setPower(.3);   //Lift Glyph Lifter
            //while (opModeIsActive() && runtime.seconds()<0.1);

            //robot.GlyphLifter.setPower(0);   //Stop Glyph Lifter

                /*runtime.reset();
                robot.LeftMotor.setPower(-0.4);  //Move Forward
                robot.RightMotor.setPower(-0.4);
                while (opModeIsActive() && runtime.seconds()<1);

                runtime.reset();
                robot.LeftMotor.setPower(0.4);  //Turn
                robot.RightMotor.setPower(-0.4);
                while (opModeIsActive() && runtime.seconds()<1.2);

                runtime.reset();
                robot.LeftMotor.setPower(-0.4);  //Move forward
                robot.RightMotor.setPower(-0.4);
                while (opModeIsActive() && runtime.seconds()<0.4);

                robot.GlyphGripper.setPosition(.9);  //Open Gripper

                runtime.reset();
                robot.LeftMotor.setPower(-0.50);
                robot.RightMotor.setPower(-0.50);
                while (opModeIsActive()&&(runtime.seconds())<1);
                */
        }

        //if it is not blue or red don't go anywhere
        else {
            robot.RightMotor.setPower(0);
            robot.LeftMotor.setPower(0);
        }
    }


          //  relativeLayout.post(new Runnable() {
              //  public void run() {
               //     relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
               // }
           // });
            //seting to start position*/




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
            robot.LeftMotor.setPower(Math.abs(speed));
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
























