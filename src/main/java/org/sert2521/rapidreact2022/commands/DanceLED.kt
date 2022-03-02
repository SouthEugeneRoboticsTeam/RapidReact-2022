package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.LEDStrip

class DanceLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun initialize() {
        for (i in 0 until robotPreferences.ledLength) {
            LEDStrip.setLEDHSV(i, 275, 94, (i % 2) * 50)
        }
    }

    override fun execute() {
        LEDStrip.cycle(4.0)
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
