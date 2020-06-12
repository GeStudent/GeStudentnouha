/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestudent.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import java.util.List;
import edu.gestudent.entities.cours;
import edu.gestudent.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author CHIKHAOUI NOUHA
 */
public class Servicescours {
    
    
    
      public ArrayList<cours> cours;
    public  String  result="";
    public static Servicescours instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private Servicescours() {
         req = new ConnectionRequest();
    }

    public static Servicescours getInstance() {
        if (instance == null) {
            instance = new Servicescours();
        }
        return instance;
    }
    
    
    
    public ArrayList<cours> parseClubs(String jsonText){
        try {
            cours=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                cours c = new cours();
                float id = Float.parseFloat(obj.get("id").toString());
                  c.setIdcour((int)id);
                  c.setName(obj.get("name").toString());
                  c.setLesson(obj.get("lesson").toString());
                 float Duration = Float.parseFloat(obj.get("duration").toString());
                  c.setDuration((int)Duration);
               
                cours.add(c);
            }
        } catch (IOException ex) {
        }
        return cours;
    }
    
    
    
  public ArrayList<cours> getAllCours(){
        String url = Statics.BASE_URL+"ListCourMobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cours = parseClubs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cours;
    }
  public boolean Addcours(cours c) {
        String url = Statics.BASE_URL + "AddCoursMobile/?name=" + c.getName() + "&lesson=" + c.getLesson() + "&duration=" + c.getDuration() ; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
  /*
    public boolean Recherche(cours c) {
        String url = Statics.BASE_URL + "search/?q=" + c.getName()  ; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
 
           /* } 
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }   
  */
  
  public String DeleteCours(cours c) {
  
          String url = Statics.BASE_URL + "deleteCourMobile/?id=" + c.getIdcour();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        System.out.println(url);
                   

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    String data = new String(req.getResponseData());
                    JSONParser j = new JSONParser();
                    Map<String, Object> tasksListJson;
                    tasksListJson = j.parseJSON(new CharArrayReader(data.toCharArray()));
                   result=(String) tasksListJson.get("body");

                } catch (IOException ex) {
                    ex.getMessage();
                }
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
  public boolean EditCours(cours c) {
        String url = Statics.BASE_URL + "EditCourMobile/?id="+c.getIdcour()+"&name=" + c.getName() + "&lesson=" + c.getLesson() + "&duration=" + c.getDuration(); //création de l'URL
            System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
  
  
}
