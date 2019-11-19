package net.akami.rlbot.core;

import net.akami.rlbot.output.ControlsOutput;
import net.akami.rlbot.trajectory.BotController;
import net.akami.rlbot.vector.Vector3f;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

public class InputListener implements Bot {

    private final int playerIndex;
    private final BotController manager;
    private final ControlsOutput EMPTY = new ControlsOutput();

    InputListener(int playerIndex, BotController manager) {
        this.playerIndex = playerIndex;
        this.manager = manager;
    }

    /**
     * This is the most important function. It will automatically get called by the framework with fresh data
     * every frame. Respond with appropriate controls!
     */
    @Override
    public ControllerState processInput(GameTickPacket packet) {

        manager.update(packet);

        if (error(packet)) return EMPTY;

        ControllerState state = manager.findController().orElse(EMPTY);
        System.out.println(new Vector3f(packet.players(0).physics().location()));
        return state;
    }

    private boolean error(GameTickPacket packet) {
        return packet.playersLength() <= playerIndex || packet.ball() == null || !packet.gameInfo().isRoundActive();
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
