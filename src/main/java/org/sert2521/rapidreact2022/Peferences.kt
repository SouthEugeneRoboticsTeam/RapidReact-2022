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

    abstract val ledLength: Int

    abstract val shooterRPM: Double
    abstract val shooterEnterRPM: Double
    abstract val shooterExitRPM: Double
}

object CompetitionPreferences : RobotPreferences() {
    override val climberPID = arrayOf(1.5, 0.5, 0.0)
    override val actuatorPID = arrayOf(0.06, 0.1, 0.0)
    override val shooterPIDF = arrayOf(0.00045, 0.0, 0.0, 0.00021)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val ledLength = 16

    override val shooterRPM = 4600.0
    override val shooterEnterRPM = 4600.0
    override val shooterExitRPM = 4500.0
}

object PracticePreferences : RobotPreferences() {
    override val climberPID = arrayOf(0.0, 0.0, 0.0)
    override val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    override val shooterPIDF = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    override val drivePID = arrayOf(2.773, 0.0, 0.0)
    override val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)

    override val ledLength = 56//58, but two are covered

    override val shooterRPM = 5000.0
    override val shooterEnterRPM = 5000.0
    override val shooterExitRPM = 4600.0
}

abstract class ControlPreferences {
    val primaryController = XboxController(PRIMARY_CONTROLLER_ID)
    val secondaryController = Joystick(SECONDARY_CONTROLLER_ID)

    abstract val joystickX: () -> Double
    abstract val joystickY: () -> Double

    abstract val fastMode: JoystickButton
    abstract val slowMode: JoystickButton

    abstract val intake: JoystickButton
    abstract val overrideIndexer: JoystickButton
    abstract val runIndexer: JoystickButton
    abstract val outtake: JoystickButton
    abstract val shoot: JoystickButton
    abstract val rev: JoystickButton

    abstract val climbNext: JoystickButton
    abstract val startClimbMid: JoystickButton
    abstract val startClimbHigh: JoystickButton
    abstract val startClimbTraversal: JoystickButton
}

object SoftwarePreferences : ControlPreferences() {
    override val joystickX = { primaryController.leftX }
    override val joystickY = { -primaryController.leftY }

    override val fastMode = JoystickButton(primaryController, 6)
    override val slowMode = JoystickButton(primaryController, 5)

    override val intake = JoystickButton(primaryController, 3)
    override val overrideIndexer = JoystickButton(primaryController, -1)
    override val runIndexer = JoystickButton(primaryController, -2)
    override val outtake = JoystickButton(primaryController, 2)
    override val shoot = JoystickButton(primaryController, 4)
    override val rev = JoystickButton(primaryController, -3)

    override val climbNext = JoystickButton(secondaryController, 5)
    override val startClimbMid = JoystickButton(secondaryController, 6)
    override val startClimbHigh = JoystickButton(secondaryController, 7)
    override val startClimbTraversal = JoystickButton(secondaryController, 8)
}

object DriveteamPreferences : ControlPreferences() {
    override val joystickX = { primaryController.rightX }
    override val joystickY = { -primaryController.leftY }

    override val fastMode = JoystickButton(primaryController, 6)
    override val slowMode = JoystickButton(primaryController, 5)

    override val intake = JoystickButton(secondaryController, 8)
    override val overrideIndexer = JoystickButton(secondaryController, 10)
    override val runIndexer = JoystickButton(secondaryController, 9)
    override val outtake = JoystickButton(secondaryController, 7)
    override val shoot = JoystickButton(secondaryController, 14)
    override val rev = JoystickButton(secondaryController, 15)

    override val climbNext = JoystickButton(secondaryController, 5)
    override val startClimbMid = JoystickButton(secondaryController, 11)
    override val startClimbHigh = JoystickButton(secondaryController, 12)
    override val startClimbTraversal = JoystickButton(secondaryController, 13)
}

val robotPreferences = CompetitionPreferences
val controlPreferences = DriveteamPreferences

fun getAuto(): Command? {
    return SmartDashboardManager.getAuto()
}
