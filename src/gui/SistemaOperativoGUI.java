package gui;

import Interfaz.IProceso;
import model.Proceso;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaOperativoGUI extends JFrame {
    private List<IProceso> procesos;
    private JPanel panelProcesos;
    private JTextArea textAreaLog;

    public SistemaOperativoGUI() {
        super("Ejemplo FIFO - Sistema Operativo");

        procesos = new ArrayList<>();
        panelProcesos = new JPanel();
        panelProcesos.setLayout(new GridLayout(0, 1));

        JScrollPane scrollPane = new JScrollPane(panelProcesos);

        textAreaLog = new JTextArea(10, 30);
        textAreaLog.setEditable(false);

        JButton agregarProcesoButton = new JButton("Agregar Proceso");
        agregarProcesoButton.addActionListener(e -> agregarProceso());

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(agregarProcesoButton);

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(textAreaLog), BorderLayout.SOUTH);

        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarProceso() {
        JTextField nombreField = new JTextField(10);
        JSpinner tiempoSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Tiempo de Ejecución:"));
        inputPanel.add(tiempoSpinner);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Agregar Proceso", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            int tiempoEjecucion = (int) tiempoSpinner.getValue();
            IProceso proceso = new Proceso(nombre, tiempoEjecucion);
            procesos.add(proceso);
            agregarPanelProceso(proceso);
        }
    }

    private void agregarPanelProceso(IProceso proceso) {
        JPanel panelProceso = new JPanel();
        panelProceso.setBorder(BorderFactory.createTitledBorder(proceso.getNombre()));
        panelProceso.setLayout(new BorderLayout());

        JProgressBar progressBar = new JProgressBar(0, proceso.getTiempoEjecucion());
        panelProceso.add(progressBar, BorderLayout.NORTH);

        JLabel tiempoLabel = new JLabel("Tiempo de CPU: 0 segundos");
        panelProceso.add(tiempoLabel, BorderLayout.SOUTH);

        panelProcesos.add(panelProceso);
        panelProcesos.revalidate(); // Actualizar el panel de procesos
        pack(); // Ajustar el tamaño del frame
        iniciarEjecucion(proceso, progressBar, tiempoLabel);
    }

    private void iniciarEjecucion(IProceso proceso, JProgressBar progressBar, JLabel tiempoLabel) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= proceso.getTiempoEjecucion(); i++) {
                    final int progreso = i;
                    try {
                        Thread.sleep(1000); // Simular 1 segundo de ejecución
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(progreso);
                        tiempoLabel.setText("Tiempo de CPU: " + progreso + " segundos");
                    });
                }
                SwingUtilities.invokeLater(() -> textAreaLog.append("Proceso " + proceso.getNombre() + " completado.\n"));
                return null;
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaOperativoGUI::new);
    }
}
