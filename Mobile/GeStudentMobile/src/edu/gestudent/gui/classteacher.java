/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestudent.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Image;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import edu.gestudent.entities.classe;
import edu.gestudent.entities.cours;
import edu.gestudent.entities.user;



import edu.gestudent.services.Servicesclass;
import java.util.ArrayList;




/**
 *
 * @author CHIKHAOUI NOUHA
 */
public class classteacher extends Form {
    
    
    
         static Form  currentForm;
         private EncodedImage placeHolder;


    public classteacher(Form previous, Resources theme) {
        currentForm=this;
        
        setTitle("Classes");
         setLayout(BoxLayout.y());
        for (classe cl : Servicesclass.getInstance().getAllclasse()) {
            
            Container InfoContainer = new Container(BoxLayout.y());
            Label nom = new Label(cl.getNameC());
             Button ListStudents=new Button("List Students");
                    Button ListCours=new Button("List Cours");
   
            InfoContainer.add(nom);
            InfoContainer.add(ListStudents);
          InfoContainer.add(ListCours);
         
            Container Container = new Container(BoxLayout.x());

     
          
            Container.add(InfoContainer);
            currentForm.add(Container);
            
        
         
            
         nom.addPointerPressedListener(ev -> { ClassDetail(cl, theme).show();});
         ListStudents.addActionListener(ev->{ 
                ListStudents(cl,theme).show();
            });
        for (cours ce : Servicesclass.getInstance().getAllCours()) {

            ListCours.addActionListener(ev->{ 
                ListCoursjointure(cl,ce,theme).show();
            });
                 
        
        }}
        
       
    
                    

           getToolbar().addCommandToOverflowMenu("Add classe", null, ev->{
                AddClass( theme).show();
        });
              getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });
 
    }
    
    
    
    
    
    
    

    
    
    
     public Form AddClass(Resources theme) {

        Form  Addclasse = new Form("classe", BoxLayout.y());

        Label Name = new Label("Name");
 
     

       
        TextField nameField = new TextField(null, "Name");
       
        Button Save = new Button("Save");

         Addclasse.addAll(Name, nameField,Save);

        Save.addActionListener(ev -> {
            if ((nameField.getText().length() == 0) ) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    classe c = new classe();
                    c.setNameC(nameField.getText());
                  
               
               

                    if (Servicesclass.getInstance().Addclass(c)) {
                        Dialog.show("Success", "classe Added", new Command("OK"));
                        new classteacher(teacher.current, theme).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "phone must be a number", new Command("OK"));
                }

            }

        });

         Addclasse.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new coursteacher(teacher.current, theme).show();
        });

        return  Addclasse;
    }
    
     
        public Form ClassDetail(classe cl, Resources theme) {

        Form ClassDetail = new Form(cl.getNameC(), BoxLayout.y());


        Label Email = new Label("name:");
        


        TextField EmailField = new TextField(null, "mp");
        EmailField.setText(cl.getNameC());
      

        Container Container = new Container(new FlowLayout());
        Container.addAll( Email, EmailField);
       
        ClassDetail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        Button Delete = new Button("Delete");
        Button Edit = new Button("Edit");
        ButtonsContainer.addAll(Edit, Delete);

        ClassDetail.add(ButtonsContainer);
        ClassDetail.revalidate();
        Delete.addActionListener(ev -> {
            String result = Servicesclass.getInstance().DeleteClasse(cl);
            if (!result.equals("Error")) {
                Dialog.show("Success", result, new Command("OK"));
                new classteacher(teacher.current, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }

        });

        Edit.addActionListener(ev -> {
           
            cl.setNameC(EmailField.getText());
            
            if (Servicesclass.getInstance().EditClasse(cl)) {
                Dialog.show("Success", "classe Edited", new Command("OK"));
                new classteacher(teacher.current, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        ClassDetail.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new classteacher(teacher.current, theme).show();
        });

        return ClassDetail;
    }
              public Form ListStudents(classe c, Resources theme) {

        Form ListMembers = new Form(c.getNameC(), BoxLayout.y());
        
          for (user u : Servicesclass.getInstance().getAllStudents(c)) {
            Container InfoContainer = new Container(BoxLayout.y());
            Label name = new Label(u.getFirstname()+" "+u.getLastname());
            Label email = new Label(u.getEmail());
            //Label post = new Label(u.getPostClub());
           // Label Nombreplace = new Label(String.valueOf(c.getNombreplace()));
            InfoContainer.add(name);
            InfoContainer.add(email);
            //InfoContainer.add(post);
            Container Container = new Container(BoxLayout.x());

           placeHolder = EncodedImage.createFromImage(theme.getImage("card.png"), false);
            String url = "http://localhost/Images/uploads/" + u.getImage();
            ConnectionRequest connection = new ConnectionRequest();
            connection.setUrl(url);
            URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);
            int width=(int) (imgurl.getWidth() /1.5);
            int Height=(int) (imgurl.getHeight() /1.5);
            ImageViewer img = new ImageViewer(imgurl.scaled(width,Height));
            Container.add(img);
            Container.add(InfoContainer);
            ListMembers.add(Container);

        }

        
        ListMembers.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new classteacher(teacher.current, theme).show();
        });

        return ListMembers;
    }
                            public Form ListCoursjointure(classe c,cours e, Resources theme) {

        Form ListCj = new Form(c.getNameC(), BoxLayout.y());
        
          for (cours u : Servicesclass.getInstance().getAllCours(c)) {
            Container InfoContainer = new Container(BoxLayout.y());
                        Label id = new Label(String.valueOf(u. getIdcour()));

            Label name = new Label(u.getName());
            // Label duration = new Label(u.getDuration());
            Label email = new Label(u.getLesson());
            //Label post = new Label(u.getPostClub());

            Label Duration = new Label(String.valueOf(u. getDuration()));
           Button sendc=new Button("Send Cours");
            InfoContainer.add(id);

            InfoContainer.add(name);
            InfoContainer.add(email);
            InfoContainer.add(Duration);
             InfoContainer.add(sendc);
            Container Container = new Container(BoxLayout.x());

           //placeHolder = EncodedImage.createFromImage(theme.getImage("card.png"), false);
            //String url = "http://localhost/Images/uploads/" + u.getImage();
            ConnectionRequest connection = new ConnectionRequest();
            //connection.setUrl(url);
            //URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);
            //int width=(int) (imgurl.getWidth() /1.5);
            //int Height=(int) (imgurl.getHeight() /1.5);
            //ImageViewer img = new ImageViewer(imgurl.scaled(width,Height));
            //Container.add(img);
            Container.add(InfoContainer);
                sendc.addActionListener(ev -> {
           
            //cl.setNameC(EmailField.getText());
            
            if (Servicesclass.getInstance().SendMail( c , e)) {
                Dialog.show("Success", "classe Edited", new Command("OK"));
                new classteacher(teacher.current, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });
            
            ListCj.add(Container);

        }

        
        ListCj.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new classteacher(teacher.current, theme).show();
        });

        return ListCj;
    }
    
}
    