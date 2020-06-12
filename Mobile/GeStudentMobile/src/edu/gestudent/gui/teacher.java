/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestudent.gui;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import edu.gestudent.entities.Session;
import edu.gestudent.entities.user;

/**
 *
 * @author Ayadi
 */
public class teacher extends Form {
    static Form current;
    user User=Session.getCurrentSession();

    public teacher(Form previous,Resources theme) {
        current=this;

        setTitle("teacher");

        setLayout(BoxLayout.y());

        getToolbar().addCommandToSideMenu("Home", null, ev -> {

        }
        );
        getToolbar().addCommandToSideMenu("cours", null, ev -> { new coursteacher(current, theme).showBack();

        });
          getToolbar().addCommandToSideMenu("classe", null, ev -> { new classteacher(current, theme).showBack();

        });
        

        getToolbar().addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, ev->{
            try {
                Session.close();
            } catch (Exception ex) {
                ex.getMessage();
            }
            previous.showBack();
               
                
                        });

    }

}
