///////////////////////////////////////////////////////////
// DeJaved by mDeJava v1.0. Copyright 1999 MoleSoftware. //
//       To download last version of this software:      //
//            http://molesoftware.hypermatr.net          //
//               e-mail:molesoftware@mail.ru             //
///////////////////////////////////////////////////////////

import java.io.Serializable;
import java.util.Vector;

public class ChartData
    implements Serializable
{

    String symbol = null;
    String symbol1 = null;
    String symbol2 = null;
    String indexName = null;
    String series = null;
    String mktType = null;
    String prevClose = null;
    String instrument = null;
    String expiryDate = null;
    String optionType = null;
    String strikePrice = null;
    String plot1 = null;
    String plot2 = null;
    Vector stockData[] = null;
    Vector stock1Data[] = null;
    Vector stock2Data[] = null;
    Vector indexData[] = null;
    Vector contractData[] = null;
    Vector underlyingData[] = null;
    Vector plot1Data[] = null;
    Vector plot2Data[] = null;

    public ChartData()
    {
    }

    public void setSymbol(String s)
    {
        symbol = s;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol1(String s)
    {
        symbol1 = s;
    }

    public String getSymbol1()
    {
        return symbol1;
    }

    public void setSymbol2(String s)
    {
        symbol2 = s;
    }

    public String getSymbol2()
    {
        return symbol2;
    }

    public void setSeries(String s)
    {
        series = s;
    }

    public String getSeries()
    {
        return series;
    }

    public void setMktType(String s)
    {
        mktType = s;
    }

    public String getMktType()
    {
        return mktType;
    }

    public void setIndexName(String s)
    {
        indexName = s;
    }

    public String getIndexName()
    {
        return indexName;
    }

    public void setPrevClose(String s)
    {
        prevClose = s;
    }

    public String getPrevClose()
    {
        return prevClose;
    }

    public void setStockData(Vector avector[])
    {
        stockData = avector;
    }

    public Vector[] getStockData()
    {
        return stockData;
    }

    public void setInstrument(String s)
    {
        instrument = s;
    }

    public String getInstrument()
    {
        return instrument;
    }

    public void setExpiryDate(String s)
    {
        expiryDate = s;
    }

    public String getExpiryDate()
    {
        return expiryDate;
    }

    public void setOptionType(String s)
    {
        optionType = s;
    }

    public String getOptionType()
    {
        return optionType;
    }

    public void setStrikePrice(String s)
    {
        strikePrice = s;
    }

    public String getStrikePrice()
    {
        return strikePrice;
    }

    public void setStock1Data(Vector avector[])
    {
        stock1Data = avector;
    }

    public Vector[] getStock1Data()
    {
        return stock1Data;
    }

    public void setStock2Data(Vector avector[])
    {
        stock2Data = avector;
    }

    public Vector[] getStock2Data()
    {
        return stock2Data;
    }

    public void setIndexData(Vector avector[])
    {
        indexData = avector;
    }

    public Vector[] getIndexData()
    {
        return indexData;
    }

    public void setContractData(Vector avector[])
    {
        contractData = avector;
    }

    public Vector[] getContractData()
    {
        return contractData;
    }

    public void setUnderlyingData(Vector avector[])
    {
        underlyingData = avector;
    }

    public Vector[] getUnderlyingData()
    {
        return underlyingData;
    }

    public void setPlot1(String s)
    {
        plot1 = s;
    }

    public String getPlot1()
    {
        return plot1;
    }

    public void setPlot2(String s)
    {
        plot2 = s;
    }

    public String getPlot2()
    {
        return plot2;
    }

    public void setPlot1Data(Vector avector[])
    {
        plot1Data = avector;
    }

    public Vector[] getPlot1Data()
    {
        return plot1Data;
    }

    public void setPlot2Data(Vector avector[])
    {
        plot2Data = avector;
    }

    public Vector[] getPlot2Data()
    {
        return plot2Data;
    }
}
