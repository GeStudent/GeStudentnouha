package com.endroid.hello;

import com.codename1.components.ImageViewer;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import edu.gestudent.entities.user;
import edu.gestudent.services.ServicesUsers;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Resources theme;
    private static user User;
//    Club c = new Club();

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    

//    public Form HomePage() {
//        Form Home = new Form("GeStudent", new FlowLayout(Component.CENTER, Component.CENTER));
//        ImageViewer imageName = new ImageViewer(theme.getImage("gestudent.png"));
//        Container cnt = new Container(BoxLayout.y());
//
//        TextField username = new TextField(null, "username");
//        TextField password = new TextField(null, "password");
//
//        Button login = new Button("login");
//
//        cnt.add(imageName);
//        cnt.add(username);
//        cnt.add(password);
//        cnt.add(login);
//        Home.add(cnt);
//
//        login.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//
//                {
//
//                    User = ServicesUsers.getInstance().Login(username.getText(), password.getText());
//                    if (User != null) {
//                        System.out.println("connecte");
//                        if (User.getRoles().equals("student")) {
//                            Student().show();
//                        } else {
//                            Teacher().show();
//                        }
//                    } else {
//                        Dialog.show("Alert", "This is already your home", "OK", null);
//                    }
//
//                }
//
//            }
//        });
//
//        return Home;
//
//    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

       new edu.gestudent.gui.Login(theme).show();

    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
