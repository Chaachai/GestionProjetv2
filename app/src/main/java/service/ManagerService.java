package service;

import android.content.Context;

import bean.Manager;
import dao.ManagerDao;

public class ManagerService extends ManagerDao {
    public ManagerService(Context context) {
        super(context);
    }


    public int create(String nom, String prenom) {
        Manager manager = new Manager();
        manager.setNom(nom);
        manager.setPrenom(prenom);
        create(manager);
        return 1;
    }
}
