/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
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

import static org.firstinspires.ftc.teamcode.Red1.JEWEL_SPEED;
import static org.firstinspires.ftc.teamcode.CopyofVuforiaWithEncoderBlue1.KILL_SPEED;
import static org.firstinspires.ftc.teamcode.CopyofVuforiaWithEncoderBlue1.OPEN_POSITION;

/**
 * This OpMode illustrates the basics of using the Vuforia engine to determine
 * the identity of Vuforia VuMarks encountered on the field. The code is structured as
 * a LinearOpMode. It shares much structure with {@link ConceptVuforiaNavigation}; we do not here
 * duplicate the core Vuforia documentation found there, but rather instead focus on the
 * differences between the use of Vuforia for navigation vs VuMark identification.
 *
 * @see ConceptVuforiaNavigation
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained in {@link ConceptVuforiaNavigation}.
 */

@Autonomous(name="Blue1", group ="Concept")

public class Blue1 extends LinearOpMode {

    Hardware7039 robot = new Hardware7039();
    ElapsedTime runtime = new ElapsedTime();
    static final double EXTEND_ARM = .2;
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
robot.RightMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.LeftMotor.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();
        robot.GlyphGripper.setPosition(.3);
        robot.Jewel.setPosition(EXTEND_ARM);
        //robot.Jewel.setPosition(EXTEND_ARM);
        telemetry.addData("Arm", EXTEND_ARM);
        telemetry.update();
        while (opModeIsActive()) {


          //  ReadPictoGraphs();

           // ReadPictoGraphs();
ReadJewels();
            // robot.Jewel.setPosition(EXTEND_ARM);
            //   robot.Jewel.setPosition(RETRACTED_ARM);



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


            runtime.reset();
            if(vuMark == RelicRecoveryVuMark.CENTER) {
                encoderDrive(FORWARD_SPEED, -24.5, 24.5, 4.0 );
                encoderDrive(FORWARD_SPEED, 32, 32, 4.0);
                encoderDrive(TURN_SPEED, 12.5, -12.5, 4.0 );
                encoderDrive(FORWARD_SPEED, 9.5, 9.5, 4.0);
                robot.GlyphGripper.setPosition(OPEN_POSITION);
                encoderDrive(FORWARD_SPEED, -1, -1, 4.0);
                while (opModeIsActive()){
                    encoderDrive(KILL_SPEED,0, 0, 4.0);
                }

            }
            if (vuMark ==RelicRecoveryVuMark.LEFT) {
                encoderDrive(FORWARD_SPEED, -24.5, 24.5, 4.0 );
                encoderDrive(FORWARD_SPEED, 26, 26, 4.0);
                encoderDrive(TURN_SPEED, 12.5, -12.5, 4.0 );
                encoderDrive(FORWARD_SPEED, 9.5, 9.5, 4.0);
                robot.GlyphGripper.setPosition(OPEN_POSITION);
                encoderDrive(FORWARD_SPEED, -1, -1, 4.0);
                while (opModeIsActive()){
                    encoderDrive(KILL_SPEED, 0, 0, 4.0);
                }

            }
            if(vuMark == RelicRecoveryVuMark.RIGHT) {
                encoderDrive(FORWARD_SPEED, -24.5, 24.5, 4.0);
                encoderDrive(FORWARD_SPEED,39.5, 39.5, 4.0);
                encoderDrive(TURN_SPEED, 12.5, -12.5, 4.0 );
                encoderDrive(FORWARD_SPEED, 9.5, 9.5, 4.0);
                robot.GlyphGripper.setPosition(OPEN_POSITION);
                encoderDrive(FORWARD_SPEED, -1, -1, 4.0);
                while (opModeIsActive()){
                    encoderDrive(KILL_SPEED, 0, 0, 4.0);
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
            encoderDrive(JEWEL_SPEED, -12, 12, 4.0  );
            robot.Jewel.setPosition(RETRACTED_ARM);
            encoderDrive(JEWEL_SPEED, 12, -12, 4.0);
            ReadPictoGraphs();
        }
        //if blue knock off blue
        else if (hsvValues[0] > 100) {
            encoderDrive(JEWEL_SPEED, 12, -12, 4.0);
            robot.Jewel.setPosition(RETRACTED_ARM);
            encoderDrive(JEWEL_SPEED, -12, 12, 4.0);
            ReadPictoGraphs();
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
