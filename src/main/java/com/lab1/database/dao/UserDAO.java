package com.lab1.database.dao;

import com.lab1.database.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDAO {

    public List<UserRole> getAllUserRole() {

        UsersEntityMF usersEntityMF = new UsersEntityMF();
        EntityManager entityManager = usersEntityMF.getEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM UserRole e");
        List<UserRole> usersList=query.getResultList();
        System.out.println("Данные записались в usersList.");
        entityManager.close();
        usersEntityMF.close();
        return usersList;
    }

    public void add(User newUser, String role) {

        UserRole userRole=new UserRole(newUser.getUsername(),role);
        UsersEntityMF usersEntityMF = new UsersEntityMF();
        EntityManager entityManager = usersEntityMF.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(newUser);
        entityManager.persist(userRole);
        entityManager.getTransaction().commit();
        entityManager.close();
        usersEntityMF.close();
        System.out.println("Пользователь добавлен в базу.");
    }

    public void updateData(User user, String name) {

        try {
            UsersEntityMF usersEntityMF = new UsersEntityMF();
            EntityManager entityManager = usersEntityMF.getEntityManager();
            User findUser = entityManager.find(User.class, name);
            entityManager.getTransaction().begin();
            findUser.setY_e(user.getY_e());
            entityManager.getTransaction().commit();
            entityManager.close();
            usersEntityMF.close();
            System.out.println("Объект обновлён.");
        } catch (NullPointerException e){
            throw new RuntimeException("Такого пользователя не существует.");
        }
    }

    public void delete(String username) {

        try {
            UsersEntityMF usersEntityMF = new UsersEntityMF();
            EntityManager entityManager = usersEntityMF.getEntityManager();
            User findUser = entityManager.find(User.class, username);
            UserRole findUserRole = entityManager.find(UserRole.class, username);
            //удаление
            entityManager.getTransaction().begin();
            entityManager.remove(findUserRole);
            entityManager.remove(findUser);
            entityManager.getTransaction().commit();
            entityManager.close();
            usersEntityMF.close();
            System.out.println("Пользователь удалён.");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Такого пользователя не существует.");
        }
    }

    public User findUserByName(String name) {

        User findUser;
        try {
            UsersEntityMF usersEntityMF = new UsersEntityMF();
            EntityManager entityManager = usersEntityMF.getEntityManager();
            findUser = entityManager.find(User.class, name);
            entityManager.close();
            usersEntityMF.close();
            System.out.println("Выполнено.");
        }catch (NullPointerException e){
            throw new RuntimeException("Такого пользователя не существует.");
        }
        return findUser;
    }
}