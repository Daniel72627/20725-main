package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous Encoder New", group = "Utilities")
public class Encoder_Autonomous_Charles extends LinearOpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;

    static final double COUNTS_PER_MOTOR_REV = 960;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 3.77953;     // For figuring circumference
    public final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public final int MaxExtension = 250;
    public final int MinExtension = -1;
    public final double ticks_per_angle = 16;


    @Override
    public void runOpMode() {


        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");

        SL = hardwareMap.get(DcMotor.class, "LH");
        SR = hardwareMap.get(DcMotor.class, "RH");

        UL = hardwareMap.get(DcMotor.class, "4b");
        UR = hardwareMap.get(DcMotor.class, "4b2");

        SWL = hardwareMap.get(Servo.class, "Lc");
        SWR = hardwareMap.get(Servo.class, "Rc");
        SC = hardwareMap.get(Servo.class, "L");
        SB = hardwareMap.get(Servo.class, "R");

        BL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.REVERSE);

        SL.setDirection(DcMotor.Direction.REVERSE);
        SR.setDirection(DcMotor.Direction.FORWARD);

        SWL.setDirection(Servo.Direction.FORWARD);
        SWR.setDirection(Servo.Direction.FORWARD);
        SC.setDirection(Servo.Direction.FORWARD);
        SB.setDirection(Servo.Direction.FORWARD);

        UL.setDirection(DcMotor.Direction.REVERSE);
        UR.setDirection(DcMotor.Direction.REVERSE);


        reset_encoders();
        SL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        SL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        moveLeft(10,1);
        moveBackward(13,1);
        turnLeft(45);
        four_up();
        upBasket(3000);
        downBasket(1000);
        four_down();
        turnLeft(45);

        // SCORE FAR RIGHT
        moveRight(3,1); // align with first block far right
        moveBackward(7,1);
        clawOut();
        openClaw(2000);
        downBasket(0);
        downArm(2000);
        closeClaw(1000);
        upArm(1500);
        clawIn();
        openClaw(1000);
        moveForward(7,1);
        moveLeft(3,1);
        turnRight(45);
        sleep(2000);
        four_up();
        upBasket(3000);
        downBasket(1000);
        four_down();
        turnLeft(45); // Strightned


        // 2nd
        moveLeft(7,1);
        moveBackward(7,1);
        clawOut();
        openClaw(2000);
        downBasket(0);
        downArm(2000);
        closeClaw(1000);
        upArm(1500);
        clawIn();
        openClaw(1000);
        moveForward(7,1);
        moveRight(7,1);
        turnRight(45);
        sleep(2000);
        four_up();
        upBasket(3000);
        downBasket(1000);
        four_down();
        turnLeft(45); // Strightned

        //3rd

        turnLeft(33);
        moveBackward(1,1);
        clawOut();
        openClaw(2000);
        downBasket(0);
        downArm(2000);
        closeClaw(1000);
        upArm(1500);
        clawIn();
        openClaw(1000);
        moveForward(1,1);
        turnRight(78);
        sleep(2000);
        four_up();
        upBasket(3000);
        four_down();
        turnLeft(45); // Strightned





















    }


    public void moveForward(double inches, double power) {

        int targetPosition = (int) (inches * COUNTS_PER_INCH);

        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

        while (FL.getCurrentPosition() < targetPosition ||
                FR.getCurrentPosition() < targetPosition ||
                BL.getCurrentPosition() < targetPosition ||
                BR.getCurrentPosition() < targetPosition) {
            telemetry.addData("MOVING FORWARDS at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }
        stop_motor();
        resetAndRunEncoders();
    }

//-----------------------------------------------------------------------------------------

    public void moveRight(double inches, double power) {

        int targetPosition = (int) (inches * COUNTS_PER_INCH);

        FL.setPower(power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(power);

        while (FL.getCurrentPosition() < targetPosition ||
                FR.getCurrentPosition() > -targetPosition ||
                BL.getCurrentPosition() > -targetPosition ||
                BR.getCurrentPosition() < targetPosition) {
            telemetry.addData("MOVING RIGHT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }
        stop_motor();
        resetAndRunEncoders();
    }

//-----------------------------------------------------------------------------------------

    public void moveBackward(double inches, double power) {

        int targetPosition = (int) (inches * COUNTS_PER_INCH);


        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(-power);

        while (FL.getCurrentPosition() > -targetPosition ||
                FR.getCurrentPosition() > -targetPosition ||
                BL.getCurrentPosition() > -targetPosition ||
                BR.getCurrentPosition() > -targetPosition) {
            telemetry.addData("MOVING BACKWARD at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }
        stop_motor();
        resetAndRunEncoders();

    }

//-----------------------------------------------------------------------------------------

    public void moveLeft(double inches, double power) {

        int targetPosition = (int) (inches * COUNTS_PER_INCH);

        FL.setPower(-power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(-power);

        while (FL.getCurrentPosition() > -targetPosition ||
                FR.getCurrentPosition() < targetPosition ||
                BL.getCurrentPosition() < targetPosition ||
                BR.getCurrentPosition() > -targetPosition) {
            telemetry.addData("MOVING LEFT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }
        stop_motor();
        resetAndRunEncoders();
    }

    public void turnLeft(int angle) {

        int targetPosition = (int)(angle * ticks_per_angle);

        FL.setPower(-1);
        BL.setPower(-1);
        FR.setPower(1);
        BR.setPower(1);

        while (FL.getCurrentPosition() > -targetPosition ||
                BL.getCurrentPosition() > -targetPosition ||
                FR.getCurrentPosition() < targetPosition ||
                BR.getCurrentPosition() < targetPosition) {
            telemetry.addData("TURNING LEFT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }
        stop_motor();
        resetAndRunEncoders();
    }

    public void turnRight(int angle) {

        int targetPosition = (int) (angle * ticks_per_angle);

        FL.setPower(1);
        BL.setPower(1);
        FR.setPower(-1);
        BR.setPower(-1);

        while (FL.getCurrentPosition() < targetPosition ||
                BL.getCurrentPosition() < targetPosition ||
                FR.getCurrentPosition() > -targetPosition ||
                BR.getCurrentPosition() > -targetPosition) {
            telemetry.addData("TURNING RIGHT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();
        resetAndRunEncoders();

    }

    public void closeClaw(int time) {
        SC.setPosition(0);
        sleep(time);
    }

    public void openClaw(int time) {
        SC.setPosition(.3);
        sleep(time);
    }

    public void downArm(int time) {
        SWL.setPosition(.96);
        SWR.setPosition(.04);
        sleep(time);
    }

    public void upArm(int time) {
        SWL.setPosition(.4);
        SWR.setPosition(.6);
        sleep(time);
    }

    public void upBasket(int time) {
        SB.setPosition(.1);
        sleep(time);
    }

    public void downBasket(int time) {
        SB.setPosition(.7);
        sleep(time);
    }

    public void four_up() {
        UL.setPower(1);
        UR.setPower(1);

    }

    public void four_down() {
        UL.setPower(-1);
        UR.setPower(-1);
    }

    public void clawOut() {

        SL.setPower(1);
        SR.setPower(1);

        while (SL.getCurrentPosition() < MaxExtension ||
                SR.getCurrentPosition() < MaxExtension) {
            telemetry.addData("SLIDES OUT at", "%7d :%7d",
                    SL.getCurrentPosition(),
                    SR.getCurrentPosition());
            telemetry.update();
        }

        SL.setPower(0);
        SR.setPower(0);

    }

    public void clawIn() {

        SL.setPower(-1);
        SR.setPower(-1);

        while (SL.getCurrentPosition() < MinExtension ||
                SR.getCurrentPosition() < MinExtension) {
            telemetry.addData("SLIDES IN at", "%7d :%7d",
                    SL.getCurrentPosition(),
                    SR.getCurrentPosition());
            telemetry.update();
        }

        SL.setPower(0);
        SR.setPower(0);

    }

    public void stop_motor() {
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        BL.setPower(0);
    }

    public void reset_encoders() {
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }



    public void resetAndRunEncoders() {
        reset_encoders();
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }




}

