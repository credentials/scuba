/*
 * $Id: HexViewer.java,v 1.1 2004/05/04 12:25:04 martijno Exp $
 */

package net.sourceforge.scuba.swing;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.sourceforge.scuba.util.Hex;

public class HexViewer extends JTable
{
    private static final long serialVersionUID = 3228256370444270294L;

    private static final Font FONT = new Font("Monospaced",Font.PLAIN,10);
    private static final int COLUMNS = 16;
    private static final int CHAR_WIDTH = 12;

    DefaultTableColumnModel columnModel;
    HexViewerDataModel dataModel;

    public HexViewer(byte[] data) {
        super();
        setupModels(data);
    }

    public HexViewer(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int offset = 0;
            while (true) {
                int count = in.read(buffer);
                if (count < 0) { break; }
                out.write(buffer, offset, count);
                offset += count;
            }
            setupModels(out.toByteArray());
    }
    
    private void setupModels(byte[] data) {
        columnModel = new DefaultTableColumnModel();
        dataModel = new HexViewerDataModel(data);
        setColumnModel(columnModel);
        setModel(dataModel);
        TableColumn column = columnModel.getColumn(COLUMNS + 1);
        column.setPreferredWidth(COLUMNS * CHAR_WIDTH);
        for (int i = COLUMNS; i > 0; i--) {
            column = columnModel.getColumn(i);
            column.setPreferredWidth(2 * CHAR_WIDTH);
        }
        column = columnModel.getColumn(0);
        column.setPreferredWidth(8 * CHAR_WIDTH);
    }

    public Font getFont() {
        return FONT;
    }

    class HexViewerDataModel extends DefaultTableModel
    {
        private static final long serialVersionUID = -788057097223004657L;

        byte[][] rows;

        public HexViewerDataModel(byte[] data) {
            super();
            rows = Hex.split(data,COLUMNS);
        }

        public int getRowCount() {
            return rows == null ? 0 : rows.length;
        }

        public int getColumnCount() {
            return 1 + COLUMNS + 1;
        }

        public String getColumnName(int col) {
            switch (col) {
            case 0:
                return "Index";
            case COLUMNS + 1:
                return "ASCII";
            default:
                return Hex.byteToHexString((byte)(col - 1));
            }
        }

        public Object getValueAt(int row, int col) {
            switch (col) {
            case 0:
                return Hex.intToHexString(COLUMNS * row);
            case COLUMNS + 1:
                return Hex.bytesToASCIIString(rows[row]);
            default:
                if (row < 0 || row >= rows.length) {
                    return null;
                } else if ((col - 1) < 0 || (col - 1) >= rows[row].length) {
                    return null;
                } else {
                    return Hex.byteToHexString(rows[row][col - 1]);
                }
            }
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}

