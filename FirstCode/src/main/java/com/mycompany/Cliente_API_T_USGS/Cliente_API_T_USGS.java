/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.Cliente_API_T_USGS;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class Cliente_API_T_USGS {

    public static void main(String[] args) {
        try {
            // URL de la API de terremotos de USGS
            String urlString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2024-01-01&endtime=2024-10-15&minmagnitude=5";
            URL url = new URL(urlString);
            
            // Abrir conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // Verificar respuesta de la conexión
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                // Parsear la respuesta JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray earthquakes = jsonResponse.getJSONArray("features");
                
                // Imprimir la información de cada terremoto
                for (int i = 0; i < earthquakes.length(); i++) {
                    JSONObject earthquake = earthquakes.getJSONObject(i);
                    JSONObject properties = earthquake.getJSONObject("properties");
                    String place = properties.getString("place");
                    double magnitude = properties.getDouble("mag");
                    
                    System.out.println("Lugar: " + place + ", Magnitud: " + magnitude);
                }
            } else {
                System.out.println("Error al consultar la API. Código de respuesta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
