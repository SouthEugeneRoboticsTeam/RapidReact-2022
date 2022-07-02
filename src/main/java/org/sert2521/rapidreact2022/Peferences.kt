package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

abstract class RobotPreferences {
    abstract val climberPID: Array<Double>
    abstract val actuatorPID: Array<Double>
    abstract val shooterPID: Array<Double>
    abstract val shooterFeedForward: Array<Double>
    abstract val shooterBackPID: Array<Double>
    abstract val shooterBackFeedForward: Array<Double>
    abstract val drivePID: Array<Double>
    abstract val driveFeedForward: Array<Double>

    abstract val ledLength: Int

    abstract val shooterFrontRPM: Double
    abstract val shooterRPMTolerance: Double
    abstract val shooterFrontStability: Double

    abstract val shooterBackRPM: Double
    abstract val shooterBackStability: Double
    abstract val shooterBackRPMTolerance: Double
    abstract val shooterBackExitRPMDrop: Double

    abstract val shooterAveragePoints: Int
}

object CompetitionPreferences : RobotPreferences() {
    override val climberPID = arrayOf(4.2, 1.4, 0.0)
    override val actuatorPID = arrayOf(0.06, 0.1, 0.0)
    override val shooterPID = arrayOf(0.001117, 0.0, 0.0)
    override val shooterFeedForward = arrayOf(0.15936, 0.00206466666, 0.00008552666)
    override val shooterBackPID = arrayOf(0.0012681, 0.0, 0.0)
    override val shooterBackFeedForward = arrayOf(0.659, 0.00134198333, 0.00006685)
    override val drivePID = arrayOf(4.9479, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.3455, 3.1133, 0.0)//9.9377

    override val ledLength = 16

    override val shooterFrontRPM = 2500.0
    override val shooterFrontStability = 1600.0
    override val shooterRPMTolerance = 400.0

    override val shooterBackRPM = 4500.0
    override val shooterBackStability = 1600.0
    override val shooterBackRPMTolerance = 400.0
    //Increase this if the balls are hitting each other
    override val shooterBackExitRPMDrop = 200.0

    override val shooterAveragePoints = 5
}

object PracticePreferences : RobotPreferences() {
    override val climberPID = arrayOf(0.0, 0.0, 0.0)
    override val actuatorPID = arrayOf(0.00, 0.0, 0.0)
    override val shooterPID = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    override val shooterFeedForward = arrayOf(0.0, 0.0, 0.0)
    override val shooterBackPID = arrayOf(0.000068, 0.0, 0.0, 0.000148)
    override val shooterBackFeedForward = arrayOf(0.0, 0.0, 0.0)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val ledLength = 56//58, but two are covered

    override val shooterFrontRPM = 3100.0
    override val shooterRPMTolerance = 50.0
    override val shooterFrontStability = 10.0

    override val shooterBackRPM = 4400.0
    override val shooterBackStability = 10.0
    override val shooterBackRPMTolerance = 100.0
    //Goes down to like 3750
    override val shooterBackExitRPMDrop = 200.0

    override val shooterAveragePoints = 5
}

abstract class ControlPreferences {
    val primaryController = XboxController(PRIMARY_CONTROLLER_ID)
    val secondaryController = Joystick(SECONDARY_CONTROLLER_ID)

    abstract val joystickX: () -> Double
    abstract val joystickY: () -> Double

    abstract val speedIncrease: () -> Double
    abstract val slowMode: JoystickButton

    abstract val intake: JoystickButton
    abstract val overrideIndexer: JoystickButton
    abstract val runIndexer: JoystickButton
    abstract val outtake: JoystickButton

    abstract val shoot: JoystickButton
    abstract val rev: JoystickButton
    abstract val forceShoot: JoystickButton

    abstract val climbNext: JoystickButton
    abstract val endClimb: JoystickButton
    abstract val lockOne: JoystickButton
    abstract val lockTwo: JoystickButton
    abstract val climb: JoystickButton

    abstract val switchCameras: JoystickButton
}

object SoftwarePreferences : ControlPreferences() {
    override val joystickX = { primaryController.leftX }
    override val joystickY = { -primaryController.leftY }

    override val speedIncrease = { primaryController.rightTriggerAxis }
    override val slowMode = JoystickButton(primaryController, 5)

    override val intake = JoystickButton(primaryController, 3)
    override val overrideIndexer = JoystickButton(primaryController, -1)
    override val runIndexer = JoystickButton(primaryController, -2)
    override val outtake = JoystickButton(primaryController, 2)

    override val shoot = JoystickButton(primaryController, 4)
    override val rev = JoystickButton(primaryController, -3)
    override val forceShoot = JoystickButton(primaryController, -4)

    override val climbNext = JoystickButton(secondaryController, 5)
    override val endClimb = JoystickButton(secondaryController, 6)
    override val lockOne = JoystickButton(secondaryController, 1)
    override val lockTwo = JoystickButton(secondaryController, 2)
    override val climb = JoystickButton(secondaryController, 8)

    override val switchCameras = JoystickButton(primaryController, 6)
}

object DriveteamPreferences : ControlPreferences() {
    override val joystickX = { primaryController.leftX }
    override val joystickY = { -primaryController.leftY }

    override val speedIncrease = { primaryController.rightTriggerAxis }
    override val slowMode = JoystickButton(primaryController, 5)

    override val intake = JoystickButton(primaryController, 2)
    override val overrideIndexer = JoystickButton(secondaryController, 10)
    override val runIndexer = JoystickButton(secondaryController, 9)
    override val outtake = JoystickButton(primaryController, 4)

    override val shoot = JoystickButton(primaryController, 1)
    override val rev = JoystickButton(secondaryController, 15)
    override val forceShoot = JoystickButton(secondaryController, 16)

    override val climbNext = JoystickButton(secondaryController, 5)
    override val endClimb = JoystickButton(secondaryController, 11)
    override val lockOne = JoystickButton(secondaryController, 1)
    override val lockTwo = JoystickButton(secondaryController, 2)
    override val climb = JoystickButton(secondaryController, 6)

    override val switchCameras = JoystickButton(primaryController, 6)
}

val robotPreferences = CompetitionPreferences
val controlPreferences = DriveteamPreferences
