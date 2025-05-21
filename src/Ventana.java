import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel principal;
    private JSpinner spId;
    private JComboBox cboPeligrosidad;
    private JTextField txtNombre;
    private JTextField txtMision;
    private JTextField txtPagoM;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblMision;
    private JLabel lblPeligrosidad;
    private JLabel lblPagoM;
    private JTextArea txtListado;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnMostrar;
    private JButton btnAporte;
    private GestionAvengers g=new GestionAvengers();

    public Ventana() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(spId.getValue().toString());
                    String nombre = txtNombre.getText();
                    String mision = txtMision.getText();
                    int peligro = Integer.parseInt(cboPeligrosidad.getSelectedItem().toString());
                    double pagoMensual = Double.parseDouble(txtPagoM.getText());
                    Avengers avengers = new Avengers(id,nombre,mision,peligro,pagoMensual);
                    g.registrarAvengers(avengers);
                    txtListado.setText("Avenger registrado con éxito.\n");
                }catch (Exception ex){
                    txtListado.setText("Error al registrar Avenger");
                }
            }
        });
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(spId.getValue().toString());
                String nuevoNombre = txtNombre.getText();
                String nuevaMision = txtMision.getText();
                int nuevaPeligrosidad = Integer.parseInt(cboPeligrosidad.getSelectedItem().toString());
                int nuevoPago = Integer.parseInt(txtPagoM.getText());
                try {
                    g.editarAvengers(codigo, nuevoNombre, nuevaMision, nuevaPeligrosidad, nuevoPago);
                    txtListado.setText("Avenger editado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());

                }
            }
        });
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("Avengers Registrados\n");
                for (Avengers avenger : g.listarAvengers()){
                    sb.append(String.format("ID: %s, Nombre: %s,  Misión: %s, Peligrosidad: %d, Pago: %.2f\n",
                            avenger.getId(), avenger.getNombre(),
                            avenger.getMision(), avenger.getNivelPeligrosidad(), avenger.getPagoMensual()));
                }
                txtListado.setText(sb.toString());
            }
        });


        btnAporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(spId.getValue().toString());
                    String detalles = g.calcularfondoheroes(id);
                    txtListado.setText(detalles);
                } catch (Exception ex) {
                    txtListado.setText("Error al calcular detalles.\n"
                    + ex.getMessage());
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
