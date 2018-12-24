package ca.jaron;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.Daily;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/* public class App {
    public static void main(String[] args){
        String apiKey = "YE4PNNTEGYCF0VMD";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

        try{
            Daily response  = stockTimeSeries.daily("NVDA");
            Map<String, String> metaData = response.getMetaData();
            System.out.println("Information: " + metaData.get("1. Information"));
            System.out.println("Stock: " + metaData.get("2. Symbol"));

            List<StockData> stockData = response.getStockData();
            stockData.forEach(stock -> {
                System.out.println("date:   " + stock.getDateTime());
                System.out.println("open:   " + stock.getOpen());
                System.out.println("high:   " + stock.getHigh());
                System.out.println("low:    " + stock.getLow());
                System.out.println("close:  " + stock.getClose());
                System.out.println("volume: " + stock.getVolume());
            });

        }catch(AlphaVantageException e){
            System.out.println("Something went wrong!");
        }

    }

}*/

public class appGUI extends Frame implements ActionListener {

    private String apiKey = "YE4PNNTEGYCF0VMD";
    private int timeout = 3000;
    private static AlphaVantageConnector apiConnector;
    private static TimeSeries stockTimeSeries ;

    //private String symbol;
    private Label symLabel;
    private Label priceLabel;
    private TextField symbol;
    private TextField price;


    public appGUI(){
        setLayout(new FlowLayout());

        symLabel = new Label("Enter a stock symbol: "); //construct new label
        add(symLabel);


        //stock symbol input
        symbol = new TextField(5);
        add(symbol);

        priceLabel = new Label("Latest stock price: ");
        add(priceLabel);
        //current price of stock
        price = new TextField(8);
        add(price);

        symbol.addActionListener(this);

        setTitle("stockApp");
        setSize(400, 300);
        setVisible(true);

    }

    public static void main(String[] args){
        new appGUI();
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        String input = symbol.getText();
        apiConnector = new AlphaVantageConnector(apiKey, timeout);
        stockTimeSeries = new TimeSeries(apiConnector);

        try{
            IntraDay response  = stockTimeSeries.intraDay(input, Interval.THIRTY_MIN, OutputSize.COMPACT);
            Map<String, String> metaData = response.getMetaData();

            List<StockData> stockData = response.getStockData();

            price.setText(String.valueOf(stockData.get(0).getClose()));

        }catch(AlphaVantageException e){
            System.out.println(("Something went wrong"));
        }
    }

}