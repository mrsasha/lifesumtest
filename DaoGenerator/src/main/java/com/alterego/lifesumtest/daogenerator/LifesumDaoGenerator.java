package com.alterego.lifesumtest.daogenerator;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class LifesumDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.alterego.lifesumtest");
        schema.enableKeepSectionsByDefault();

        addLifesumSearchJsonResponse(schema);
        addLifesumItemDAO(schema);


        try {
            new DaoGenerator().generateAll(schema, "..\\..\\app\\src-gen");
        } catch (IOException io) {
            new DaoGenerator().generateAll(schema, "./app/src-gen");
        }
    }

    private static void addLifesumSearchJsonResponse(Schema schema) {
        Entity queen_playlist_response = schema.addEntity("LifesumSearchDaoEntry");
        queen_playlist_response.addIdProperty().primaryKey().autoincrement();
        queen_playlist_response.addStringProperty("lifesum_response_string");
    }

    private static void addLifesumItemDAO(Schema schema) {
        Entity article = schema.addEntity("LifesumItem");
        //article.addIdProperty().primaryKey().autoincrement();

        article.addLongProperty("id").primaryKey();
        article.addIntProperty("categoryid");
        article.addDoubleProperty("fiber");
        article.addStringProperty("headimage");
        article.addDoubleProperty("pcsingram");
        article.addStringProperty("brand");

        article.addDoubleProperty("unsaturatedfat");
        article.addDoubleProperty("fat");
        article.addIntProperty("servingcategory");
        article.addIntProperty("typeofmeasurement");
        article.addDoubleProperty("protein");
        article.addIntProperty("defaultserving");
        article.addDoubleProperty("mlingram");
        article.addDoubleProperty("saturatedfat");
        article.addStringProperty("category");
        article.addBooleanProperty("verified");
        article.addStringProperty("title");
        article.addStringProperty("pcstext");

        article.addDoubleProperty("sodium");
        article.addDoubleProperty("carbohydrates");
        article.addIntProperty("showonlysametype");
        article.addIntProperty("calories");
        article.addIntProperty("serving_version");

        article.addDoubleProperty("sugar");
        article.addIntProperty("measurementid");
        article.addDoubleProperty("cholesterol");
        article.addDoubleProperty("gramsperserving");
        article.addIntProperty("showmeasurement");
        article.addDoubleProperty("potassium");




    }
}
