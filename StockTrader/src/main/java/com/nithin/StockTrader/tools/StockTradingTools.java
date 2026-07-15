package com.nithin.StockTrader.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;



@Component
public class StockTradingTools {


    @Tool(name = "stock_price_retriever",description = "Retrieves the stock price")
    public double getStockPrice(
            @ToolParam(description = "This is the Stock name")
            String ticker){
        return Math.random() * 1000;
    }

    @Tool(name = "stock_buyer",description = "Buys the stocks based on the stock price")
    public double buyStock(
            @ToolParam(description = "This is the Stock name we need to buy")
            String ticker,
            @ToolParam(description = "Quantity of the stock we need to buy based upon the user request")
            int quantity){
        return getStockPrice(ticker);

    }
}
