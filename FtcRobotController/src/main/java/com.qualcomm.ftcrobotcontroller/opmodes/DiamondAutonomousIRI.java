package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// DiamondAutonomousRed
//
//with four inch wheels and 40-1 motors, 1 inch = roughly 43 encoder units

import java.util.Timer;

/**
 * Provide a basic autonomous operational mode that uses the left and right
 * drive motors and associated encoders implemented using a state machine for
 * the DiamondBot, when playing as red.
 *
 * @author SSI Robotics/Max Davy/DiamondBlades
 * @version 2016-03-04-17-08
 */
public class DiamondAutonomousIRI extends DiamondBotHardware_IRI

{
    //--------------------------------------------------------------------------
    //
    // PushBotAuto
    //
    /**
     * Construct the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public DiamondAutonomousIRI()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // AutonomousRed

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels and arms.
        //
        reset_drive_encoders();
        m_bumper_position(.5);
        m_back_bumper_position(.5);
        m_triggers_position(0);
        m_other_triggers_position(.89);
        int v_state = 0;

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop ()

    {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
        //
        // Synchronize the state machine and hardware.
        //
        case 0:
            //
            // Reset the encoders to ensure they are at a known good value.
            //
            reset_drive_encoders();

            //
            // Transition to the next state when this method is called again.
            //
            v_state++;

            break;
        //
        // Drive forward until the encoders exceed the specified values.
        //
        case 1:
            //
            // Tell the system that motor encoders will be used.  This call MUST
            // be in this state and NOT the previous or the encoders will not
            // work.  It doesn't need to be in subsequent states.
            //
            run_using_encoders ();

            //
            // Start the drive wheel motors at full power.
            //
            set_drive_power (1.0f, 1.0f);

            //
            // Have the motor shafts turned the required amount?
            //
            // If they haven't, then the op-mode remains in this state (i.e this
            // block will be executed the next time this method is called).
            //
            if (have_drive_encoders_reached (inch_to_encoder(111), inch_to_encoder(111)))
            {
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                reset_drive_encoders ();

                //
                // Stop the motors.
                //
                set_drive_power (0.0f, 0.0f);

                //
                // Transition to the next state when this method is called
                // again.
                //
                v_state++;
            }
            break;
            case 2:
                if (has_left_drive_encoder_reset()&& has_right_drive_encoder_reset())
                {
                    v_state++;
                }
                break;

            case 3:
                //
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //
                run_using_encoders ();

                //
                // Start the drive wheel motors at full power.
                //
                set_drive_power (-1.0f, 1.0f);

                //
                // Have the motor shafts turned the required amount?
                //
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                //
                if (have_drive_encoders_reached (degree_to_encoder(-45), degree_to_encoder(45)))
                {
                    //
                    // Reset the encoders to ensure they are at a known good value.
                    //
                    reset_drive_encoders ();

                    //
                    // Stop the motors.
                    //
                    set_drive_power (0.0f, 0.0f);

                    //
                    // Transition to the next state when this method is called
                    // again.
                    //
                    v_state++;
                }
                break;
            case 4:
                if (has_left_drive_encoder_reset()&& has_right_drive_encoder_reset())
                {
                    v_state++;
                }
                break;
            case 5:
                //
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //
                run_using_encoders ();

                //
                // Start the drive wheel motors at full power.
                //
                set_drive_power (1.0f, 1.0f);

                //
                // Have the motor shafts turned the required amount?
                //
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                //
                if (have_drive_encoders_reached(inch_to_encoder(16), inch_to_encoder(16)))
                {
                    //
                    // Reset the encoders to ensure they are at a known good value.
                    //
                    reset_drive_encoders ();

                    //
                    // Stop the motors.
                    //
                    set_drive_power (0.0f, 0.0f);

                    //
                    // Transition to the next state when this method is called
                    // again.
                    //
                    v_state++;
                }
                break;
            case 6:
                //
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //
                run_using_encoders ();

                //
                // Start the arm motor at half power.
                //
                m_arm_power(.5f);

                //
                // Has the motor shaft turned the required amount?
                //
                // If it hasn't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                //
                if (has_arm_encoder_reached(385))
                {
                    //
                    // Reset the encoders to ensure they are at a known good value.
                    //
                    reset_drive_encoders ();
                    reset_arm_encoder();

                    //
                    // Stop the motors.
                    //
                    m_arm_power (0.0f);

                    //
                    // Transition to the next state when this method is called
                    // again.
                    //
                    v_state++;
                }
                break;
            case 7:
                m_tape_power(1.0f);
                Timer timer = new Timer();
                try {
                    timer.wait(1000);
                } catch (InterruptedException e) {
                    m_tape_power(0.0f);
                }
                m_tape_power(0.0f);
                v_state++;
                break;
            //
        // Perform no action - stay in this case until the OpMode is stopped.
        // This method will still be called regardless of the state machine.
        //
        default:
            //
            // The autonomous actions have been accomplished (i.e. the state has
            // transitioned into its final state.
            //
            break;
        }

        //
        // Send telemetry data to the driver station.
        //
        //update_telemetry (); // Update common telemetry
        if (v_state == 0)
        {
            telemetry.addData("18", "State: " + v_state + "Initializing");
        }
        else if (v_state == 1 || v_state == 5)
        {
            telemetry.addData("18", "State: " + v_state + "Driving Forward");
        }
        else if (v_state == 2 || v_state == 4){
            telemetry.addData("18", "State: " + v_state + "Resetting Drive Encoders");
        }
        else if (v_state == 3){
            telemetry.addData("18", "State: " + v_state + "Turning 45 left");
        }
        else if (v_state == 6){
            telemetry.addData("18", "State: " + v_state + "Moving Arm");
        }
        else
        {
            telemetry.addData("18", "State: " + v_state + "Completed");
        }

    } // loop

    //--------------------------------------------------------------------------
    //
    // v_state
    //
    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int v_state = 0;

} // PushBotAuto
