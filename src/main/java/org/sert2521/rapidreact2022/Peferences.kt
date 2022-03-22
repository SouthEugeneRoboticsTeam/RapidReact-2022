package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

abstract class RobotPreferences {
    abstract val climberPID: Array<Double>
    abstract val actuatorPID: Array<Double>
    abstract val shooterPIDF: Array<Double>
    abstract val shooterBackPIDF: Array<Double>
    abstract val drivePID: Array<Double>
    abstract val driveFeedForward: Array<Double>

    abstract val ledLength: Int

    abstract val shooterRPM: Double
    abstract val shooterRPMTolerance: Double
    abstract val shooterStability: Double
    abstract val shooterExitRPM: Double

    abstract val shooterBackRPM: Double
    abstract val shooterBackStability: Double
    abstract val shooterBackRPMTolerance: Double

    abstract val shooterAveragePoints: Int
}

object CompetitionPreferences : RobotPreferences() {
    override val climberPID = arrayOf(3.4, 0.9, 0.0)
    override val actuatorPID = arrayOf(0.06, 0.1, 0.0)
    override val shooterPIDF = arrayOf(0.00040, 0.0, 0.0, 0.0002)
    override val shooterBackPIDF = arrayOf(0.000065, 0.0, 0.0, 0.000134)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val ledLength = 16

    override val shooterRPM = 2700.0
    override val shooterStability = 1800.0
    override val shooterRPMTolerance = 270.0
    override val shooterExitRPM = 2600.0

    override val shooterBackRPM = 4700.0
    override val shooterBackStability = 1400.0
    override val shooterBackRPMTolerance = 270.0

    override val shooterAveragePoints = 5
}

object PracticePreferences : RobotPreferences() {
    override val climberPID = arrayOf(0.0, 0.0, 0.0)
    override val actuatorPID = arrayOf(0.00, 0.0, 0.0)
    override val shooterPIDF = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    override val shooterBackPIDF = arrayOf(0.000068, 0.0, 0.0, 0.000148)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val ledLength = 56//58, but two are covered

    override val shooterRPM = 3100.0
    override val shooterRPMTolerance = 50.0
    override val shooterStability = 10.0
    override val shooterExitRPM = 2800.0

    override val shooterBackRPM = 4400.0
    override val shooterBackStability = 10.0
    override val shooterBackRPMTolerance = 100.0

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

    override val climbNext = JoystickButton(secondaryController, 5)
    override val endClimb = JoystickButton(secondaryController, 6)
    override val lockOne = JoystickButton(secondaryController, 1)
    override val lockTwo = JoystickButton(secondaryController, 2)
    override val climb = JoystickButton(secondaryController, 8)

    override val switchCameras = JoystickButton(primaryController, 6)
}

object DriveteamPreferences : ControlPreferences() {
    override val joystickX = { primaryController.rightX }
    override val joystickY = { -primaryController.leftY }

    override val speedIncrease = { primaryController.rightTriggerAxis }
    override val slowMode = JoystickButton(primaryController, 5)

    override val intake = JoystickButton(secondaryController, 8)
    override val overrideIndexer = JoystickButton(secondaryController, 10)
    override val runIndexer = JoystickButton(secondaryController, 9)
    override val outtake = JoystickButton(secondaryController, 7)
    override val shoot = JoystickButton(secondaryController, 14)
    override val rev = JoystickButton(secondaryController, 15)

    override val climbNext = JoystickButton(secondaryController, 5)
    override val endClimb = JoystickButton(secondaryController, 11)
    override val lockOne = JoystickButton(secondaryController, 1)
    override val lockTwo = JoystickButton(secondaryController, 2)
    override val climb = JoystickButton(secondaryController, 13)

    override val switchCameras = JoystickButton(primaryController, 6)
}

val robotPreferences = CompetitionPreferences
val controlPreferences = DriveteamPreferences
