package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorTargetVelocityParameters;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous Encoder:(", group = "Utilities")
public class Encoder extends LinearOpMode {

    public DcMotor BL, BR, FR, FL, SL, SR, UL,UR;
    public Servo SWL,SWR,SC,SB;

    static final double COUNTS_PER_MOTOR_REV = 537.7;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 3.77953;     // For figuring circumference
    public final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public final int MaxExtension = 1000;
    public final int MinExtension = 0;
    public final double ticks_per_angle = 7.12;


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

        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);

        SL.setDirection(DcMotor.Direction.REVERSE);
        SR.setDirection(DcMotor.Direction.REVERSE);

        SWL.setDirection(Servo.Direction.FORWARD);
        SWR.setDirection(Servo.Direction.FORWARD);
        SC.setDirection(Servo.Direction.FORWARD);
        SB.setDirection(Servo.Direction.FORWARD);

        UL.setDirection(DcMotor.Direction.FORWARD);
        UR.setDirection(DcMotor.Direction.FORWARD);

        reset_encoders();
        SL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        SL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        upBasket(1000);
        moveForward(10,1);
        moveBackward(10,1);
        moveLeft(10,1);
        moveRight(10,1);
        moveLeft(10,1);
        turnLeft(90);
        turnRight(90);
        clawOut();
        downArm(1000);
        openClaw(1000);
        closeClaw(1000);
        upArm(1000);
        downBasket(1000);
        clawIn();
        openClaw(1000);



        // SCORE FIRST ONEEEEEEEEEEEEEE
        moveLeft(10,1);
        moveBackward(13,1);
        turnLeft(45);
        four_up(2000);
        upBasket(1000);
        four_down(2000);
        turnLeft(45); // straightened

        // SCORE FAR RIGHT
        moveRight(3,1); // align with first block far right
        moveBackward(8,1);
        clawOut();
        openClaw(1000);
        downBasket(0);
        downArm(1500);
        closeClaw(1000);
        upArm(1000);
        clawIn();
        openClaw(1000);
        clawOut(); // Maybe bc claw be stuck inside basket maybe // FIX THIS FIXXXXXXXXXXXXXXXXXXXXXXXX
        moveForward(8,1);
        moveLeft(3,1);
        turnRight(45);
        four_up(2000);
        upBasket(1000);
        four_down(1000);
        turnLeft(45); // Strightned


        // 2nd
        moveLeft(7,1);
        moveBackward(8,1);
        clawOut();
        openClaw(1000);
        downBasket(0);
        downArm(1000);
        clawIn();
        openClaw(1000);
        clawOut(); // fix this toooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
        moveForward(8,1);
        moveRight(7,1);
        turnRight(45);
        four_up(2000);
        upBasket(1000);
        four_down(1000);
        turnLeft(45); // Strightned

        //3rd
        // probably turn left 45 then back then extend and grab and all that just a lil different

        // we just need to find the turning the numbers
















    }


    public void moveForward(double inches, double power) {

        int targetPosition = (int)(inches * COUNTS_PER_INCH);

        FL.setTargetPosition(targetPosition);
        BL.setTargetPosition(targetPosition);
        FR.setTargetPosition(targetPosition);
        BR.setTargetPosition(targetPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(power);

        while(opModeIsActive() & (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("DRIVING FORWARDS at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();

        reset_encoders();

    }

    //-----------------------------------------------------------------------------------------

    public void moveRight(double inches, double power) {

        int targetPosition = (int)(inches * COUNTS_PER_INCH);

        FL.setTargetPosition(targetPosition);
        BL.setTargetPosition(-targetPosition);
        FR.setTargetPosition(-targetPosition);
        BR.setTargetPosition(targetPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(power);

        while(opModeIsActive() & (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("STRAFING RIGHT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();

        reset_encoders();
    }

    //-----------------------------------------------------------------------------------------

    public void moveBackward(double inches, double power) {

        int targetPosition = (int)(inches * COUNTS_PER_INCH);

        FL.setTargetPosition(-targetPosition);
        BL.setTargetPosition(-targetPosition);
        FR.setTargetPosition(-targetPosition);
        BR.setTargetPosition(-targetPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(-power);

        while(opModeIsActive() & (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("DRIVING BACKWARDS at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();

        reset_encoders();

    }

    //-----------------------------------------------------------------------------------------

    public void moveLeft(double inches, double power) {

        int targetPosition = (int)(inches * COUNTS_PER_INCH);

        FL.setTargetPosition(-targetPosition);
        BL.setTargetPosition(targetPosition);
        FR.setTargetPosition(targetPosition);
        BR.setTargetPosition(-targetPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(-power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(-power);

        while(opModeIsActive() & (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("STRAFING LEFT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();

        reset_encoders();
    }

    public void turnLeft(int angle) {

        int targetPosition = (int) (angle * ticks_per_angle);

        FL.setTargetPosition(-targetPosition);
        BL.setTargetPosition(-targetPosition);
        FR.setTargetPosition(targetPosition);
        BR.setTargetPosition(targetPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(-1);
        BL.setPower(-1);
        FR.setPower(1);
        BR.setPower(1);

        while(opModeIsActive() & (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("TURNING LEFT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();

        reset_encoders();
    }

    public void turnRight(int angle) {

        int targetPosition = (int) (angle * ticks_per_angle );

        FL.setTargetPosition(targetPosition);
        BL.setTargetPosition(targetPosition);
        FR.setTargetPosition(-targetPosition);
        BR.setTargetPosition(-targetPosition);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setPower(1);
        BL.setPower(1);
        FR.setPower(-1);
        BR.setPower(-1);

        while(opModeIsActive() & (FL.isBusy() || FR.isBusy() || BL.isBusy() || BR.isBusy())) {
            telemetry.addData("STRAFING LEFT at", "%7d :%7d :%7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
            telemetry.update();
        }

        stop_motor();

        reset_encoders();
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

    public void four_up(int time) {
        UL.setPower(1);
        UR.setPower(1);
        sleep(time);
        UL.setPower(0);
        UR.setPower(0);

    }

    public void four_down(int time) {
        UL.setPower(-1);
        UR.setPower(-1);
        sleep(time);
        UL.setPower(0);
        UR.setPower(0);

    }

    public void clawOut() {
        SL.setTargetPosition(MaxExtension);
        SR.setTargetPosition(MaxExtension);
        SL.setPower(.8);
        SR.setPower(.8);

        while (opModeIsActive() && (SL.isBusy() && SR.isBusy())) {

        }

        SL.setPower(0);
        SR.setPower(0);

    }

    public void clawIn() {

        SL.setTargetPosition(MinExtension);
        SR.setTargetPosition(MinExtension);
        SL.setPower(-.8);
        SR.setPower(-.8);

        while (opModeIsActive() && (SL.isBusy() && SR.isBusy())) {

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




}

