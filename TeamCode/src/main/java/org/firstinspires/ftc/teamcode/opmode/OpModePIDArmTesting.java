package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command.claw.*;
import org.firstinspires.ftc.teamcode.command.lift.*;
import org.firstinspires.ftc.teamcode.command.arm.*;

import org.firstinspires.ftc.teamcode.opmode.baseOpModes.BaseTotalOpMode;

@TeleOp(name = "Arm PID TeleOp")
public class OpModePIDArmTesting extends BaseTotalOpMode {
    // image of gamepad: https://gm0.org/en/latest/_images/logitech-f310.png

    private GamepadEx driverOp1;

    private DropCone dropCone;

    private GrabCone grabCone;
    private GoHome goHome;

    private GoScore goScore;

    private LiftUp liftUp;

    private LiftDown liftDown;

    private LiftStop liftStop;

    private Button armManip, slideManip, clawManip;

    private Button moveGround, moveLow, moveMedium, moveHigh, moveCancel, isUp, isDown;


    @Override
    public void initialize() {
        super.initialize();
        /*
        Player1
            START -> moveCancel (Cancel all current movement)
            A -> moveGround (Ground junction)
            X -> moveLow (Low junction)
            B -> moveMedium (Medium junction)
            Y -> moveHigh (High junction)
              Y
            X   B
              A

            right_bumper -> alternates between claw open and close
            dpad_up -> slides go up
            dpad_down -> slides go down
         */

        driverOp1 = new GamepadEx(gamepad1);

        //toggles between open and close
        grabCone = new GrabCone(arm);
        dropCone = new DropCone(arm);
        clawManip = (new GamepadButton(driverOp1, GamepadKeys.Button.RIGHT_BUMPER)).toggleWhenPressed(grabCone, dropCone);

        //flips between arm out and arm in
        goHome = new GoHome(arm);
        goScore = new GoScore(arm);
        armManip = (new GamepadButton(driverOp1, GamepadKeys.Button.LEFT_BUMPER)).toggleWhenPressed(goHome, goScore);


        liftStop = new LiftStop(arm);
        //manual lift code
        liftUp = new LiftUp(arm);
        isUp = (new GamepadButton(driverOp1, GamepadKeys.Button.DPAD_UP)).whileHeld(liftUp);

        isDown = (new GamepadButton(driverOp1, GamepadKeys.Button.DPAD_DOWN)).whileHeld(liftDown);


        // automatic junction code [quite mid actually(since perkeet wrote it), everything else is w code(Since I wrote it)]
        //TODO REMEMBER TO TUNE VALUES IN ArmSubsystem_OG BEFORE TRYING TO USE
        moveCancel = (new GamepadButton(driverOp1, GamepadKeys.Button.START)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.NONE)
        );
        moveGround = (new GamepadButton(driverOp1, GamepadKeys.Button.A)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.GROUND)
        );
        moveLow = (new GamepadButton(driverOp1, GamepadKeys.Button.X)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.LOW)
        );
        moveMedium = (new GamepadButton(driverOp1, GamepadKeys.Button.B)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.MEDIUM)
        );
        moveHigh = (new GamepadButton(driverOp1, GamepadKeys.Button.Y)).whenPressed(
                new SetJunction(arm, ArmSubsystem_OG.Junction.HIGH)
        );
        register(arm);
    }
}
