package com.cenfotec.proyecto;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.List;

@Named
public class EntregarVehiculo implements JavaDelegate {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String carroSeleccionado = (String) delegateExecution.getVariable("type_vehicle");

        //select solo 1 el que tiene el id mas bajo
        Usuario usuarioGanador = jdbcTemplate.queryForObject("select nombreCompleto, identificacion, correo, telefono, carroSeleccionado, monto,estadoSubasta, fechaSolicitud from Usuarios" +
                " where carroSeleccionado = '" + carroSeleccionado + "' and estadoSubasta = 'Abierto' order by fechaSolicitud asc limit 1", (rs, rowNum) ->
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
