package com.cenfotec.proyecto;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.List;

@Named
public class RealizarSubasta implements JavaDelegate {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

//        String nombreCompleto = (String) delegateExecution.getVariable("nombreCompleto");
//        String telefono = (String) delegateExecution.getVariable("telefono");
//        String identificacion = (String) delegateExecution.getVariable("identificacion");
//        String correo = (String) delegateExecution.getVariable("correo");
//        String carroSeleccionado = (String) delegateExecution.getVariable("carroSeleccionado");
//        Double monto = (Double) delegateExecution.getVariable("monto");

        //jdbcTemplate.execute("DROP TABLE Usuarios");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Usuarios (nombreCompleto varchar(255), identificacion varchar(255) primary key, correo varchar(255) unique , telefono varchar(255)," +
                "carroSeleccionado varchar(255), monto decimal, estadoSubasta varchar(255), fechaSolicitud DATE)");
//        jdbcTemplate.update("Insert into Usuario (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto) " +
//                " values (?,?,?)",nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto);


//        jdbcTemplate.update("Insert into Usuarios (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud) " +
//                " values(?,?,?,?,?,?,?,?)","Maria", "123456789", "m@gmail.com", "88888888", "Suburvan mini", 40000, "Abierto", LocalDate.now());
//
//        jdbcTemplate.update("Insert into Usuarios (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud) " +
//                " values (?,?,?,?,?,?,?,?)","Julio", "9874654321", "julio@gmail.com", "87544568", "Suburban plus", 48000, "Abierto", LocalDate.now());
//
//        jdbcTemplate.update("Insert into Usuarios (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud) " +
//                " values (?,?,?,?,?,?,?,?)","Mario", "2165478995", "mario@gmail.com", "84659874", "Suburvan mini", 42000, "Abierto", LocalDate.now());
//
//        jdbcTemplate.update("Insert into Usuarios (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud) " +
//                " values (?,?,?,?,?,?,?,?)","Mariela", "874569548", "mariela@gmail.com", "82549876", "Monoplaza", 10000, "Abierto", LocalDate.now());
//
//        jdbcTemplate.update("Insert into Usuarios (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud) " +
//                " values (?,?,?,?,?,?,?,?)","Marlon", "4567894219", "marlon@gmail.com", "54684587", "Heavy Suburban", 62000, "Abierto", LocalDate.now());
//
//        jdbcTemplate.update("Insert into Usuarios (nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud) " +
//                " values (?,?,?,?,?,?,?,?)","Tracy", "124987565", "tracy@gmail.com", "75849652", "Sedan", 25000, "Abierto", LocalDate.now());

        // aqui tienen un select all
        List<Usuario> usuarios  = jdbcTemplate.query("Select * from Usuarios", (rs, rowNum) ->
                new Usuario(
                        rs.getString("nombreCompleto"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("carroSeleccionado"),
                        rs.getDouble("monto"),
                        rs.getString("estadoSubasta"),
                        rs.getDate("fechaSolicitud")
                ));
        //aqui listo todo
        for (Usuario Usuario: usuarios) {
            System.out.println(Usuario.getNombreCompleto() + " " + Usuario.getEstadoSubasta());
        }
        String carroSeleccionado = (String) delegateExecution.getVariable("type_vehicle");

        //select solo 1 el que tiene el id mas bajo
        Usuario usuarioGanador = jdbcTemplate.queryForObject("select nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud from Usuarios" +
                " where carroSeleccionado = '" + carroSeleccionado + "' and estadoSubasta = 'Abierto' order by monto desc limit 1", (rs, rowNum) ->
                new Usuario(
                        rs.getString("nombreCompleto"),
                        rs.getString("identificacion"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("carroSeleccionado"),
                        rs.getDouble("monto"),
                        rs.getString("estadoSubasta"),
                        rs.getDate("fechaSolicitud")
                ));


        usuarioGanador.cambiarEstadoSubasta();

        jdbcTemplate.update("Update Usuarios set estadoSubasta= '" + usuarioGanador.getEstadoSubasta()
                + "' where identificacion = '" + usuarioGanador.getIdenfificacion() + "'");



        System.out.println(usuarioGanador.getIdenfificacion() + " " + usuarioGanador.getNombreCompleto());

       delegateExecution.setVariable("nombre", usuarioGanador.getNombreCompleto());
       delegateExecution.setVariable("correo", usuarioGanador.getCorreo());
       delegateExecution.setVariable("telefono", usuarioGanador.getTelefono());
       delegateExecution.setVariable("vehiculoAdquirido", usuarioGanador.getCarroSeleccionado());
    }
}
