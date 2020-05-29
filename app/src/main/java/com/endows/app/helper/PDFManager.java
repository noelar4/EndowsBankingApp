package com.endows.app.helper;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.endows.app.models.app.Errors;
import com.endows.app.models.db.TransactionHistory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PDFManager {

    BaseColor myColor = WebColors.getRGBColor("#9E9E9E");
    BaseColor myColor1 = WebColors.getRGBColor("#757575");

    public Errors generatePdfReport(Context context, List<TransactionHistory> tnxHistory) {
        PdfPCell cell;
        String path;
        File dir;
        File file;
        Errors errors = null;
        //create document file
        Document doc = new Document();
        try {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Endows/PDF_Files";
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            file = new File(dir, "Endows_statement_" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
            //create table
            PdfPTable pt = new PdfPTable(5);
            pt.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35, 20, 25};
            pt.setWidths(fl);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            try {
                pt.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.addElement(new Paragraph("Transaction history"));
                pt.addCell(cell);
                cell = new PdfPCell(new Paragraph(""));
                cell.setBorder(Rectangle.NO_BORDER);
                pt.addCell(cell);

                PdfPTable pTable = new PdfPTable(1);
                pTable.setWidthPercentage(100);
                cell = new PdfPCell();
                cell.setColspan(1);
                cell.addElement(pt);
                pTable.addCell(cell);
                PdfPTable table = new PdfPTable(5);

                float[] columnWidth = new float[]{30, 30, 30, 20, 60};
                table.setWidths(columnWidth);

                cell = new PdfPCell();

                cell.setBackgroundColor(myColor);
                cell.setColspan(5);
                cell.addElement(pTable);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(5);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setColspan(5);

                cell.setBackgroundColor(myColor1);

                cell = new PdfPCell(new Phrase("Transaction Number"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("To"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("From"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Amount"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Timestamp"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(5);

                for (int i = 1; i < tnxHistory.size(); i++) {
                    if (tnxHistory.get(i) != null) {
                        table.addCell("" + i);
                        table.addCell(tnxHistory.get(i).getTo() != null ? tnxHistory.get(i).getTo() : "");
                        table.addCell(tnxHistory.get(i).getFrom() != null ? tnxHistory.get(i).getFrom() : "");
                        table.addCell(tnxHistory.get(i).getAmount() != null ? (!("Y".equalsIgnoreCase(tnxHistory.get(i).getIsCredit()))?"-$"+tnxHistory.get(i).getAmount():"+$"+tnxHistory.get(i).getAmount()) : "");
                        table.addCell(tnxHistory.get(i).getTimestamp() != null ? tnxHistory.get(i).getTimestamp() : "");
                    }
                }

                PdfPTable ftable = new PdfPTable(5);
                ftable.setWidthPercentage(100);
                float[] columnWidthaa = new float[]{30, 10, 30, 10, 50};
                ftable.setWidths(columnWidthaa);

                doc.add(table);
                Toast.makeText(context, "Downloaded PDF at "+path, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                errors = new Errors("",e.getMessage(),e.getCause().toString());
            } finally {
                doc.close();
            }
        } catch (Exception e) {
            errors = new Errors("",e.getMessage(),e.getCause().toString());
            e.printStackTrace();
        }
        return errors;
    }
}
