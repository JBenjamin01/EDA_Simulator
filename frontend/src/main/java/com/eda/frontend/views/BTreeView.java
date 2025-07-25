package com.eda.frontend.views;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.eda.frontend.components.BTreeNodeView;
import com.eda.frontend.tree.BTree;
import com.eda.frontend.tree.BTreeNode;
import com.eda.frontend.tree.BinaryTreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class BTreeView extends VBox {

    private BTree tree;
    private final Pane canvas;
    private final ComboBox<Integer> degreeSelector;
    private final TextArea logArea;

    public BTreeView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Simulador de Árbol B");
        title.setFont(new Font("Arial", 24));

        TextField input = new TextField();
        input.setPromptText("Ingrese valor");
        input.setPrefWidth(150);

        degreeSelector = new ComboBox<>();
        for (int i = 2; i <= 5; i++) degreeSelector.getItems().add(i);
        degreeSelector.setValue(2); // valor por defecto

        Button insert = new Button("Insertar");
        Button create = new Button("Crear Árbol");

        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(8), Insets.EMPTY)));
        controls.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(8), BorderWidths.DEFAULT)));
        controls.getChildren().addAll(new Label("Grado mínimo:"), degreeSelector, input, insert, create);

        canvas = new Pane();
        canvas.setStyle("-fx-border-color: gray; -fx-background-color: white;");
        canvas.setPrefHeight(500);
        canvas.setPrefWidth(800);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(120);
        logArea.setStyle("-fx-font-family: 'monospace'; -fx-font-size: 13px;");

        getChildren().addAll(title, controls, canvas, logArea);

        Button guardarBtn = new Button("Guardar");
        guardarBtn.setOnAction(e -> guardarEstructura());

        Button cargarBtn = new Button("Cargar");
        cargarBtn.setOnAction(e -> cargarEstructura());

        controls.getChildren().addAll(guardarBtn, cargarBtn);

        create.setOnAction(e -> {
            int degree = degreeSelector.getValue();
            tree = new BTree(degree);
            canvas.getChildren().clear();
            logArea.clear();
            logStep("Árbol B creado con grado mínimo " + degree);
        });

        insert.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                if (tree == null) {
                    showError("Debe crear el árbol primero");
                    return;
                }

                logStep("Insertando valor " + value + "...");
                tree.insert(value);
                drawTree();
                logStep("Valor " + value + " insertado correctamente.");
                input.clear();
            } catch (NumberFormatException ex) {
                showError("Ingrese un número válido");
            }
        });
    }

    private void guardarEstructura() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Serializamos TODO el árbol
            BTreeNode root = tree.getRoot();
            
            String datosComoTexto = mapper.writeValueAsString(root);

            // Generar nombre aleatorio (por ejemplo: "Binario_ABC123")
            String codigoAleatorio = "Splay_" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            var requestMap = new HashMap<String, Object>();
            requestMap.put("nombre", codigoAleatorio);
            requestMap.put("tipo", "SPLAY");
            requestMap.put("datosJson", datosComoTexto);

            String requestBody = mapper.writeValueAsString(requestMap);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/estructuras/guardar"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(res -> Platform.runLater(() -> logStep("Árbol guardado correctamente como " + codigoAleatorio)));
        } catch (Exception e) {
            showError("Error al guardar estructura: " + e.getMessage());
        }
    }

    private void cargarEstructura() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/estructuras/listar/SPLAY"))
                .header("Accept", "application/json")
                .GET()
                .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            List<Map<String, Object>> estructuras = mapper.readValue(
                                response.body(),
                                mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
                            );

                            if (estructuras.isEmpty()) {
                                logStep("No se encontraron árboles splay guardados.");
                            } else {
                                logStep("Estructuras splay disponibles:");
                                for (Map<String, Object> estructura : estructuras) {
                                    String nombre = (String) estructura.get("nombre");
                                    Long id = Long.valueOf(estructura.get("id").toString());
                                    logStep(" - [" + id + "] " + nombre);
                                }
                            }
                        } catch (Exception e) {
                            showError("Error al procesar respuesta: " + e.getMessage());
                        }
                    });
                });
        } catch (Exception e) {
            showError("Error al cargar estructuras: " + e.getMessage());
        }
    }

    private void drawTree() {
        canvas.getChildren().clear();
        if (tree != null && tree.getRoot() != null) {
            drawNode(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
        }
    }

    private void drawNode(BTreeNode node, double x, double y, double offset) {
        if (node == null) return;

        BTreeNodeView nodeView = new BTreeNodeView(node.keys);
        nodeView.setLayoutX(x);
        nodeView.setLayoutY(y);

        double childX = x - offset * (node.children.size() - 1) / 2.0;
        double childY = y + 80;

        for (int i = 0; i < node.children.size(); i++) {
            BTreeNode child = node.children.get(i);
            double cx = childX + i * offset;

            Line line = new Line(x + 15 * node.keys.size(), y + 30, cx + 15 * child.keys.size(), childY);
            line.setStroke(Color.GRAY);
            canvas.getChildren().add(0, line); // Dibujar detrás del nodo

            drawNode(child, cx, childY, offset / 1.5);
        }

        FadeTransition ft = new FadeTransition(Duration.millis(500), nodeView);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        canvas.getChildren().add(nodeView);
    }

    private void logStep(String message) {
        logArea.appendText(message + "\n");
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
