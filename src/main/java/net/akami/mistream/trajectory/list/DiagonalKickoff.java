package net.akami.mistream.trajectory.list;

import net.akami.mistream.trajectory.BotController;
import net.akami.mistream.trajectory.OutputSequence;
import net.akami.mistream.vector.Vector3f;

import java.util.Arrays;
import java.util.List;

public class DiagonalKickoff extends KickoffOutputSequence {

    public DiagonalKickoff(BotController botController) {
        super(new Vector3f(-2048, -2560, 17.01), botController);
    }

    // we invert the boolean "isOnLeft" because if the car is on the left, we want it to go on the right to get centered
    @Override
    protected List<OutputSequence> loadChildren() {
        int factor = !isOnLeft() ? -1 : 1;
        return Arrays.asList(
                new BasicMovement(12, -1, 0),
                new BasicMovement(3, -1, factor),
                new SideDash(botController, isOnLeft(), -1),
                new BasicMovement(26, -1, -factor, factor),
                new JumpMovement(10, 0, 0, 1, botController),
                new ResetMovement(1, botController),
                new JumpMovement(0, -factor, 0, 1, botController)
                //new LinearEndToEndSequence(Vector3f.ZERO, botController.data(CarInfoProvider.class), 1)
        );
    }
}
