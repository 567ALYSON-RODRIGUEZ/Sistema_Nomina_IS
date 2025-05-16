package com.api.nomina.datos;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.nomina.modelo.EEmpresa;
import com.api.nomina.repositorio.EmpresaRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DEmpresa {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public void crearEmpresa(Integer idEmpresa, String nombreLegal, String sigla, String NIT, String domicilioFiscal, String correo, String telefono, String estado) {
        empresaRepositorio.spGestionEmpresa("CREATE", idEmpresa, nombreLegal, sigla, NIT, domicilioFiscal, correo, telefono, estado);
    }

    
    public void reemplazarEmpresa(Integer idEmpresa, String nombreLegal, String sigla, String NIT, String domicilioFiscal, String correo, String telefono, String estado) {
        empresaRepositorio.spGestionEmpresa("UPDATE", idEmpresa, nombreLegal, sigla, NIT, domicilioFiscal, correo, telefono, estado);
    }
    
    @Transactional
    public Map<String, Object> actualizarParcial(Integer idEmpresa, Map<String, Object> campos) {
        EEmpresa actual = buscarEmpresaPorId(idEmpresa);
        if (actual == null) {
            throw new RuntimeException("Empresa no encontrada");
        }

        Map<String, Object> cambios = new HashMap<>();

        // Compara y actualiza los campos
        String nombreLegal = (String) campos.getOrDefault("nombreLegal", actual.getNombreLegal());
        if (nombreLegal != null && !nombreLegal.equals(actual.getNombreLegal())) {
            cambios.put("nombreLegal", nombreLegal);
            actual.setNombreLegal(nombreLegal);
        }

        String sigla = (String) campos.getOrDefault("sigla", actual.getSigla());
        if (sigla != null && !sigla.equals(actual.getSigla())) {
            cambios.put("sigla", sigla);
            actual.setSigla(sigla);
        }

        // Manejo de nit, verificando si es null antes de compararlo
        String nit = (String) campos.getOrDefault("nit", actual.getNit());
        if (nit != null && !nit.equals(actual.getNit())) {
            cambios.put("nit", nit);
            actual.setNit(nit);
        }

        String domicilioFiscal = (String) campos.getOrDefault("domicilioFiscal", actual.getDomicilioFiscal());
        if (domicilioFiscal != null && !domicilioFiscal.equals(actual.getDomicilioFiscal())) {
            cambios.put("domicilioFiscal", domicilioFiscal);
            actual.setDomicilioFiscal(domicilioFiscal);
        }

        String correo = (String) campos.getOrDefault("correo", actual.getCorreo());
        if (correo != null && !correo.equals(actual.getCorreo())) {
            cambios.put("correo", correo);
            actual.setCorreo(correo);
        }

        String telefono = (String) campos.getOrDefault("telefono", actual.getTelefono());
        if (telefono != null && !telefono.equals(actual.getTelefono())) {
            cambios.put("telefono", telefono);
            actual.setTelefono(telefono);
        }

        String estado = (String) campos.getOrDefault("estado", actual.getEstado());
        if (estado != null && !estado.equals(actual.getEstado())) {
            cambios.put("estado", estado);
            actual.setEstado(estado);
        }

        // Si hay cambios, persiste la entidad
        if (!cambios.isEmpty()) {
            empresaRepositorio.save(actual); // Guarda los cambios en la base de datos
        }

        return cambios; // Devuelve los campos actualizados
    }


    public void eliminarEmpresa(Integer idEmpresa) {
        empresaRepositorio.spGestionEmpresa("DELETE", idEmpresa, null, null, null, null, null, null, null);
    }
    
   
    @Transactional(readOnly = true)
    public List<EEmpresa> listarEmpresas() {
        return empresaRepositorio.spEmpresaListar("READ", null, null, null, null, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public EEmpresa buscarEmpresaPorId(Integer idEmpresa) {
        List<EEmpresa> resultado = empresaRepositorio.spEmpresaListar("READ_ID", idEmpresa, null, null, null, null, null, null, null);
        return resultado.isEmpty() ? null : resultado.get(0); // Devuelve el primero que coincida
    }

    
}


