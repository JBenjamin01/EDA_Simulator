package com.eda.frontend.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AboutView extends VBox {

    public AboutView() {
        setPadding(new Insets(30));
        setSpacing(12);
        setAlignment(Pos.TOP_LEFT);

        // Título principal
        Label title = new Label("EDA - Simulator");
        title.setFont(Font.font("Arial", 26));
        title.setStyle("-fx-font-weight: bold;");

        // Universidad
        Label university = new Label("Universidad Nacional de San Agustín de Arequipa");
        university.setFont(Font.font("Arial", 18));

        // Escuela
        Label school = new Label("Escuela Profesional de Ingeniería de Sistemas");
        school.setFont(Font.font("Arial", 16));

        // Curso
        Label course = new Label("Curso: Estructura de Datos y Algoritmos");
        course.setFont(Font.font("Arial", 16));

        // Docente
        Label teacher = new Label("Docente: Ing. Roni Guillermo Apaza Aceituno");
        teacher.setFont(Font.font("Arial", 16));

        // Semestre
        Label semester = new Label("Semestre académico: 2025-I");
        semester.setFont(Font.font("Arial", 16));

        // Versión
        Label version = new Label("Versión actual del sistema: 1.0 (2025)");
        version.setFont(Font.font("Arial", 16));

        // Integrantes
        Label authorsTitle = new Label("Créditos del equipo desarrollador:");
        authorsTitle.setFont(Font.font("Arial", 17));
        authorsTitle.setStyle("-fx-font-weight: bold;");

        VBox authorsList = new VBox();
        authorsList.setSpacing(6);
        authorsList.setAlignment(Pos.TOP_LEFT);

        // Primer alumno
        VBox alumno1 = new VBox(
            styledLabel("- Jhonatan Benjamin Mamani Céspedes"),
            createEmailHyperlink("jmamanices@unsa.edu.pe")
        );

        // Segundo alumno
        VBox alumno2 = new VBox(
            styledLabel("- Jeic Lucciano Valentino Tijero Dávila"),
            createEmailHyperlink("jtijero@unsa.edu.pe")
        );

        authorsList.getChildren().addAll(alumno1, alumno2);

        // Herramientas usadas
        Label tools = new Label("Tecnologías utilizadas: Java 17, JavaFX, Scene Builder, Spring Boot, Gradle, Maven.");
        tools.setFont(Font.font("Arial", 15));

        // Repositorio
        Label repoLabel = new Label("Repositorio oficial:");
        repoLabel.setFont(Font.font("Arial", 15));
        Hyperlink githubLink = new Hyperlink("https://github.com/JBenjamin01/EDA_Simulator");
        githubLink.setFont(Font.font("Arial", 14));
        githubLink.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/JBenjamin01/EDA_Simulator"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Licencia
        Label license = new Label("Licencia: Proyecto académico libre para uso educativo.");
        license.setFont(Font.font("Arial", 15));

        // Créditos extra
        Label extraCredits = new Label("Agradecimientos: Al docente guía y a los compañeros de curso.");
        extraCredits.setFont(Font.font("Arial", 15));

        // Copyright
        Text copyrightText = new Text("© 2025 Universidad Nacional de San Agustín de Arequipa. Todos los derechos reservados.");
        copyrightText.setFill(Color.GRAY);
        copyrightText.setStyle("-fx-font-size: 13px;");
        TextFlow copyright = new TextFlow(copyrightText);

        getChildren().addAll(
            title,
            university,
            school,
            course,
            teacher,
            semester,
            version,
            authorsTitle,
            authorsList,
            tools,
            repoLabel,
            githubLink,
            license,
            extraCredits,
            copyright
        );
    }

    private Hyperlink createEmailHyperlink(String email) {
        Hyperlink link = new Hyperlink(email);
        link.setFont(Font.font("Arial", 14));
        link.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().mail(new java.net.URI("mailto:" + email));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        link.setStyle("-fx-text-fill: #1a73e8;");
        return link;
    }

    private Label styledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 15));
        return label;
    }
}
