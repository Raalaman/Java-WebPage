/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Linea;
import paw.model.Pedido;
import paw.model.PedidoAnulado;
import paw.util.UtilesString;
import paw.util.servlet.MiParameterParser;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
public class CreateExcell extends HttpServlet {

    GestorBDPedidos gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MiParameterParser parser = new MiParameterParser(request, "UTF-8");
        String cp = parser.getStringParameter("cp", null);
        HttpSession session = request.getSession();
        if (!UtilesString.isVacia(cp)) {
            tratarPedido(cp, session, response, request);
        } else {
            request.setAttribute("link", "../Salir");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No deberías estar aquí");
        }
        UtilesServlet.doForward(request, response, "pedidosCliente.jsp");
    }

    private void tratarPedido(String cp, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        try {
            Pedido pedido = gbd.getPedido(cp);
            if (pedido != null) {
                Cliente cliente = (Cliente) session.getAttribute("Cliente");
                if (gbd.getPedidos(cliente.getCodigo()).stream().anyMatch(x -> x.getCodigo().compareTo(cp) == 0)) {
                    tratarExcell(response, request, pedido, cliente);
                } else {
                    request.setAttribute("link", "../Salir");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usted no está autorizado para consultar esta información");
                }
            } else {
                PedidoAnulado pedidoAnulado = gbd.getPedidoAnulado(cp);
                if (pedidoAnulado != null) {
                    Cliente cliente = (Cliente) session.getAttribute("Cliente");
                    if (gbd.getPedidos(cliente.getCodigo()).stream().anyMatch(x -> x.getCodigo().compareTo(cp) == 0)) {
                        tratarExcell(response, request, pedido, cliente);
                    } else {
                        request.setAttribute("link", "../Salir");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usted no está autorizado para consultar esta información");
                    }
                }
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CreateExcell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Se podría hacer muchísimo mejor este método, existen repeticiones de código y variables que se podrían reutilizar.
    private void tratarExcell(HttpServletResponse response, HttpServletRequest request, Pedido pedido, Cliente cliente) throws ServletException, IOException {
        try (OutputStream out = response.getOutputStream();
                HSSFWorkbook workbook = new HSSFWorkbook();) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Pedido-" + pedido.getCodigo() + "-Electrosa.xls");
            HSSFSheet thisSheet = workbook.createSheet();

            createMergedCells(thisSheet);

            primeraFila(thisSheet, workbook, pedido);

            CellStyle StyleTeceraLinea = terceraFila(thisSheet, workbook);

            CellStyle StyleQuintaLinea = quintaFila(thisSheet, workbook, cliente, pedido);

            septimaFila(thisSheet, workbook, StyleQuintaLinea, pedido);

            decimaFila(thisSheet, StyleTeceraLinea);

            onceavaFila(thisSheet, workbook);

            doceavaFila(thisSheet, workbook);

            int lineaActual = 12;
            for (Linea linea : pedido.getLineas()) {
                lineaActual++;
                CellRangeAddress MergeLineas = new CellRangeAddress(lineaActual, lineaActual, 1, 2);
                thisSheet.addMergedRegion(MergeLineas);
                crearLineaPedido(thisSheet, workbook, linea, lineaActual);
            }

            decimoSeptimaLinea(thisSheet, workbook, lineaActual + 1);

            ultimaFila(thisSheet, workbook, lineaActual + 2);
            autosizeALL(thisSheet);
            workbook.write(out);
            out.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VerPedido.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "AreaCliente");
            throw new ServletException(ex);
        }
    }

    private void createMergedCells(HSSFSheet thisSheet) {
        CellRangeAddress MergePrimeraLinea = new CellRangeAddress(0, 0, 0, 6);
        CellRangeAddress MergeTerceraLinea = new CellRangeAddress(2, 2, 0, 6);
        CellRangeAddress MergeQuintaLineaAB = new CellRangeAddress(4, 4, 0, 1);
        CellRangeAddress MergeQuintaLineaDE = new CellRangeAddress(4, 4, 3, 4);
        CellRangeAddress MergeQuintaLineaFG = new CellRangeAddress(4, 4, 5, 6);
        CellRangeAddress MergeSeptimaLineaAB = new CellRangeAddress(6, 7, 0, 1);
        CellRangeAddress MergeSeptimaLineaC = new CellRangeAddress(6, 7, 2, 2);
        CellRangeAddress MergeDecimaLinea = new CellRangeAddress(9, 9, 0, 6);
        CellRangeAddress MergeOnceavaFila = new CellRangeAddress(11, 11, 0, 6);
        CellRangeAddress MergeDoceavaFila = new CellRangeAddress(12, 12, 1, 2);
        thisSheet.addMergedRegion(MergePrimeraLinea);
        thisSheet.addMergedRegion(MergeTerceraLinea);
        thisSheet.addMergedRegion(MergeQuintaLineaAB);
        thisSheet.addMergedRegion(MergeQuintaLineaDE);
        thisSheet.addMergedRegion(MergeQuintaLineaFG);
        thisSheet.addMergedRegion(MergeSeptimaLineaAB);
        thisSheet.addMergedRegion(MergeSeptimaLineaC);
        thisSheet.addMergedRegion(MergeDecimaLinea);
        thisSheet.addMergedRegion(MergeOnceavaFila);
        thisSheet.addMergedRegion(MergeDoceavaFila);
    }

    private void autosizeALL(HSSFSheet thisSheet) {
        thisSheet.autoSizeColumn(0);
        thisSheet.autoSizeColumn(1);
        thisSheet.autoSizeColumn(2);
        thisSheet.autoSizeColumn(3);
        thisSheet.autoSizeColumn(4);
        thisSheet.autoSizeColumn(5);
        thisSheet.autoSizeColumn(6);
    }

    private void decimaFila(HSSFSheet thisSheet, CellStyle StyleTeceraLinea) {
        HSSFRow DecimaFila = thisSheet.createRow(9);
        HSSFCell DecimaCell = DecimaFila.createCell(0);
        DecimaCell.setCellStyle(StyleTeceraLinea);
        DecimaCell.setCellType(CellType.STRING);
        DecimaCell.setCellValue("Detalle del pedido");
    }

    private void onceavaFila(HSSFSheet thisSheet, final HSSFWorkbook workbook) {
        HSSFRow onceavaFila = thisSheet.createRow(11);
        CellStyle styleOnceavaFila = workbook.createCellStyle();
        styleOnceavaFila.setAlignment(HorizontalAlignment.RIGHT);
        styleOnceavaFila.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        styleOnceavaFila.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        styleOnceavaFila.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont fontOnceava = workbook.createFont();
        fontOnceava.setFontName("Arial");
        fontOnceava.setFontHeightInPoints((short) 10);
        fontOnceava.setBold(true);
        fontOnceava.setColor(HSSFColor.BLACK.index);
        styleOnceavaFila.setFont(fontOnceava);
        HSSFCell DecimaCell = onceavaFila.createCell(0);
        DecimaCell.setCellStyle(styleOnceavaFila);
        DecimaCell.setCellType(CellType.STRING);
        DecimaCell.setCellValue("Fecha Entrega   ");
    }

    private void doceavaFila(HSSFSheet thisSheet, final HSSFWorkbook workbook) {
        HSSFRow doceavaFila = thisSheet.createRow(12);
        CellStyle styleOnceavaFila = workbook.createCellStyle();
        styleOnceavaFila.setAlignment(HorizontalAlignment.CENTER);
        styleOnceavaFila.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        styleOnceavaFila.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        styleOnceavaFila.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont fontOnceava = workbook.createFont();
        fontOnceava.setFontName("Arial");
        fontOnceava.setFontHeightInPoints((short) 10);
        fontOnceava.setBold(true);
        fontOnceava.setColor(HSSFColor.BLACK.index);
        styleOnceavaFila.setFont(fontOnceava);

        HSSFCell doceavaCellA = doceavaFila.createCell(0);
        doceavaCellA.setCellStyle(styleOnceavaFila);
        doceavaCellA.setCellType(CellType.STRING);
        doceavaCellA.setCellValue("Cantidad");

        HSSFCell doceavaCellBC = doceavaFila.createCell(1);
        doceavaCellBC.setCellStyle(styleOnceavaFila);
        doceavaCellBC.setCellType(CellType.STRING);
        doceavaCellBC.setCellValue("Articulo");

        HSSFCell doceavaCellD = doceavaFila.createCell(3);
        doceavaCellD.setCellStyle(styleOnceavaFila);
        doceavaCellD.setCellType(CellType.STRING);
        doceavaCellD.setCellValue("P.V.P.");

        HSSFCell doceavaCellE = doceavaFila.createCell(4);
        doceavaCellE.setCellStyle(styleOnceavaFila);
        doceavaCellE.setCellType(CellType.STRING);
        doceavaCellE.setCellValue("Su precio");

        HSSFCell doceavaCellF = doceavaFila.createCell(5);
        doceavaCellF.setCellStyle(styleOnceavaFila);
        doceavaCellF.setCellType(CellType.STRING);
        doceavaCellF.setCellValue("Deseada");

        HSSFCell doceavaCellG = doceavaFila.createCell(6);
        doceavaCellG.setCellStyle(styleOnceavaFila);
        doceavaCellG.setCellType(CellType.STRING);
        doceavaCellG.setCellValue("Prevista");
    }

    private void septimaFila(HSSFSheet thisSheet, final HSSFWorkbook workbook, CellStyle StyleQuintaLinea, Pedido pedido) {
        HSSFRow SeptimaFila = thisSheet.createRow(6);
        CellStyle StyleSeptimaFila = workbook.createCellStyle();
        StyleSeptimaFila.setAlignment(HorizontalAlignment.RIGHT);
        HSSFFont fontSexta = workbook.createFont();
        fontSexta.setFontName("Arial");
        fontSexta.setFontHeightInPoints((short) 10);
        fontSexta.setBold(true);
        fontSexta.setColor(HSSFColor.BLACK.index);
        StyleSeptimaFila.setFont(fontSexta);

        HSSFCell SeptimaLineaAB = SeptimaFila.createCell(0);
        SeptimaLineaAB.setCellStyle(StyleSeptimaFila);
        SeptimaLineaAB.setCellType(CellType.STRING);
        SeptimaLineaAB.setCellValue("A entregar en: ");

        HSSFFont fontSextaOtrA = workbook.createFont();
        fontSextaOtrA.setFontName("Arial");
        fontSextaOtrA.setFontHeightInPoints((short) 10);
        fontSextaOtrA.setBold(false);

        fontSextaOtrA.setColor(HSSFColor.BLACK.index);
        CellStyle OtherChange = workbook.createCellStyle();
        OtherChange.cloneStyleFrom(StyleQuintaLinea);
        OtherChange.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        OtherChange.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        OtherChange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        OtherChange.setFont(fontSextaOtrA);
        OtherChange.setAlignment(HorizontalAlignment.LEFT);

        HSSFCell SeptimaLineaC = SeptimaFila.createCell(2);
        SeptimaLineaC.setCellStyle(OtherChange);
        SeptimaLineaC.setCellType(CellType.STRING);
        SeptimaLineaC.setCellValue(pedido.getDirEntrega().getCalle() + System.lineSeparator() + pedido.getDirEntrega().getCp() + "-" + pedido.getDirEntrega().getCiudad());
    }

    private CellStyle quintaFila(HSSFSheet thisSheet, final HSSFWorkbook workbook, Cliente cliente, Pedido pedido) {
        HSSFRow QuintaFila = thisSheet.createRow(4);
        CellStyle StyleQuintaLinea = workbook.createCellStyle();
        HSSFFont fontQuinta = workbook.createFont();
        fontQuinta.setFontName("Arial");
        fontQuinta.setFontHeightInPoints((short) 10);
        fontQuinta.setBold(false);
        fontQuinta.setColor(HSSFColor.BLACK.index);
        StyleQuintaLinea.setFont(fontQuinta);
        StyleQuintaLinea.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        StyleQuintaLinea.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        StyleQuintaLinea.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCell QuintaLineaAB = QuintaFila.createCell(0);
        QuintaLineaAB.setCellStyle(StyleQuintaLinea);
        QuintaLineaAB.setCellType(CellType.STRING);
        QuintaLineaAB.setCellValue("Cliente: " + cliente.getCodigo());
        HSSFCell QuintaLineaC = QuintaFila.createCell(2);
        QuintaLineaC.setCellStyle(StyleQuintaLinea);
        QuintaLineaC.setCellType(CellType.STRING);
        QuintaLineaC.setCellValue("Cliente: " + cliente.getNombre());
        HSSFCell QuintaLineaDE = QuintaFila.createCell(3);
        QuintaLineaDE.setCellStyle(StyleQuintaLinea);
        QuintaLineaDE.setCellType(CellType.STRING);
        QuintaLineaDE.setCellValue("Cif: " + cliente.getCif());
        CellStyle change = workbook.createCellStyle();
        change.cloneStyleFrom(StyleQuintaLinea);
        change.setAlignment(HorizontalAlignment.RIGHT);
        HSSFCell QuintaLineaFG = QuintaFila.createCell(5);
        QuintaLineaFG.setCellStyle(change);
        QuintaLineaFG.setCellType(CellType.STRING);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String date = format1.format(pedido.getFechaCierre().getTime());
        QuintaLineaFG.setCellValue("Fecha: " + date);
        return StyleQuintaLinea;
    }

    private CellStyle terceraFila(HSSFSheet thisSheet, final HSSFWorkbook workbook) {
        //Tecera Fila
        HSSFRow TeceraFila = thisSheet.createRow(2);
        CellStyle StyleTeceraLinea = workbook.createCellStyle();
        HSSFFont fontTecera = workbook.createFont();
        fontTecera.setFontName("Arial");
        fontTecera.setFontHeightInPoints((short) 10);
        fontTecera.setBold(true);
        fontTecera.setColor(HSSFColor.WHITE.index);
        StyleTeceraLinea.setFont(fontTecera);
        StyleTeceraLinea.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        StyleTeceraLinea.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
        StyleTeceraLinea.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCell TeceraLinea = TeceraFila.createCell(0);
        TeceraLinea.setCellStyle(StyleTeceraLinea);
        TeceraLinea.setCellType(CellType.STRING);
        TeceraLinea.setCellValue("Identificacion del cliente");
        return StyleTeceraLinea;
    }

    private void primeraFila(HSSFSheet thisSheet, final HSSFWorkbook workbook, Pedido pedido) {
        HSSFRow PrimeraFila = thisSheet.createRow(0);
        CellStyle StylePrimeraLinea = workbook.createCellStyle();
        HSSFFont fontPrimera = workbook.createFont();
        fontPrimera.setFontName("Arial");
        fontPrimera.setFontHeightInPoints((short) 12);
        fontPrimera.setBold(true);
        fontPrimera.setColor(HSSFColor.WHITE.index);
        StylePrimeraLinea.setFont(fontPrimera);
        StylePrimeraLinea.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        StylePrimeraLinea.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
        StylePrimeraLinea.setFillBackgroundColor(HSSFColor.GREY_80_PERCENT.index);
        StylePrimeraLinea.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCell PrimeraLinea = PrimeraFila.createCell(0);
        PrimeraLinea.setCellStyle(StylePrimeraLinea);
        PrimeraLinea.setCellType(CellType.STRING);
        PrimeraLinea.setCellValue("ELECTROSA - Hoja de pedido                       Ref. Pedido: " + pedido.getCodigo());
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(VerPedido.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private void crearLineaPedido(HSSFSheet thisSheet, HSSFWorkbook workbook, Linea linea, int lineaActual) {
        HSSFRow doceavaFila = thisSheet.createRow(lineaActual);
        CellStyle styleOnceavaFila = workbook.createCellStyle();
        styleOnceavaFila.setAlignment(HorizontalAlignment.CENTER);
        styleOnceavaFila.setFillForegroundColor(HSSFColor.WHITE.index);
        styleOnceavaFila.setFillBackgroundColor(HSSFColor.WHITE.index);
        styleOnceavaFila.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont fontOnceava = workbook.createFont();
        fontOnceava.setFontName("Arial");
        fontOnceava.setFontHeightInPoints((short) 10);
        fontOnceava.setBold(true);
        fontOnceava.setColor(HSSFColor.BLACK.index);
        styleOnceavaFila.setFont(fontOnceava);

        HSSFCell doceavaCellA = doceavaFila.createCell(0);
        doceavaCellA.setCellStyle(styleOnceavaFila);
        doceavaCellA.setCellType(CellType.STRING);
        doceavaCellA.setCellValue(linea.getCantidad());

        HSSFCell doceavaCellBC = doceavaFila.createCell(1);
        doceavaCellBC.setCellStyle(styleOnceavaFila);
        doceavaCellBC.setCellType(CellType.STRING);
        doceavaCellBC.setCellValue(linea.getArticulo().getNombre());

        HSSFCell doceavaCellD = doceavaFila.createCell(3);
        doceavaCellD.setCellStyle(styleOnceavaFila);
        doceavaCellD.setCellType(CellType.STRING);
        doceavaCellD.setCellValue(linea.getArticulo().getPvp());

        HSSFCell doceavaCellE = doceavaFila.createCell(4);
        doceavaCellE.setCellStyle(styleOnceavaFila);
        doceavaCellE.setCellType(CellType.STRING);
        doceavaCellE.setCellValue(linea.getPrecioReal());

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String dateDeseada = format1.format(linea.getFechaEntregaDeseada().getTime());

        HSSFCell doceavaCellF = doceavaFila.createCell(5);
        doceavaCellF.setCellStyle(styleOnceavaFila);
        doceavaCellF.setCellType(CellType.STRING);
        doceavaCellF.setCellValue(dateDeseada);

        String datePrevista = "S/D";
        if (linea.getFechaEntregaPrevista() != null) {
            datePrevista = format1.format(linea.getFechaEntregaPrevista().getTime());
        }

        HSSFCell doceavaCellG = doceavaFila.createCell(6);
        doceavaCellG.setCellStyle(styleOnceavaFila);
        doceavaCellG.setCellType(CellType.STRING);
        doceavaCellG.setCellValue(datePrevista);

    }

    private void decimoSeptimaLinea(HSSFSheet thisSheet, HSSFWorkbook workbook, int lineaActual) {
        CellRangeAddress MergeDecimoSeptimaFilaAE = new CellRangeAddress(lineaActual, lineaActual, 0, 4);
        CellRangeAddress MergeDecimoSeptimaFilaFG = new CellRangeAddress(lineaActual, lineaActual, 5, 6);
        thisSheet.addMergedRegion(MergeDecimoSeptimaFilaAE);
        thisSheet.addMergedRegion(MergeDecimoSeptimaFilaFG);
        HSSFRow TeceraFila = thisSheet.createRow(lineaActual);
        CellStyle StyleTeceraLinea = workbook.createCellStyle();
        StyleTeceraLinea.setAlignment(HorizontalAlignment.RIGHT);
        HSSFFont fontTecera = workbook.createFont();
        fontTecera.setFontName("Arial");
        fontTecera.setFontHeightInPoints((short) 10);
        fontTecera.setBold(true);
        fontTecera.setColor(HSSFColor.WHITE.index);
        StyleTeceraLinea.setFont(fontTecera);
        StyleTeceraLinea.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        StyleTeceraLinea.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
        StyleTeceraLinea.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCell TeceraLinea = TeceraFila.createCell(0);
        TeceraLinea.setCellStyle(StyleTeceraLinea);
        TeceraLinea.setCellType(CellType.STRING);
        TeceraLinea.setCellValue("TOTAL:");

        HSSFCell precioTotal = TeceraFila.createCell(5);
        precioTotal.setCellStyle(StyleTeceraLinea);
        precioTotal.setCellType(CellType.FORMULA);
        String strFormula = "SUM(E14:E" + lineaActual + ")";
        precioTotal.setCellFormula(strFormula);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.evaluateInCell(precioTotal);
    }

    private void ultimaFila(HSSFSheet thisSheet, HSSFWorkbook workbook, int i) {
        CellRangeAddress MergeUltima = new CellRangeAddress(i, i, 0, 6);
        thisSheet.addMergedRegion(MergeUltima);
        HSSFRow TeceraFila = thisSheet.createRow(i);
        CellStyle StyleTeceraLinea = workbook.createCellStyle();
        StyleTeceraLinea.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont fontTecera = workbook.createFont();
        fontTecera.setFontName("Arial");
        fontTecera.setFontHeightInPoints((short) 8);
        fontTecera.setBold(true);
        fontTecera.setColor(HSSFColor.BLACK.index);
        StyleTeceraLinea.setFont(fontTecera);
        StyleTeceraLinea.setFillForegroundColor(HSSFColor.WHITE.index);
        StyleTeceraLinea.setFillBackgroundColor(HSSFColor.WHITE.index);
        StyleTeceraLinea.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFCell TeceraLinea = TeceraFila.createCell(0);
        TeceraLinea.setCellStyle(StyleTeceraLinea);
        TeceraLinea.setCellType(CellType.STRING);
        TeceraLinea.setCellValue("* S.D.: sin disponibilidad. Recibirá una notificación de entrega en el momento en que podamos atender su petición.");

    }
}
