package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
@Disabled
public class Functions extends LinearOpMode
{
  Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    VuforiaLocalizer vuforia;
    NormalizedColorSensor colorSensor;
    View relativeLayout;
    @Override
    public void runOpMode()
    {


    }

    public class GoForwardForAHalfSecond{
        GoForwardForAHalfSecond(){
            robot.LeftMotor.setPower(0.6);
            robot.RightMotor.setPower(0.6);
            runtime.reset();
            while (opModeIsActive()&& (runtime.seconds()<0.5));

        }
    }
    public class GoForwardForOneSecond{
    GoForwardForOneSecond(){
        robot.LeftMotor.setPower(0.6);
        robot.RightMotor.setPower(0.6);
        runtime.reset();
        while (opModeIsActive()&& (runtime.seconds()<1));

    }
}
public class GoBackwardForHalfSecond{
    GoBackwardForHalfSecond(){
        robot.LeftMotor.setPower(-0.6);
        robot.RightMotor.setPower(-0.6);
        runtime.reset();
        while (opModeIsActive()&& (runtime.seconds()<0.5));

    }
}
public class GoBackwardsForOneSecond{
    GoBackwardsForOneSecond(){
        robot.LeftMotor.setPower(-0.6);
        robot.RightMotor.setPower(-0.6);
        runtime.reset();
        while (opModeIsActive()&& (runtime.seconds()<1));

    }
}
public class TurnRight{
    TurnRight(){
        robot.LeftMotor.setPower(0.6);
        robot.RightMotor.setPower(-0.6);
        runtime.reset();
        while (opModeIsActive()&& (runtime.seconds()<0.5));
    }

}
public class TurnLeft{
    TurnLeft(){
        robot.LeftMotor.setPower(-0.6);
        robot.RightMotor.setPower(0.6);
        runtime.reset();
        while (opModeIsActive()&& (runtime.seconds()<0.5));

    }
}
public class ReadPictographs{
    VuforiaLocalizer vuforia;
    ReadPictographs(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


        parameters.vuforiaLicenseKey = "AS+QHXr/////AAAAGVyp6kN2Dkf5mToAr7hhqvAbPwQkQrWVlv7Ish2458X+fqOCT95EqEN0d2doBzzhB5U2omktsSEjVuuEshc3eFPgnfatgK7/jf9o89JgSEadKAMwjMTcE0q6nqmLXWQnzwxs/XHLNkU4uxwnjwtFFr5Lb2zU1VGl9DqxOme3jV9xVrbKJSOVfB1qXlxIMAs5Ggu0EdOeJb7XZcbA9Q0jzKdbIxPrD4CO7sxbaOBL0mUnzURGH2RWBF26zkcqKN4/HZuMu3ud/aQja1etyLAn9VOkk4e56t0Wm1xss9i+V4E49bJyCA+Fyu+nSlovTSqEkBUvybuhiCvuEAWeWsWOMO/sRniL5kWG61r6sGVSmPqK";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
            } else {
                telemetry.addData("VuMark", "not visible");


                telemetry.update();
            }

        }
    }
    }
    public class ReadJewels{
        ReadJewels(){
            int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
            relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
            try {
                RunSample();
            } finally {
                relativeLayout.post(new Runnable() {
                    public void run() {
                        relativeLayout.setBackgroundColor(Color.WHITE);
                    }
                });
            }
        }
        }
        public void RunSample(){
                float[] hsvValues = new float[3];
                final float values[] = hsvValues;
                boolean bPrevState = false;
                boolean bCurrState = false;
                colorSensor = hardwareMap.get(NormalizedColorSensor.class, "JewelReader");
                if (colorSensor instanceof SwitchableLight) {
                    ((SwitchableLight)colorSensor).enableLight(true);
                }
                waitForStart();
                while (opModeIsActive()) {
                    bCurrState = gamepad1.x;
                    if (bCurrState != bPrevState) {
                        if (bCurrState) {
                            if (colorSensor instanceof SwitchableLight) {
                                SwitchableLight light = (SwitchableLight)colorSensor;
                                light.enableLight(!light.isLightOn());
                            }
                        }
                    }
                    bPrevState = bCurrState;
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

                    if (hsvValues[0] >100){
                        robot.RightMotor.setPower(-.6);
                        robot.LeftMotor.setPower(-.6);
                        while (opModeIsActive() && (runtime.seconds() < 2));
                    }
                    else if (hsvValues[0] <10){
                        robot.RightMotor.setPower(.6);
                        robot.LeftMotor.setPower(.6);
                        while (opModeIsActive() && (runtime.seconds()<2));
                    }
                    else
                        robot.RightMotor.setPower(0);
                    robot.LeftMotor.setPower(0);
                    relativeLayout.post(new Runnable() {
                        public void run() {
                            relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                        }
                    });
                }
            }
        }



