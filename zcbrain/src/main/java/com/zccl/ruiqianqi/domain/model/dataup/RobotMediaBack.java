package com.zccl.ruiqianqi.domain.model.dataup;

import static com.zccl.ruiqianqi.config.RemoteProtocol.A_ORDER_CALLBACK;
import static com.zccl.ruiqianqi.config.RemoteProtocol.B_REMIND_RESULT;

/**
 * Created by ruiqianqi on 2017/3/22 0022.
 */

public class RobotMediaBack {
    private String cmd = A_ORDER_CALLBACK;
    private String command;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
