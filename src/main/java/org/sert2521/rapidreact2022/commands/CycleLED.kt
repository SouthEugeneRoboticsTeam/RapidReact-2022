package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.LEDStrip
import kotlin.math.PI
import kotlin.math.sin

class CycleLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun initialize() {
        for (i in 0 until robotPreferences.ledLength) {
            LEDStrip.setLEDHSV(i, 275, 94, (((sin(i * 2 * PI / 20) + 1) * 0.5) * 50).toInt())
        }
    }

    override fun execute() {
        LEDStrip.cycle(20.0)
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
