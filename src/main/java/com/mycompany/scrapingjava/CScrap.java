/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scrapingjava;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author DUSTIN
 */
public class CScrap {
    public void ScrapSitioWeb(JTextField urlSitio, JTable resultado){
        String url = urlSitio.getText();
        
        try{
            Document doc = Jsoup.connect(url).get();
            System.out.println("Conexion Exitosa");
            
            Elements productos = doc.select("a.poly-component__title");
            Elements precio = doc.select("div.poly-component__price");
            Elements precioOferta = doc.select("div.poly-price__current");
            
            DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Titulo","PrecioNormal","PrecioOferta","Enlace"},0);
            
            resultado.setModel(modelo);
            
            modelo.setRowCount(0);
            
            for (int i = 0; i < productos.size(); i++) {
                String titulo = productos.get(i).text();
                String precioNormal = precio.get(i).select("span.andes-money-amount__fraction").first().text();
                String precioOfertaFinal = precioOferta.get(i).select("span.andes-money-amount__fraction").text();
                String link = productos.get(i).attr("href");
                
                modelo.addRow(new Object[]{titulo,precioNormal,precioOfertaFinal,link});
            }
            
        }catch(Exception e){
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Error","Mensaje"},0);
            resultado.setModel(model);
            model.setRowCount(0);
            model.addRow(new Object[]{"Error", e.getMessage()});
        }
    }
}
