/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHTML_EXEC;

import java.awt.Color;

/**
 *
 * @author richard
 */
public class InterpretaColor {
    
    public InterpretaColor()
    {
        
    }
    
    public  Color stringToColor(String color)
    {
        color = color.toLowerCase();
        switch(color)
        {
            case "red":
            {
                return Color.red;
            }
            case "blue":
            {
                return Color.blue;
            }
            case "yellow":
            {
                return Color.yellow;
            }
            case "green":
            {
                return Color.green;
            }
            case "orange":
            {
                return Color.orange;
            }
            case "white":
            {
                return Color.white;
            }
            case "black":
            {
                return Color.black;
            }
            default:
            {
                return ColorHex(color);
            }
        }
    }
    
    private Color ColorHex(String color)
    {
        try {
            Color col = Color.decode(color);
            return col;
        } catch (Exception e) {
            return Color.GRAY;
        }
    }
}
