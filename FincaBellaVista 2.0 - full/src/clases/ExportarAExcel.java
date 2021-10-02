package clases;
import java.io.DataOutputStream;
import java.util.List;
import jxl.Workbook;
import jxl.write.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;

public class ExportarAExcel {
    private File archivo;
    private  List<JTable> tabla; 
    private  List<String> nom_filas;

    public ExportarAExcel(File archivo, List<JTable> tabla, List<String> nom_filas) throws Exception {
        this.archivo = archivo;
        this.tabla = tabla;
        this.nom_filas = nom_filas;
        if(nom_filas.size()!=tabla.size()){
            throw new Exception ("Error");
        }
    }
    
    public boolean export(){
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(archivo));
            WritableWorkbook w = Workbook.createWorkbook(out);
            for (int i = 0; i < tabla.size(); i++) {
                JTable table = tabla.get(i);
                WritableSheet s = w.createSheet(nom_filas.get(i), 0);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    for (int k = 0; k < table.getRowCount(); k++) {
                        if(k==0){
                            Object object1 = table.getColumnName(j);
                            s.addCell(new Label (j,k,String.valueOf(object1)));
                        }
                        Object object = table.getValueAt(k, j);
                        s.addCell(new Label (j,k+1,String.valueOf(object)));
                    }
                }
            }
            w.write();
            w.close();
            return true;
        }
        catch(IOException | WriteException e){        
            return false;
        }
    }
   
}
