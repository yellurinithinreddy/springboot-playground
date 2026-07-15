package com.nithin.StockTrader.controller;

import com.nithin.StockTrader.tools.StockTradingTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockBuyController {


    private final ChatClient chatClient;
    private final StockTradingTools stockTradingTools;

    @PostMapping("/chat")
    public String buyStocks(@RequestBody String buy){
        return chatClient.prompt()
                .system("You are a AI assistant that buy stocks based on the instruction given using the tools provided. If any of the instruction has missing information  Let the user know about it")
                .user(buy)
                .tools(stockTradingTools)
                .call()
                .content();
    }
}
