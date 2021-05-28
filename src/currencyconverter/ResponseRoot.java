/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyconverter;

import java.util.HashMap;

/**
 *
 * @author doug_na
 */
public class ResponseRoot {
    public boolean success;
    public String terms;
    public String privacy;
    public int timeStamp;
    public String source;
    public HashMap<String,Object> quotes;
}
