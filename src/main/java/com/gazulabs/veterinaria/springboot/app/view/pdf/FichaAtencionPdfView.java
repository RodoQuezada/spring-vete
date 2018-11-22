package com.gazulabs.veterinaria.springboot.app.view.pdf;


import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component("ficha-atencion/ver-diagnostico")
public class FichaAtencionPdfView extends AbstractPdfView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        FichaAtencion fichaAtencion = (FichaAtencion) model.get("fichaAtencion");

        PdfPTable tablaCliente = new PdfPTable(1);
        tablaCliente.setSpacingAfter(20);

        PdfPCell cell = null;
        cell = new PdfPCell(new Phrase("DATOS DEL CLIENTE"));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);

        tablaCliente.addCell(cell);
        tablaCliente.addCell("Nombre: " +  fichaAtencion.getPaciente().getCliente().getNombre().concat(" ").concat(fichaAtencion.getPaciente().getCliente().getApellidoPaterno()));
        tablaCliente.addCell("Rut: "+ fichaAtencion.getPaciente().getCliente().getRut());
        tablaCliente.addCell("Correo Electronico: " + fichaAtencion.getPaciente().getCliente().getEmail());
        tablaCliente.addCell("Dirección: " + fichaAtencion.getPaciente().getCliente().getDireccion());

        PdfPTable tablaPaciente = new PdfPTable(1);
        tablaPaciente.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase("DATOS DEL PACIENTE"));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);

        tablaPaciente.addCell(cell);
        tablaPaciente.addCell("Nombre: " + fichaAtencion.getPaciente().getNombre());
        tablaPaciente.addCell("Especie: " + fichaAtencion.getPaciente().getRaza().getEspecie().getNombre());
        tablaPaciente.addCell("Raza: " + fichaAtencion.getPaciente().getRaza().getNombre());

        PdfPTable tablaFichaAtencion = new PdfPTable(1);
        tablaFichaAtencion.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase("DATOS FICHA ATENCIÓN"));
        cell.setBackgroundColor(new Color(50, 230, 203));
        cell.setPadding(8f);

        tablaFichaAtencion.addCell(cell);

        tablaFichaAtencion.addCell("Fecha: " + df.format(fichaAtencion.getFechaAtencion()));
        tablaFichaAtencion.addCell("Motivo Visita: " + fichaAtencion.getMotivo());
        tablaFichaAtencion.addCell("Diagnostico: " + fichaAtencion.getDiagnostico());

        document.add(tablaCliente);
        document.add(tablaPaciente);
        document.add(tablaFichaAtencion);


    }
}
