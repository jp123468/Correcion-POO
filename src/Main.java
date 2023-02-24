import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    private JPanel panel2;
    private JLabel RENTADEVEHICULOSLabel;
    private JTextField txtID;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtAnio;
    private JButton insertarDatosButton;
    private JButton buscarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JComboBox txtEstado;
    private JComboBox textCombustible;
    private PreparedStatement ps;

    public Main(){




        insertarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                try{
                    conn = getConnection();
                    ps = conn.prepareStatement("INSERT INTO vehiculo(id, marca, modelo, anio, tipo_combustible, estado) VALUES(?,?,?,?,?,?)" );
                    try{

                        if( !txtID.getText().matches("[0-9]*") ){
                            throw new SQLException("Ingresa bien los datos del Vehiculo");

                        }
                        else {
                            ps.setString(1, txtID.getText());
                            ps.setString(2, txtMarca.getText());
                            ps.setString(3, txtModelo.getText());
                            ps.setString(4, txtAnio.getText());
                            ps.setString(5, textCombustible.getSelectedItem().toString());
                            ps.setString(6, txtEstado.getSelectedItem().toString());
                        }
                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        JOptionPane.showMessageDialog(null,"Ingrese bien los datos del Vehiculo");
                    }


                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Vehiculo Ingresado en el sistema");
                    }else{
                        JOptionPane.showMessageDialog(null, "El vehiculo No pudo ser Ingresada en el sistema");
                    }
                    conn.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }

            }
        });



        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;

                try{
                    conn = getConnection();
                    ps = conn.prepareStatement("UPDATE vehiculo SET id = ?, marca = ?, modelo = ?, anio = ?, tipo_combustible = ?, estado=? WHERE id ="+txtID.getText() );
                    try{

                        if( !txtID.getText().matches("[0-9]*") ){
                            throw new SQLException("Ingresa bien los datos del Vehiculo");

                        }else{
                            ps.setString(1, txtID.getText());
                            ps.setString(2, txtMarca.getText());
                            ps.setString(3, txtModelo.getText());
                            ps.setString(4, txtAnio.getText());
                            ps.setString(5, textCombustible.getSelectedItem().toString());
                            ps.setString(6, txtEstado.getSelectedItem().toString());
                        }
                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        JOptionPane.showMessageDialog(null,"Ingrese bien los datos del Vehiculo");
                    }


                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Vehiculo modificada correctamente");
                    }else{
                        JOptionPane.showMessageDialog(null, "Vehiculo no se pudo modificar");
                    }
                    conn.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
        //ELIMINAR
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                try {
                    conn = getConnection();
                    ps = conn.prepareStatement("DELETE FROM vehiculo WHERE id = ?");
                    ps.setString(1, txtID.getText());
                    int rowsAffected = ps.executeUpdate(); // Cambiar a executeUpdate()
                    conn.close();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Vehiculo eliminado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se eliminó ningún Vehiculo");
                    }
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Ingrese bien los datos del Vehiculo");
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                ResultSet rs;
                try{
                    conn = getConnection();


                    ps = conn.prepareStatement("SELECT * FROM vehiculo WHERE id= ?" );
                    ps.setString(1, txtID.getText());

                    rs = ps.executeQuery();

                    try{

                        if( !txtID.getText().matches("[0-9]*") ){
                            throw new SQLException("Ingresa bien los datos");
                        }else{
                            if(rs.next()){
                                txtID.setText( Integer.toString(rs.getInt("id")) );
                                txtMarca.setText(rs.getString("marca"));
                                txtModelo.setText(rs.getString("modelo"));
                                txtAnio.setText(rs.getString("anio"));
                                String tipoCombustible = rs.getString("tipo_combustible");
                                if (tipoCombustible != null) {
                                    textCombustible.setSelectedItem(tipoCombustible);
                                }
                                String estado = rs.getString("estado");
                                if (estado != null) {
                                    txtEstado.setSelectedItem(estado);
                                }
                            }else{
                                System.out.println("Error no funciona");
                                JOptionPane.showMessageDialog(null,"Ingrese bien los datos del Vehiculo");
                            }
                        }
                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        JOptionPane.showMessageDialog(null,"Ingrese bien los datos del Vehiculo");
                    }
                    conn.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
    }
    public void mostrarDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/vehiculo","root","Ligacampeon1");

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM vehiculo");

            while(rs.next()){

                JOptionPane.showMessageDialog(null, rs.getInt("id") + "\n" + rs.getString(2)
                        +"\n"+ rs.getString("modelo") +"\n" +
                        rs.getInt("anio") + "\n"+
                        rs.getString(5)+ "\n"+ rs.getString(6));

            }

            conexion.close();
        }catch (Exception ex){
            System.out.println(ex);
            ex.printStackTrace();
        }

    }

    public static Connection getConnection(){
        Connection conn = null;
        String base = "vehiculo";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "UGPCUGR2002";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException | SQLException ex){
            System.out.printf("Error: " + ex);
        }
        return conn;
    }




    public static void main(String[] args) {
        JFrame frame = new JFrame("VEHICULOS");
        frame.setContentPane(new Main().panel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
