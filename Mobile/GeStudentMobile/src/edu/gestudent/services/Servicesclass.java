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
import edu.gestudent.entities.classe;
import edu.gestudent.entities.cours;
import edu.gestudent.entities.user;

import edu.gestudent.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CHIKHAOUI NOUHA
 */
public class Servicesclass {
          public ArrayList<classe> classe;
          public ArrayList<cours> cours;
           public ArrayList<user> students;
    public  String  result="";
    public static Servicesclass instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private Servicesclass() {
         req = new ConnectionRequest();
    }

    public static Servicesclass getInstance() {
        if (instance == null) {
            instance = new Servicesclass();
        }
        return instance;
    }
    
    
    
    public ArrayList<classe> parseClubs(String jsonText){
        try {
            classe=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                classe c = new classe();
                float id = Float.parseFloat(obj.get("id").toString());
                  c.setIdclass((int)id);
                  c.setNameC(obj.get("nameC").toString());
                  
                
               
                classe.add(c);
            }
        } catch (IOException ex) {
        }
        return classe;
    }
    
    
      public ArrayList<classe> getAllclasse(){
        String url = Statics.BASE_URL+"ListClassMobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                classe = parseClubs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return classe;
    }
        public boolean Addclass(classe c) {
        String url = Statics.BASE_URL + "AddClassMobile/?nameC=" + c.getNameC()  ; //création de l'URL
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
        
        
        
          public String DeleteClasse(classe c) {
  
          String url = Statics.BASE_URL + "DeleteClassMobile/?id=" + c.getIdclass();
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
          public boolean EditClasse(classe c) {
        String url = Statics.BASE_URL + "EditClassMobile/?id="+c.getIdclass()+"&NameC=" + c.getNameC() ; //création de l'URL
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
                public boolean SendMail(classe c , cours e) {
        String url = Statics.BASE_URL + "SendMailCoursMOBILE?idclass="+c.getIdclass()+"&idcours=" + e.getIdcour() ; //création de l'URL
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
          
          
          
              
    public ArrayList<cours> parseCour(String jsonText){
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
               public ArrayList<user> parseMembers(String jsonText) {
        try {
            students = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                user u = new user();
                float id = Float.parseFloat(obj.get("id").toString());
                u.setId((int) id);
                //u.setPostClub(obj.get("post").toString());
                u.setFirstname(obj.get("firstname").toString());
                u.setLastname(obj.get("lastname").toString());
                u.setImage(obj.get("image").toString());
                u.setEmail(obj.get("email").toString());
              

                students.add(u);
            }

        } catch (IOException ex) {
            ex.getMessage();
        }

        return students;
    }
                 public ArrayList<cours> getAllCours(){
        String url = Statics.BASE_URL+"ListCourMobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cours = parseCour(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cours;
    }
          public ArrayList<user> getAllStudents(classe c) {
        String url = Statics.BASE_URL + "ClassestudentMobile/"+c.getIdclass();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                students = parseMembers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return students;
    }
          
              public ArrayList<cours> getAllCours(classe c) {
        String url = Statics.BASE_URL + "ClasseCourMobile/"+c.getIdclass();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cours = parseCour(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cours;
    }
        
        
}
