package com.epam.spring.core.view;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Occasion;
import com.epam.spring.core.model.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class TicketPdfView extends AbstractPdfView{
    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<Ticket> tickets = (List<Ticket>)model.get("tickets");
        String message = (String)model.get("message");
        document.add(new Paragraph(message));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        for(Ticket ticket: tickets) {
            Table table = new Table(2);
            table.addCell("Event");
            Occasion occasion = ticket.getOccasion();
            Event event = occasion.getEvent();
            table.addCell(ticket.getOccasion().getEvent().getName());
            table.addCell("Place");
            table.addCell(ticket.getOccasion().getAuditorium().getName());
            table.addCell("Date");
            table.addCell(dateFormat.format(ticket.getOccasion().getDate()));
            table.addCell("Time");
            table.addCell(timeFormat.format(ticket.getOccasion().getDate()));
            table.addCell("Seat");
            table.addCell(String.format("%d",ticket.getSeat()));
            document.add(table);
        }
    }
}
