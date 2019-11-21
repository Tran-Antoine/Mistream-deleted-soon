package net.akami.rlbot.core;

import net.akami.rlbot.output.ControlsOutput;
import net.akami.rlbot.trajectory.BotController;
import net.akami.rlbot.trajectory.list.BoostPickUpSequence;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

public class InputProcessor implements Bot {

    private static final ControlsOutput EMPTY_CONTROLLER = new ControlsOutput();

    private final int playerIndex;
    private final BotController manager;

    public InputProcessor(int playerIndex, BotController manager) {
        BoostPickUpSequence.bot = this;
        this.playerIndex = playerIndex;
        this.manager = manager;
    }

    @Override
    public ControllerState processInput(GameTickPacket packet) {

        if (error(packet)) {
            return EMPTY_CONTROLLER;
        }

        manager.update(packet);
        ControllerState state = manager.provideController().orElse(EMPTY_CONTROLLER);
        return state;
    }

    private boolean error(GameTickPacket packet) {
        return packet.playersLength() < 2 || packet.ball() == null
                || !packet.gameInfo().isRoundActive();
    }

    @Override
    public void retire() {
        System.out.println("Retiring sample bot " + playerIndex);
    }

    @Override
    public int getIndex() {
        return this.playerIndex;
    }
}
