/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestudent.gui;

import com.codename1.components.SpanLabel;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import edu.gestudent.entities.cours;
import static edu.gestudent.gui.coursclasse.currentForm;
import edu.gestudent.services.Servicescours;

/**
 *
 * @author CHIKHAOUI NOUHA
 */
public class coursteacher extends Form{
     static Form  currentForm;
 

    public coursteacher(Form previous, Resources theme) {
        currentForm=this;
        setTitle("cours");
        setLayout(BoxLayout.y());
                TextField nameField = new TextField(null, "Recherche");
currentForm.add(nameField);
          for (cours c : Servicescours.getInstance().getAllCours()) {
            Container InfoContainer = new Container(BoxLayout.y());
                    Button Delete = new Button("More details");

            
            Label nom = new Label(c.getName());
             Label lesson= new Label(c.getLesson());
            Label duration = new Label(String.valueOf(c.getDuration()));
            InfoContainer.add(nom);
            InfoContainer.add(lesson);
            InfoContainer.add(duration);
                 InfoContainer.add(Delete);
 
            Container Container = new Container(BoxLayout.x());

     
          
            Container.add(InfoContainer);
            currentForm.add(Container);
            
            
                  Delete.addActionListener(ev->{ 
                CourDetail(c,theme).show();
            });
            
 //nom.addPointerPressedListener(ev -> { CourDetail(c, theme).show();});
           
        } 
        
        //      SpanLabel sp = new SpanLabel();
        //sp.setText(Servicescours.getInstance().getAllCours().toString());
        //add(sp);
        
        getToolbar().addCommandToOverflowMenu("Add cours", null, ev->{
                AddCours( theme).show();
        });
        

        getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });
       
//traitement




           
    }

   
    
 
    
    
    
    
         public Form AddCours(Resources theme) {

        Form  AddCours = new Form("Cours", BoxLayout.y());

        Label Name = new Label("Name");
        Label lesson = new Label("lesson");
        Label duration = new Label("duration");
     

       
        TextField nameField = new TextField(null, "Name");
        TextField lessonField = new TextField(null, "lesson");
        TextField durationField = new TextField(null, "duration");
        Button Save = new Button("Save");

         AddCours.addAll(Name, nameField,lesson,lessonField ,duration,durationField,Save);

        Save.addActionListener(ev -> {
            if ((nameField.getText().length() == 0) || (lessonField.getText().length() == 0) || durationField.getText().length() == 0
                  ) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    cours c = new cours();
                    c.setName(nameField.getText());
                    c.setLesson(lessonField.getText());
               
                c.setDuration(Integer.parseInt(durationField.getText()));

                    if (Servicescours.getInstance().Addcours(c)) {
                        Dialog.show("Success", "cours Added", new Command("OK"));
                        new coursteacher(teacher.current, theme).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "phone must be a number", new Command("OK"));
                }

            }

        });

         AddCours.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new coursteacher(teacher.current, theme).show();
        });

        return  AddCours;
    }
    
         
         
         
         
         
         
         
         
         public Form CourDetail(cours c, Resources theme) {

        Form ClubDetail = new Form(c.getName(), BoxLayout.y());

       

        Label ClubName = new Label("Name:");
        Label Email = new Label("Lesson:");
        Label phone = new Label("Duration:");

      SpanLabel Message = new SpanLabel("Descrption: \n" + c.getName() + "\n" );

        TextField ClubNameField = new TextField(null, "Name");

        ClubNameField.setText(c.getName());

        TextField EmailField = new TextField(null, "Lesson");
        EmailField.setText(c.getLesson());
        TextField phoneField = new TextField(null, "Duration");
        phoneField.setText(String.valueOf(c.getDuration()));

        Container Container = new Container(new FlowLayout());
        Container.addAll( Email, EmailField, phone, phoneField,Message);
       
        ClubDetail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        Button Delete = new Button("Delete");
        Button Edit = new Button("Edit");
        ButtonsContainer.addAll(Edit, Delete);

        ClubDetail.add(ButtonsContainer);
        ClubDetail.revalidate();
        Delete.addActionListener(ev -> {
            String result = Servicescours.getInstance().DeleteCours(c);
            if (!result.equals("Error")) {
                Dialog.show("Success", result, new Command("OK"));
                new coursteacher(teacher.current, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }

        });

        Edit.addActionListener(ev -> {
            c.setLesson(EmailField.getText());
            c.setName(ClubNameField.getText());
            c.setDuration(Integer.parseInt(phoneField.getText()));
            if (Servicescours.getInstance().EditCours(c)) {
                Dialog.show("Success", "cours Edited", new Command("OK"));
                new coursteacher(teacher.current, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        ClubDetail.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new coursteacher(teacher.current, theme).show();
        });

        return ClubDetail;
    }
}
