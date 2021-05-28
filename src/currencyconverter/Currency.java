/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyconverter;

/**
 *
 * @author doug_na
 */
public class Currency {
    
    private String currencyName;
    
    public String getName(){
        return currencyName;
    }
    public void setName(String currencyName){
        this.currencyName = currencyName;
    }
    private String id;
    
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    
    public String toString(){
        return id;
    }
}
    

