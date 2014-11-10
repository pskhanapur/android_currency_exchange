package com.reallynow;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.util.Log;



/**
 * Using this class, we convert currencies to its foreign exchange. This class uses google calculator to perform the conversion process
 * @author Bright Dadson
 */
public final class Converter
{
  private static final String error = "error:";
  private static final String noErrorFound = "\"\"";
  private static final String regExp = "-?\\d+(.\\d+)?";
 
  private int valueToConvert;
  private String convertFrom, convertTo;
 
  public Converter()
  {}
 
  /**
   * Convert submitted value from and to the submitted conversion codes.
   * 
   * @param valueToConvert - Amount to convert
   * @param convertFrom - Currency code to convert from
   * @param convertTo - Currency code to convert to
   * @return
   */
  public double getConvertedValue(int valueToConvert, String convertFrom, String convertTo)
  {
    try
    {
      this.valueToConvert = valueToConvert;
      this.convertFrom = convertFrom;
      this.convertTo = convertTo;
      String convertedValue = extractConvertedValue(convert());
      if (convertedValue != null && isNumeric(convertedValue))
      {
        BigDecimal roundVal = new BigDecimal(convertedValue);
        roundVal.round(new MathContext(2, RoundingMode.HALF_UP));
        return roundVal.doubleValue();
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace(System.out);
    }
    return 0d;
  }
 
  /**Connect to Google api using http GET request to perform the currency conversion**/
  private String convert()
  {
    try
    {
      String code = String.valueOf("http://rate-exchange.appspot.com/currency?from="+ convertFrom  +  "&to=" + convertTo);
      URL converterUrl = new URL(code);
      Log.d("MainActivity", "url:" + code);
      URLConnection urlConnection = converterUrl.openConnection();
 
      InputStream inputStream = urlConnection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
 
      String conversionResult = bufferedReader.readLine();
      bufferedReader.close();
      inputStream.close();
      urlConnection = null;
 
      Log.d("MainActivity", "response:" + conversionResult);
      
      JSONObject jsonObject = new JSONObject(conversionResult);
    
      double xrate = jsonObject.getDouble("rate");
      Log.d("MainActivity", "json:" + xrate);
      
      
      return String.valueOf(xrate);
    }
    catch (Exception e)
    {
      e.printStackTrace(System.out);
    }
    return null;
  }
 
  /**If error is found within the response string, throw runtime exception to report, else parse the result for extraction**/
  private String extractConvertedValue(String convertedResult) throws Exception
  {
	/*
    String[] convertedResStrings = convertedResult.split(",");
    for (int i = 0; i < convertedResStrings.length; i++)
    {
      if ((convertedResStrings[i].contains(error)) && convertedResStrings[i].split(" ")[1].equals(noErrorFound))
      {
        String convertedValue = extract(convertedResStrings[i - 1]);
        if (!(convertedValue.isEmpty()))
        {
          return convertedValue;
        }
      }
      else if ((convertedResStrings[i].contains(error)) && !convertedResStrings[i].split(" ")[1].equals(noErrorFound))
      {
        throw new RuntimeException("Error occured while converting amount: "+convertedResStrings[i].split(" ")[1]);
      }
    } */
	
    return convertedResult;
  }
 
  private String extract(String str)
  {
    StringBuffer sBuffer = new StringBuffer();
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(str);
    if (m.find())
    {
      sBuffer.append(m.group());
    }
    return sBuffer.toString();
  }
 
  private boolean isNumeric(String str)
  {
    return str.matches(regExp);
  }
 
}