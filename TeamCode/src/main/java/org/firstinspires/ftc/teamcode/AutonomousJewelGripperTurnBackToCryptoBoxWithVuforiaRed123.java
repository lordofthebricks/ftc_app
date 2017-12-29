package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

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

import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.javac.util.Constants.format;

/**
 * Created by jgp22 on 10/25/2017.
 */
@Autonomous(name = "Autonomous Red1 (((John))))")
public class AutonomousJewelGripperTurnBackToCryptoBoxWithVuforiaRed12 extends LinearOpMode {

    Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    static final double EXTEND_ARM = .25;
    static final double RETRACTED_ARM = .90;
    VuforiaLocalizer vuforia;
    NormalizedColorSensor colorSensor;
    View relativeLayout;
    OpenGLMatrix lastLocation = null;
    public static final String TAG = "Vuforia Navigation Sample2";

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        //ReadPictoGraphs();
        robot.Jewel.setPosition(EXTEND_ARM);
        while (opModeIsActive()) {
            robot.GlyphGripper.setPosition(.3);
            ReadPictoGraphs();
            {
                ReadJewels();
            }
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
                robot.LeftMotor.setPower(.45);
                robot.RightMotor.setPower(.45);
                while (runtime.seconds() < 1.2) {

                    runtime.reset();
                    robot.RightMotor.setPower(-.45);
                    robot.LeftMotor.setPower(.45);
                    while (runtime.seconds() < .5) {

                        runtime.reset();
                        robot.LeftMotor.setPower(.45);
                        robot.RightMotor.setPower(.45);
                        while (runtime.seconds() < .5) ;
                    }
                }
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
                robot.RightMotor.setPower(.50);
              robot.LeftMotor.setPower(-.50);

                while (opModeIsActive() && (runtime.seconds() <0.2));
                robot.Jewel.setPosition(RETRACTED_ARM);

                runtime.reset();
                robot.LeftMotor.setPower(0.50);  //Turn back
                robot.RightMotor.setPower(-0.50);
                while (opModeIsActive() && runtime.seconds()<0.2);

                runtime.reset();
                robot.GlyphLifter.setPower(.3);   //Lift Glyph Lifter
                while (opModeIsActive() && runtime.seconds()<0.1);

                robot.GlyphLifter.setPower(0);   //Stop Glyph Lifter

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
            else if (hsvValues[0] >100){
                robot.RightMotor.setPower(-.4);
               robot.LeftMotor.setPower(.4);
               // runtime.reset();
                while (opModeIsActive() && (runtime.seconds()<0.2));
                robot.Jewel.setPosition(RETRACTED_ARM);

                runtime.reset();
                robot.LeftMotor.setPower(-0.4);  //Turn back
                robot.RightMotor.setPower(0.4);
                while (opModeIsActive() && runtime.seconds()<0.2);

                runtime.reset();
                robot.GlyphLifter.setPower(.3);   //Lift Glyph Lifter
                while (opModeIsActive() && runtime.seconds()<0.1);

                robot.GlyphLifter.setPower(0);   //Stop Glyph Lifter

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
          //  relativeLayout.post(new Runnable() {
              //  public void run() {
               //     relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
               // }
           // });
            //seting to start position*/


    }
}
























