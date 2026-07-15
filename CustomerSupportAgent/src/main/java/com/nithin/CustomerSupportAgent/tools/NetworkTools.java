package com.nithin.CustomerSupportAgent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class NetworkTools {


    @Tool(name = "router_restarter",description = "reboot the router if the user asks to reboot it")
    public String rebootRouter(
            @ToolParam(description = "This is the serial number of the router to be rebooted.")
            String serialNo){
        return "Restarted the router with serialNo "+serialNo;
    }
}
