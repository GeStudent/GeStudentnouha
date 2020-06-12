/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestudent.gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import edu.gestudent.entities.Session;
import edu.gestudent.entities.cours;
import edu.gestudent.entities.user;
import edu.gestudent.services.Servicescours;

/**
 *
 * @author CHIKHAOUI NOUHA
 */
public class coursclasse extends Form{
    
        static Form  currentForm;


    user User = Session.getCurrentSession();

    public coursclasse(Form previous, Resources theme) {

        currentForm = this;
        currentForm.setTitle("Club");
        currentForm.setLayout(BoxLayout.y());

        for (cours c : Servicescours.getInstance().getAllCours()) {
            Container InfoContainer = new Container(BoxLayout.y());
            Label nom = new Label(c.getName());
             Label lesson= new Label(c.getLesson());
            Label duration = new Label(String.valueOf(c.getDuration()));
            InfoContainer.add(nom);
            InfoContainer.add(lesson);
            InfoContainer.add(duration);
         
            Container Container = new Container(BoxLayout.x());

     
          
            Container.add(InfoContainer);
            currentForm.add(Container);

           
        }

        currentForm.getToolbar().addCommandToOverflowMenu("Add cours", null, ev -> {
          Addcours(theme).show();
        });

        currentForm.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });

    }

   
    
    public Form Addcours(Resources theme)
    {    
       
        
        Form Addcours = new Form("ADD", new FlowLayout(Component.CENTER, Component.CENTER));
        Addcours.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
             currentForm.showBack();
        });

        return Addcours;
    }
    
}
