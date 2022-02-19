package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.JoystickButton

abstract class RobotPreferences {
    abstract val climberPID: Array<Double>
    abstract val actuatorPID: Array<Double>
    abstract val shooterPIDF: Array<Double>
    abstract val drivePID: Array<Double>
    abstract val driveFeedForward: Array<Double>

    abstract val shooterRPM: Double
    abstract val shooterTolerance: Double
    abstract val shooterIdleRPM: Double
}

object CompetitionPreferences : RobotPreferences() {
    override val climberPID = arrayOf(0.0, 0.0, 0.0)
    override val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    override val shooterPIDF = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val shooterRPM = 5000.0
    override val shooterTolerance = 100.0
    override val shooterIdleRPM = 0.0
}

object PracticePreferences : RobotPreferences() {
    override val climberPID = arrayOf(0.0, 0.0, 0.0)
    override val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    override val shooterPIDF = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val shooterRPM = 5000.0
    override val shooterTolerance = 100.0
    override val shooterIdleRPM = 0.0
}

abstract class ControlPreferences {
    val primaryController = XboxController(PRIMARY_CONTROLLER_ID)
    val secondaryController = Joystick(SECONDARY_CONTROLLER_ID)

    abstract val joystickX: () -> Double
    abstract val joystickY: () -> Double

    abstract val slowMode: JoystickButton

    abstract val intake: JoystickButton
    abstract val overrideIndexer: JoystickButton
    abstract val shoot: JoystickButton
    abstract val outtake: JoystickButton

    abstract val climbNext: JoystickButton
    abstract val startClimbMid: JoystickButton
    abstract val startClimbTraversal: JoystickButton
}

object SoftwarePreferences : ControlPreferences() {
    override val joystickX = { primaryController.leftX }
    override val joystickY = { -primaryController.leftY }

    override val slowMode = JoystickButton(primaryController, XboxController.Button.kRightBumper.value)

    override val intake = JoystickButton(primaryController, XboxController.Button.kX.value)
    override val overrideIndexer = JoystickButton(primaryController, XboxController.Button.kA.value)
    override val shoot = JoystickButton(primaryController, XboxController.Button.kY.value)
    override val outtake = JoystickButton(primaryController, XboxController.Button.kB.value)

    override val climbNext = JoystickButton(secondaryController, 1)
    override val startClimbMid = JoystickButton(secondaryController, 2)
    override val startClimbTraversal = JoystickButton(secondaryController, 3)
}

object DriveteamPreferences : ControlPreferences() {
    override val joystickX = { primaryController.leftX }
    override val joystickY = { -primaryController.leftY }

    override val slowMode = JoystickButton(primaryController, XboxController.Button.kRightBumper.value)

    override val intake = JoystickButton(primaryController, XboxController.Button.kX.value)
    override val overrideIndexer = JoystickButton(primaryController, XboxController.Button.kA.value)
    override val shoot = JoystickButton(primaryController, XboxController.Button.kY.value)
    override val outtake = JoystickButton(primaryController, XboxController.Button.kB.value)

    override val climbNext = JoystickButton(secondaryController, 1)
    override val startClimbMid = JoystickButton(secondaryController, 2)
    override val startClimbTraversal = JoystickButton(secondaryController, 3)
}

val robotPreferences = PracticePreferences
val controlPreferences = SoftwarePreferences

fun getAuto(): Command? {
    return SmartDashboardManager.getAuto()
}
