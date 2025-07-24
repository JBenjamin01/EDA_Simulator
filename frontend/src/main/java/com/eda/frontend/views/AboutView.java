package com.eda.frontend.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AboutView extends VBox {

    public AboutView() {
        setPadding(new Insets(40));
        setSpacing(20);
        setAlignment(Pos.TOP_LEFT);
        setStyle("-fx-background-color: #F9F9F9;");

        // Título
        Label title = new Label("EDA Simulator");
        title.setFont(Font.font("Arial", 30));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label subtitle = new Label("Información del proyecto");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setStyle("-fx-text-fill: #555555;");

        Separator sep1 = new Separator();

        // Información institucional
        VBox institutionInfo = new VBox(8,
            styledInfo("Universidad Nacional de San Agustín de Arequipa", 18),
            styledInfo("Escuela Profesional de Ingeniería de Sistemas", 16),
            styledInfo("Curso: Estructura de Datos y Algoritmos", 16),
            styledInfo("Docente: Msc. Roni Guillermo Apaza Aceituno", 16),
            styledInfo("Semestre académico: 2025-I", 16),
            styledInfo("Versión del sistema: 1.0 (2025)", 16)
        );

        Separator sep2 = new Separator();

        // Créditos
        Label authorsTitle = new Label("Equipo de desarrollo");
        authorsTitle.setFont(Font.font("Arial", 18));
        authorsTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        VBox authorsList = new VBox(10,
            createAuthor("- Jhonatan Benjamin Mamani Céspedes", "jmamanices@unsa.edu.pe"),
            createAuthor("- Jeic Lucciano Valentino Tijero Dávila", "jtijero@unsa.edu.pe")
        );

        Separator sep3 = new Separator();

        // Herramientas y recursos
        VBox toolsInfo = new VBox(10);
        toolsInfo.getChildren().addAll(
            styledInfo("Tecnologías utilizadas: Java 17, JavaFX, Scene Builder, Spring Boot, Gradle, Maven.", 15),
            styledInfo("Repositorio oficial:", 15),
            createHyperlink("https://github.com/JBenjamin01/EDA_Simulator")
        );

        Separator sep4 = new Separator();

        // Licencia y agradecimientos
        VBox closingNotes = new VBox(8,
            styledInfo("Licencia: Proyecto académico libre para uso educativo.", 15),
            styledInfo("Agradecimientos: Al docente guía y a los compañeros de curso.", 15)
        );

        // Copyright
        Text copyrightText = new Text("© 2025 Universidad Nacional de San Agustín de Arequipa. Todos los derechos reservados.");
        copyrightText.setFill(Color.GRAY);
        TextFlow copyright = new TextFlow(copyrightText);

        getChildren().addAll(
            title, subtitle, sep1,
            institutionInfo, sep2,
            authorsTitle, authorsList, sep3,
            toolsInfo, sep4,
            closingNotes,
            new Separator(),
            copyright
        );
    }

    private Label styledInfo(String text, int fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", fontSize));
        label.setStyle("-fx-text-fill: #333333;");
        return label;
    }

    private VBox createAuthor(String name, String email) {
        Label nameLabel = styledInfo(name, 15);
        Hyperlink emailLink = createEmailHyperlink(email);
        VBox authorBox = new VBox(2, nameLabel, emailLink);
        authorBox.setAlignment(Pos.TOP_LEFT);
        return authorBox;
    }

    private Hyperlink createEmailHyperlink(String email) {
        Hyperlink link = new Hyperlink(email);
        link.setFont(Font.font("Arial", 14));
        link.setStyle("-fx-text-fill: #1a73e8;");
        link.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().mail(new java.net.URI("mailto:" + email));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return link;
    }

    private Hyperlink createHyperlink(String url) {
        Hyperlink link = new Hyperlink(url);
        link.setFont(Font.font("Arial", 14));
        link.setStyle("-fx-text-fill: #1a73e8;");
        link.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return link;
    }
}
