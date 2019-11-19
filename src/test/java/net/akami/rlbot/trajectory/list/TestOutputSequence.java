package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.trajectory.FragmentedOutputSequence;
import net.akami.rlbot.trajectory.OutputSequence;
import rlbot.flat.GameTickPacket;

import java.util.*;

public class TestOutputSequence extends FragmentedOutputSequence {

    @Override
    protected List<OutputSequence> loadChildren() {
        return new ArrayList<>(Arrays.asList(
                new ForwardMovement(100, 0.3f),
                new LeftMovement(100, 0.3f),
                new RightMovement(100, 0.3f)
        ));
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        return queue.size() == 0;
    }
}
