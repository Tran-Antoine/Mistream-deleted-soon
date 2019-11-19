package net.akami.rlbot.core;

import net.akami.rlbot.trajectory.BotController;
import rlbot.Bot;
import rlbot.manager.BotManager;
import rlbot.pyinterop.SocketServer;

public class BotLauncher extends SocketServer {

    BotLauncher(int port, BotManager botManager) {
        super(port, botManager);
    }

    @Override
    protected Bot initBot(int index, String botType, int team) {
        return new InputListener(index, new BotController());
    }
}
